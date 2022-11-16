const { createProxyMiddleware } = require('http-proxy-middleware');

// eslint-disable-next-line func-names
module.exports = (app) => {
  app.use(
    '/chat',
    createProxyMiddleware({
      target: 'http://3.38.9.104:8080/:splat',
      changeOrigin: true,
    }),
  );
  app.use(
    '/api',
    createProxyMiddleware({
      target: 'http://3.38.9.104:8081/:splat',
      changeOrigin: true,
    }),
  );
};
