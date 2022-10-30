import React from 'react';
import SignIn from '../components/SignIn/SignIn';
import Header from '../components/Header/Header';

function LoginPage() {
  return (
    <>
      <Header title="로그인" path="/"></Header>
      <SignIn />
    </>
  );
}

export default LoginPage;
