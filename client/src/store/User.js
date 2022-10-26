import { createSlice } from '@reduxjs/toolkit';

const initialStateValue = { email: '', nickname: '' };

export const userSlice = createSlice({
  name: 'user',
  initialState: { value: initialStateValue },
  reducers: {
    login: (state, action) => {
      const temp = state;
      temp.value = action.payload;
    },
  },
});

export const { login } = userSlice.actions;
export default userSlice.reducer;
