import React from 'react';
import { useNavigate } from 'react-router-dom';
import {
  CardWrapper,
  CardOtt,
  CardTitle,
  CardPerson,
  } from './Card.styles';

export function Card({ottUrl, title, person}) {
  const navigate = useNavigate();

  return(
    <CardWrapper onClick={() => {
      navigate(`/signUp`)
    }}>
      <CardOtt src={ottUrl}/>
      <CardTitle>{title}
        넷플릭스 패밀리 구해요!
      </CardTitle>
      <CardPerson>{person}
        3/4
      </CardPerson>
    </CardWrapper>
  );
}

