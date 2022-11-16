import React from 'react';
import Header from '../components/Header/Header';
import UserInfo from './../components/UserInfo/UserInfo';
import styled from 'styled-components';
import { toast, ToastContainer } from 'react-toastify';
import { RiLogoutBoxRLine } from 'react-icons/ri';
import { removeAccessToken, removeRefreshToken, removeAuthentication } from './../utils/index';
import { useNavigate } from 'react-router-dom';
import { signOutAPI } from './../api/User';

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
    signOutAPI()
      .then(() => {
        removeAccessToken();
        removeRefreshToken();
        removeAuthentication();
        toast.success(<h1>로그아웃 되었습니다!.</h1>, {
          position: 'top-center',
          autoClose: 1000,
        });
      })
      .catch((error) => {
        console.log(error);
      });
    navigate('/');
  };

  return (
    <>
      <Header title="내 정보 관리" path="/mypage">
        <LogoutBtn onClick={handleClick}>
          <RiLogoutBoxRLine size={28} />
        </LogoutBtn>
      </Header>
      <ToastContainer />
      <UserInfo />
    </>
  );
};

export default UserInfoPage;
