import axios from 'axios';

const BASE_URL = '/api/v1/users';
const BASE_API = 'http://http://3.38.9.104:8081';

/**
 * 로그인 - POST
 * @param { {email: string, password: string} } data
 */
export const loginUser = async (data) => {
  return axios({
    baseUrl: BASE_API,
    url: `${BASE_URL}/sign-in`,
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    data: JSON.stringify(data),
  });
};

/**
 * 로그아웃 - GET
 */
export const logoutUser = (token) => {
  return axios({
    baseUrl: BASE_API,
    url: `/api/v1/sign-out`,
    method: 'POST',
    headers: { 'Content-Type': 'application/json', Authorization: `Bearer ${token}` },
  });
};

/**
 * 토큰 값 만료 확인 여부
 */
export const isValidateToken = () => {
  return axios({
    baseUrl: BASE_API,
    url: `${BASE_URL}/my-page`,
    method: 'GET',
    headers: { 'Content-Type': 'application/json' },
  });
};

/**
 * 파티원 초대 - 닉네임 검색
 * @param {string} name
 */
export const searchUser = (name, token) => {
  return axios({
    baseUrl: BASE_API,
    url: `${BASE_URL}/search-user`,
    method: 'GET',
    headers: { 'Content-Type': 'application/json;charset=UTF-8', Authorization: `Bearer ${token}` },
    params: {
      nickname: name,
    },
  });
};

/**
 * 마이페이지 - 유저 정보 가져오기
 */
export const getInfo = (token) => {
  return axios({
    baseUrl: BASE_API,
    url: `${BASE_URL}/my-page`,
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
      Authorization: `Bearer ${token}`,
    },
  });
};

/**
 * 비밀번호 초기화 이메일 전송
 * @param {{email:string; name: string}} body
 */
export const findPassword = (body) => {
  return axios({
    baseUrl: BASE_API,
    url: `${BASE_URL}/reset-password`,
    method: 'POST',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
    data: JSON.stringify(body),
  });
};

/**
 * 비밀번호 초기화 메일 인증
 * @param {string} code
 * @returns
 */
export const checkCode = (code) => {
  return axios({
    baseUrl: BASE_API,
    url: `${BASE_URL}/reset-password`,
    methode: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
    params: {
      code: code,
    },
  });
};

/**
 * 비밀번호 초기화 메일 인증
 * @param {{code: string; password: string}} body
 */
export const resetPassword = (body) => {
  return axios({
    url: `${BASE_URL}/reset-password`,
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
    data: JSON.stringify(body),
  });
};

/**
 * 현재 비밀번호 일치 여부 확인
 * @param {string} password
 */
export const isCurrentPassword = (password, token) => {
  return axios({
    url: `${BASE_URL}/password`,
    method: 'POST',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
      Authorization: `Bearer ${token}`,
    },
    data: JSON.stringify(password),
  });
};

/**
 * 새 비밀번호 변경
 * @param {string} password
 */
export const putNewPassword = (password, token) => {
  return axios({
    url: `${BASE_URL}/password`,
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
      Authorization: `Bearer ${token}`,
    },
    data: JSON.stringify(password),
  });
};

/**
 * 회원 탈퇴
 * @param {{email : string, password:string}} body
 */
export const withdrawalUser = (body, token) => {
  return axios({
    url: `${BASE_URL}/`,
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
      Authorization: `Bearer ${token}`,
    },
    data: JSON.stringify(body),
  });
};
