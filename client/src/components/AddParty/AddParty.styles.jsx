import styled from 'styled-components';
import { Button, Input } from '../../styles/Common';

const Wrapper = styled.div`
  position: relative;
  padding: 2rem 1rem;
`;

const Header = styled.header`
  width: 100%;
`;

const Description = styled.p`
  margin-bottom: 1rem;
  font-size: 0.85rem;
`;

const GatherForm = styled.form`
  width: 100%;
  display: flex;
  flex-direction: column;
`;

const Label = styled.label`
  margin-bottom: 0.5rem;
  font-weight: 700;
  font-size: 1rem;
`;

const CustomInput = styled(Input)`
  /* border-radius: 40px; */
`;

const Text = styled.textarea`
  resize: none;
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
  Text,
  SubmitButton,
  ErrorMessage,
};
