import styled from 'styled-components';

const Button = styled.button`
  width: 100%;
  height: 3rem;
  margin-bottom: 1rem;
  font-size: 1rem;
  color: white;
  background-color: ${(props) => props.theme.highlight.darkest};
  border-radius: 12px;
`;

export default { Button };
