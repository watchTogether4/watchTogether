import React from 'react';
import { CardWrapper, CardDesc, CardTitle, CardPerson } from './Card.styles';

export function Card({ ottId, ottUrl, title, people, body, partyId, leaderNickname }) {
  return (
    <>
      <CardWrapper type='button'>
        <CardDesc>
          <CardTitle>{title}</CardTitle>
          <CardPerson></CardPerson>
        </CardDesc>
      </CardWrapper>
    </>
  );
}
