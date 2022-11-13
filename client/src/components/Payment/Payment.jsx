import React from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { useQuery } from 'react-query';
import otts from '../../mocks/platform';
import { joinPartyAPI } from './../../api/Parties';
import { myPageAPI } from './../../api/User';
import { withdrawAPI } from './../../api/Transaction';
import {
  Wrapper,
  Border,
  MainTitle,
  Highlight,
  HighlightTwo,
  HighlightRed,
  InfoList,
  ButtonSection,
} from './Payment.styles';
import { Button } from '../../styles/Common';
import { toast, ToastContainer } from 'react-toastify';

function Payment() {
  const navigate = useNavigate();
  const getUserInfo = () => {
    return myPageAPI().then((res) => res.data);
  };

  const joinUserParty = () => {
    joinPartyAPI(body)
      .then((res) => {
        console.log(res.data);
        toast.success(<h1>성공적으로 신청되었습니다.</h1>, {
          position: 'top-center',
          autoClose: 1500,
        });
        setTimeout(() => {
          navigate('/partyList');
        }, 1500);
      })
      .catch((error) => {
        console.log(error);
        toast.error(error.response.data.message, {
          position: 'top-center',
          autoClose: 1000,
        });
      });
  };

  const Payment = () => {
    withdrawAPI(body2)
      .then((res) => {
        console.log(res.data);
        joinUserParty();
      })
      .catch((error) => {
        console.log(error);
        toast.error(error.response.data.message, {
          position: 'top-center',
          autoClose: 1000,
        });
      });
  };

  const { state } = useLocation();
  const title = state.title;
  const partyId = state.partyId;
  const leaderNickname = state.leaderNickname;

  const { data } = useQuery('getInfo', getUserInfo);
  let cash = 0;
  let nickName = '';
  if (data) {
    cash = data.cash;
    nickName = data.nickname;
  }

  const platform = state.ottId;
  const platformPrice = otts[platform - 1].price / 4;
  const platformPriceFormat = platformPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  const totalPrice = otts[platform - 1].price / 4 + 900;
  const totalPriceFormat = totalPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  const afterCash = cash - totalPrice;
  const afterCashFormat = afterCash.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');

  const body = { nickName: nickName, partyId: partyId };
  const body2 = { partyId: partyId };

  return (
    <>
      <ToastContainer />
      <Wrapper>
        {data && (
          <div>
            <Border>
              <MainTitle>고객 정보</MainTitle>
              <InfoList>
                <li>
                  <span>이름</span>
                  <span>{data.nickname}</span>
                </li>
                <li>
                  <span>이메일</span>
                  <span>{data.email}</span>
                </li>
              </InfoList>
            </Border>
            <Border>
              <MainTitle>결제 정보</MainTitle>
              <InfoList>
                <li>
                  <span>파티 모집 명</span>
                  <span>{title}</span>
                </li>
                <li>
                  <span>플랫폼 금액</span>
                  <span>{platformPriceFormat} 원</span>
                </li>
                <li>
                  <span>월 파티원 이용료</span>
                  <span>900 원</span>
                </li>
                <li>
                  <span>결제 금액</span>
                  <span>
                    <HighlightRed>{totalPriceFormat} 원</HighlightRed>
                  </span>
                </li>
                <li>
                  <span>보유 캐쉬</span>
                  <span>
                    <Highlight>{data.cash} 원</Highlight>
                  </span>
                </li>
                <li>
                  <span>결제 후 캐쉬 잔액</span>
                  <span>
                    <HighlightTwo>{afterCashFormat} 원</HighlightTwo>
                  </span>
                </li>
              </InfoList>
            </Border>
            <ButtonSection>
              <Button
                type="button"
                onClick={() => {
                  Payment();
                }}
              >
                신청하기
              </Button>
            </ButtonSection>
          </div>
        )}
      </Wrapper>
    </>
  );
}

export default Payment;
