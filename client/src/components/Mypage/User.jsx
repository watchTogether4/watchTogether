import React from 'react';
import { Link } from 'react-router-dom';
import { IoIosArrowForward } from 'react-icons/io';
import { InfoList, List } from './User.styles';
import Profile from './Profile';

function Chat() {
  return (
    <>
      <Profile />
      <InfoList direction="column" alignItems="flex-start">
        <List justifyContent="space-between">
          <Link to="./user">
            내 정보
            <IoIosArrowForward />
          </Link>
        </List>
        <List justifyContent="space-between">
          <Link to="/">
            내 파티
            <IoIosArrowForward />
          </Link>
        </List>
        <List justifyContent="space-between">
          <Link to="/">
            결제 내역
            <IoIosArrowForward />
          </Link>
        </List>
      </InfoList>
    </>
  );
}

export default Chat;
