import React, { useState, useEffect } from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { toast, ToastContainer } from 'react-toastify';

import { ButtonContainer, SubmitButton, CancleButton, ModalInput } from '../../styles/Common';
import { Title, ErrorMessage } from './../Modal/Modal.styles';
import 'react-toastify/dist/ReactToastify.css';

import Modal from './../Modal/Modal';
import { changePasswordAPI, checkPasswordAPI } from './../../api/User';

const PasswordModal = ({ modal }) => {
  const [message, setMessage] = useState('');
  const [isValidate, setIsValidate] = useState('');

  const { values, errors, handleChange, handleSubmit } = useFormik({
    initialValues: {
      currentPassword: '',
      newPassword: '',
      confirmPassword: '',
    },

    validationSchema: Yup.object().shape({
      currentPassword: Yup.string()
        .min(8, '비밀번호는 최소 8자리 이상입니다!')
        .max(16, '비밀번호는 최대 16자리입니다!')
        .required('현재 비밀번호를 입력하세요!'),
      newPassword: Yup.string()
        .min(8, '비밀번호는 최소 8자리 이상입니다!')
        .max(16, '비밀번호는 최대 16자리입니다!')
        .required('새 비밀번호를 입력하세요!')
        .matches(
          /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?])[^\s]*$/,
          '비밀번호는 영어, 숫자, 특수문자를 모두 포함해야 합니다.',
        )
        .notOneOf(
          [Yup.ref('currentPassword')],
          '현재 비밀번호와 같은 비밀번호로 변경할 수 없습니다!',
        ),
      confirmPassword: Yup.string()
        .oneOf([Yup.ref('newPassword'), null], '비밀번호가 일치하지 않습니다!')
        .required('비밀번호 확인을 입력하세요!'),
    }),

    onSubmit: async (values) => {
      validateCurrent(values.currentPassword);

      if (message === '' && isValidate) {
        validateNew(values.newPassword);
      }
    },
  });

  const accessToken = localStorage.getItem('access-token');
  const validateCurrent = (currentPassword) => {
    const body = { password: currentPassword };

    checkPasswordAPI(body)
      .then((res) => {
        if (message !== '') setMessage('');
        setIsValidate(true);
      })
      .catch((error) => {
        setMessage(error.response.data.message);
      });
  };

  const validateNew = (newPassword) => {
    const body = { password: newPassword };
    changePasswordAPI(body)
      .then((res) => {
        toast.success(<h1>비밀번호가 변경 되었습니다</h1>, {
          position: 'top-center',
          autoClose: 1000,
        });
        setTimeout(() => {
          modal(false);
        }, 1000);
      })
      .catch((error) => {
        setMessage(error.response.data.message);
      });
  };

  const handleClick = () => {
    modal(false);
  };

  useEffect(() => {
    if (message === '' && isValidate) {
      validateNew(values.newPassword);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [message]);

  useEffect(() => {
    setMessage('');
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [values]);

  const error = errors.currentPassword || errors.newPassword || errors.confirmPassword;

  return (
    <>
      <ToastContainer />
      <Modal handleSubmit={handleSubmit}>
        <Title>비밀번호 변경</Title>
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
        {errors && <ErrorMessage>{error}</ErrorMessage>}
        {message && <ErrorMessage>{message}</ErrorMessage>}

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
