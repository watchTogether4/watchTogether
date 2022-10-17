import styled from 'styled-components';

const Input = styled.input`
  width: 100%;
  height: 50px;
  padding: 0 16px;
  margin-bottom: 16px;
  outline: none;
  border: 1px solid ${(props) => props.theme.border};
  border-radius: 12px;
`;

export default {
  Input,
};
