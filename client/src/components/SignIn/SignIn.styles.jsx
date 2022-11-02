import styled from 'styled-components';
import { Flex, Input, Button } from '../../styles/Common';

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
  ${Flex};
  width: 100%;
`;

const LoginInput = styled(Input)`
  margin-bottom: 0;
  margin-top: 0.5rem;
`;

const LoginButton = styled(Button)`
  margin-top: 0.5rem;
`;

const LinkContainer = styled.div`
  ${Flex}
  width: 100%;
  font-size: 0.9rem;
  font-weight: 600;

  button {
    font-size: 0.9rem;
    font-weight: 600;
    color: ${(props) => props.theme.color.warning_300};
    background-color: transparent;
  }
  a {
    color: ${(props) => props.theme.color.success_200};
  }
`;

const ErrorMessage = styled.p`
  width: 100%;
  padding-left: 1rem;
  margin-top: 0.2rem;
  font-size: 0.8rem;
  color: red;
`;

export { Wrapper, LoginInput, LoginButton, Desc, LoginForm, ErrorMessage, LinkContainer };
