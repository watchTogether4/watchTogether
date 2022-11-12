import axios from 'axios';

const BASE_URL = '/api/v1/alert';

export const postAlert = (body, token) => {
  return axios({
    url: BASE_URL,
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    },
    data: JSON.stringify(body),
  });
};

export const putAlert = (body, token) => {
  return axios({
    url: `${BASE_URL}/check`,
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    },
    data: JSON.stringify(body),
  });
};

export const getAlert = (body, token) => {
  return axios({
    url: `${BASE_URL}/list`,
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
      Authorization: `Bearer ${token}`,
    },
    params: {
      email: body,
    },
  });
};

