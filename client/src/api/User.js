import { API } from './Token';

const BASE_API = 'http://3.38.9.104:8081/api/v1/users';

/**
 *
 * @param {{email: string, password: string, nickname: string, birth: string}} userForm
 * @returns {} email, message
 */
export const signUpAPI = async (userForm) => {
  return await API.post(`${BASE_API}/sign-up`, userForm);
};

/**
 *
 * @param {{email: string, password: string}} userForm
 * @returns {} email, accessToken, refreshToken, message
 */
export const signInAPI = async (userForm) => {
  return await API.post(`${BASE_API}/sign-in`, userForm);
};

/**
 *
 * @return {} message
 */
export const signOutAPI = async () => {
  return await API.post(`${BASE_API}/sign-out`);
};

/**
 *
 * @param {{email: string, password: string}} userForm
 * @return {} message
 */
export const secessionUserAPI = async (userForm) => {
  return await API.delete(`${BASE_API}`, userForm);
};

/**
 * @param { string } accessToken
 * @return {} email, nickname, cash, birth, message
 */
export const myPageAPI = async () => {
  return await API.get(`${BASE_API}/my-page`);
};

/**
 * @param { string } password
 * @return {} message
 */
export const checkPasswordAPI = async (password) => {
  console.log(password);
  return await API.post(`${BASE_API}/password`, password);
};

/**
 * @param { string } password
 * @return {} message
 */
export const changePasswordAPI = async (password) => {
  return await API.put(`${BASE_API}/password`, password);
};

/**
 * @param {{email: string, password: string}} userForm
 * @return {} message
 */
export const findPasswordAPI = async (userForm) => {
  return await API.post(`${BASE_API}/rest-password`, userForm);
};

/**
 * @param {string} code
 * @return {} message
 */
export const checkResetPasswordAPI = async (code) => {
  return await API.get(`${BASE_API}/rest-password?code=${code}`);
};

/**
 * @param {{code: string, password: string}} passwordForm
 * @return {} message
 */
export const NewPasswordAPI = async (passwordForm) => {
  return await API.put(`${BASE_API}/rest-password`, passwordForm);
};

/**
 *
 * @param { string } name
 * @return {} nickname, message
 */
export const searchUserAPI = async (name) => {
  return await API.get(`${BASE_API}/search-user?nickname=${name}`);
};
