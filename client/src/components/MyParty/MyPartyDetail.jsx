import React, { useState } from 'react';
import { useNavigate, useParams, useLocation } from 'react-router-dom';
import AlertModal from './AlertModal';
import PasswordModal from './PasswordModal';
import { Wrapper, Board, Withdrawal, InfoList } from './MyPartyDetail.styles';
import 'moment/locale/ko';
import otts from '../../mocks/platform';

function MyPartyDetail() {
  const [isLeave, setIsLeave] = useState(false);
  const [isChange, setIsChange] = useState(false);
  const { state } = useLocation();
  const moment = require("moment");
  const { id, title, body, members, ottId, createdDt, people, leaderNickname } = state.data;
  const nickName = state.nickName;
  const createdDate = moment(createdDt.toString()).format('YYYY년 MM월 DD일, HH시 mm분');
  const ottUrl = otts.filter((a) => a.id === ottId);
  const ottName = ottUrl[0].name;
  const { partyOttId, partyOttPassword } = state.data;

  const leaveData = {nickName: nickName, partyId: id};

  const handleClick = (e) => {
    e.preventDefault();
    const name = e.target.dataset.name;
    if (name === 'change') setIsChange(true);
    if (name === 'leave') setIsLeave(true);
  };
  
  console.log(state);

  return (
    <>
      <Wrapper>
        <Board>
          <InfoList>
          <li>
            <span>파티명: </span>
            <span>{title}</span>
          </li>
          <li>
            <span>파티 소개: </span>
            <span>{body}</span>
          </li>
          <li>
            <span>파티 생성일: </span>
            <span>{createdDate}</span>
          </li>
          <li>
            <span>파티 인원: </span>
            <span>{people} 명</span>
          </li>
          <li>
            <span>OTT 플랫폼: </span>
            <span>{ottName}</span>
          </li>
          <li>
            <span>계정 아이디: </span>
            <span>{people == 4? partyOttId: '파티 대기 중'}</span>
          </li>
          <li>
            <span>계정 비밀번호: </span>
            <span>{people == 4? partyOttPassword: '파티 대기 중'}</span>
          </li>
          <li>
            <span>구성 멤버: </span>
            <span>{members.map((a) => a.nickName).join(', ')}</span>
          </li>
          </InfoList>
        </Board>
        <Withdrawal type="button" onClick={handleClick} data-name="leave">파티 나가기</Withdrawal>
        <Withdrawal type="button" onClick={handleClick} data-name="change">OTT 비밀번호 변경하기</Withdrawal>
      </Wrapper>

      {isLeave && <AlertModal modal={setIsLeave} data={leaveData} leaderNickname={leaderNickname} id={id} />}
      {isChange && <PasswordModal modal={setIsChange} nickName={nickName} partyId={id} partyOttPassword={partyOttPassword} />}
    </>
  );
}

export default MyPartyDetail;
