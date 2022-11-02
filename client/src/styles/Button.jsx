import styled from 'styled-components';

const ButtonContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
`;

const Button = styled.button`
  width: 100%;
  height: 3rem;
  margin-bottom: 1rem;
  font-size: 1rem;
  color: ${(props) => props.theme.color.white};
  background-color: ${(props) => props.theme.color.highlight_500};
  border-radius: 12px;
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

export { ButtonContainer, Button, SubmitButton, CancleButton };
