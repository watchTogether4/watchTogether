import { configureStore } from '@reduxjs/toolkit';
import userReducer from './User';
import tokenReducer from './Auth';

export default configureStore({
  reducer: {
    user: userReducer,
    authToken: tokenReducer,
  },
});
