import React from 'react';
import Header from '../components/Header/Header';
import Main from '../components/Main/Main';
import Logo from '../components/Main/Logo';

function MainPage() {
  return (
    <>
      <Header>
        <Logo />
      </Header>
      <Main />
    </>
  );
}

export default MainPage;
