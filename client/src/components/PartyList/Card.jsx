import React from 'react';
import { useNavigate } from 'react-router-dom';
import OttImage from './Image/netflix.png';
import {
  CardWrapper,
  CardOtt,
  CardTitle,
  CardPerson,
  } from './Card.styles';

export function Card() {
  const navigate = useNavigate();

  return(
    <CardWrapper onclick={() => {
      navigate(`https://localhost:3000/signIn`)
    }}>
      <CardOtt src={OttImage}/>
      <CardTitle>
        넷플릭스 패밀리 구해요!
      </CardTitle>
      <CardPerson>
        3/4
      </CardPerson>
    </CardWrapper>
  );
}

