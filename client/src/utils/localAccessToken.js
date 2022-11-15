export const getAccessToken = () => localStorage.getItem('access_token');

export const setAccessToken = (accessToken) => localStorage.setItem('access_token', accessToken);

export const removeAccessToken = () => localStorage.removeItem('access_token');
