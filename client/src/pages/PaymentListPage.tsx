import React from 'react';
import Header from '../components/Header/Header';
import PaymentList from '../components/Payment/PaymentList';

const PaymentListPage = () => {
  return (
    <>
      <Header title="결제 내역" path="/mypage"></Header>
      <PaymentList />
    </>
  );
};

export default PaymentListPage;