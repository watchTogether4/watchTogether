import React from 'react';
import Header from '../components/Header/Header';
import PartyList from '../components/PartyList/PartyList';

const PartyListPage = () => {
  return (
    <>
      <Header title="파티 목록" path="/"></Header>
      <PartyList/>
    </>
  );
};

export default PartyListPage;
