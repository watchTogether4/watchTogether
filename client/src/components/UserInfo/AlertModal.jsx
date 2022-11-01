/* eslint-disable react/prop-types */
import React from 'react';
import { useNavigate } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';

import { ButtonContainer, SubmitButton, CancleButton } from '../../styles/Common';
import { AlertTitle, AlertText } from './../Modal/Modal.styles';

import Modal from './../Modal/Modal';
import { getInfo } from '../../api/Users';

const AlertModal = ({ modal }) => {
  const navigate = useNavigate();
  const handleClick = (e) => {
    const name = e.target.dataset.name;
    if (name === 'cancle') modal(false);
    if (name === 'withdrawal') {
      // 서버 데이터 전송 함수
      withdrawal();
    }
  };

  const withdrawal = () => {
    // 서버 데이터 전송 함수
    getInfo().then((res) => {
      toast.success(
        <>
          <h1>회원 탈퇴 완료</h1>
          <p>다음에 다시 만나요!😥</p>
        </>,
        {
          position: 'top-center',
          autoClose: 1500,
        },
      );
      setTimeout(() => {
        modal(false);
        navigate('/');
      }, 1500);
    });
  };

  return (
    <>
      <ToastContainer />
      <Modal>
        <AlertTitle>회원 탈퇴</AlertTitle>
        <AlertText>정말 탈퇴하시겠습니까? 😥</AlertText>
        <ButtonContainer>
          <CancleButton type="button" onClick={handleClick} data-name="cancle">
            취소하기
          </CancleButton>
          <SubmitButton type="button" onClick={handleClick} data-name="withdrawal">
            탈퇴하기
          </SubmitButton>
        </ButtonContainer>
      </Modal>
    </>
  );
};

export default AlertModal;
