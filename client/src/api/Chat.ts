import { API } from './Token';

const BASE_API = 'http://localhost:8080/api/v1/chat';

/**
 *
 * @param { number } roomId
 */
export const enterRoomAPI = async (roomId: number) => {
  return await API.post(`${BASE_API}`, roomId);
};

/**
 *
 * @param { number } roomId
 */
export const leaveRoomAPI = async (roomId: any) => {
  return await API.delete(`${BASE_API}`, roomId);
};

/**
 *
 * @param { number } roomId
 * @returns sender, message, date
 */
export const getChatAPI = async (roomId: number) => {
  return await API.get(`${BASE_API}?roomId=${roomId}`);
};
