import React from 'react';
import { useNavigate } from 'react-router-dom';
import { CardWrapper, CardDesc, InfoList } from './Card.styles';
import { IoIosArrowForward } from 'react-icons/io';
import otts from '../../mocks/platform';

export function Card({ data }) {
  console.log(data);
  const { id, title, ottId } = data;
  const navigate = useNavigate();
  const ottUrl = otts.filter((a) => a.id === ottId);

  // 사용자가 버튼을 클릭하면 id가 28이라고 가정할 때, mypage/myparty/28 로 이동하게 되고, id 28에 대한 데이터가 state로 담겨요!
  return (
    <>
      <CardWrapper type="button" onClick={() => navigate(`./${id}`, { state: { data: data } })}>
        <CardDesc>
          <InfoList>
            <img src={ottUrl[0].image} alt="" width="50px" height="50px" />
            <h2>{title}</h2>
          </InfoList>
        </CardDesc>
        <IoIosArrowForward size={30} />
      </CardWrapper>
    </>
  );
}
