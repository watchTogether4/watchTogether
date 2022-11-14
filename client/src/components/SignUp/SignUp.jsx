import { useFormik } from 'formik';
import * as Yup from 'yup';
import { Link, useNavigate } from 'react-router-dom';
import { SignUpForm, Wrapper, ErrorMsg, Input, LoginLink, Button } from './SignUp.styles';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { signUpAPI } from '../../api/User';

function SignUp() {
  const navigate = useNavigate();

  const validationSchema = Yup.object().shape({
    email: Yup.string().email('올바른 이메일 형식이 아닙니다!').required('이메일을 입력하세요!'),
    nickname: Yup.string()
      .min(2, '닉네임은 최소 2글자 이상입니다!')
      .max(10, '닉네임은 최대 10글자입니다!')
      .required('닉네임을 입력하세요!'),
    password: Yup.string()
      .min(8, '비밀번호는 최소 8자리 이상입니다!')
      .max(16, '비밀번호는 최대 16자리입니다!')
      .required('비밀번호를 입력하세요!')
      .matches(
        /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?])[^\s]*$/,
        '영어, 숫자, 공백을 제외한 특수문자를 모두 포함해야 합니다!',
      ),
    passwordConfirm: Yup.string()
      .oneOf([Yup.ref('password'), null], '비밀번호가 일치하지 않습니다!')
      .required('비밀번호 확인을 입력하세요!'),
    birth: Yup.string().required('생일을 선택해주세요!'),
  });

  const onSubmit = async (values) => {
    const body = { ...values };
    try {
      signUpAPI(body).then(() => {
        toast.success(<h1>회원가입이 완료되었습니다.</h1>, {
          position: 'top-center',
          autoClose: 1000,
        });
      });
      setTimeout(() => {
        navigate('/signIn');
      }, 1500);
    } catch (e) {
      toast.error(e.response.data.message, {
        position: 'top-center',
      });
    }
  };

  const { values, errors, handleBlur, handleChange, handleSubmit } = useFormik({
    initialValues: {
      email: '',
      nickname: '',
      password: '',
      passwordConfirm: '',
      birth: '',
    },
    validationSchema,
    onSubmit,
  });

  return (
    <Wrapper>
      <ToastContainer />
      <SignUpForm
        autoComplete="off"
        direction="column"
        justifyContent="space-evenly"
        onSubmit={handleSubmit}
      >
        <ErrorMsg>{errors.email}</ErrorMsg>
        <Input
          value={values.email}
          onChange={handleChange}
          id="email"
          type="email"
          placeholder="이메일"
          onBlur={handleBlur}
        />
        <ErrorMsg>{errors.nickname}</ErrorMsg>
        <Input
          value={values.nickname}
          onChange={handleChange}
          id="nickname"
          type="text"
          placeholder="닉네임"
          onBlur={handleBlur}
        />
        <ErrorMsg>{errors.password}</ErrorMsg>
        <Input
          value={values.password}
          onChange={handleChange}
          id="password"
          type="password"
          placeholder="비밀번호"
          onBlur={handleBlur}
        />
        <ErrorMsg>{errors.passwordConfirm}</ErrorMsg>
        <Input
          value={values.passwordConfirm}
          onChange={handleChange}
          id="passwordConfirm"
          type="password"
          placeholder="비밀번호 확인"
          onBlur={handleBlur}
        />
        <ErrorMsg>{errors.birth}</ErrorMsg>
        <Input
          value={values.birth}
          onChange={handleChange}
          id="birth"
          type="date"
          placeholder="생일"
          onBlur={handleBlur}
        />
        <Button type="submit">회원가입하기</Button>
        <LoginLink>
          <p>가치와치 회원이십니까?</p>
          <Link to="/signIn">로그인하기</Link>
        </LoginLink>
      </SignUpForm>
    </Wrapper>
  );
}

export default SignUp;
