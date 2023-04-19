import React from 'react';
import Ott from '../components/Select/Ott';
import Header from '../components/Header/Header';

function OttPage() {
  return (
    <>
      <Header title="OTT 플랫폼을 선택하세요." path="/select"></Header>
      <Ott />
    </>
  );
}

export default OttPage;
