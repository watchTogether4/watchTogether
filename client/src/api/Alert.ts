import { API } from './Token';

const BASE_API = 'http://3.38.9.104:8081/api/v1/alert';

/**
 *
 * @param {{nickName: string[], partyId: string, inviteId: string, message: string, type: string}} partyForm
 */
export const alertAPI = async (partyForm: {
  nickName: string[];
  partyId: string;
  inviteId: string;
  message: string;
  type: string;
}) => {
  return await API.post(`${BASE_API}`, partyForm);
};

/**
 *
 * @param {number} notificationId
 */
export const checkAlertAPI = async (notificationId: number) => {
  return await API.put(`${BASE_API}/check`, notificationId);
};

/**
 *
 * @param {string} email
 */
export const listAlertAPI = async (email: string) => {
  return await API.get(`${BASE_API}/list?email=${email}`);
};
