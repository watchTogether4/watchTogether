import React from 'react';
import { CardWrapper, CardDesc, InfoList, Visible } from './Card.styles';

export function Card({ trader, dt, result, type }) {
  const date = dt.slice(0, 5);
  console.log(date.join(','));
  const year = date[0];
  const month = date[1];
  const day = date[2];
  const hour = date[3];
  const min = date[4];

  const format = `${year}년 ${month}월 ${day}일 ${hour}시 ${min}분 `;

  return (
    <>
      <CardWrapper type="button">
        <CardDesc>
          <InfoList>
            <li>
              <span>결제일: </span>
              <span>{format}</span>
            </li>
            <li>
              <span>결제 결과: </span>
              <span>{result}</span>
            </li>
            <li>
              <span>결제 유형: </span>
              <span>{type}</span>
            </li>
            <li>
              <Visible trader={trader}>거래자 닉네임: </Visible>
              <Visible trader={trader}>{trader}</Visible>
            </li>
          </InfoList>
        </CardDesc>
      </CardWrapper>
    </>
  );
}
