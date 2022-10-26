import axios from 'axios';

/**
 * 로그인 - POST
 * @param { {email: string, password: string} } data
 */
export const loginUser = async (data) => (
  axios({
    url: '/api/users/sign-in',
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    data: JSON.stringify(data),
  })
);

/**
 * 로그아웃 - GET
 */
export const logoutUser = () => axios({
  url: '/api/users/sign-in',
  method: 'GET',
  headers: { 'Content-Type': 'application/json' },
});
