import axios from 'axios';

export const enterChat = (body, token) => {
  return axios({
    url: 'http://3.38.9.104:8080/api/v1/chat',
    method: 'POST',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
      Authorization: `Bearer ${token}`,
    },
    data: JSON.stringify(body),
  });
};

export const getChat = (body, token) => {
  return axios({
    url: 'http://localhost:8080/api/v1/chat',
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
      Authorization: `Bearer ${token}`,
    },
    params: {
      roomId: body,
    },
  });
};
