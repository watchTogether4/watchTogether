import React from 'react';
import { useLocation } from 'react-router-dom'; 
import { useQuery } from 'react-query';
import { findMyParties } from './../../api/Parties';
import { Wrapper, Board } from './MyParty.styles';

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

  // 파티 리스트가 [{}, {}] 형태로 data에 쌓임
  const { data } = useQuery('getBoardList', getBoardList);

  console.log(data);

  return (
    <>
    <Wrapper>
        안녕
    </Wrapper>
    </>
  );
}

export default MyParty;
