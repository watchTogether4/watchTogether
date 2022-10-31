import React from 'react';
import Header from '../components/Header/Header';
import UserInfo from './../components/UserInfo/UserInfo';
import styled from 'styled-components';
import { RiLogoutBoxRLine } from 'react-icons/ri';
import { removeCookieToken } from './../utils/Cookie';
import { useNavigate } from 'react-router-dom';

const LogoutBtn = styled.button`
  width: 3rem;
  height: 3rem;
  position: absolute;
  right: 0.5rem;
  background-color: transparent;
`;

const UserInfoPage = () => {
  const navigate = useNavigate();

  const handleClick = () => {
    removeCookieToken();
    navigate('/');
  };

  return (
    <>
      <Header title="내 정보 관리" path="/mypage">
        <LogoutBtn onClick={handleClick}>
          <RiLogoutBoxRLine size={28} />
        </LogoutBtn>
      </Header>
      <UserInfo />
    </>
  );
};

export default UserInfoPage;
