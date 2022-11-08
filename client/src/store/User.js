import { createSlice } from '@reduxjs/toolkit';

const initialStateValue = { email: '', nickname: '', cash: 0, birth: '' };

export const userSlice = createSlice({
  name: 'user',
  initialState: { value: initialStateValue },
  reducers: {
    info: (state, action) => {
      const temp = state;
      temp.value = action.payload;
    },
  },
});

export const { info } = userSlice.actions;
export default userSlice.reducer;
