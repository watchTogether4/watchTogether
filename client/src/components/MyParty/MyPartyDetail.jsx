import React, { useState } from 'react';
import { useNavigate, useLocation, useOutletContext } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
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
  const { userInfo } = useOutletContext();
  const navigate = useNavigate();
  const { state } = useLocation();
  const [isLeave, setIsLeave] = useState(false);
  const [isChange, setIsChange] = useState(false);
  console.log(state.data);
  const {
    id,
    title,
    body,
    members,
    ottId,
    payDt,
    people,
    leaderNickname,
    partyOttId,
    partyOttPassword,
    createdChat,
  } = state.data;
  const nickName = state.nickName;

  const date = payDt !== null ? payDt[3] : '';
  const leader = members.filter((a) => a.leader === true);
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
      // 채팅방이 생성된 적 없으면 파티장만 생성할 수 있도록 함
      if (userInfo.nickname === leader[0].nickName) {
        enterRoomAPI({ roomId: id })
          .then((res) => {
            changeValue();
          })
          .catch((error) => console.log(error));
      } else {
        toast.error('파티장만 채팅방을 생성할 수 있습니다.', {
          position: 'top-center',
          autoClose: 1000,
        });
      }
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
      <ToastContainer />
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
                <Highlight>파티 결제 예정일: </Highlight>
              </span>
              <span>{date === '' ? '-' : `매 월 ${date}일`}</span>
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
              {createdChat ? ' 채팅방 입장하기' : '채팅방 생성하기'}
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
