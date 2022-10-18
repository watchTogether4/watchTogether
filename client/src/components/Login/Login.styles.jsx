import styled from 'styled-components';
import { Flex } from '../../styles/Common';

const Wrapper = styled.div`
  width: 100%;
  height: calc(100vh - 80px);
  padding: 2rem;
  ${Flex}
`;

const Desc = styled.div`
  ${Flex}
  width: 100%;
  height: 30vh;
  font-weight: 800;
  font-size: 1.5rem;
`;

const LoginForm = styled.form`
  width: 100%;
  ${Flex};
`;

const SignUpLink = styled.div`
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

export { Wrapper, Desc, LoginForm, SignUpLink };
