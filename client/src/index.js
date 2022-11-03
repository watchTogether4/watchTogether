import axios from 'axios';
import React from 'react';
import ReactDOM from 'react-dom/client';

import './styles/reset.css';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';

import { ThemeProvider } from 'styled-components';
import { Provider } from 'react-redux';
import { CookiesProvider } from 'react-cookie';
import { QueryClient, QueryClientProvider } from 'react-query';

import theme from './styles/theme/theme';
import App from './App';
import store from './store';

const root = ReactDOM.createRoot(document.getElementById('root'));

const client = new QueryClient();
axios.defaults.withCredentials = true;

root.render(
  <CookiesProvider>
    <React.StrictMode>
      <QueryClientProvider client={client}>
        <Provider store={store}>
          <ThemeProvider theme={theme}>
            <App />
          </ThemeProvider>
        </Provider>
      </QueryClientProvider>
    </React.StrictMode>
  </CookiesProvider>,
);
