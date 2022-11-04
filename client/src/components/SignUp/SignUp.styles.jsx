import styled from 'styled-components';
import { Flex } from '../../styles/Common';

const Wrapper = styled.div`
  ${Flex}
  padding: 2rem;
  width: 100%;
  height: calc(100vh - 80px);
`;

const SignUpForm = styled.form`
  ${Flex}
  width: 100%;
`;

const LoginLink = styled.div`
  ${Flex}
  width: 100%;
  font-size: 0.9rem;
  font-weight: 600;
  p {
    margin-right: 0.5rem;
  }
  a {
    color: ${(props) => props.theme.color.success_200};
  }
`;

const ErrorMsg = styled.div`
  width: 100%;
  padding: 0.2rem 1rem;
  font-size: 0.8rem;
  font-weight: 600;
  color: blue;
`;

const Input = styled.input`
  width: 100%;
  height: 3rem;
  padding: 0 1rem;
  outline: 0;
  border: 1px solid ${(props) => props.theme.color.dark_100};
  border-radius: 12px;
`;

const Button = styled.button`
  width: 100%;
  height: 3rem;
  margin-top: 1rem;
  margin-bottom: 1rem;
  font-size: 1rem;
  color: ${(props) => props.theme.color.white};
  background-color: ${(props) => props.theme.color.highlight_500};
  border-radius: 12px;
`;

export { SignUpForm, LoginLink, Wrapper, ErrorMsg, Input, Button };
