import React from 'react';
import { useNavigate, Outlet } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
import { getAuthentication } from '../utils/index';

const PrivateRoutes = () => {
  const navigate = useNavigate();
  const isAuthenticated = getAuthentication();

  if (isAuthenticated === null || isAuthenticated === 'false') {
    toast.error(<h1>세션이 만료되었거나, 로그인이 되지 않았습니다.</h1>, {
      position: 'top-center',
      autoClose: 1500,
    });
    setTimeout(() => {
      navigate('./signIn');
    }, 1500);
  } else {
    return (
      <>
        <ToastContainer />
        <Outlet />
      </>
    );
  }
};

export default PrivateRoutes;
