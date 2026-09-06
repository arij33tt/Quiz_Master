# QuizMaster Frontend

Plain HTML, CSS and JavaScript frontend aligned with the current Quiz_Master repository.

## Run

Serve this directory with any static HTTP server. Do not open `index.html` directly if your browser blocks cross-origin requests.

Update `js/config.js` with the actual AdminServ/UserServ ports.

## Current backend alignment

- User registration: `POST /user/register` with `{ userID, password }`.
- User login: Spring Security form login at `POST /login` on UserServ.
- User dashboard: `POST /user/dashboard`.
- Start attempt: `POST /start/{quizID}`. Current backend returns a raw Long attempt id.
- Question progression/submission: `POST /quiz/{attemptID}`.
- Heartbeat: current backend is `GET /quiz/{attemptID}/heartbeat`, every 30 seconds.
- Admin create quiz: `POST /admin/quiz`.
- Admin add question: `POST /admin/{topicId}`.
- Admin dashboard: `POST /admin/dashboard`.
- Admin attempts: `POST /admin/{quizID}`.
- Admin attempt questions: `POST /admin/{attemptID}`.

## Important backend notes

The current UserServ security configuration protects `/user/**`, which also currently protects `/user/register`. Registration therefore needs `/user/register` permitted anonymously before the registration screen can work.

The current repository does not expose an admin Spring Security login configuration, so the admin login UI is ready but its `/login` call will only work once AdminServ authentication is configured.

The frontend uses `credentials: include` for session-based Spring Security. Cross-origin deployment therefore requires appropriate CORS configuration on the services.
