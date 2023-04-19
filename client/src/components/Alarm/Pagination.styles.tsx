import styled from 'styled-components';

const Nav = styled.nav`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 5px;
  margin: 1rem;
`;

const Button = styled.button`
  width: 2.5rem;
  height: 2.5rem;
  display: flex;
  align-items: center;
  justify-content: center;
  border: none;
  border-radius: 8px;
  background: ${(props) => props.theme.color.light_300};
  color: white;
  font-size: 1rem;

  &[disabled] {
    background: ${(props) => props.theme.color.dark_100};
    cursor: revert;
    transform: revert;
  }

  &[aria-current] {
    background: ${(props) => props.theme.color.highlight_500};
    font-weight: bold;
    cursor: revert;
    transform: revert;
  }
`;

export { Nav, Button };
