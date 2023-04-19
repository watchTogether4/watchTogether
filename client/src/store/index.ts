import { configureStore } from '@reduxjs/toolkit';
import userReducer from './User';

export default configureStore({
  reducer: {
    user: userReducer,
  },
});
