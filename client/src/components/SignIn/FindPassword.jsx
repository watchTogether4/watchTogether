import React, { useState, useEffect } from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import Modal from './../Modal/Modal';
import { ModalInput, ButtonContainer, SubmitButton, CancleButton } from '../../styles/Common';
import { Title, ErrorMessage } from './../Modal/Modal.styles';
import { findPasswordAPI } from './../../api/User';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const FindPassword = ({ modal }) => {
  const [message, setMessage] = useState('');
  const { values, errors, handleChange, handleSubmit } = useFormik({
    initialValues: {
      email: '',
      nickname: '',
    },
    validationSchema: Yup.object().shape({
      email: Yup.string().required('이메일을 입력해주세요'),
      nickname: Yup.string().required('닉네임을 입력해주세요'),
    }),
    onSubmit: (values) => {
      findPasswordAPI(values)
        .then((res) => {
          console.log(res.data);
          toast.success(
            <>
              <h1>비밀번호 초기화 메일 전송 완료</h1>
              <p>메일을 확인해주세요.</p>
            </>,
            {
              position: 'top-center',
              autoClose: 1000,
            },
          );
          setTimeout(() => {
            modal(false);
          }, 1000);
        })
        .catch((error) => {
          console.log(error.response.data.message);
          setMessage(error.response.data.message);
        });
    },
  });

  const handleClick = (e) => {
    e.preventDefault();
    modal(false);
  };

  useEffect(() => {
    setMessage('');
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [values]);

  const error = errors.email || errors.nickname;
  return (
    <>
      <ToastContainer />
      <Modal handleSubmit={handleSubmit}>
        <Title>비밀번호 찾기</Title>

        <ModalInput
          type="text"
          name="email"
          placeholder="Email"
          value={values.email}
          onChange={handleChange}
        />
        <ModalInput
          type="text"
          name="nickname"
          placeholder="NickName"
          value={values.nickname}
          onChange={handleChange}
        />
        {errors && <ErrorMessage>{error}</ErrorMessage>}
        {message && <ErrorMessage>{message}</ErrorMessage>}

        <ButtonContainer>
          <CancleButton type="button" onClick={handleClick}>
            취소하기
          </CancleButton>
          <SubmitButton type="submit">비밀번호 찾기</SubmitButton>
        </ButtonContainer>
      </Modal>
    </>
  );
};

export default FindPassword;
