import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { Provider } from 'react-redux';
import store from './redux/store';
// route
import MainPage from './pages/MainPage';
import SignIn from './pages/SignInPage';
import MyPage from './pages/MyPage';
import SignUp from './pages/SignUpPage';
import AddParty from './pages/AddPartyPage';

function App() {
  return (
    <BrowserRouter>
      <Provider store={store}>
        <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/signIn" element={<SignIn />} />
          <Route path="/signUp" element={<SignUp />} />
          {/* 마이 페이지 */}
          <Route path="/addParty" element={<AddParty />} />
          <Route path="/mypage" element={<MyPage />} />
          {/* <Route path="/mypage/myparty" element={<MyParty />} />
          <Route path="/mypage/myparty/:id" element={<MyPartyDetail />} />
          <Route path="/mypage/myparty/:id/chat" element={<Chat />} />

          <Route path="/select" element={<Select />} />
          <Route path="/partyList" element={<PartyList />} /> */}
        </Routes>
      </Provider>
    </BrowserRouter>
  );
}

export default App;
