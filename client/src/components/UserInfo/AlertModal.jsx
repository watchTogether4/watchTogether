/* eslint-disable react/prop-types */
import React from 'react';
import { useNavigate } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
import {
  Wrapper,
  Container,
  ButtonContainer,
  AlertTitle,
  AlertText,
  CancleButton,
  SubmitButton,
} from './Modal.style';
import { getInfo } from '../../api/Users';

const AlertModal = ({ modal }) => {
  const navigate = useNavigate();
  const handleClick = (e) => {
    const name = e.target.dataset.name;
    if (name === 'cancle') modal(false);
    if (name === 'withdrawal') {
      withdrawal();
    }
  };

  const withdrawal = () => {
    // 서버 데이터 전송 함수
    getInfo().then((res) => {
      toast.success(<h1>정상적으로 탈퇴 처리 되었습니다.</h1>, {
        position: 'top-center',
        autoClose: 1000,
      });
      setTimeout(() => {
        modal(false);
        navigate('/');
      }, 1000);
    });
  };

  return (
    <Wrapper>
      <ToastContainer />
      <Container>
        <AlertTitle>회원 탈퇴</AlertTitle>
        <AlertText>정말 탈퇴하시나요?</AlertText>
        <ButtonContainer>
          <CancleButton type="button" onClick={handleClick} data-name="cancle">
            취소하기
          </CancleButton>
          <SubmitButton type="button" onClick={handleClick} data-name="withdrawal">
            탈퇴하기
          </SubmitButton>
        </ButtonContainer>
      </Container>
    </Wrapper>
  );
};

export default AlertModal;
