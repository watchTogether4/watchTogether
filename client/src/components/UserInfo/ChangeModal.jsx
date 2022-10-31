/* eslint-disable react/prop-types */
import React, { useState, useEffect } from 'react';
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

const ChangePassword = ({ modal }) => {
  const initialValue = { currentPassword: '', newPassword: '', confirmPassword: '' };
  const [password, setPassword] = useState(initialValue);
  const [message, setMessage] = useState('');
  const [isValidate, setIsValidate] = useState(false);
  // const handleClick = () => {
  //   if (alert.title === '파티 등록 완료') {
  //     navigate('/');
  //     modal(false);
  //   }
  //   modal(false);
  // };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setPassword({ ...password, [name]: value });
  };

  // 취소 버튼 클릭 이벤트
  const handleClick = () => {
    modal(false);
  };

  const validateCurrent = (currentPassword) => {
    const body = { password: currentPassword };
    isCurrentPassword(body)
      .then((res) => {
        setMessage(res.data);
        modal(false);
      })
      .catch((error) => {
        console.log(error.response.data.message);
        setMessage(error.response.data.message);
      });
  };

  const validateNew = (newPassword) => {
    const body = { password: newPassword };
    putNewPassword(body)
      .then((res) => {
        console.log(res.data);
        setMessage(res.data);
      })
      .catch((error) => {
        console.log(error.response.data.message);
        setMessage(error.response.data.message);
      });
  };

  const formValidate = () => {
    const { currentPassword, newPassword, confirmPassword } = password;

    let error = '';

    if (!currentPassword) {
      error = '현재 비밀번호를 입력해주세요.';
    } else if (!newPassword) {
      error = '새 비밀번호를 입력해주세요.';
    } else if (!confirmPassword) {
      error = '비밀번호를 다시 한번 확인해주세요.';
    } else {
      if (currentPassword === newPassword) {
        error = '현재 비밀번호와 같은 비밀번호로 변경할 수 없습니다.';
      }
      if (newPassword !== confirmPassword) error = '비밀번호가 서로 다릅니다.';
    }

    return error;
  };

  const validatePassword = () => {
    const { currentPassword, newPassword } = password;
    setIsValidate(false);

    validateCurrent(currentPassword);

    if (isValidate === true) {
      validateNew(newPassword);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    setMessage(formValidate(password));

    if (formValidate(password) === '') {
      validatePassword();
    }

    setIsValidate(true);
  };

  useEffect(() => {
    if (message === '' && isValidate) {
      validatePassword();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [message]);

  return (
    <Wrapper>
      <Container onSubmit={handleSubmit}>
        <Title>비밀번호 변경</Title>
        <PasswordInput
          type="password"
          name="currentPassword"
          placeholder="현재 비밀번호"
          onChange={handleChange}
        />
        <PasswordInput
          type="password"
          name="newPassword"
          placeholder="새 비밀번호"
          onChange={handleChange}
        />
        <PasswordInput
          type="password"
          name="confirmPassword"
          placeholder="비밀번호 확인"
          onChange={handleChange}
        />
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
