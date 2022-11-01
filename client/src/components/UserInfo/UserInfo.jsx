import React, { useState } from 'react';
import { useQuery } from 'react-query';
import Avvvatars from 'avvvatars-react';

import { Wrapper, Profile, InfoList, Button, Withdrawal } from './UserInfo.styles';
import { getInfo } from './../../api/Users';
import AlertModal from './AlertModal';
import PasswordModal from './PasswordModal';

const UserInfo = () => {
  const getUserInfo = () => {
    return getInfo().then((res) => res.data);
  };

  const { data, isLoading } = useQuery('getInfo', getUserInfo);
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
        {data && (
          <div>
            <Profile>
              <Avvvatars value={data.email} style="shape" size={100} />
            </Profile>
            <InfoList>
              <li>
                <span>이메일</span>
                <span>{data.email}</span>
              </li>
              <li>
                <span>비밀번호</span>
                <Button type="button" onClick={handleClick} data-name="changePassword">
                  변경하기
                </Button>
              </li>
              <li>
                <span>닉네임</span>
                <span>{data.nickname}</span>
              </li>
              <li>
                <span>생년월일</span>
                <span>{data.birth}</span>
              </li>
              <li>
                <span>캐시 잔액</span>
                <span>{data.cash}</span>
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
