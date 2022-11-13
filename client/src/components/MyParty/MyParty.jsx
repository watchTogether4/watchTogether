import React, { useState, useEffect } from 'react';
import { useQuery } from 'react-query';
import { Wrapper, Board, Highlight, HighlightRed, HighlightTwo } from './MyParty.styles';
import { findMyPartyAPI } from './../../api/Parties';
import { myPageAPI } from '../../api/User';
import { Card } from './Card';

function MyParty() {
  let nickName = '';
  const [myParty, setMyParty] = useState();
  const [waiting, setWaiting] = useState();

  const getInfo = () => {
    return myPageAPI().then((res) => res.data);
  };

  const { data } = useQuery('getBoardList', getInfo, {
    retry: false, // 데이터 불러오기 실패하면 다시 시도 안함
  });

  if (data) {
    nickName = data.nickname;
  }

  const body = { nickName: nickName };

  useEffect(() => {
    findMyPartyAPI(body).then((res) => {
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
