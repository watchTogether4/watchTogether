import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useQuery } from 'react-query';
import { IoIosArrowForward } from 'react-icons/io';
import { InfoList, List } from './User.styles';
import Profile from './Profile';
import { getInfo } from './../../api/Users';
import PasswordConfirm from './PasswordConfirm';

function Chat() {
  const accessToken = localStorage.getItem('access-token');
  const getUserInfo = () => {
    return getInfo(accessToken).then((res) => res.data);
  };
  const { data, isLoading } = useQuery('getInfo', getUserInfo);

  const navigate = useNavigate();
  const [isOpen, setIsOpen] = useState(false);

  const handleClick = (path) => {
    path && navigate(`./${path}`, { state: data });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    navigate('./user', { state: data });
  };

  return (
    <>
      <Profile data={data} />
      {data && (
        <InfoList direction="column" alignItems="flex-start">
          <List justifyContent="space-between" onClick={() => setIsOpen(true)}>
            내 정보
            <IoIosArrowForward />
          </List>
          <List justifyContent="space-between" onClick={() => navigate('./myparty', {state: { nickname: data.nickname }})}>
            내 파티
            <IoIosArrowForward />
          </List>
          <List justifyContent="space-between" onClick={() => navigate('./paymentlist')}>
            결제 내역
            <IoIosArrowForward />
          </List>
        </InfoList>
      )}

      {isOpen && <PasswordConfirm modal={setIsOpen} handleSubmit={handleSubmit} data={data} />}
    </>
  );
}

export default Chat;
