import styled from 'styled-components';
import { Button, Input } from '../../styles/Common';

const Wrapper = styled.div`
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5);
  overflow: hidden;
`;

const Container = styled.form`
  display: flex;
  flex-direction: column;
  position: absolute;
  width: 80%;
  padding: 1.5rem 1rem;
  background-color: #fff;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border-radius: 12px;
`;

const Title = styled.h2`
  margin-bottom: 1rem;
  font-weight: 600;
  font-size: 1.1rem;
  text-align: center;
`;

const Description = styled.p`
  font-size: 0.85rem;
  color: ${(props) => props.theme.color.dark_300};
`;
const PasswordInput = styled(Input)`
  height: 2.5rem;
  margin-bottom: 0.5rem;
`;

const ButtonContainer = styled.div`
  margin-top: 0.5em;
  display: flex;
  justify-content: space-between;
`;

const SubmitButton = styled(Button)`
  width: 48%;
  height: 2.5rem;
  margin-bottom: 0;
  border-radius: 1rem;
`;

const CancleButton = styled(SubmitButton)`
  background-color: ${(props) => props.theme.color.warning_300};
`;

const AlertTitle = styled.h2`
  font-size: 1.2rem;
  font-weight: 700;
  text-align: center;
  margin-bottom: 1rem;
  white-space: pre-wrap;
`;

const AlertText = styled.p`
  font-size: 1rem;
  text-align: center;
  margin-bottom: 1rem;
  white-space: pre-wrap;
`;

export {
  Wrapper,
  Container,
  Title,
  Description,
  ButtonContainer,
  PasswordInput,
  SubmitButton,
  CancleButton,
  AlertTitle,
  AlertText,
};
