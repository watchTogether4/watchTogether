import React from 'react';
import { useNavigate } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';

import { ButtonContainer, SubmitButton, CancleButton } from '../../styles/Common';
import { AlertTitle, AlertText } from '../Modal/Modal.styles';

import Modal from '../Modal/Modal';
import { leavePartyAPI } from '../../api/Parties';
import { withdrawCancelAPI } from '../../api/Transaction';

const AlertModal = ({ modal, data, leaderNickname, id }) => {
  const navigate = useNavigate();
  const handleClick = (e) => {
    const name = e.target.dataset.name;
    if (name === 'cancle') modal(false);
    if (name === 'leave') {
      if (data.nickName === leaderNickname) {
        toast.error(<h1>파티장은 파티를 나갈 수 없습니다! 😠</h1>, {
          position: 'top-center',
          autoClose: 2000,
        });
        setTimeout(() => {
          modal(false);
        }, 2000);
      } else {
        cancelTransaction();
        leaveParty();
      }
    }
  };

  console.log(data);

  const data2 = id;

  const cancelTransaction = () => {
    withdrawCancelAPI(data2)
      .then((res) => {
        console.log(res.data);
      })
      .catch((error) => {
        console.log(error);
        console.log(error.response.data.message);
      });
  };

  const leaveParty = () => {
    leavePartyAPI(data)
      .then((res) => {
        console.log(res.data);
        toast.success(
          <>
            <h1>파티 나가기 완료</h1>
          </>,
          {
            position: 'top-center',
            autoClose: 1500,
          },
        );

        setTimeout(() => {
          modal(false);
          navigate('/mypage');
        }, 1500);
      })
      .catch((error) => {
        console.log(error);
        console.log(error.response.data.message);
      });
  };

  return (
    <>
      <ToastContainer />
      <Modal>
        <AlertTitle>파티 나가기</AlertTitle>
        <AlertText>정말 파티를 나가시겠습니까? 😥</AlertText>
        <ButtonContainer>
          <CancleButton type="button" onClick={handleClick} data-name="cancle">
            취소하기
          </CancleButton>
          <SubmitButton type="button" onClick={handleClick} data-name="leave">
            파티 나가기
          </SubmitButton>
        </ButtonContainer>
      </Modal>
    </>
  );
};

export default AlertModal;
