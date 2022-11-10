import React from 'react';
import { useLocation } from 'react-router-dom'; 
import { useQuery } from 'react-query';
import { findMyParties } from './../../api/Parties';
import { Wrapper, Board, Card } from './MyParty.styles';

function MyParty() {
  const accessToken = localStorage.getItem('access-token');

  const { state } = useLocation();
  let nickName = '';
  if (state) {
    nickName = state.nickname;
  };
  const body = { nickName: nickName }
  let selectedOtt = 1;
  if (state==null) {
    selectedOtt = 1;
  } else {
    selectedOtt = state.ott;
  };

  const getBoardList = () => {
    return findMyParties(body, accessToken).then((res) => res.data);
  };

  const { data } = useQuery('getBoardList', getBoardList);

  console.log(data);

  return (
    <>
    <Wrapper>
    <Board>
      {/* {data.filter((party)=>party.ottId===selectedOtt).map((party) => (
        <Card
          // map 사용 시 key 값 필수 -> 유니크한 값 (보통 index나 id 사용 )
          key={party.id}
          // platform 중에 ottId 가 같은 것만 모아서 새 배열로 리턴
          partyId={party.id}
          ottId={party.ottId}
          ottUrl={party.ottId ? platform.filter((a) => a.id === party.ottId) : ''}
          title={party.title}
          people={party.people}
          body={party.body}
          leaderNickname={party.leaderNickname}
        />
      ))}
      {data.filter((party)=>party.ottId!==selectedOtt).map((party) => (
        <Card
          // map 사용 시 key 값 필수 -> 유니크한 값 (보통 index나 id 사용 )
          key={party.id}
          // platform 중에 ottId 가 같은 것만 모아서 새 배열로 리턴
          partyId={party.id}
          ottId={party.ottId}
          ottUrl={party.ottId ? platform.filter((a) => a.id === party.ottId) : ''}
          title={party.title}
          people={party.people}
          body={party.body}
          leaderNickname={party.leaderNickname}
        />
      ))} */}
    </Board>
    </Wrapper>
    </>
  );
}

export default MyParty;
