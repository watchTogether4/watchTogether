import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useOutletContext } from 'react-router-dom';
import { IoIosArrowForward } from 'react-icons/io';
import { InfoList, List } from './User.styles';
import Profile from './Profile';
import PasswordConfirm from './PasswordConfirm';

function Chat() {
  const { userInfo } = useOutletContext();
  const navigate = useNavigate();
  const [isOpen, setIsOpen] = useState(false);

  const handleSubmit = (e) => {
    e.preventDefault();
    navigate('./user');
  };

  return (
    <>
      <Profile data={userInfo} />
      {userInfo && (
        <InfoList direction="column" alignItems="flex-start">
          <List justifyContent="space-between" onClick={() => setIsOpen(true)}>
            내 정보
            <IoIosArrowForward />
          </List>
          <List
            justifyContent="space-between"
            onClick={() => navigate('./myparty', { state: { nickname: userInfo.nickname } })}
          >
            내 파티
            <IoIosArrowForward />
          </List>
          <List justifyContent="space-between" onClick={() => navigate('./paymentlist')}>
            결제 내역
            <IoIosArrowForward />
          </List>
        </InfoList>
      )}

      {isOpen && <PasswordConfirm modal={setIsOpen} handleSubmit={handleSubmit} data={userInfo} />}
    </>
  );
}

export default Chat;
