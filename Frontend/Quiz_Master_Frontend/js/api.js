(function () {
  const config = window.QUIZ_CONFIG;

  async function request(baseUrl, path, options = {}) {
    const response = await fetch(`${baseUrl}${path}`, {
      ...options,
      headers: {
        'Content-Type': 'application/json',
        ...(options.headers || {})
      }
    });

    const text = await response.text();
    let body = null;
    if (text) {
      try { body = JSON.parse(text); } catch { body = text; }
    }

    if (!response.ok) {
      const message = extractError(body) || `Request failed (${response.status})`;
      const error = new Error(message);
      error.status = response.status;
      error.body = body;
      throw error;
    }

    return body;
  }

  function extractError(body) {
    if (!body) return '';
    if (typeof body === 'string') return body;
    return body.message || body.error || body.detail || body.title || '';
  }

  const jsonPost = (base, path, data) => request(base, path, {
    method: 'POST',
    body: JSON.stringify(data)
  });

  window.API = {
    user: {
      dashboard: () => jsonPost(config.userBaseUrl, '/user/dashboard', null),
      startAttempt: (quizId) => jsonPost(config.userBaseUrl, `/start/${encodeURIComponent(quizId)}`, quizId),
      submitQuestion: (attemptId, question) => jsonPost(config.userBaseUrl, `/quiz/${encodeURIComponent(attemptId)}`, question),
      heartbeat: (attemptId) => request(config.userBaseUrl, `/quiz/${encodeURIComponent(attemptId)}/heartbeat`, {
        method: config.heartbeatMethod
      }),
      history: (attemptId) => jsonPost(config.userBaseUrl, `/user/${encodeURIComponent(attemptId)}`, attemptId)
    },
    admin: {
      dashboard: () => jsonPost(config.adminBaseUrl, '/admin/dashboard', null),
      createQuiz: (quiz) => jsonPost(config.adminBaseUrl, '/admin/quiz', quiz),
      addQuestion: (topicId, question) => jsonPost(config.adminBaseUrl, `/admin/${encodeURIComponent(topicId)}`, question),
      attempts: (quizId) => jsonPost(config.adminBaseUrl, `/admin/${encodeURIComponent(quizId)}`, quizId),
      attemptQuestions: (attemptId) => jsonPost(config.adminBaseUrl, `/admin/${encodeURIComponent(attemptId)}`, attemptId)
    }
  };
})();
