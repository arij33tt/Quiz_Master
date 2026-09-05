(function () {
  const app = document.getElementById('app');
  const toast = document.getElementById('toast');
  const state = {
    role: sessionStorage.getItem('role') || null,
    userQuizzes: [],
    adminQuizzes: [],
    selectedQuiz: null,
    attemptId: null,
    currentQuestion: null,
    selectedViewIndex: -1,
    permutation: null,
    timerId: null,
    heartbeatId: null,
    remainingSeconds: 0
  };

  const esc = value => String(value ?? '').replace(/[&<>'"]/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;',"'":'&#39;','"':'&quot;'}[c]));
  const path = () => location.hash.replace(/^#/, '') || '/';
  const go = value => { location.hash = value; };
  const showToast = message => {
    toast.textContent = message;
    toast.classList.add('show');
    clearTimeout(showToast.t);
    showToast.t = setTimeout(() => toast.classList.remove('show'), 3200);
  };
  const errorText = e => e?.message || 'Something went wrong.';
  const formatTime = seconds => `${String(Math.floor(seconds / 60)).padStart(2,'0')}:${String(seconds % 60).padStart(2,'0')}`;

  function shell(content, title = 'QuizMaster', action = '') {
    return `<div class="app-shell">
      <header class="topbar">
        <button class="btn btn-ghost brand" data-action="home"><span class="brand-mark">Q</span>${title}</button>
        <div class="top-actions">${action}</div>
      </header>${content}
    </div>`;
  }

  function roleChooser() {
    app.innerHTML = `<div class="page role-grid">
      <div class="hero">
        <span class="badge">QuizMaster</span>
        <h1>Welcome to QuizMaster</h1>
        <p class="muted">Choose how you want to enter the system.</p>
      </div>
      <div class="grid grid-2">
        <button class="card clickable role-card" data-role-login="user">
          <div class="icon">◉</div>
          <h2>User</h2>
          <p class="muted">Sign in as a candidate to browse quizzes, take assessments and review your attempts.</p>
          <span class="btn btn-primary">User login</span>
        </button>
        <button class="card clickable role-card" data-role-login="admin">
          <div class="icon">▣</div>
          <h2>Admin</h2>
          <p class="muted">Sign in as an administrator to create quizzes, add questions and inspect attempts.</p>
          <span class="btn btn-primary">Admin login</span>
        </button>
      </div>
    </div>`;
  }

  function loginPage(role) {
    const isAdmin = role === 'admin';
    const title = isAdmin ? 'Admin login' : 'User login';
    const description = isAdmin
      ? 'Sign in to manage quizzes and candidate attempts.'
      : 'Sign in to access your quizzes and assessment history.';

    app.innerHTML = `<div class="login-page">
      <div class="login-card">
        <button class="btn btn-ghost login-back" data-action="home">← Back</button>
        <div class="login-brand"><span class="brand-mark">Q</span><span>QuizMaster</span></div>
        <div class="hero login-hero">
          <span class="badge">${isAdmin ? 'Administrator' : 'Candidate'}</span>
          <h1>${title}</h1>
          <p class="muted">${description}</p>
        </div>
        <form id="login-form" class="form">
          <input type="hidden" name="role" value="${role}">
          <div class="field">
            <label for="login-username">${isAdmin ? 'Username' : 'User ID'}</label>
            <input id="login-username" name="username" type="text" autocomplete="username" placeholder="Enter ${isAdmin ? 'admin username' : 'user ID'}" required autofocus>
          </div>
          <div class="field">
            <label for="login-password">Password</label>
            <input id="login-password" name="password" type="password" autocomplete="current-password" placeholder="Enter password" required>
          </div>
          <button class="btn btn-primary btn-block" type="submit">Sign in</button>
          ${!isAdmin ? `<button class="btn btn-secondary btn-block" type="button" data-action="register">Create new user</button>` : ``}
          <div id="login-status" class="login-status"></div>
        </form>
      </div>
    </div>`;
  }

  function registerPage() {
    app.innerHTML = `<div class="login-page">
      <div class="login-card">
        <button class="btn btn-ghost login-back" data-action="user-login">← Back</button>
        <div class="login-brand"><span class="brand-mark">Q</span><span>QuizMaster</span></div>
        <div class="hero login-hero">
          <span class="badge">New candidate</span>
          <h1>Create account</h1>
          <p class="muted">Create a user account to take quizzes and keep your attempts.</p>
        </div>
        <form id="register-form" class="form">
          <div class="field"><label for="register-userID">User ID</label><input id="register-userID" name="userID" type="text" autocomplete="username" required autofocus></div>
          <div class="field"><label for="register-password">Password</label><input id="register-password" name="password" type="password" autocomplete="new-password" required></div>
          <div class="field"><label for="register-confirm">Confirm password</label><input id="register-confirm" name="confirm" type="password" autocomplete="new-password" required></div>
          <button class="btn btn-primary btn-block" type="submit">Create account</button>
          <div id="register-status" class="login-status"></div>
        </form>
      </div>
    </div>`;
  }

  async function userDashboard() {
    app.innerHTML = shell(`<main class="page"><div class="hero"><span class="badge">Candidate</span><h1>Available quizzes</h1><p class="muted">Choose a quiz to see its rules and start an assessment.</p></div><div id="content" class="loading">Loading quizzes…</div></main>`, 'QuizMaster', '<button class="btn btn-secondary" data-action="user-history">My attempts</button><button class="btn btn-ghost" data-action="logout">Exit</button>');
    try {
      state.userQuizzes = await API.user.dashboard();
      renderUserQuizzes();
    } catch (e) {
      document.getElementById('content').innerHTML = `<div class="error-box">${esc(errorText(e))}<br><small>The current GitHub backend does not yet expose <code>POST /user/dashboard</code>, so this screen is ready but cannot load live data yet.</small></div>`;
    }
  }

  function renderUserQuizzes() {
    const content = document.getElementById('content');
    const quizzes = Array.isArray(state.userQuizzes) ? state.userQuizzes : [];
    if (!quizzes.length) { content.innerHTML = '<div class="card empty">No quizzes are currently available.</div>'; return; }
    content.innerHTML = `<div class="grid grid-2">${quizzes.map((q, i) => {
      const id = q.quizID ?? q.quizId ?? q.id;
      return `<article class="card quiz-card">
        <div class="quiz-head"><div><span class="badge">Quiz #${esc(id)}</span><h3>${esc(q.topicId || q.topic || 'Untitled topic')}</h3></div><strong>${esc(q.attemptsLeft ?? q.attempts ?? '—')} left</strong></div>
        <div class="stat-row"><div class="stat"><strong>${esc(q.numberOfQuestion ?? '—')}</strong><span>Questions</span></div><div class="stat"><strong>${esc(q.timeLimit ?? '—')} min</strong><span>Time</span></div><div class="stat"><strong>+${esc(q.correct ?? 1)}</strong><span>Correct</span></div><div class="stat"><strong>${esc(q.wrong ?? 0)}</strong><span>Wrong</span></div></div>
        <button class="btn btn-primary btn-block" data-quiz-index="${i}">Open quiz</button>
      </article>`;
    }).join('')}</div>`;
  }

  function quizDetails() {
    const q = state.selectedQuiz;
    const attemptsLeft = q?.attemptsLeft ?? q?.attempts ?? '—';
    app.innerHTML = shell(`<main class="page narrow"><div class="hero"><span class="badge">Quiz #${esc(q.quizID ?? q.quizId ?? '')}</span><h1>${esc(q.topicId || q.topic || 'Quiz')}</h1><p class="muted">Read the assessment rules before starting.</p></div>
      <section class="card">
        <div class="grid grid-2"><div><div class="muted">Attempts left</div><h2>${esc(attemptsLeft)}</h2></div><div><div class="muted">Time limit</div><h2>${esc(q.timeLimit ?? '—')} min</h2></div></div>
        <div class="stat-row"><div class="stat"><strong>+${esc(q.correct ?? 1)}</strong><span>Correct answer</span></div><div class="stat"><strong>${esc(q.wrong ?? 0)}</strong><span>Wrong answer</span></div><div class="stat"><strong>${esc(q.notAttended ?? 0)}</strong><span>Not attended</span></div><div class="stat"><strong>${esc(q.numberOfQuestion ?? '—')}</strong><span>Total questions</span></div></div>
        <div style="margin-top:20px"><button class="btn btn-primary btn-block" data-action="start-quiz">Start assessment</button></div>
      </section>
      <p class="muted" style="font-size:13px;margin-top:14px">Starting is delegated to the backend. The server remains authoritative for attempt availability and concurrency.</p>
    </main>`, 'QuizMaster', '<button class="btn btn-ghost" data-action="user-dashboard">Back</button>');
  }

  async function startQuiz() {
    const q = state.selectedQuiz;
    const quizId = q.quizID ?? q.quizId ?? q.id;
    try {
      const response = await API.user.startAttempt(quizId);
      // Current repository returns a raw Long, not AttemptDTO.
      const attemptId = typeof response === 'number' ? response : Number(response?.attemptID ?? response);
      if (!Number.isFinite(attemptId) || attemptId === -1) {
        showToast('No new attempt is available.');
        return;
      }
      state.attemptId = attemptId;
      state.remainingSeconds = Number(q.timeLimit || 0) * 60;
      await loadFirstQuestion();
    } catch (e) {
      showToast(errorText(e));
    }
  }

  async function loadFirstQuestion() {
    // The current backend obtains the first question internally when the
    // first request to /quiz/{attemptId} is made. It still expects a body,
    // so send a neutral DTO rather than inventing an answer.
    try {
      const question = await API.user.submitQuestion(state.attemptId, {
        questionID: null, question: '', option1: '', option2: '', option3: '', option4: '', isMCQ: true, selectedAns: -1, seed: 0, seq: 0
      });
      if (!question) {
    finishAssessment();
    return;
}

console.log("QUESTION FROM SERVER:", question);
console.log("SEED:", question.seed, "TYPE:", typeof question.seed);

question.questionID = Number(question.questionID);
question.seed = Number(question.seed);
question.seq = Number(question.seq);

if (!Number.isInteger(question.seed)) {
    showToast("Invalid seed received from server.");
    return;
}

beginAssessment(question);
    } catch (e) {
      showToast(errorText(e));
    }
  }

 function beginAssessment(question) {
    clearAssessmentTimers();

    question.questionID = Number(question.questionID);
    question.seed = Number(question.seed);
    question.seq = Number(question.seq);

    // Backend seed is 1..24.
    // Frontend permutation accepts 0..23 internally.
    if (!Number.isInteger(question.seed) ||
        question.seed < 1 ||
        question.seed > 24) {
        showToast(`Invalid seed received: ${question.seed}`);
        return;
    }

    state.currentQuestion = question;
    state.selectedViewIndex = -1;

    state.permutation =
        Permutation.getPermutation(question.seed);

    startClock();
    startHeartbeat();
    renderAssessment();
}

  function renderAssessment() {
    const q = state.currentQuestion;
    const viewOptions = Permutation.toViewOptions(q);
    app.innerHTML = `<div class="assessment-shell"><div class="assessment-top"><div><strong>Quiz #${esc(state.selectedQuiz?.quizID ?? state.selectedQuiz?.quizId ?? '')}</strong><div class="muted" style="font-size:12px">Assessment in progress</div></div><div id="timer" class="timer">${formatTime(state.remainingSeconds)}</div></div>
      <main class="assessment-main"><div class="question-meta"><span>Question</span><span>Single choice</span></div><section class="question-box"><h1 class="question-text">${esc(q.question)}</h1><div class="options">${viewOptions.map((o, i) => `<button class="option ${state.selectedViewIndex === i ? 'selected' : ''}" data-option-index="${i}"><span class="option-key">${String.fromCharCode(65+i)}</span><span>${esc(o.text)}</span></button>`).join('')}</div><div class="assessment-actions"><button class="btn btn-primary" data-action="next-question">${state.remainingSeconds <= 0 ? 'Submit' : 'Next'}</button></div></section></main></div>`;
  }

  function startClock() {
    state.timerId = setInterval(() => {
      state.remainingSeconds = Math.max(0, state.remainingSeconds - 1);
      const el = document.getElementById('timer');
      if (el) {
        el.textContent = formatTime(state.remainingSeconds);
        el.className = `timer ${state.remainingSeconds <= 30 ? 'danger' : state.remainingSeconds <= 60 ? 'warning' : ''}`;
      }
      if (state.remainingSeconds === 0) {
        showToast('Time is up. Submitting the current question.');
        submitCurrentQuestion(true);
      }
    }, 1000);
  }

  function startHeartbeat() {
    const send = () => API.user.heartbeat(state.attemptId).catch(() => {});
    send();
    state.heartbeatId = setInterval(send, window.QUIZ_CONFIG.heartbeatIntervalMs);
  }

  function clearAssessmentTimers() {
    if (state.timerId) clearInterval(state.timerId);
    if (state.heartbeatId) clearInterval(state.heartbeatId);
    state.timerId = null;
    state.heartbeatId = null;
  }

  async function submitCurrentQuestion(autoSubmit = false) {
    const q = state.currentQuestion;
    if (!q || state.attemptId == null) return;
    const selectedAns = state.selectedViewIndex >= 0 ? Permutation.actualAnswerFromViewIndex(state.selectedViewIndex, state.permutation) : -1;
    const solved = { ...q, selectedAns: Number(selectedAns),
    seed: Number(q.seed),
    seq: Number(q.seq) };
    try {
      const next = await API.user.submitQuestion(state.attemptId, solved);
      if (!next) { finishAssessment(); return; }
      if (autoSubmit) { finishAssessment(); return; }
      beginAssessment(next);
    } catch (e) {
      showToast(errorText(e));
    }
  }

  function finishAssessment() {
    clearAssessmentTimers();
    app.innerHTML = shell(`<main class="page narrow"><section class="card" style="text-align:center;padding:55px 30px"><span class="badge success">Assessment complete</span><h1 style="margin-top:15px">You have completed the quiz.</h1><p class="muted">Your attempt has been handed back to the server for final scoring.</p><div style="margin-top:25px"><button class="btn btn-primary" data-action="user-dashboard">Back to quizzes</button></div></section></main>`, 'QuizMaster');
  }
  
async function userHistory() {

    app.innerHTML = shell(
        `<main class="page">
            <div class="hero">
                <span class="badge">History</span>
                <h1>Previous attempts</h1>
                <p class="muted">Your previous quiz attempts.</p>
            </div>

            <div id="content" class="loading">
                Loading attempts…
            </div>
        </main>`,
        'QuizMaster',
        '<button class="btn btn-ghost" data-action="user-dashboard">Back</button>'
    );

    try {

        const attempts = await API.user.attempts();

        const arr = Array.isArray(attempts) ? attempts : [];

        if (!arr.length) {
            document.getElementById('content').innerHTML =
                '<div class="card empty">No previous attempts.</div>';
            return;
        }

        document.getElementById('content').innerHTML = `
            <div class="card">
                ${arr.map(a => `
                    <div class="attempt-item">

                        <div>
                            <strong>
                                Attempt #${esc(a.attemptID)}
                            </strong>

                            <div class="muted">
                                ${a.completedQuiz
                                    ? 'Completed'
                                    : 'In progress'}
                            </div>
                        </div>

                        <div>
                            <strong>
                                Score: ${esc(a.score ?? 0)}
                            </strong>
                        </div>

                        <button
                            class="btn btn-secondary"
                            data-user-attempt-id="${esc(a.attemptID)}">
                            View paper
                        </button>

                    </div>
                `).join('')}
            </div>
        `;

    } catch (e) {

        document.getElementById('content').innerHTML =
            `<div class="error-box">${esc(errorText(e))}</div>`;
    }
}

  function renderHistoryQuestions(questions, seed) {
    const arr = Array.isArray(questions) ? questions : [];
    return arr.map((q, index) => {
      const localSeed = q.seed ?? seed ?? 0;
      let view = [];
      try { view = Permutation.toViewOptions({ ...q, seed: localSeed }); } catch { view = [1,2,3,4].map(n => ({number:n,text:q[`option${n}`]})); }
      const selected = Number(q.selectedAns ?? q.answer ?? -1);
      return `<article class="card question-review"><div class="muted">Question ${index + 1}</div><h3>${esc(q.question)}</h3>${view.map((o, i) => `<div class="review-option ${o.number === selected ? 'selected' : ''}">${String.fromCharCode(65+i)}. ${esc(o.text)}${o.number === selected ? ' <span class="muted">(your answer)</span>' : ''}</div>`).join('')}</article>`;
    }).join('');
  }

  async function adminDashboard() {
    app.innerHTML = shell(`<main class="page"><div class="hero"><span class="badge">Administrator</span><h1>Admin dashboard</h1><p class="muted">Manage quizzes and inspect candidate attempts.</p></div><div class="grid grid-2"><button class="card clickable role-card" data-action="create-quiz"><h2>Create quiz</h2><p class="muted">Configure questions, time limit, marking and allowed attempts.</p></button><button class="card clickable role-card" data-action="add-question"><h2>Add question</h2><p class="muted">Add an MCQ to a topic with its correct answer.</p></button></div><div id="quiz-list" class="loading" style="margin-top:18px">Loading recent quizzes…</div></main>`, 'QuizMaster', '<button class="btn btn-ghost" data-action="logout">Exit</button>');
    try {
      state.adminQuizzes = await API.admin.dashboard();
      renderAdminQuizzes();
    } catch (e) {
      document.getElementById('quiz-list').innerHTML = `<div class="error-box">${esc(errorText(e))}<br><small>The current repository does not yet contain <code>POST /admin/dashboard</code>. The management UI is ready for the endpoint.</small></div>`;
    }
  }

  function renderAdminQuizzes() {
    const el = document.getElementById('quiz-list');
    const quizzes = Array.isArray(state.adminQuizzes) ? state.adminQuizzes : [];
    if (!quizzes.length) { el.innerHTML = '<div class="card empty">No quizzes found.</div>'; return; }
    el.innerHTML = `<div class="grid grid-2">${quizzes.map((q,i) => `<article class="card quiz-card"><div class="quiz-head"><div><span class="badge">#${esc(q.quizID ?? q.quizId ?? '')}</span><h3>${esc(q.topicId || q.topic || 'Quiz')}</h3></div><span class="muted">${esc(q.numberOfQuestion ?? '—')} Q</span></div><div class="stat-row"><div class="stat"><strong>${esc(q.timeLimit ?? '—')}m</strong><span>Time</span></div><div class="stat"><strong>+${esc(q.correct ?? 1)}</strong><span>Correct</span></div><div class="stat"><strong>${esc(q.attempts ?? '—')}</strong><span>Attempts</span></div></div><button class="btn btn-secondary btn-block" data-admin-quiz-index="${i}">View attempts</button></article>`).join('')}</div>`;
  }

  function createQuizPage() {
    app.innerHTML = shell(`<main class="page narrow"><div class="hero"><span class="badge">Admin</span><h1>Create quiz</h1><p class="muted">This form maps directly to the current AdminServ QuizDTO.</p></div><form id="quiz-form" class="card form"><div class="field"><label>Topic ID</label><input name="topicId" required /></div><div class="grid grid-2"><div class="field"><label>Number of questions</label><input type="number" min="1" name="numberOfQuestion" required /></div><div class="field"><label>Time limit (minutes)</label><input type="number" min="1" name="timeLimit" required /></div></div><div class="grid grid-2"><div class="field"><label>Allowed attempts</label><input type="number" min="1" name="attempts" value="1" required /></div><div class="field"><label>Correct marking</label><input type="number" name="correct" value="1" required /></div></div><div class="grid grid-2"><div class="field"><label>Wrong marking</label><input type="number" name="wrong" value="0" required /></div><div class="field"><label>Not attended marking</label><input type="number" name="notAttended" value="0" required /></div></div><button class="btn btn-primary" type="submit">Create quiz</button><div id="form-status"></div></form></main>`, 'QuizMaster', '<button class="btn btn-ghost" data-action="admin-dashboard">Back</button>');
  }

  function addQuestionPage() {
    app.innerHTML = shell(`<main class="page narrow"><div class="hero"><span class="badge">Admin</span><h1>Add question</h1><p class="muted">The current AdminServ endpoint is <code>POST /admin/{topicId}</code>.</p></div><form id="question-form" class="card form"><div class="field"><label>Topic ID</label><input name="topicId" required /></div><div class="field"><label>Question</label><textarea name="question" required></textarea></div>${[1,2,3,4].map(n => `<div class="field"><label>Option ${n}</label><input name="option${n}" required /></div>`).join('')}<div class="field"><label>Correct option(s)</label><input name="correct" placeholder="Example: option1" required /></div><button class="btn btn-primary" type="submit">Add question</button><div id="form-status"></div></form></main>`, 'QuizMaster', '<button class="btn btn-ghost" data-action="admin-dashboard">Back</button>');
  }

  async function submitQuizForm(form) {
    const data = Object.fromEntries(new FormData(form).entries());
    ['attempts','numberOfQuestion','timeLimit','correct','wrong','notAttended'].forEach(k => data[k] = Number(data[k]));
    try { await API.admin.createQuiz(data); document.getElementById('form-status').innerHTML = '<div class="badge success">Quiz created.</div>'; form.reset(); }
    catch (e) { document.getElementById('form-status').innerHTML = `<div class="error-box">${esc(errorText(e))}</div>`; }
  }

  async function submitQuestionForm(form) {
    const data = Object.fromEntries(new FormData(form).entries());
    const topicId = data.topicId;
    delete data.topicId;
    data.isMCQ = true;
    data.correct = Number(data.correct);
    try { await API.admin.addQuestion(topicId, data); document.getElementById('form-status').innerHTML = '<div class="badge success">Question added.</div>'; form.reset(); }
    catch (e) { document.getElementById('form-status').innerHTML = `<div class="error-box">${esc(errorText(e))}</div>`; }
  }

  async function adminAttempts(quiz) {
    const quizId = quiz.quizID ?? quiz.quizId ?? quiz.id;
    app.innerHTML = shell(`<main class="page narrow"><div class="hero"><span class="badge">Quiz #${esc(quizId)}</span><h1>Candidate attempts</h1><p class="muted">Recent attempts returned by AdminServ.</p></div><div id="content" class="loading">Loading attempts…</div></main>`, 'QuizMaster', '<button class="btn btn-ghost" data-action="admin-dashboard">Back</button>');
    try {
      const attempts = await API.admin.attempts(quizId);
      const arr = Array.isArray(attempts) ? attempts : [];
      document.getElementById('content').innerHTML = arr.length ? `<div class="card">${arr.map(a => `<div class="attempt-item"><div><strong>Attempt #${esc(a.attemptID)}</strong><div class="muted">${a.completedQuiz ? 'Completed' : 'In progress'}</div></div><div><strong>${esc(a.score)}</strong></div><button class="btn btn-secondary" data-attempt-id="${esc(a.attemptID)}">View paper</button></div>`).join('')}</div>` : '<div class="card empty">No attempts found.</div>';
    } catch (e) { document.getElementById('content').innerHTML = `<div class="error-box">${esc(errorText(e))}</div>`; }
  }

  async function adminAttemptQuestions(attemptId) {
    app.innerHTML = shell(`<main class="page narrow"><div class="hero"><span class="badge">Attempt #${esc(attemptId)}</span><h1>Attempt paper</h1><p class="muted">Correct answers are intentionally not shown in this view.</p></div><div id="content" class="loading">Loading question paper…</div></main>`, 'QuizMaster', '<button class="btn btn-ghost" data-action="admin-dashboard">Back</button>');
    try {
      const questions = await API.admin.attemptQuestions(attemptId);
      document.getElementById('content').innerHTML = renderHistoryQuestions(questions);
    } catch (e) { document.getElementById('content').innerHTML = `<div class="error-box">${esc(errorText(e))}</div>`; }
  }

  function logout() {
    state.role = null; sessionStorage.removeItem('role'); clearAssessmentTimers(); go('/');
  }

  async function route() {
    clearAssessmentTimers();
    const p = path();
    if (p === '/') { roleChooser(); return; }
    if (p === '/login/user') { loginPage('user'); return; }
    if (p === '/login/admin') { loginPage('admin'); return; }
    if (p === '/register') { registerPage(); return; }
    if (p === '/user') { state.role = 'user'; sessionStorage.setItem('role','user'); await userDashboard(); return; }
    if (p === '/user/quiz' && state.selectedQuiz) { quizDetails(); return; }
    if (p === '/user/attempt' && state.attemptId && state.currentQuestion) { renderAssessment(); startClock(); startHeartbeat(); return; }
    if (p === '/user/history') { await userHistory(); return; }
    if (p === '/admin') { state.role = 'admin'; sessionStorage.setItem('role','admin'); await adminDashboard(); return; }
    if (p === '/admin/create') { createQuizPage(); return; }
    if (p === '/admin/question') { addQuestionPage(); return; }
    if (p === '/admin/attempts' && state.selectedQuiz) { await adminAttempts(state.selectedQuiz); return; }
    if (p === '/admin/attempt' && state.attemptId) { await adminAttemptQuestions(state.attemptId); return; }
    go('/');
  }

 document.addEventListener('click', async e => {

    const roleLogin = e.target.closest('[data-role-login]');
    if (roleLogin) {
        go(`/login/${roleLogin.dataset.roleLogin}`);
        return;
    }

    const quiz = e.target.closest('[data-quiz-index]');
    if (quiz) {
        state.selectedQuiz =
            state.userQuizzes[Number(quiz.dataset.quizIndex)];
        go('/user/quiz');
        return;
    }

    const adminQuiz = e.target.closest('[data-admin-quiz-index]');
    if (adminQuiz) {
        state.selectedQuiz =
            state.adminQuizzes[Number(adminQuiz.dataset.adminQuizIndex)];
        go('/admin/attempts');
        return;
    }

    const option = e.target.closest('[data-option-index]');
    if (option) {
        state.selectedViewIndex =
            Number(option.dataset.optionIndex);
        renderAssessment();
        return;
    }

    // ADMIN attempt
    const attempt = e.target.closest('[data-attempt-id]');
    if (attempt) {
        state.attemptId =
            Number(attempt.dataset.attemptId);
        go('/admin/attempt');
        return;
    }

    // USER history attempt
    const userAttempt =
        e.target.closest('[data-user-attempt-id]');

    if (userAttempt) {
        state.attemptId =
            Number(userAttempt.dataset.userAttemptId);
        go('/user/history/attempt');
        return;
    }

    const action =
        e.target.closest('[data-action]')?.dataset.action;

    if (!action) return;

    if (action === 'home')
        go('/');

    if (action === 'register')
        go('/register');

    if (action === 'user-login')
        go('/login/user');

    if (action === 'logout')
        logout();

    if (action === 'user-dashboard')
        go('/user');

    if (action === 'user-history')
        go('/user/history');

    if (action === 'start-quiz')
        await startQuiz();

    if (action === 'next-question')
        await submitCurrentQuestion(false);

    if (action === 'admin-dashboard')
        go('/admin');

    if (action === 'create-quiz')
        go('/admin/create');

    if (action === 'add-question')
        go('/admin/question');
});;

  document.addEventListener('submit', async e => {
    const form = e.target.closest('#login-form');
    if (form) {
      e.preventDefault();
      const role = form.querySelector('[name=role]').value;
      const username = form.querySelector('[name=username]').value.trim();
      const password = form.querySelector('[name=password]').value;
      const status = document.getElementById('login-status');
      status.innerHTML = '<div class="badge warn">Signing in…</div>';
      try {
        if (role === 'user') {
          await API.auth.userLogin(username, password);
          state.role = 'user';
          sessionStorage.setItem('role', 'user');
          go('/user');
        } else {
          await API.auth.adminLogin(username, password);
          state.role = 'admin';
          sessionStorage.setItem('role', 'admin');
          go('/admin');
        }
      } catch (err) {
        status.innerHTML = `<div class="error-box">${esc(errorText(err))}</div>`;
      }
      return;
    }
    if (e.target.id === 'register-form') {
      e.preventDefault();
      const data = Object.fromEntries(new FormData(e.target).entries());
      const status = document.getElementById('register-status');
      if (data.password !== data.confirm) { status.innerHTML = '<div class="error-box">Passwords do not match.</div>'; return; }
      status.innerHTML = '<div class="badge warn">Creating account…</div>';
      try {
        await API.auth.register(data.userID.trim(), data.password);
        status.innerHTML = '<div class="badge success">Account created. You can now sign in.</div>';
        setTimeout(() => go('/login/user'), 700);
      } catch (err) {
        status.innerHTML = `<div class="error-box">${esc(errorText(err))}</div>`;
      }
      return;
    }
    if (e.target.id === 'quiz-form') { e.preventDefault(); await submitQuizForm(e.target); }
    if (e.target.id === 'question-form') { e.preventDefault(); await submitQuestionForm(e.target); }
  });

  window.addEventListener('hashchange', route);
  route();
})();
