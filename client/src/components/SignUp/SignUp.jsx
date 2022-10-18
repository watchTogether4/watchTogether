/* eslint-disable react/jsx-props-no-spreading */
import React, { useState } from 'react';
import Axios from 'axios';
import { Link } from 'react-router-dom';
import { Input, Button } from '../../styles/Common';
import { SignUpForm, LoginLink, Wrapper } from './SignUp.style';

function SignUp() {
  const [email, setEmail] = useState('');
  const [nickname, setNickname] = useState('');
  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');
  const [birthday, setBirthday] = useState('');

  const onEmailHandler = (event) => {
    setEmail(event.currentTarget.value);
  };
  const onNicknameHandler = (event) => {
    setNickname(event.currentTarget.value);
  };
  const onPasswordHandler = (event) => {
    setPassword(event.currentTarget.value);
  };
  const onPasswordConfirmHandler = (event) => {
    setPasswordConfirm(event.currentTarget.value);
  };
  const onBirthdayHandler = (event) => {
    setBirthday(event.currentTarget.value);
  };

  const onSubmitHandler = (event) => {
    event.preventDefault();

    const body = {
      email,
      password,
      nickname,
      birthday
    };
    Axios.post('/api/users/signUp', body);
  };

  return (
    <Wrapper direction="column" justifyContent="space-evenly">
      <SignUpForm direction="column" justifyContent="space-evenly">
        <Input type="email" value={email} name="Email" onChange={onEmailHandler} />
        <Input type="text" value={nickname} name="Nickname" onChange={onNicknameHandler} />
        <Input type="password" value={password} name="Password" onChange={onPasswordHandler} />
        <Input type="password" value={passwordConfirm} name="PasswordConfirm" onChange={onPasswordConfirmHandler} />
        <Input type="date" value={birthday} name="Birthday" onChange={onBirthdayHandler} />

        <Button type="button" onClick={onSubmitHandler}>
          회원가입하기
        </Button>
        <LoginLink>
          <p>가치와치 회원이십니까?</p>
          <Link to="/login">로그인하기</Link>
        </LoginLink>
      </SignUpForm>
    </Wrapper>
  );
}

export default SignUp;
