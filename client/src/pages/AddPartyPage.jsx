import React from 'react';
import Header from '../components/Header/Header';
import AddParty from './../components/AddParty/AddParty';

function AddPartyPage() {
  return (
    <>
      <Header title="파티 모집하기" path="/select"></Header>
      <AddParty />
    </>
  );
}

export default AddPartyPage;
