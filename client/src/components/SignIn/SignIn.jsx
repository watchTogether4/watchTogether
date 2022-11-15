import React, { useState, useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import FindPassword from './FindPassword';
import { setRefreshToken, setAccessToken, setAuthentication } from '../../utils/index';
import { signInAPI, myPageAPI } from '../../api/User';
import { info } from '../../store/User';
import {
  Wrapper,
  LoginInput,
  LoginButton,
  Desc,
  LoginForm,
  ErrorMessage,
  LinkContainer,
} from './SignIn.styles';

function Login() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const intialValues = { email: '', password: '' };
  const [formValues, setFormValues] = useState(intialValues);
  const [formErrors, setFormErrors] = useState('');
  const [isVaildate, setIsValidate] = useState(false);
  const [isOpen, setIsOpen] = useState(false);

  const setToken = (data) => {
    setRefreshToken(data.refreshToken);
    setAccessToken(data.accessToken);
    setAuthentication(true);
  };

  const saveUserInfo = (data) => {
    myPageAPI().then((res) => {
      dispatch(info(res.data));
    });
  };

  const submitForm = async () => {
    await signInAPI(formValues)
      .then((res) => {
        setToken(res.data);
        saveUserInfo(res.data);

        toast.success(<h1>성공적으로 로그인했습니다.</h1>, {
          position: 'top-center',
          autoClose: 1000,
        });
        setTimeout(() => {
          navigate('/partyList');
        }, 1000);
      })

      .catch((error) => {
        console.log(error);
        toast.error(error.response.data.message, {
          position: 'top-center',
          autoClose: 1000,
        });
      });
  };

  const validate = (values) => {
    let emailErrors = '';
    let passwordErrors = '';

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
    // 유효성 함수 호출 후 에러 저장
    setFormErrors(validate(formValues));

    // 에러 없이 바로 유효성 검사 통과할 경우
    if (validate(formValues) === '') {
      setIsValidate(true);
      submitForm();
    }

    setIsValidate(true);
  };

  const handleClick = (e) => {
    e.preventDefault();
    setIsOpen(true);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormValues({ ...formValues, [name]: value });
  };

  // 에러 발생시 처리
  useEffect(() => {
    if (formValues === '' && isVaildate) {
      submitForm();
    }
  }, [formErrors]);

  return (
    <Wrapper direction="column" justifyContent="space-evenly">
      <ToastContainer />
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
        <LinkContainer justifyContent="space-between">
          <button type="button" onClick={handleClick}>
            비밀번호 찾기
          </button>
          <Link to="/signUp">회원가입</Link>
        </LinkContainer>
      </LoginForm>

      {isOpen && <FindPassword modal={setIsOpen} />}
    </Wrapper>
  );
}

export default Login;
