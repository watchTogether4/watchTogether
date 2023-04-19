import styled from 'styled-components';

const Input = styled.input`
  width: 100%;
  height: 3rem;
  padding: 0 1rem;
  margin-bottom: 1rem;
  outline: 0;
  border: 1px solid ${(props) => props.theme.color.dark_100};
  border-radius: 12px;
`;

const ModalInput = styled(Input)`
  border: 0;
  border-radius: 1rem;
  background-color: ${(props) => props.theme.color.light_200};
`;

export { Input, ModalInput };
