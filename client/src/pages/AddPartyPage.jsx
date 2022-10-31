import React from 'react';
import Header from '../components/Header/Header';
import AddParty from '../components/AddParty/AddParty';

const AddPartyPage = () => {
  return (
    <>
      <Header title="파티원 모집하기" path="/"></Header>
      <AddParty />
    </>
  );
};

export default AddPartyPage;
