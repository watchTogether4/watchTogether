import React from 'react';
import { Link } from 'react-router-dom';
import { IoIosArrowBack, IoIosArrowForward } from 'react-icons/io';
import { InfoList, List } from './User.styles';
import Profile from './Profile';

function Chat() {
  return (
    <>
      <Profile />
      <InfoList direction="column" alignItems="flex-start">
        <List justifyContent="space-between">
          <Link to="/">결제</Link>
          <IoIosArrowForward />
        </List>
        <List justifyContent="space-between">
          <Link to="/">내 파티</Link>
          <IoIosArrowForward />
        </List>
      </InfoList>
    </>
  );
}

export default Chat;
