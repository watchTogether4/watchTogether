import React from 'react';
import { useNavigate } from 'react-router-dom';
import { CardWrapper, CardOtt, CardDesc, CardTitle, CardPerson } from './Card.styles';
import { IoIosArrowForward } from 'react-icons/io';

export function Card({ ottUrl, title, people }) {
  const navigate = useNavigate();
  const image = ottUrl ? ottUrl[0].image : '';
  console.log(ottUrl);
  return (
    <CardWrapper
      onClick={() => {
        navigate(`/`);
      }}
    >
      <CardOtt src={image} />
      <CardDesc>
        <CardTitle>{title}</CardTitle>
        <CardPerson>모집 인원 : {people} / 4</CardPerson>
      </CardDesc>
      <IoIosArrowForward size={30} />
    </CardWrapper>
  );
}
