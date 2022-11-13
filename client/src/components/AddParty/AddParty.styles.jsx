import styled from 'styled-components';
import { Button, Input } from '../../styles/Common';

const Wrapper = styled.div`
  position: relative;
  /* padding: 0 1rem; */
`;

const Header = styled.header`
  width: 100%;
`;

const Description = styled.p`
  margin-bottom: 1rem;
  font-size: 0.85rem;
  text-align: center;
`;

const GatherForm = styled.form`
  width: 100%;
  padding: 0 1rem;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
`;

const Label = styled.label`
  margin-bottom: 0.5rem;
  font-weight: 700;
  font-size: 1rem;
`;

const CustomInput = styled(Input)`
  /* border-radius: 40px; */
`;

const ButtonContainer = styled.div`
  display: flex;
  align-items: center;
`;

const CustomButton = styled.button`
  height: 3rem;
  margin-left: 0.5rem;
  background-color: transparent;
  font-size: 1rem;
  font-weight: 600;
`;

const Text = styled.textarea`
  resize: none;
  width: 100%;
  height: 8rem;
  line-height: 1.2rem;
  margin-bottom: 0.5rem;
  padding: 1rem;
  border: 1px solid ${(props) => props.theme.color.dark_100};
  border-radius: 12px;
`;
const SubmitButton = styled(Button)``;

const ErrorMessage = styled.p`
  width: 100%;
  margin-bottom: 0.5rem;
  font-size: 0.9rem;
  color: red;
`;

export {
  Wrapper,
  Header,
  Description,
  GatherForm,
  Label,
  CustomInput,
  ButtonContainer,
  CustomButton,
  Text,
  SubmitButton,
  ErrorMessage,
};
