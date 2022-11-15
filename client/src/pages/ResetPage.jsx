import React, { useState, useEffect } from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { useParams, useNavigate } from 'react-router-dom';
import { ModalInput, ButtonContainer, SubmitButton, CancleButton } from './../styles/Common';
import { Title, ErrorMessage } from '../components/Modal/Modal.styles';
import Modal from './../components/Modal/Modal';
import { checkResetPasswordAPI, NewPasswordAPI } from './../api/User';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const ResetPage = () => {
  const { code } = useParams();
  const navigate = useNavigate();

  const [isOpen, setIsOpen] = useState(false);
  const [message, setMessage] = useState('');

  const { values, errors, handleChange, handleSubmit } = useFormik({
    initialValues: {
      password: '',
      confirmPassword: '',
    },

    validationSchema: Yup.object().shape({
      password: Yup.string()
        .min(8, '비밀번호는 최소 8자리 이상입니다!')
        .max(16, '비밀번호는 최대 16자리입니다!')
        .required('비밀번호를 입력해주세요!')
        .matches(
          /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?])[^\s]*$/,
          '비밀번호는 영어,숫자,특수문자를 모두 포함해야 합니다.',
        ),
      passwordConfirm: Yup.string()
        .oneOf([Yup.ref('password'), null], '비밀번호가 일치하지 않습니다!')
        .required('비밀번호 확인을 입력하세요!'),
    }),

    onSubmit: (values) => {
      const body = {
        code: String(code),
        password: values.password,
      };

      NewPasswordAPI(body)
        .then((res) => {
          console.log(res.data);
          toast.success(
            <>
              <h1>비밀번호가 변경 되었습니다</h1>
              <p>새 비밀번호로 로그인 해주세요!</p>
            </>,
            {
              position: 'top-center',
              autoClose: 1000,
            },
          );
          setTimeout(() => {
            navigate('/signIn');
          }, 1000);
        })
        .catch((error) => {
          console.log(error);
          setMessage(error.response.data.message);
        });
    },
  });

  const handleClick = (e) => {
    e.preventDefault();
    navigate('/signIn');
  };
  useEffect(() => {
    checkResetPasswordAPI(code)
      .then((res) => {
        setIsOpen(true);
      })
      .catch((error) => {
        console.log(error.response.data.message);
      });
  }, [code]);

  const error = errors.password || errors.passwordConfirm;

  return (
    <>
      <ToastContainer />
      {isOpen && (
        <Modal handleSubmit={handleSubmit}>
          <Title>비밀번호 변경</Title>
          <ModalInput
            type="password"
            name="password"
            placeholder="새 비밀번호"
            value={values.password || ''}
            onChange={handleChange}
          />
          <ModalInput
            type="password"
            name="passwordConfirm"
            placeholder="비밀번호 확인"
            value={values.passwordConfirm || ''}
            onChange={handleChange}
          />

          {errors && <ErrorMessage>{error}</ErrorMessage>}
          {message && <ErrorMessage>{message}</ErrorMessage>}

          <ButtonContainer>
            <CancleButton type="button" onClick={handleClick}>
              취소하가
            </CancleButton>
            <SubmitButton type="submit">변경하기</SubmitButton>
          </ButtonContainer>
        </Modal>
      )}
    </>
  );
};

export default ResetPage;
