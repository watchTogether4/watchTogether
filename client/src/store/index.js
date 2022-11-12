import { configureStore } from '@reduxjs/toolkit';
import userReducer from './User';
import tokenReducer from './Auth';
import chatReducer from './Chat';

export default configureStore({
  reducer: {
    user: userReducer,
    authToken: tokenReducer,
    message: chatReducer,
  },
});
