import React from 'react';
import { useNavigate, useLocation } from 'react-router-dom'; 
import { useQuery } from 'react-query';
import otts from '../../mocks/platform';
import { joinParty } from './../../api/Parties';
import { getInfo } from './../../api/Users';
import { withdraw } from './../../api/Transaction';
import { Wrapper, Border, MainTitle, Highlight, HighlightTwo, HighlightRed, InfoList, ButtonSection } from './Payment.styles';
import { Button } from '../../styles/Common';
import { toast, ToastContainer } from 'react-toastify';
import axios from 'axios';

function Payment() {
  const accessToken = localStorage.getItem('access-token');
  const navigate = useNavigate();
  const getUserInfo = () => {
    return getInfo(accessToken).then((res) => res.data);
  };

  const joinUserParty = () => {
    axios
    .all([joinParty(body, accessToken), withdraw(body2, accessToken)])
    .then(axios.spread((res1, res2) => {
      console.log('success');
      toast.success(<h1>성공적으로 신청 되었습니다.</h1>,
        {position: 'top-center', autoClose: 3000, });
      setTimeout(() => {
        navigate('/myPage');
      }, 3000);
    })
    )
    .catch((error) => {
      console.log(error)
      console.error()
      toast.error(error.response.data.message, {
        position: 'top-center',
      });
    });
  };

  // const joinUserParty = () => {
  //   withdraw(body2, accessToken)
  //     .then((res) => {
  //       console.log(res.data);
  //       toast.success(<h1>모집 글이 등록되었습니다</h1>, {
  //         position: 'top-center',
  //         autoClose: 1500,
  //       });
  //       setTimeout(() => {
  //         navigate('/partyList');
  //       }, 1500);
  //     })
  //     .catch((error) => {
  //       console.log(error)
  //       toast.error(error.response.data.message, {
  //         position: 'top-center',
  //         autoClose: 1000,
  //       });
  //     });
  // };


  const { state } = useLocation();
  const title = state.title;
  const partyId = state.partyId;
  const leaderNickname = state.leaderNickname;

  const { data } = useQuery('getInfo', getUserInfo);
  let cash = 0;
  let nickName = '';
  if (data) {
    cash = data.cash;
    nickName = data.nickName;
  };

  const platform = state.ottId;
  const platformPrice = otts[platform-1].price / 4;
  const platformPriceFormat = platformPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  const totalPrice = (otts[platform-1].price / 4 + 500);
  const totalPriceFormat = totalPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  const afterCash = cash - totalPrice;
  const afterCashFormat = afterCash.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');

  const body = {nickName: nickName, partyId: partyId}
  const body2 = {partyId: partyId}

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
                <span>500 원</span>
                </li>
                <li>
                <span>결제 금액</span>
                <span><HighlightRed>{totalPriceFormat} 원</HighlightRed></span>
                </li>
                <li>
                <span>보유 캐쉬</span>
                <span><Highlight>{data.cash} 원</Highlight></span>
                </li>
                <li>
                <span>결제 후 캐쉬 잔액</span>
                <span><HighlightTwo>{afterCashFormat} 원</HighlightTwo></span>
                </li>
            </InfoList>
          </Border>
          <ButtonSection>
            <Button type='button' onClick={() => {joinUserParty(body)}}>신청하기</Button>
          </ButtonSection>
        </div>
      )}
    </Wrapper>
    </>
  );
}

export default Payment;