import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { IoIosArrowForward } from 'react-icons/io';
import { InfoList, List } from './User.styles';
import Profile from './Profile';
import PasswordConfirm from './PasswordConfirm';

function Chat() {
  const { value } = useSelector((state) => state.user);
  const navigate = useNavigate();
  const [isOpen, setIsOpen] = useState(false);

  const handleSubmit = (e) => {
    e.preventDefault();
    navigate('./user');
  };

  return (
    <>
      <Profile data={value} />
      {value && (
        <InfoList direction="column" alignItems="flex-start">
          <List justifyContent="space-between" onClick={() => setIsOpen(true)}>
            내 정보
            <IoIosArrowForward />
          </List>
          <List
            justifyContent="space-between"
            onClick={() => navigate('./myparty', { state: { nickname: value.nickname } })}
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

      {isOpen && <PasswordConfirm modal={setIsOpen} handleSubmit={handleSubmit} data={value} />}
    </>
  );
}

export default Chat;
