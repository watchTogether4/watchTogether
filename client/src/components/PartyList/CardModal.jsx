import React, { useState } from 'react';
import { useQuery } from 'react-query';
import { toast, ToastContainer } from 'react-toastify';
import { ButtonContainer, SubmitButton, CancleButton } from '../../styles/Common';
import { Title, Description } from '../Modal/Modal.styles';
import { CardOtt, CardText, DescContainer, Desc1, Desc2 } from './CardModal.styles';
import Modal from '../Modal/Modal';
import { showPartyList } from '../../api/Parties';

const CardModal = ({ cardNo, modal, ottUrl }) => {
  const accessToken = localStorage.getItem('access-token');
  const getBoardList = () => {
    return showPartyList(accessToken).then((res) => res.data);
  };
  const { data } = useQuery('getBoardList', getBoardList);
  const image = ottUrl ? ottUrl[0].image : '';

  const handleClick = () => {
    modal(false);
  };

  console.log(data[cardNo-1]);
  
  return (
    <>
      <ToastContainer />
        {data && (
          <Modal>
            <Title>{data[cardNo-1].title}</Title>
            <DescContainer direction="column" justifyContent="flex-start">
              <Desc1 direction="row">
                <CardOtt src={image}/>
                <CardText>모집 인원 : {data[cardNo-1].people} / 4</CardText>
              </Desc1>
              <Desc2>
                <Description>{data[cardNo-1].body}</Description>
              </Desc2>
            </DescContainer>
            <ButtonContainer>
              <CancleButton type='button' onClick={handleClick}>
                취소하기 
              </CancleButton>
              <SubmitButton type='submit'>신청하기</SubmitButton>
            </ButtonContainer>
          </Modal>
        )}
    </>
  )
};

export default CardModal;
