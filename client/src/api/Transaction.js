import axios from 'axios';

const BASE_URL = `/api/v1/transactions`;
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
 * 파티 참가 결제하기
 */
export const withdraw = (body, token) =>
  axios({
    url: `${BASE_URL}/withdraw`,
    method: 'POST',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
      Authorization: `Bearer ${token}`,
    },
    data: JSON.stringify(body),
  });

/**
 * 충전하기
 */
export const charge = (body, token) =>
  axios({
    url: `${BASE_URL}/charge`,
    method: 'POST',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
      Authorization: `Bearer ${token}`,
    },
    data: JSON.stringify(body),
  });

