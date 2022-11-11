import React from 'react';
import Header from '../components/Header/Header';
import MyPartyDetail from '../components/MyParty/MyPartyDetail';

const MyPartyDetailPage = () => {
  return (
    <>
      <Header title="내 파티 상세보기" path="/mypage"></Header>
      <MyPartyDetail />
    </>
  );
};

export default MyPartyDetailPage;