import React, { useState, useEffect } from 'react';
import { useQuery } from 'react-query';
import { Wrapper, Board, Highlight, HighlightRed, HighlightTwo } from './MyParty.styles';
import { findMyPartyAPI } from './../../api/Parties';
import { Card } from './Card';
import { useOutletContext } from 'react-router-dom';

function MyParty() {
  let nickName = '';
  const { userInfo } = useOutletContext();
  const [myParty, setMyParty] = useState();
  const [waiting, setWaiting] = useState();

  if (userInfo) {
    nickName = userInfo.nickname;
  }

  const body = { nickName: nickName };
  useEffect(() => {
    findMyPartyAPI(body).then((res) => {
      console.log(body, res.data);
      setMyParty(res.data[0]); // 완성된 파티
      setWaiting(res.data[1]); // 대기중인 파티
    });
  }, []);

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
