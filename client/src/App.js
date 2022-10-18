import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
// route
import MainPage from './pages/MainPage';
import LoginPage from './pages/LoginPage';
import MyPage from './pages/MyPage';
import SignUp from './pages/SignUpPage';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/mypage" element={<MyPage />} />
        <Route path="/signUp" element={<SignUp />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
