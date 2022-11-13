import React, { useState, useEffect } from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { toast, ToastContainer } from 'react-toastify';

import { ButtonContainer, SubmitButton, CancleButton, ModalInput } from '../../styles/Common';
import { Title, ErrorMessage } from '../Modal/Modal.styles';
import 'react-toastify/dist/ReactToastify.css';

import Modal from '../Modal/Modal';
import { checkPasswordAPI } from '../../api/User';
import { useNavigate } from 'react-router-dom';

const PasswordConfirm = ({ modal, data, setPassword, setIsChecked }) => {
  const [message, setMessage] = useState('');

  const { values, errors, handleChange, handleSubmit } = useFormik({
    initialValues: {
      password: '',
    },

    validationSchema: Yup.object().shape({
      password: Yup.string()
        .min(8, '비밀번호는 최소 8자리 이상입니다!')
        .max(16, '비밀번호는 최대 16자리입니다!')
        .required('현재 비밀번호를 입력하세요!'),
    }),

    onSubmit: async (values) => {
      const body = { password: values.password };
      checkPasswordAPI(body)
        .then((response) => {
          toast.success(<h1>비밀번호를 성공적으로 확인했습니다.</h1>, {
            position: 'top-center',
            autoClose: 1000,
          });

          setTimeout(() => {
            modal(false);
            setPassword(values.password);
            setIsChecked(true);
          }, 1000);
        })
        .catch((error) => {
          setMessage(error.response.data.message);
        });
    },
  });

  const handleClick = () => {
    modal(false);
  };

  useEffect(() => {
    setMessage('');
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [values]);

  return (
    <>
      <ToastContainer />
      <Modal handleSubmit={handleSubmit}>
        <Title>비밀번호 확인</Title>
        <ModalInput
          type="password"
          name="password"
          placeholder="비밀번호"
          value={values.password}
          onChange={handleChange}
        />
        {errors && <ErrorMessage>{errors.password}</ErrorMessage>}
        {message && <ErrorMessage>{message}</ErrorMessage>}

        <ButtonContainer>
          <CancleButton type="button" onClick={handleClick}>
            취소
          </CancleButton>
          <SubmitButton type="submit">확인</SubmitButton>
        </ButtonContainer>
      </Modal>
    </>
  );
};

export default PasswordConfirm;
