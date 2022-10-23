import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { Provider } from 'react-redux';
import store from './redux/store';
// route
import MainPage from './pages/MainPage';
import SignIn from './pages/SignInPage';
import MyPage from './pages/MyPage';
import SignUp from './pages/SignUpPage';

function App() {
  return (
    <BrowserRouter>
      <Provider store={store}>
        <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/signIn" element={<SignIn />} />
          <Route path="/mypage" element={<MyPage />} />
          <Route path="/signUp" element={<SignUp />} />
        </Routes>
      </Provider>
    </BrowserRouter>
  );
}

export default App;
