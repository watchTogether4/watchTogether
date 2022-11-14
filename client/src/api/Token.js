import axios from 'axios';
import { getAccessToken, removeAccessToken, setAccessToken } from '../utils/localAccessToken';
import { getRefreshToken, removeRefreshToken, setRefreshToken } from '../utils/cookieRefreshToken';

export const API = axios.create({});

// 요청 보내기 전에 수행할 일
API.interceptors.request.use((res) => {
  const accessToken = getAccessToken();

  if (accessToken) {
    res.headers['Content-Type'] = 'application/json;charset=UTF-8';
    res.headers['Authorization'] = `Bearer ${accessToken}`;
  }
  return res;
});

// response 받은 후에 실행할 일
API.interceptors.response.use(
  (response) => response,
  (error) => {
    const originalRequest = error.config;

    if (error.code === 'EXPIRED_ACCESS_TOKEN' && !originalRequest.retry) {
      originalRequest.retry = true;
      const prevRefreshToken = getRefreshToken();
      const prevAccessToken = getAccessToken();

      return API.post('/api/v1/refresh-token', {
        prevAccessToken,
        prevRefreshToken,
      })
        .then((res) => {
          const { accessToken, refreshToken } = res.data;
          setAccessToken(accessToken);
          setRefreshToken(refreshToken);
          originalRequest.headers.Authorization = `Bearer ${accessToken}`;
          return originalRequest;
        })
        .catch((error) => {
          alert('세션이 만료되어 로그인 페이지로 돌아갑니다.');
          removeRefreshToken();
          removeAccessToken();
          return false;
        });
    }

    return Promise.reject(error);
  },
);
