/* eslint-disable react/prop-types */
import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Wrapper, Container, AlertTitle, AlertText, SubmitButton } from './AlertModal.styles';

const AlertModal = ({ modal, alert }) => {
  const navigate = useNavigate();
  const handleClick = () => {
    if (alert.title === '파티 등록 완료') {
      navigate('/');
      modal(false);
    }
    modal(false);
  };

  return (
    <Wrapper>
      <Container>
        <AlertTitle>{alert.title}</AlertTitle>
        <AlertText>{alert.message}</AlertText>
        <SubmitButton type="button" onClick={handleClick}>
          확인
        </SubmitButton>
      </Container>
    </Wrapper>
  );
};

export default AlertModal;
