import React from 'react';
import { useLocation } from 'react-router-dom';
import { useQuery } from 'react-query';
import { Card } from './Card';
import { getAllPartyAPI } from './../../api/Parties';
import { Wrapper, Board } from './PartyList.styles';
import platform from '../../mocks/platform';

function PartyList() {
  const { state } = useLocation();
  let selectedOtt = 1;
  if (state == null) {
    selectedOtt = 1;
  } else {
    selectedOtt = state.ott;
  }

  const getBoardList = () => {
    return getAllPartyAPI().then((res) => res.data);
  };

  // 파티 리스트가 [{}, {}] 형태로 data에 쌓임
  const { data, isLoading } = useQuery('getBoardList', getBoardList, {
    retry: false, // 데이터 불러오기 실패하면 다시 시도 안함
  });

  return (
    <>
      <Wrapper>
        {/* data가 있을 경우에만 렌더링  */}

        {isLoading ? (
          <div>Loading...</div>
        ) : (
          <Board>
            {data
              .filter((party) => party.ottId === selectedOtt)
              .map((party) => (
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
            {data
              .filter((party) => party.ottId !== selectedOtt)
              .map((party) => (
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
          </Board>
        )}
      </Wrapper>
    </>
  );
}

export default PartyList;
