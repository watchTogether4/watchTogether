import { API } from './Token';

const BASE_API = `http://3.38.9.104:8081/api/v1/transactions`;

/**
 * 사용자 거래 내역 리스트
 * @returns list, message
 */
export const getListAPI = async () => {
  return await API.get(`${BASE_API}`);
};

/**
 * 사용자 캐쉬 충전
 * @param {{email: string, password: string, amount: number}} userForm
 * @returns email, message
 */
export const chargeAPI = async (userForm) => {
  return await API.post(`${BASE_API}/charge`, userForm);
};

/**
 * 사용자 캐쉬 출금
 * @param {number} partyId
 * @returns nickname, transactionType, transactionResultType, amount, balanceSnapshot, traderNickname, transactionDt, message
 */
export const withdrawAPI = async (partyId) => {
  return await API.post(`${BASE_API}/withdraw`, partyId);
};

/**
 * 사용자 캐쉬 출금 취소
 * @param {number} partyId
 * @returns nickname, transactionType, transactionResultType, amount, balanceSnapshot, traderNickname, transactionDt, message
 */
export const withdrawCancelAPI = async (partyId) => {
  return await API.post(`${BASE_API}/withdraw/cancel`, partyId);
};
