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
    color:${(props) => props.theme.color.success_200};
  }
`;

export { SignUpForm, LoginLink, Wrapper };
