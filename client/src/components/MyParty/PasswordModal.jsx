import React, { useState, useEffect } from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { toast, ToastContainer } from 'react-toastify';

import { ButtonContainer, SubmitButton, CancleButton, ModalInput } from '../../styles/Common';
import { Title } from '../Modal/Modal.styles';
import 'react-toastify/dist/ReactToastify.css';

import Modal from '../Modal/Modal';
import { changePasswordAPI } from '../../api/Parties';

const PasswordModal = ({ modal, partyId, partyOttPassword, nickName }) => {
  const { values, handleChange, handleSubmit } = useFormik({
    initialValues: {
      currentPassword: '',
      newPassword: '',
      confirmPassword: '',
    },

    validationSchema: Yup.object().shape({
      currentPassword: Yup.string(),
      newPassword: Yup.string().notOneOf(
        [Yup.ref('currentPassword')],
        '현재 비밀번호와 같은 비밀번호로 변경할 수 없습니다!',
      ),
      confirmPassword: Yup.string()
        .oneOf([Yup.ref('newPassword'), null], '비밀번호가 일치하지 않습니다!')
        .required('비밀번호 확인을 입력하세요!'),
    }),

    onSubmit: async (values) => {
      changePartyPassword(values.newPassword);
    },
  });

  const changePartyPassword = (newPassword) => {
    const body = {
      nickname: nickName,
      partyId: partyId,
      password: partyOttPassword,
      newPassword: newPassword,
    };

    changePasswordAPI(body)
      .then((res) => {
        toast.success(<h1>OTT 계정 비밀번호가 변경 되었습니다</h1>, {
          position: 'top-center',
          autoClose: 1000,
        });
        setTimeout(() => {
          modal(false);
        }, 1000);
      })
      .catch((error) => {
        toast(error.response.data.message);
      });
  };

  const handleClick = () => {
    modal(false);
  };

  return (
    <>
      <ToastContainer />
      <Modal handleSubmit={handleSubmit}>
        <Title>OTT 계정 비밀번호 변경</Title>
        <ModalInput
          type="password"
          name="currentPassword"
          placeholder="현재 비밀번호"
          value={values.currentPassword}
          onChange={handleChange}
        />
        <ModalInput
          type="password"
          name="newPassword"
          placeholder="새 비밀번호"
          value={values.newPassword}
          onChange={handleChange}
        />
        <ModalInput
          type="password"
          name="confirmPassword"
          placeholder="새 비밀번호 확인"
          value={values.confirmPassword}
          onChange={handleChange}
        />

        <ButtonContainer>
          <CancleButton type="button" onClick={handleClick}>
            취소하기
          </CancleButton>
          <SubmitButton type="submit">변경하기</SubmitButton>
        </ButtonContainer>
      </Modal>
    </>
  );
};

export default PasswordModal;
