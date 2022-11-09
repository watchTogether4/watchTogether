import React from 'react';
import Header from '../components/Header/Header';
import Charge from '../components/Payment/Charge';

const ChargePage = () => {
  return (
    <>
      <Header title="충전하기" path="/mypage"></Header>
      <Charge />
    </>
  );
};

export default ChargePage;