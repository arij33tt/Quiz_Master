// Change these two values to the ports where your Spring services run.
// Keep all endpoint paths in api.js so the UI does not know service URLs.
window.QUIZ_CONFIG = {
  adminBaseUrl: 'http://localhost:8080',
  userBaseUrl: 'http://localhost:8081',
  heartbeatMethod: 'GET',
  heartbeatIntervalMs: 30000
};
