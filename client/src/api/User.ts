import { API } from './Token';

const BASE_API = 'http://localhost:8081/api/v1/users';

/**
 *
 * @param {{email: string, password: string, nickname: string, birth: string}} userForm
 * @returns {} email, message
 */
export const signUpAPI = async (userForm: {
  email: string;
  password: string;
  nickname: string;
  birth: string;
}) => {
  return await API.post(`${BASE_API}/sign-up`, userForm);
};

/**
 *
 * @param {{email: string, password: string}} userForm
 * @returns {} email, accessToken, refreshToken, message
 */
export const signInAPI = async (userForm: { email: string; password: string }) => {
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
 * @param {{email: string}} userForm
 * @return {} message
 */
export const secessionUserAPI = async (userForm: { email: string }) => {
  return await API.delete(`${BASE_API}?email=${userForm}`);
};

export const myPageAPI = async () => {
  return await API.get(`${BASE_API}/my-page`);
};

/**
 * @param { string } password
 * @return {} message
 */
export const checkPasswordAPI = async (password: string) => {
  console.log(password);
  return await API.post(`${BASE_API}/password`, password);
};

/**
 * @param { string } password
 * @return {} message
 */
export const changePasswordAPI = async (password: string) => {
  return await API.put(`${BASE_API}/password`, password);
};

/**
 * @param {{email: string, password: string}} userForm
 * @return {} message
 */
export const findPasswordAPI = async (userForm: { email: string; password: string }) => {
  return await API.post(`${BASE_API}/reset-password`, userForm);
};

/**
 * @param {string} code
 * @return {} message
 */
export const checkResetPasswordAPI = async (code: string) => {
  return await API.get(`${BASE_API}/reset-password?code=${code}`);
};

/**
 * @param {{code: string, password: string}} passwordForm
 * @return {} message
 */
export const NewPasswordAPI = async (passwordForm: { code: string; password: string }) => {
  return await API.put(`${BASE_API}/reset-password`, passwordForm);
};

/**
 *
 * @param { string } name
 * @return {} nickname, message
 */
export const searchUserAPI = async (name: string) => {
  return await API.get(`${BASE_API}/search-user?nickname=${name}`);
};
