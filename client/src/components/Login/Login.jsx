import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { Input, Button } from '../../styles/Common';
import { Wrapper, Desc, LoginForm, SignUpLink } from './Login.styles';

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const signIn = () => console.log(email, password);
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

      <LoginForm direction="column" justifyContent="space-evenly">
        <Input
          mb="0.3rem"
          type="text"
          name="userEmail"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <Input
          type="password"
          name="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <Button type="button" onClick={signIn}>
          로그인
        </Button>
        <SignUpLink>
          <p>가치와치 계정이 없으신가요? </p>
          <Link to="/signUp">회원가입</Link>
        </SignUpLink>
      </LoginForm>
    </Wrapper>
  );
}

export default Login;
