import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { toast, ToastContainer } from 'react-toastify';
import Modal from './../Modal/Modal';
import { Title, AlertText } from './../Modal/Modal.styles';
import { ButtonContainer, SubmitButton, CheckButton, CancleButton } from '../../styles/Common';
import { checkAlertAPI } from '../../api/Alert';
import { acceptPartyAPI, checkContinueAPI } from '../../api/Parties';

const MessageModal = ({ data, modal }) => {
  const { value } = useSelector((state) => state.user);

  const body = { notificationId: data.notificationId };
  const body2 = { nick: value.nickname, uuid: data.uuid };

  const [isLoading, setIsLoading] = useState(false);
  const [type, setType] = useState(false);

  useEffect(() => {
    if (data.type === 'INVITE' || data.type === 'CONTINUE') {
      setType(!type);
    }
  }, [data.type]);

  const postAlert = () => {
    checkAlertAPI(body)
      .then((res) => {
        if (data.type === 'INVITE') {
          acceptMember();
        } else if (data.type === 'CONTINUE') {
          const body3 = { nickname: value.nickname, partyId: res.partyId };
          check(body3);
        } else {
          modal(false);
        }
      })
      .catch((error) => console.log(error));
  };

  const check = (body3) => {
    checkContinueAPI(body3).then((res) => {
      console.log(res.data);
    });
  };

  const acceptMember = () => {
    acceptPartyAPI(body2)
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
          {type ? (
            <>
              <CancleButton onClick={handleClickCancle}>
                {data.type === 'INVITE' ? '거절 하기' : '취소하기'}
              </CancleButton>
              <SubmitButton onClick={handleClick}>수락 하기</SubmitButton>
            </>
          ) : (
            <CheckButton onClick={handleClick}>확인</CheckButton>
          )}
        </ButtonContainer>
      </Modal>
    </>
  );
};

export default MessageModal;
