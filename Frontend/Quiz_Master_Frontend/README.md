# QuizMaster Frontend

Plain HTML + CSS + JavaScript frontend for the Quiz_Master Spring services.

## Run

The simplest development setup is to serve this directory with any static HTTP server. Do not open `index.html` directly with `file://`, because browser CORS/fetch behavior is restrictive there.

Examples:

```bash
cd Frontend
python -m http.server 5500
```

Then open `http://localhost:5500`.

## Backend URLs

Edit `js/config.js`:

```js
window.QUIZ_CONFIG = {
  adminBaseUrl: 'http://localhost:8080',
  userBaseUrl: 'http://localhost:8081',
  heartbeatMethod: 'GET',
  heartbeatIntervalMs: 30000
};
```

Change the ports to match your Spring services.

## Important backend alignment

The frontend was written against the actual repository where it differs from the draft API description:

- Admin question creation is currently `POST /admin/{topicId}`.
- `POST /start/{quizID}` currently returns a raw `Long`, not `AttemptDTO`.
- User `QuestionDTO` currently contains `seed`, `selectedAns`, and `seq`.
- User heartbeat is currently `GET /quiz/{attemptID}/heartbeat`.
- The current backend only exposes the two Admin creation endpoints and the User attempt/heartbeat endpoints in its controllers. Dashboard/history endpoints described in the requirements are not yet implemented, so their UI screens are prepared but will show an API error until those endpoints exist.

## Assessment behavior

The frontend reconstructs the option order from the question seed. If the permutation is `[2,3,1,4]`, the visible order is option2, option3, option1, option4. Clicking visible position 3 therefore submits `selectedAns: 1`.

The same permutation logic is used for review screens, without displaying the correct answer.

The heartbeat starts with the assessment and repeats every 30 seconds. It stops when the assessment ends.
