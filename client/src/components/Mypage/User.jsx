import React, { useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useQuery } from 'react-query';
import { IoIosArrowForward } from 'react-icons/io';
import { InfoList, List } from './User.styles';
import Profile from './Profile';
import { getInfo } from './../../api/Users';

function Chat() {
  const getUserInfo = () => {
    return getInfo().then((res) => res.data);
  };
  const navigate = useNavigate();
  const { data, isLoading } = useQuery('getInfo', getUserInfo);

  const handleClick = (path) => {
    path && navigate(`./${path}`, { state: data });
  };
  return (
    <>
      <Profile />
      <InfoList direction="column" alignItems="flex-start">
        <List justifyContent="space-between" onClick={() => handleClick('user')}>
          내 정보
          <IoIosArrowForward />
        </List>
        <List justifyContent="space-between" onClick={() => handleClick()}>
          내 파티
          <IoIosArrowForward />
        </List>
        <List justifyContent="space-between" onClick={() => handleClick()}>
          결제 내역
          <IoIosArrowForward />
        </List>
      </InfoList>
    </>
  );
}

export default Chat;
