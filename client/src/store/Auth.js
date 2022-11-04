import { createSlice } from '@reduxjs/toolkit';

const TOKEN_TIME_OUT = 1000 * 60 * 60;

export const tokenSlice = createSlice({
  name: 'authToken',
  initialState: {
    authenticated: false,
    accessToken: null,
    expireTime: null,
  },
  reducers: {
    SET_TOKEN: (state, action) => {
      const temp = state;
      temp.authenticated = true;
      temp.accessToken = action.payload;
      temp.expireTime = new Date().getTime() + TOKEN_TIME_OUT;
    },
    DELETE_TOKEN: (state) => {
      const temp = state;
      temp.authenticated = false;
      temp.accessToken = null;
      temp.expireTime = null;
    },
  },
});

export const { SET_TOKEN, DELETE_TOKEN } = tokenSlice.actions;

export default tokenSlice.reducer;
