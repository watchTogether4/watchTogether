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

export const listParty = (body) =>
  axios({
    url: `${BASE_URL}/showPartyList`,
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
    data: JSON.stringify(body),
  });
