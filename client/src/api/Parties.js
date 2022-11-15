import { API } from './Token';

const BASE_API = `http://localhost:8081/api/v1/parties`;

/**
 *
 * @param {{nick: string, uuid: string}} userInfo
 */
export const acceptPartyAPI = async (userInfo) => {
  return await API.post(`${BASE_API}/accept`, userInfo);
};

/**
 *
 * @param {{ninkname: string, partyId:number, password: string, newPassword: string}} userForm
 */
export const changePasswordAPI = async (userForm) => {
  return await API.post(`${BASE_API}/changePassword`, userForm);
};

/**
 *
 * @param {{nickname: string, partyId: number}} userForm
 */
export const checkContinueAPI = async (userForm) => {
  return await API.post(`${BASE_API}/checkContinueMessage`, userForm);
};

/**
 *
 * @param {{
 * ottId: number,
 * title: string,
 * body: string,
 * partyOttId: string,
 * partyOttPassword: string,
 * leaderNickName: string,
 * receiversNickName: string
 * }} partyForm
 */
export const createPartyAPI = async (partyForm) => {
  return await API.post(`${BASE_API}/create`, partyForm);
};

/**
 *
 * @param {string} nickname
 * @returns partyId, partyTitle, partyBody, payAmount, createTime, ottName, partyEndDate, members
 */
export const findMyPartyAPI = async (nickname) => {
  return await API.post(`${BASE_API}/find-myParties`, nickname);
};

/**
 *
 * @param {{nickName: string, partyId: number}} partyForm
 */
export const joinPartyAPI = async (partyForm) => {
  return await API.post(`${BASE_API}/join`, partyForm);
};

/**
 *
 * @param {{nickName: string, partyId: number}} userForm
 */
export const leavePartyAPI = async (userForm) => {
  return await API.post(`${BASE_API}/leave`, userForm);
};

/**
 *
 * @returns partyId, partyTitle, partyBody, payAmount, createTime, ottName, partyEndDate, members
 */
export const getAllPartyAPI = async () => {
  return await API.get(`${BASE_API}/showPartyList`);
};

/**
 * @param {number} partyId
 * @returns partyId, partyTitle, partyBody, payAmount, createTime, ottName, partyEndDate, members
 */
export const createChatAPI = async (partyId) => {
  return await API.put(`${BASE_API}/createChat`, partyId);
};