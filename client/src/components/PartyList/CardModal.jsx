import React from 'react';
import { useNavigate } from 'react-router-dom'; 
import { toast, ToastContainer } from 'react-toastify';
import { ButtonContainer, SubmitButton, CancleButton } from '../../styles/Common';
import { Title, Description } from '../Modal/Modal.styles';
import { CardOtt, CardText, DescContainer, Desc1, Desc2 } from './CardModal.styles';
import Modal from '../Modal/Modal';
import { showPartyList } from '../../api/Parties';

const CardModal = ({ modal, ottUrl, title, people, body, ottId, partyId, leaderNickname }) => {
  const navigate = useNavigate();
  const alarmNagivate = () => {toast('결제 페이지로 이동합니다!', {
    autoClose: 3000,
    position: 'top-center',
  })
  setTimeout(() => {
    navigate('/payment', { state: { title: title, people: people, body: body, ottId: ottId, partyId: partyId, leaderNickname: leaderNickname } });
  }, 3000);
  };
  const image = ottUrl ? ottUrl[0].image : '';
  const handleClick = () => {
    modal(false);
  };
  return (
    <>
      <ToastContainer />
        <Modal>
            <Title>{title}</Title>
            <DescContainer direction="column" justifyContent="flex-start">
              <Desc1 direction="row">
                <CardOtt src={image}/>
                <CardText>모집 인원 : {people} / 4</CardText>
              </Desc1>
              <Desc2>
                <Description>{body}</Description>
              </Desc2>
            </DescContainer>
            <ButtonContainer>
              <CancleButton type='button' onClick={handleClick}>
                취소하기 
              </CancleButton>
              <SubmitButton type='button' onClick={() => {alarmNagivate()}}>신청하기</SubmitButton>
            </ButtonContainer>
          </Modal>
    </>
  )
};

export default CardModal;
