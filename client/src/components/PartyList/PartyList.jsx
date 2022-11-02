import React, { useState } from 'react';
import { useQuery } from 'react-query';
import { Card } from './Card'
import { listParty } from './../../api/Parties';
import { Wrapper, Board } from './PartyList.styles';

function PartyList() {
  const getList = () => {
    return listParty().then((res) => res.data);
  };
  const { data } = useQuery('listParty', getList);
  const [boardList] = useState([]);
  const sortList = () => {

  };

  return (
    <>
      <Wrapper>
        <Board>
          {boardList.map((data) => (
            <Card 
              ottUrl={`./Image/${data.ottId}`} 
              title={data.title} 
              person={data.person}
            />
          ))}
        </Board>
        <Card/>
        <Card/>
        <Card/>
        <Card/>
        <Card/>
        <Card/>
      </Wrapper>
    </>
  );
}

export default PartyList;
