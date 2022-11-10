import React from 'react';
import { CardWrapper, CardDesc, InfoList, Visible } from './Card.styles';

export function Card({ trader, dt, result, type }) {
  const traderIsNull = trader;
  console.log(traderIsNull);

  return (
    <>
      <CardWrapper type='button'>
        <CardDesc>
          <InfoList>
            <li>
              <span>결제일: </span>
              <span>{dt}</span>
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
              <Visible>거래자 닉네임: </Visible>
              <Visible trader = {trader}>{trader}</Visible>
            </li>
          </InfoList>
        </CardDesc>
      </CardWrapper>
    </>
  );
}
