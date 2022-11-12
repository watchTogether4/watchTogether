const { createProxyMiddleware } = require('http-proxy-middleware');

// eslint-disable-next-line func-names
module.exports = (app) => {
  app.use(
    '/chat',
    createProxyMiddleware({
      target: 'http://localhost:8080',
      changeOrigin: true,
    }),
  );
  app.use(
    '/api',
    createProxyMiddleware({
      target: 'http://localhost:8081',
      changeOrigin: true,
    }),
  );
};
