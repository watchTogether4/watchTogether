import axios from 'axios';
import { getCookieToken } from '../utils/Cookie'

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
export const createParty = (body) => (
  axios({
    url: '/api/parties/create',
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${getCookieToken()}`
    },
    data: JSON.stringify(body),
  })
);

export const acceptParty = (body) => (
  axios({
    url: '/api/parties/accept',
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    data: JSON.stringify(body),
  })
);
