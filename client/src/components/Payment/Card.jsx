import React from 'react';
import { CardWrapper, CardDesc, InfoList, Visible } from './Card.styles';
import 'moment/locale/ko';

export function Card({ trader, dt, result, type }) {
  const traderIsNull = trader;
  const moment = require("moment");
  const date = moment(dt.toString()).format('YY년 MM월 DD일, HH시 mm분 ss초');
  console.log(traderIsNull);

  return (
    <>
      <CardWrapper type='button'>
        <CardDesc>
          <InfoList>
            <li>
              <span>결제일: </span>
              <span>{date}</span>
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
              <Visible trader = {trader}>거래자 닉네임: </Visible>
              <Visible trader = {trader}>{trader}</Visible>
            </li>
          </InfoList>
        </CardDesc>
      </CardWrapper>
    </>
  );
}