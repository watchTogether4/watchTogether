import axios from 'axios';

/**
 * 로그인 - POST
 * @param { {email: string, password: string} } data
 */

const BASE_URL = '/api/v1/users';

export const loginUser = async (data) => (
  axios({
    url: `${BASE_URL}/sign-in`,
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    data: JSON.stringify(data),
  })
);

/**
 * 로그아웃 - GET
 */
export const logoutUser = () => axios({
  url: `${BASE_URL}/sign-in`,
  method: 'GET',
  headers: { 'Content-Type': 'application/json' },
});


/**
 * 토큰 값 만료 확인 여부 
 */
export const isValidateToken = () => axios({
  url: `${BASE_URL}/my-page`,
  method: 'GET',
  headers: { 'Content-Type': 'application/json' },
});


/**
 * 파티원 초대 - 닉네임 검색 
 * @param {string} name 
 */
export const searchUser = (name) => axios({
  url: `${BASE_URL}/search-user`,
  method: 'GET',
  headers: { 'Content-Type': 'application/json;charset=UTF-8' },
  params: {
    nickname: name
  },
})
