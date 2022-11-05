import React from 'react';
import Alarm from './../components/Alarm/Alarm';
import Header from './../components/Header/Header';

const AlarmPage = () => {
  return (
    <>
      <Header title="쪽지함" path="/partyList"></Header>
      <Alarm />
    </>
  );
};

export default AlarmPage;
