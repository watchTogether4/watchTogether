import React from 'react';
import { useEffect } from 'react';
import { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate, Outlet } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
import { myPageAPI } from '../api/User';
import { getAuthentication } from '../utils/index';
import { info } from '../store/User';

export interface StateType {
  user: {
    value: { nickname: string; email: string; cash: number; birth: string };
  };
}

interface UseStateType {
  nickname: string;
  email: string;
  cash: number;
  birth: string;
}

const PrivateRoutes = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const isAuthenticated = getAuthentication();
  const { value } = useSelector((state: StateType) => state.user);

  const [userInfo, setUserInfo] = useState<UseStateType>();

  // 인증이 필요한 페이지가 렌더링 될 때, 리덕스에 저장된 값이 있으면 해당 값을 불러오고 없으면 유저 정보 api를 호출하고 리덕스에 저장
  useEffect(() => {
    if (value.email === '') {
      myPageAPI()
        .then((res) => {
          dispatch(info(res.data));
          setUserInfo(res.data);
        })
        .catch((error) => error.response.data.message);
    } else {
      setUserInfo(value);
    }
  }, [dispatch, value]);

  if (isAuthenticated === null || isAuthenticated === 'false') {
    toast.error(<h1>세션이 만료되었거나, 로그인이 되지 않았습니다.</h1>, {
      position: 'top-center',
      autoClose: 1500,
    });
    setTimeout(() => {
      navigate('./signIn');
    }, 1500);
    return null;
  } else {
    return (
      <>
        <ToastContainer />
        <Outlet context={{ userInfo }} />
      </>
    );
  }
};

export default PrivateRoutes;
