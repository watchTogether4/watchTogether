import React, { useState } from 'react';
import { Card } from './Card'
import { showPartyList } from './../../api/Parties';
import { Wrapper, Board } from './PartyList.styles';
import icons from '../../mocks/platform';

function PartyList() {
  const accessToken = localStorage.getItem('access-token');
  const [boardList, setBoardList] = useState([]);
  const getBoardList = () => {
    return showPartyList(accessToken).then((res) => {
      console.log(res.data)});
  };
  getBoardList().then(result=> setBoardList(result));
  // const sortList = () => {
  // };

  return (
    <>
      <Wrapper>
        <Board>
          {boardList.map((data) => (
            <Card 
              ottUrl={data.partyOttId} 
              title={data.title} 
              people={data.people}
            />
          ))}
        </Board>
      </Wrapper>
    </>
  );
}

export default PartyList;
