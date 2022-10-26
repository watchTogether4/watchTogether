import { Cookies } from 'react-cookie';

const cookies = new Cookies();

export const setRefreshToken = (refreshToken) => {
  const today = new Date();
  const TOKEN_EXPIRE_TIME = 1000 * 60 * 1;
  const expireDate = today.setDate(today.getDate() + TOKEN_EXPIRE_TIME);

  return cookies.set('refresh_token', refreshToken, {
    sameSite: 'strict',
    path: '/',
    expires: new Date(expireDate),
  });
};

export const getCookieToken = () => cookies.get('refresh_token');

export const removeCookieToken = () =>
  // eslint-disable-next-line implicit-arrow-linebreak
  cookies.remove('refresh_token', { sameSite: 'strict', path: '/' });
