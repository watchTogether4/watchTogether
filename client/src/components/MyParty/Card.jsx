import React from 'react';
import { useNavigate } from 'react-router-dom';
import { CardWrapper, CardDesc, InfoList, } from './Card.styles';
import { IoIosArrowForward } from 'react-icons/io';
export function Card({ data }) {
  const {myParty} = data;
  const { title, partyId, body, amount, startDt, endDt, ott, member } = myParty;
  const navigate = useNavigate();

  return (
    <>
      <CardWrapper type='button' onClick = { () => navigate('./{$partyId}', { state: {data: myParty} })}>
        <CardDesc>
          <InfoList>
            <li>
              <span>
                파티명: 
              </span>
              <span>
                {title}
              </span>
            </li>
            <li>
              <span>
                파티 소개: 
              </span>
              <span>
                {body}
              </span>
            </li>
            <li>
              <span>
                amount: 
              </span>
              <span>
                {amount}
              </span>
            </li>
            <li>
              <span>
                파티 생성일: 
              </span>
              <span>
                {startDt}
              </span>
            </li>
            <li>
              <span>
                파티 종료일: 
              </span>
              <span>
                {endDt}
              </span>
            </li>
            <li>
              <span>
                OTT 플랫폼: 
              </span>
              <span>
                {ott}
              </span>
            </li>
            <li>
              <span>
                구성 멤버: 
              </span>
              <span>
                {member}
              </span>
            </li>
          </InfoList>
        </CardDesc>
        <IoIosArrowForward size={30} />
      </CardWrapper>
    </>
  );
}
