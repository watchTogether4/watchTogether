import React, { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import AlertModal from './AlertModal';
import PasswordModal from './PasswordModal';
import {
  Wrapper,
  Board,
  Withdrawal,
  InfoList,
  Highlight,
  HighlightRed,
  HighlightTwo,
  ButtonSection,
} from './MyPartyDetail.styles';
import 'moment/locale/ko';
import otts from '../../mocks/platform';
import { createChatAPI } from '../../api/Parties';
import { enterRoomAPI } from '../../api/Chat';

function MyPartyDetail() {
  const navigate = useNavigate();
  const { state } = useLocation();
  const [isLeave, setIsLeave] = useState(false);
  const [isChange, setIsChange] = useState(false);
  const {
    id,
    title,
    body,
    members,
    ottId,
    createdDt,
    people,
    leaderNickname,
    partyOttId,
    partyOttPassword,
    createdChat,
  } = state.data;
  const nickName = state.nickName;

  const moment = require('moment');
  const createdDate = moment(createdDt.toString()).format('YY년 MM월 DD일, HH시 mm분');

  const ottUrl = otts.filter((a) => a.id === ottId);
  const ottName = ottUrl[0].name;

  const leaveData = { nickName: nickName, partyId: id };

  const changeValue = () => {
    createChatAPI({ partyId: id })
      .then(() => {
        navigate('./chat');
      })
      .catch((error) => console.log(error));
  };

  const checkRoom = () => {
    if (createdChat) {
      // 채팅방이 이미 생성되어 있으면 바로 이동
      navigate('./chat');
    } else {
      // 채팅방이 없으면 생성하고, 값 바꿔준 후 들어감
      enterRoomAPI({ roomId: id })
        .then((res) => {
          changeValue();
        })
        .catch((error) => console.log(error));
    }
  };

  const handleClick = (e) => {
    e.preventDefault();
    const name = e.target.dataset.name;
    if (name === 'change') setIsChange(true);
    if (name === 'leave') setIsLeave(true);
    if (name === 'chat') checkRoom();
  };

  return (
    <>
      <Wrapper>
        <Board>
          <InfoList>
            <li>
              <span>
                <Highlight>파티명: </Highlight>
              </span>
              <span>{title}</span>
            </li>
            <li>
              <span>
                <Highlight>파티 소개: </Highlight>
              </span>
              <span>{body}</span>
            </li>
            <li>
              <span>
                <Highlight>파티 생성일: </Highlight>
              </span>
              <span>{createdDate}</span>
            </li>
            <li>
              <span>
                <Highlight>파티 인원: </Highlight>
              </span>
              <span>{people} 명</span>
            </li>
            <li>
              <span>
                <Highlight>OTT 플랫폼: </Highlight>
              </span>
              <span>{ottName}</span>
            </li>
            <li>
              <span>
                <Highlight>계정 아이디: </Highlight>
              </span>
              <span>
                {people == 4 ? (
                  <HighlightTwo>{partyOttId}</HighlightTwo>
                ) : (
                  <HighlightRed>파티 대기 중</HighlightRed>
                )}
              </span>
            </li>
            <li>
              <span>
                <Highlight>계정 비밀번호: </Highlight>
              </span>
              <span>
                {people == 4 ? (
                  <HighlightTwo>{partyOttPassword}</HighlightTwo>
                ) : (
                  <HighlightRed>파티 대기 중</HighlightRed>
                )}
              </span>
            </li>
            <li>
              <span>
                <Highlight>구성 멤버: </Highlight>
              </span>
              <span>{members.map((a) => a.nickName).join(', ')}</span>
            </li>
          </InfoList>
        </Board>
        <ButtonSection>
          {people === 4 && (
            <Withdrawal type="button" onClick={handleClick} data-name="chat">
              채팅방 입장하기
            </Withdrawal>
          )}
          <Withdrawal type="button" onClick={handleClick} data-name="change">
            OTT 비밀번호 변경하기
          </Withdrawal>
          <Withdrawal type="button" onClick={handleClick} data-name="leave">
            <HighlightRed>파티 나가기</HighlightRed>
          </Withdrawal>
        </ButtonSection>
      </Wrapper>

      {isLeave && (
        <AlertModal modal={setIsLeave} data={leaveData} leaderNickname={leaderNickname} id={id} />
      )}
      {isChange && (
        <PasswordModal
          modal={setIsChange}
          nickName={nickName}
          partyId={id}
          partyOttPassword={partyOttPassword}
        />
      )}
    </>
  );
}

export default MyPartyDetail;
