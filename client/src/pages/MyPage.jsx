import React from 'react';
import User from '../components/Mypage/User';
import Header from '../components/Header/Header';
import Menu from '../components/Menu/Menu';

function MyPage() {
  return (
    <>
      <Header title="마이 페이지" path="/"></Header>
      <User />
      <Menu page="mypage" />
    </>
  );
}

export default MyPage;
