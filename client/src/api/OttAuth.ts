import { API } from './Token';

/**
 *
 * @param { {id: string, password: string, ottType: string} } ottForm
 */
export const ottAuthAPI = async (ottForm: { id: string; password: string; ottType: string }) => {
  return await API.post('http://localhost:8081/api/v1/ottAuth', ottForm);
};
