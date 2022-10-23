import { useFormik } from 'formik';
import * as Yup from 'yup';
import { Link } from 'react-router-dom';
import { SignUpForm, Wrapper, ErrorMsg, Input, LoginLink, Button } from './SignUp.style';

function SignUp() {
  const validationSchema = Yup.object().shape({
    email: Yup.string()
      .email('올바른 이메일 형식이 아닙니다!')
      .required('이메일을 입력하세요!'),
    nickname: Yup.string()
      .min(2, '닉네임은 최소 2글자 이상입니다!')
      .max(10, '닉네임은 최대 10글자입니다!')
      .required('닉네임을 입력하세요!'),
    password: Yup.string()
      .min(4, '비밀번호는 최소 4자리 이상입니다!')
      .max(16, '비밀번호는 최대 16자리입니다!')
      .required('비밀번호를 입력하세요!'),
    passwordConfirm: Yup.string()
      .oneOf([Yup.ref('password'), null], '비밀번호가 일치하지 않습니다!')
      .required('비밀번호 확인을 입력하세요!'),
    birthday: Yup.string()
      .required('생일을 선택해주세요!')
  });

  const onSubmit = () => {
    console.log('제출되었습니다.');
  };

  const { values, errors, handleBlur, handleChange, handleSubmit, } = useFormik({
    initialValues: {
      email: '',
      nickname: '',
      password: '',
      passwordConfirm: '',
      birthday: '',
    },
    validationSchema,
    onSubmit,
  });

  console.log(errors);

  return (
    <Wrapper>
      <SignUpForm autoComplete="off" direction="column" justifyContent="space-evenly" onSubmit={handleSubmit}>
        <ErrorMsg>
          {errors.email}
        </ErrorMsg>
        <Input
          value={values.email}
          onChange={handleChange}
          id="email"
          type="email"
          placeholder="이메일"
          onBlur={handleBlur}
        />
        <ErrorMsg>
          {errors.nickname}
        </ErrorMsg>
        <Input
          value={values.nickname}
          onChange={handleChange}
          id="nickname"
          type="text"
          placeholder="닉네임"
          onBlur={handleBlur}
        />
        <ErrorMsg>
          {errors.password}
        </ErrorMsg>
        <Input
          value={values.password}
          onChange={handleChange}
          id="password"
          type="password"
          placeholder="비밀번호"
          onBlur={handleBlur}
        />
        <ErrorMsg>
          {errors.passwordConfirm}
        </ErrorMsg>
        <Input
          value={values.passwordConfirm}
          onChange={handleChange}
          id="passwordConfirm"
          type="password"
          placeholder="비밀번호 확인"
          onBlur={handleBlur}
        />
        <ErrorMsg>
          {errors.birthday}
        </ErrorMsg>
        <Input
          value={values.birthday}
          onChange={handleChange}
          id="birthday"
          type="date"
          placeholder="생일"
          onBlur={handleBlur}
        />
        <Button type="button" onClick={onSubmit}>
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
