import React from 'react';
import { useNavigate } from 'react-router-dom';
import {
  CardWrapper,
  CardOtt,
  CardTitle,
  CardPerson,
  } from './Card.styles';

export function Card({ott, title, people}) {
  const navigate = useNavigate();

  return(
    <CardWrapper onClick={() => {
      navigate(`/signUp`)
    }}>
      <CardOtt src={ott}/>
      <CardTitle>
        {title}
      </CardTitle>
      <CardPerson>
        {people}
      </CardPerson>
    </CardWrapper>
  );
}

