import axios from 'axios';

const BASE_URL = `/api/v1/parties`;
/**
 *
 * @param {{
 * ottId: number,
 * title:string,
 * body:string,
 * partyOttId: string,
 * partyOttPassword: string,
 * leaderNickName: string,
 * receiversNickName: string
 * }} body
 */

/**
 * 파티 모집 글 등록하기
 */
export const createParty = (body, token) =>
  axios({
    url: `${BASE_URL}/create`,
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    },
    data: JSON.stringify(body),
  });

/**
 * 파티 신청 수락하기
 */
export const acceptParty = (body, token) =>
  axios({
    url: `${BASE_URL}/accept`,
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    },
    data: JSON.stringify(body),
  });

/**
 * 파티 목록 출력하기
 */
export const showPartyList = (token) =>
  axios({
    url: `${BASE_URL}/showPartyList`,
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    },
  });

/**
 * 파티 참여하기
 */
export const joinParty = (body, token) =>
  axios({
    url: `${BASE_URL}/join`,
    method: 'POST',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
      Authorization: `Bearer ${token}`,
    },
    data: JSON.stringify(body),
  });
<<<<<<< HEAD


/**
 * 내 파티 목록 출력하기
 */  
 export const findMyParties = (body, token) => {
  console.log(body);
  return axios({
    url: `${BASE_URL}/find-myParties`,
    method: 'POST',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
      Authorization: `Bearer ${token}`,
    },
    data: JSON.stringify(body),
  });
};
=======
>>>>>>> ba2993d74aa3ac4d9c31efca452692bf4ad79fc0
