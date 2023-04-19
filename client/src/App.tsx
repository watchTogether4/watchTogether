import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { AnimatePresence } from 'framer-motion';
// route
import MainPage from './pages/MainPage';
import SignIn from './pages/SignInPage';
import SignUp from './pages/SignUpPage';

import Select from './pages/SelectPage';
import PartyList from './pages/PartyListPage';
import AddParty from './pages/AddPartyPage';
import Alarm from './pages/AlarmPage';

import MyPage from './pages/MyPage';
import UserInfo from './pages/UserInfoPage';
import Chat from './pages/ChatPage';

import Reset from './pages/ResetPage';
import PrivateRoutes from './pages/PrivateRoutes';
import OttPage from './pages/OttPage';
import Payment from './pages/PaymentPage';
import Charge from './pages/ChargePage';
import PaymentList from './pages/PaymentListPage';
import MyParty from './pages/MyPartyPage';
import MyPartyDetail from './pages/MyPartyDetailPage';

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
            <Route path="/ott" element={<OttPage />} />
            <Route path="/partyList" element={<PartyList />} />
            <Route path="/addParty" element={<AddParty />} />

            <Route path="/mypage" element={<MyPage />} />
            <Route path="/mypage/myparty" element={<MyParty />} />
            <Route path="/mypage/user" element={<UserInfo />} />
            <Route path="/mypage/myparty/:id" element={<MyPartyDetail />} />
            <Route path="/mypage/myparty/:id/chat" element={<Chat />} />
            <Route path="/mypage/paymentlist" element={<PaymentList />} />

            <Route path="/message" element={<Alarm />} />
            <Route path="/payment" element={<Payment />} />
            <Route path="/charge" element={<Charge />} />
          </Route>

          <Route path="/code=:code" element={<Reset />} />
        </Routes>
      </AnimatePresence>
    </BrowserRouter>
  );
}

export default App;
