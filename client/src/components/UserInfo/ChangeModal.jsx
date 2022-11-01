/* eslint-disable react/prop-types */
import React, { useState } from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { toast, ToastContainer } from 'react-toastify';
import {
  Wrapper,
  Container,
  Title,
  PasswordInput,
  ButtonContainer,
  SubmitButton,
  CancleButton,
} from './Modal.style';
import { isCurrentPassword } from './../../api/Users';
import { putNewPassword } from './../../api/Users';
import { useEffect } from 'react';
import 'react-toastify/dist/ReactToastify.css';

const ChangePassword = ({ modal }) => {
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
          '영어, 숫자, 공백을 제외한 특수문자를 모두 포함해야 합니다!',
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

  const validateCurrent = (currentPassword) => {
    const body = { password: currentPassword };
    isCurrentPassword(body)
      .then((res) => {
        if (message !== '') setMessage('');
        setIsValidate(true);
      })
      .catch((error) => {
        setMessage('현재 비밀번호가 일치하지 않습니다.');
      });
  };

  const validateNew = (newPassword) => {
    const body = { password: newPassword };
    putNewPassword(body)
      .then((res) => {
        console.log('success');

        toast.success(<h1>{res.data.message}</h1>, {
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

  // 취소 버튼 클릭 이벤트
  const handleClick = () => {
    modal(false);
  };

  const error = errors.currentPassword || errors.newPassword || errors.confirmPassword;

  return (
    <Wrapper>
      <ToastContainer />
      <Container onSubmit={handleSubmit}>
        <Title>비밀번호 변경</Title>
        <PasswordInput
          type="password"
          name="currentPassword"
          placeholder="현재 비밀번호"
          onChange={handleChange}
          value={values.currentPassword}
        />
        <PasswordInput
          type="password"
          name="newPassword"
          placeholder="새 비밀번호"
          onChange={handleChange}
          value={values.newPassword}
        />
        <PasswordInput
          type="password"
          name="confirmPassword"
          placeholder="비밀번호 확인"
          onChange={handleChange}
          value={values.confirmPassword}
        />
        {errors && <div>{error}</div>}
        {message && <div>{message}</div>}
        <ButtonContainer>
          <CancleButton type="button" onClick={handleClick}>
            취소하기
          </CancleButton>
          <SubmitButton type="submit">변경하기</SubmitButton>
        </ButtonContainer>
      </Container>
    </Wrapper>
  );
};

export default ChangePassword;
