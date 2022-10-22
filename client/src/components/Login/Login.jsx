import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';
import {
  Wrapper,
  LoginInput,
  LoginButton,
  Desc,
  LoginForm,
  ErrorMessage,
  SignUpLink,
} from './Login.styles';

function Login() {
  const navigate = useNavigate();

  const intialValues = { email: '', password: '' };
  const [formValues, setFormValues] = useState(intialValues);
  const [formErrors, setFormErrors] = useState('');
  const [isVaildate, setIsValidate] = useState(false);

  const submitForm = () => {
    // 백으로 유저 정보 전달
    console.log(formValues);
    axios({
      url: '/api/users/signIn',
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      data: JSON.stringify(formValues)
    })
      .then((response) => {
        // 토큰 값 함수 생성
        console.log(response.data);
      })
      .catch((error) => {
        if (error.response) {
          // 요청이 이루어졌으며 서버가 2xx의 범위를 벗어나는 상태 코드로 응답함
          setFormErrors(error.response.data.errorMessage);
        } else if (error.request) {
          // 요청이 이루어 졌으나 응답을 받지 못함
          console.log(error.request);
        } else {
          // 오류를 발생시킨 요청을 설정하는 중에 문제 발생
          console.log('Error', error.message);
        }
        console.log(error.config);
      });
  };

  const validate = (values) => {
    let emailErrors = '';
    let passwordErrors = '';
    // 정규식 표현
    // eslint-disable-next-line operator-linebreak
    const regex =
      /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

    // 이메일 값이 없을시
    if (!values.email) {
      emailErrors = '이메일을 입력해주세요.';
      // 이메일 정규식 표현이 옳지 않을시
    } else if (!regex.test(values.email)) {
      emailErrors = '이메일이 정확하지 않습니다.';
    }

    // 비밀번호 값이 없을시
    if (!values.password) {
      passwordErrors = '비밀번호를 입력해주세요.';
      // 비밀번호의 길이(length)가 4글자 이하일 때
    } else if (values.password.length < 4) {
      passwordErrors = '비밀번호가 너무 짧습니다.';
    }

    return emailErrors || passwordErrors;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setFormErrors(validate(formValues));

    if (validate(formValues) === undefined) {
      setIsValidate(true);
      submitForm();
    }

    setIsValidate(true);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormValues({ ...formValues, [name]: value });
  };

  useEffect(() => {
    if (Object.keys(formErrors).length === 0 && isVaildate) {
      submitForm();
    }
  }, [formErrors]);

  return (
    <Wrapper direction="column" justifyContent="space-evenly">
      <Desc justifyContent="flex-start">
        쉬운 파티원 초대와 매칭,
        <br />
        결제까지 한번에.
        <br />
        가치와치
        <br />
      </Desc>

      <LoginForm
        onSubmit={handleSubmit}
        noValidate
        direction="column"
        justifyContent="space-evenly"
      >
        <LoginInput
          type="text"
          name="email"
          value={formValues.email}
          onChange={handleChange}
          required
        />
        <LoginInput
          type="password"
          name="password"
          value={formValues.password}
          onChange={handleChange}
          required
        />

        {formErrors && <ErrorMessage className="error">{formErrors}</ErrorMessage>}
        <LoginButton type="submit">로그인</LoginButton>
        <SignUpLink>
          <p>가치와치 계정이 없으신가요? </p>
          <Link to="/signUp">회원가입</Link>
        </SignUpLink>
      </LoginForm>
    </Wrapper>
  );
}

export default Login;
