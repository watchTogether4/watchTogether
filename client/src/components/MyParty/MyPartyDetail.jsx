import React from 'react';
import { useNavigate, useParams, useLocation } from 'react-router-dom';
import { Wrapper, Board } from './MyPartyDetail.styles';

function MyPartyDetail() {
  const { state } = useLocation();
  const { title, body, members, ottId, createdDt, invisibleDt } = state.data;
  return (
    <>
      <Wrapper>
        <Board>
          <li>
            <span>파티명:</span>
            <span>{title}</span>
          </li>
          <li>
            <span>파티 소개:</span>
            <span>{body}</span>
          </li>
          <li>
            <span>파티 생성일:</span>
            <span>{createdDt}</span>
          </li>
          <li>
            <span>파티 종료일:</span>
            <span>{invisibleDt}</span>
          </li>
          <li>
            <span>OTT 플랫폼:</span>
            <span>{ottId}</span>
          </li>
          <li>
            <span>구성 멤버:</span>
            <span>{members.map((a) => a.nickName).join(', ')}</span>
          </li>
        </Board>
      </Wrapper>
    </>
  );
}

export default MyPartyDetail;
