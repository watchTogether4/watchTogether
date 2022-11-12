import React, { useState, useEffect } from 'react';
import { useQuery } from 'react-query';
import { useNavigate } from 'react-router-dom';
import { findMyParties } from './../../api/Parties';
import { getInfo } from './../../api/Users';
import { Wrapper, Board, Highlight, HighlightRed, HighlightTwo } from './MyParty.styles';
import { Card } from './Card';

function MyParty() {
  let nickName = '';
  const accessToken = localStorage.getItem('access-token');
  const [myParty, setMyParty] = useState();
  const [waiting, setWaiting] = useState();
  const getUserInfo = () => {
    return getInfo(accessToken).then((res) => res.data);
  };

  const { data } = useQuery('getInfo', getUserInfo);

  if (data) {
    nickName = data.nickname;
  }

  const body = { nickName: nickName };

  useEffect(() => {
    findMyParties(body, accessToken).then((res) => {
      setMyParty(res.data[0]); // 완성된 파티
      setWaiting(res.data[1]); // 대기중인 파티
    });
  }, [data]);

  return (
    <>
      <Wrapper>
        {waiting && (
          <Board>
            <Highlight>대기 중 파티</Highlight>
            {waiting.map((party) => (
              <Card key={party.id} data={party} nickName={nickName} />
            ))}
          </Board>
        )}
        {myParty && (
          <Board>
            <Highlight>내 파티</Highlight>
            {myParty.map((party) => (
              <Card key={party.id} data={party} nickName={nickName} />
            ))}
          </Board>
        )}
      </Wrapper>
    </>
  );
}

export default MyParty;
