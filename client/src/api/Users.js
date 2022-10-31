import axios from 'axios';
import { getCookieToken } from '../utils/Cookie';

const BASE_URL = '/api/v1/users';

/**
 * 로그인 - POST
 * @param { {email: string, password: string} } data
 */
export const loginUser = async (data) => {
  return axios({
    url: `${BASE_URL}/sign-in`,
    method: 'POST',
    headers: { 'Content-Type': 'application/json;charset=UTF-8' },
    data: JSON.stringify(data),
  });
};
/**
 * 로그아웃 - GET
 */
export const logoutUser = () => {
  return axios({
    url: `${BASE_URL}/sign-in`,
    method: 'GET',
    headers: { 'Content-Type': 'application/json' },
  });
};

/**
 * 토큰 값 만료 확인 여부
 */
export const isValidateToken = () => {
  return axios({
    url: `${BASE_URL}/my-page`,
    method: 'GET',
    headers: { 'Content-Type': 'application/json' },
  });
};

/**
 * 파티원 초대 - 닉네임 검색
 * @param {string} name
 */
export const searchUser = (name) => {
  return axios({
    url: `${BASE_URL}/search-user`,
    method: 'GET',
    headers: { 'Content-Type': 'application/json;charset=UTF-8' },
    params: {
      nickname: name,
    },
  });
};

/**
 * 마이페이지 - 유저 정보 가져오기
 */
export const getInfo = () => {
  return axios({
    url: `${BASE_URL}/my-page`,
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
      Authorization: `Bearer ${getCookieToken()}`,
    },
  });
};

export const isCurrentPassword = (password) => {
  return axios({
    url: `${BASE_URL}/password`,
    method: 'POST',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
      Authorization: `Bearer ${getCookieToken()}`,
    },
    data: JSON.stringify(password),
  });
};

export const putNewPassword = (password) => {
  return axios({
    url: `${BASE_URL}/password`,
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
      Authorization: `Bearer ${getCookieToken()}`,
    },
    data: JSON.stringify(password),
  });
};
