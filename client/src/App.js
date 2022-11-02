import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { AnimatePresence } from 'framer-motion';
// route
import MainPage from './pages/MainPage';
import SignIn from './pages/SignInPage';
import MyPage from './pages/MyPage';
import SignUp from './pages/SignUpPage';
import AddParty from './pages/AddPartyPage';
import UserInfo from './pages/UserInfoPage';
import PartyList from './pages/PartyListPage';
import Reset from './pages/ResetPage';
import Select from './pages/SelectPage';
import Ott from './pages/OttPage';

function App() {
  return (
    <BrowserRouter>
      <AnimatePresence>
        <Routes>
          <Route path="/" element={<MainPage />} />

          <Route path="/code=:code" element={<Reset />} />
          <Route path="/signIn" element={<SignIn />} />
          <Route path="/signUp" element={<SignUp />} />

          <Route path="/addParty" element={<AddParty />} />

          <Route path="/mypage" element={<MyPage />} />
          <Route path="/mypage/user" element={<UserInfo />} />

          <Route path="/partyList" element={<PartyList />} />
          <Route path="/select" element={<Select />} />
          <Route path="/ott" element={<Ott />} />
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
