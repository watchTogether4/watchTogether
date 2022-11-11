import React, { useState } from 'react';
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
  const body = { notificationId: data.notificationId };
  const body2 = { nick: value.nickname, uuid: data.uuid };

  const [isLoading, setIsLoading] = useState(false);

  const postAlert = () => {
    putAlert(body, accessToken)
      .then((res) => {
        console.log(res.data);
        acceptMember();
      })
      .catch((error) => console.log(error));
  };

  const acceptMember = () => {
    acceptParty(body2, accessToken)
      .then((res) => {
        toast.success(<h1>파티에 가입되었습니다.</h1>, {
          position: 'top-center',
          autoClose: 1000,
        });
        setTimeout(() => {
          modal(false);
          setIsLoading(false);
        }, 1500);
      })
      .catch((error) => {
        console.log(error.response.data.message);
        toast.error(error.response.data.message, {
          position: 'top-center',
          autoClose: 1000,
        });
        setIsLoading(false);
      });
  };
  const handleClick = (e) => {
    e.preventDefault();
    if (isLoading === false) {
      setIsLoading(true);
      postAlert();
    }
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
          <CancleButton onClick={handleClickCancle}>
            {data.type === 'INVITE' ? '거절 하기' : '확인'}
          </CancleButton>
          <SubmitButton onClick={handleClick}>
            {data.type === 'INVITE' ? '수락 하기' : '확인'}
          </SubmitButton>
        </ButtonContainer>
      </Modal>
    </>
  );
};

export default MessageModal;
