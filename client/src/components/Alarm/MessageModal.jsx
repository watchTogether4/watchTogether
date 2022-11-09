import React from 'react';
import { useSelector } from 'react-redux';
import { toast, ToastContainer } from 'react-toastify';
import Modal from './../Modal/Modal';
import { Title, AlertText } from './../Modal/Modal.styles';
import { ButtonContainer, SubmitButton, CancleButton } from '../../styles/Common';
import { putAlert } from '../../api/Alert';
import { acceptParty } from '../../api/Parties';

const MessageModal = ({ data, modal }) => {
  const { value } = useSelector((state) => state.user);
  const accessToken = localStorage.getItem('access-token');

  const handleClick = (e) => {
    e.preventDefault();
    // type = invite 일경우 서버 데이터 전송
    // 공통 : 읽음처리 , 모달 off
    const body = { notificationId: data.notificationId };
    const body2 = { nick: value.nickname, uuid: data.uuid };
    putAlert(body, accessToken)
      .then((res) => {
        acceptParty(body2, accessToken)
          .then((res) => {
            console.log(res.data);
          })
          .catch((error) => {
            console.log(error.response.data.message);
            toast.error(error.response.data.message, {
              position: 'top-center',
              autoClose: 1000,
            });
          });
        modal(false);
      })
      .catch((error) => console.log(error));
  };

  // 취소
  const handleClickCancle = (e) => {
    e.preventDefault();
    modal(false);
  };

  return (
    <>
      <ToastContainer />
      <Modal>
        <Title onClick={() => modal(false)}>{data.type}</Title>
        <AlertText>{data.message}</AlertText>
        <ButtonContainer>
          <CancleButton onClick={handleClickCancle}>취소</CancleButton>
          <SubmitButton onClick={handleClick}>
            {data.type === 'INVITE' ? '수락 하기' : '확인'}
          </SubmitButton>
        </ButtonContainer>
      </Modal>
    </>
  );
};

export default MessageModal;
