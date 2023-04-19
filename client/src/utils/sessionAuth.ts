export const getAuthentication = () => sessionStorage.getItem('isAuthenticated');

export const setAuthentication = (boolean: string) =>
  sessionStorage.setItem('isAuthenticated', boolean);

export const removeAuthentication = () => sessionStorage.removeItem('isAuthenticated');
