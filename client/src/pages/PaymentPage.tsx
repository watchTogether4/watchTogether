import React from 'react';
import Header from '../components/Header/Header';
import Payment from '../components/Payment/Payment';

const PaymentPage = () => {
  return (
    <>
      <Header title="결제하기" path="/partyList"></Header>
      <Payment />
    </>
  );
};

export default PaymentPage;