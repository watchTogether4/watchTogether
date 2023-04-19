import React from 'react';
import Header from '../components/Header/Header';
import PartyList from '../components/PartyList/PartyList';
import Menu from '../components/Menu/Menu';

const PartyListPage = () => {
  return (
    <>
      <Header title="파티 목록" path="/"></Header>
      <PartyList />
      <Menu page="partyList" />
    </>
  );
};

export default PartyListPage;
