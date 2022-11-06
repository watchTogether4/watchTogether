import React from 'react';
import Modal from './../Modal/Modal';
import { Title, AlertText } from './../Modal/Modal.styles';
import { ButtonContainer, SubmitButton, CancleButton } from '../../styles/Common';
const MessageModal = ({ data, modal }) => {
  // 확인
  const handleClick = (e) => {
    e.preventDefault();
    // type = invite 일경우 서버 데이터 전송
    // 공통 : 읽음처리 , 모달 off
  };

  // 취소
  const handleClickCancle = (e) => {
    e.preventDefault();
    modal(false);
  };

  return (
    <Modal>
      <Title onClick={() => modal(false)}>{data.type}</Title>
      <AlertText>{data.message}</AlertText>
      <ButtonContainer>
        <CancleButton onClick={handleClickCancle}>취소</CancleButton>
        <SubmitButton onClick={handleClick}>
          {data.type === 'invite' ? '수락 하기' : '확인'}
        </SubmitButton>
      </ButtonContainer>
    </Modal>
  );
};

export default MessageModal;
