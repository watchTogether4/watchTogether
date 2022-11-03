import React, { useState } from 'react';
import Avvvatars from 'avvvatars-react';

import { Wrapper, Profile, InfoList, Button, Withdrawal } from './UserInfo.styles';
import AlertModal from './AlertModal';
import PasswordModal from './PasswordModal';
import { useLocation } from 'react-router-dom';

const UserInfo = () => {
  const { state } = useLocation();
  console.log(state);

  const [isChange, setIsChange] = useState(false);
  const [isWithdrawal, setIsWithdrawal] = useState(false);

  const handleClick = (e) => {
    e.preventDefault();
    const name = e.target.dataset.name;
    if (name === 'changePassword') setIsChange(true);
    if (name === 'withdrawal') setIsWithdrawal(true);
  };

  return (
    <>
      <Wrapper>
        {state && (
          <div>
            <Profile>
              <Avvvatars value={state.email} style="shape" size={100} />
            </Profile>
            <InfoList>
              <li>
                <span>이메일</span>
                <span>{state.email}</span>
              </li>
              <li>
                <span>비밀번호</span>
                <Button type="button" onClick={handleClick} data-name="changePassword">
                  변경하기
                </Button>
              </li>
              <li>
                <span>닉네임</span>
                <span>{state.nickname}</span>
              </li>
              <li>
                <span>생년월일</span>
                <span>{state.birth}</span>
              </li>
              <li>
                <span>캐시 잔액</span>
                <span>{state.cash}</span>
              </li>
            </InfoList>
          </div>
        )}
        <Withdrawal type="button" onClick={handleClick} data-name="withdrawal">
          탈퇴하기
        </Withdrawal>
      </Wrapper>
      {/* 비밀번호 변경 modal */}
      {isChange && <PasswordModal modal={setIsChange} />}

      {/* 탈퇴 modal */}
      {isWithdrawal && <AlertModal modal={setIsWithdrawal} />}
    </>
  );
};

export default UserInfo;
