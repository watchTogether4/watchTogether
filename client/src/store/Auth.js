import { createSlice } from '@reduxjs/toolkit';

export const tokenSlice = createSlice({
  name: 'authToken',
  initialState: {
    authenticated: false,
    accessToken: null,
  },
  reducers: {
    SET_TOKEN: (state, action) => {
      const temp = state;
      temp.authenticated = true;
      temp.accessToken = action.payload;
    },
    DELETE_TOKEN: (state) => {
      const temp = state;
      temp.authenticated = false;
      temp.accessToken = null;
    },
  },
});

export const { SET_TOKEN, DELETE_TOKEN } = tokenSlice.actions;

export default tokenSlice.reducer;
