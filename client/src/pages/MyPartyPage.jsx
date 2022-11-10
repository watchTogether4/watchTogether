import React from 'react';
import Header from '../components/Header/Header';
import MyParty from '../components/MyParty/MyParty';

const MyPartyPage = () => {
  return (
    <>
      <Header title="내 파티 목록" path="/mypage"></Header>
      <MyParty />
    </>
  );
};

export default MyPartyPage;