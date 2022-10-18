import styled from 'styled-components';

const Input = styled.input`
  width: 100%;
  height: 3rem;
  padding: 0 1rem;
  margin-bottom: ${(props) => (props.mb ? props.mb : '1rem')};
  outline: 0;
  border: 1px solid ${(props) => props.theme.color.dark_100};
  border-radius: 12px;
`;

export default Input;
