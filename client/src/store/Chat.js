import { createSlice } from '@reduxjs/toolkit';

const initialStateValue = { sender: '', message: '' };

export const chatSlice = createSlice({
  name: 'message',
  initialState: { value: initialStateValue },
  reducers: {
    addMsg: (state, action) => {
      const temp = state;
      temp.value = action.payload;
    },
  },
});

export const { addMsg } = chatSlice.actions;
export default chatSlice.reducer;
