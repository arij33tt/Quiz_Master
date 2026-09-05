(function () {

  const config = window.QUIZ_CONFIG;


  // =========================================================
  // GENERIC REQUEST
  // =========================================================

  async function request(baseUrl, path, options = {}) {

    const response = await fetch(`${baseUrl}${path}`, {
      ...options,

      credentials: options.credentials || config.credentials,

      headers: {
        'Content-Type': 'application/json',
        ...(options.headers || {})
      }
    });


    const text = await response.text();

    let body = null;

    if (text) {
      try {
        body = JSON.parse(text);
      } catch {
        body = text;
      }
    }


    if (!response.ok) {

      const message =
        extractError(body) ||
        `Request failed (${response.status})`;

      const error = new Error(message);

      error.status = response.status;
      error.body = body;

      throw error;
    }


    return body;
  }


  // =========================================================
  // ERROR HANDLING
  // =========================================================

  function extractError(body) {

    if (!body) {
      return '';
    }


    if (typeof body === 'string') {
      return body;
    }


    return (
      body.message ||
      body.error ||
      body.detail ||
      body.title ||
      ''
    );
  }


  // =========================================================
  // LOGIN
  // =========================================================

  async function login(baseUrl, username, password) {

    const body = new URLSearchParams();

    body.set('username', username);
    body.set('password', password);


    const response = await fetch(`${baseUrl}/login`, {

      method: 'POST',

      credentials: config.credentials,

      headers: {
        'Content-Type':
          'application/x-www-form-urlencoded'
      },

      body: body,

      redirect: 'follow'
    });


    if (!response.ok) {

      const text = await response.text();

      throw new Error(
        text || `Login failed (${response.status})`
      );
    }


    return true;
  }


  // =========================================================
  // POST JSON HELPER
  // =========================================================

  function jsonPost(baseUrl, path, data) {

    return request(baseUrl, path, {

      method: 'POST',

      body: JSON.stringify(data)
    });
  }


  // =========================================================
  // PUBLIC API
  // =========================================================

  window.API = {


    // =======================================================
    // AUTHENTICATION
    // =======================================================

    auth: {

      // User service
      // POST http://localhost:8081/login

      userLogin: (username, password) =>
        login(
          config.userBaseUrl,
          username,
          password
        ),


      // Admin service
      // POST http://localhost:8080/login

      adminLogin: (username, password) =>
        login(
          config.adminBaseUrl,
          username,
          password
        ),


      // User registration
      // POST http://localhost:8081/register

      register: (userID, password) =>
        jsonPost(
          config.userBaseUrl,
          '/register',
          {
            userID: userID,
            password: password
          }
        )
    },


    // =======================================================
    // USER SERVICE
    // =======================================================

    user: {

      attempts: () =>
    jsonPost(
        config.userBaseUrl,
        '/user/attempts',
        null
    ),
      // -----------------------------------------------------
      // USER DASHBOARD
      // POST /user/dashboard
      // -----------------------------------------------------

      dashboard: () =>
        jsonPost(
          config.userBaseUrl,
          '/user/dashboard',
          null
        ),


      // -----------------------------------------------------
      // START ATTEMPT
      // POST /start/{quizID}
      // -----------------------------------------------------
startAttempt: (quizId) =>
  request(
    config.userBaseUrl,
    `/start/${encodeURIComponent(quizId)}`,
    {
      method: 'POST'
    }
  ),


      // -----------------------------------------------------
      // SUBMIT CURRENT QUESTION
      // POST /quiz/{attemptID}
      // -----------------------------------------------------

      submitQuestion: (attemptId, question) =>
        jsonPost(
          config.userBaseUrl,
          `/quiz/${encodeURIComponent(attemptId)}`,
          question
        ),


      // -----------------------------------------------------
      // HEARTBEAT
      // GET /quiz/{attemptID}/heartbeat
      // -----------------------------------------------------

      heartbeat: (attemptId) =>
        request(
          config.userBaseUrl,
          `/quiz/${encodeURIComponent(attemptId)}/heartbeat`,
          {
            method: config.heartbeatMethod
          }
        ),


      // -----------------------------------------------------
      // PREVIOUS ATTEMPT HISTORY
      // POST /user/{attemptID}
      // -----------------------------------------------------

      history: (attemptId) =>
        jsonPost(
          config.userBaseUrl,
          `/user/${encodeURIComponent(attemptId)}`,
          attemptId
        )
    },


    // =======================================================
    // ADMIN SERVICE
    // =======================================================

    admin: {


      // -----------------------------------------------------
      // ADMIN DASHBOARD
      // POST /admin/dashboard
      // -----------------------------------------------------

      dashboard: () =>
        jsonPost(
          config.adminBaseUrl,
          '/admin/dashboard',
          null
        ),


      // -----------------------------------------------------
      // CREATE QUIZ
      // POST /admin/quiz
      // -----------------------------------------------------

      createQuiz: (quiz) =>
        jsonPost(
          config.adminBaseUrl,
          '/admin/quiz',
          quiz
        ),


      // -----------------------------------------------------
      // ADD QUESTION
      // POST /admin/{topicID}
      // -----------------------------------------------------

      addQuestion: (topicId, question) =>
        jsonPost(
          config.adminBaseUrl,
          `/admin/${encodeURIComponent(topicId)}`,
          question
        ),


      // -----------------------------------------------------
      // VIEW QUIZ ATTEMPTS
      //
      // POST /admin/quiz/{quizID}
      // -----------------------------------------------------

      attempts: (quizId) =>
        jsonPost(
          config.adminBaseUrl,
          `/admin/quiz/${encodeURIComponent(quizId)}`,
          quizId
        ),


      // -----------------------------------------------------
      // VIEW QUESTIONS OF AN ATTEMPT
      //
      // POST /admin/attempt/{attemptID}
      // -----------------------------------------------------

      attemptQuestions: (attemptId) =>
        jsonPost(
          config.adminBaseUrl,
          `/admin/attempt/${encodeURIComponent(attemptId)}`,
          attemptId
        )
    }
  };

})();