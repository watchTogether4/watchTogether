import React from 'react';
import { useNavigate } from 'react-router-dom';
// styles
import { IoIosArrowBack } from 'react-icons/io';
import { Wrapper, Title, BackBtn } from './SignInHeader.styles';

function LoginHeader() {
  const navigate = useNavigate();
  return (
    <Wrapper>
      <BackBtn type="button" onClick={() => navigate('/')}>
        <IoIosArrowBack size={30} />
      </BackBtn>
      <Title>로그인</Title>
    </Wrapper>
  );
}

export default LoginHeader;
