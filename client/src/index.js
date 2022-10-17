import React from 'react';
import ReactDOM from 'react-dom/client';
// styles
import './styles/reset.css';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import { ThemeProvider } from 'styled-components';
import theme from './styles/theme/theme';

import App from './App';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <ThemeProvider theme={theme}>
      <App />
    </ThemeProvider>
  </React.StrictMode>,
);
