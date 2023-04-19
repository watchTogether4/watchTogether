import React from 'react';
import SignUp from '../components/SignUp/SignUp';
import Header from '../components/Header/Header';

function SignUpPage() {
  return (
    <>
      <Header title="회원가입" path="/signIn"></Header>
      <SignUp />
    </>
  );
}

export default SignUpPage;
