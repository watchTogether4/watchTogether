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
    method: 'PUT',
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
