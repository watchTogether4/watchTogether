import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useQuery } from 'react-query';
import { myPageAPI } from './../../api/User';
import { chargeAPI } from './../../api/Transaction';
import {
  Wrapper,
  Border,
  MainTitle,
  Highlight,
  HighlightTwo,
  HighlightRed,
  InfoList,
  Section,
  Button,
  SubmitButton,
  SmallButton,
} from './Charge.styles';
import { toast, ToastContainer } from 'react-toastify';
import PasswordConfirm from './PasswordConfirm';

function Charge() {
  const accessToken = localStorage.getItem('access-token');
  const getUserInfo = () => {
    return myPageAPI(accessToken).then((res) => res.data);
  };
  const { data } = useQuery('getInfo', getUserInfo);
  let cash = 0;
  let email = '';
  const [password, setPassword] = useState(0);
  const [isChecked, setIsChecked] = useState(false);
  if (data) {
    cash = data.cash;
    email = data.email;
  }
  const navigate = useNavigate();
  const [isOpen, setIsOpen] = useState(false);
  const [amount, setAmount] = useState(0);
  const [method, setMethod] = useState('신용카드');
  const afterCash = amount + cash;
  const handleClick = (amount) => {
    setAmount(amount);
  };
  const handleClick2 = (method) => {
    setMethod(method);
  };

  const body = { email: email, password: password, amount: amount };
  console.log(body);
  const chargeCash = () => {
    if (isChecked === true) {
      chargeAPI(body, accessToken)
        .then((res) => {
          console.log(res.data);
          toast.success(<h1>성공적으로 충전되었습니다!</h1>, {
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
    } else {
      toast.error(<h1>비밀번호 확인을 완료해주세요!</h1>);
    }
  };

  return (
    <>
      <ToastContainer />
      <Wrapper>
        <Border>
          <MainTitle>충전 금액</MainTitle>
          <Section direction="row" justifyContent="flex-start">
            <Button
              amount={5000}
              clicked={amount === 5000 ? true : false}
              onClick={() => handleClick(5000)}
            >
              5,000원
            </Button>
            <Button
              amount={10000}
              clicked={amount === 10000 ? true : false}
              onClick={() => handleClick(10000)}
            >
              10,000원
            </Button>
            <Button
              amount={20000}
              clicked={amount === 20000 ? true : false}
              onClick={() => handleClick(20000)}
            >
              20,000원
            </Button>
          </Section>
          <Section direction="row" justifyContent="flex-start">
            <Button
              amount={30000}
              clicked={amount === 30000 ? true : false}
              onClick={() => handleClick(30000)}
            >
              30,000원
            </Button>
            <Button
              amount={50000}
              clicked={amount === 50000 ? true : false}
              onClick={() => handleClick(50000)}
            >
              50,000원
            </Button>
            <Button
              amount={100000}
              clicked={amount === 100000 ? true : false}
              onClick={() => handleClick(100000)}
            >
              100,000원
            </Button>
          </Section>
        </Border>
        <Border>
          <MainTitle>충전 방식</MainTitle>
          <Section direction="row" justifyContent="flex-start">
            <Button
              method={'신용카드'}
              clicked={method === '신용카드' ? true : false}
              onClick={() => handleClick2('신용카드')}
            >
              신용카드
            </Button>
            <Button
              method={'카카오페이'}
              clicked={method === '카카오페이' ? true : false}
              onClick={() => handleClick2('카카오페이')}
            >
              카카오페이
            </Button>
            <Button
              method={'네이버페이'}
              clicked={method === '네이버페이' ? true : false}
              onClick={() => handleClick2('네이버페이')}
            >
              네이버페이
            </Button>
          </Section>
        </Border>
        <Border>
          <MainTitle>충전 정보</MainTitle>
          {data && (
            <InfoList>
              <li>
                <span>닉네임</span>
                <span>{data.nickname}</span>
              </li>
              <li>
                <span>이메일</span>
                <span>{data.email}</span>
              </li>
              <li>
                <span>비밀번호 확인하기</span>
                <span>
                  <SmallButton
                    type="button"
                    onClick={() => {
                      setIsOpen(true);
                    }}
                  >
                    비밀번호 확인
                  </SmallButton>
                </span>
              </li>
              <li>
                <span>결제수단</span>
                <span>
                  <Highlight>{method}</Highlight>
                </span>
              </li>
              <li>
                <span>충전 금액</span>
                <span>
                  <Highlight>{amount.toLocaleString('ko-KR')} 원</Highlight>
                </span>
              </li>
              <li>
                <span>보유 캐쉬</span>
                <span>
                  <HighlightRed>{data.cash.toLocaleString('ko-KR')} 원</HighlightRed>
                </span>
              </li>
              <li>
                <span>결제 후 캐쉬 잔액</span>
                <span>
                  <HighlightTwo>+ {afterCash.toLocaleString('ko-KR')} 원</HighlightTwo>
                </span>
              </li>
            </InfoList>
          )}
        </Border>
        <SubmitButton
          type="button"
          onClick={() => {
            chargeCash(body);
          }}
        >
          충전하기
        </SubmitButton>
      </Wrapper>
      {isOpen && (
        <PasswordConfirm
          modal={setIsOpen}
          data={data}
          setPassword={setPassword}
          setIsChecked={setIsChecked}
        />
      )}
    </>
  );
}

export default Charge;
