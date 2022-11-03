import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { AnimatePresence } from 'framer-motion';
// route
import MainPage from './pages/MainPage';
import SignIn from './pages/SignInPage';
import SignUp from './pages/SignUpPage';
import Select from './pages/SelectPage';
import PartyList from './pages/PartyListPage';
import MyPage from './pages/MyPage';
import UserInfo from './pages/UserInfoPage';
import Reset from './pages/ResetPage';
import PrivateRoutes from './pages/PrivateRoutes';

function App() {
  return (
    <BrowserRouter>
      <AnimatePresence>
        <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/signIn" element={<SignIn />} />
          <Route path="/signUp" element={<SignUp />} />

          <Route element={<PrivateRoutes />}>
            <Route path="/select" element={<Select />} />
            <Route path="/partyList" element={<PartyList />} />

            <Route path="/mypage" element={<MyPage />} />
            <Route path="/mypage/user" element={<UserInfo />} />

            <Route path="/code=:code" element={<Reset />} />
          </Route>

          {/* <Route path="/mypage/myparty" element={<MyParty />} />
            <Route path="/mypage/myparty/:id" element={<MyPartyDetail />} />
            <Route path="/mypage/myparty/:id/chat" element={<Chat />} />
            <Route path="/select" element={<Select />} />
            */}
        </Routes>
      </AnimatePresence>
    </BrowserRouter>
  );
}

export default App;
