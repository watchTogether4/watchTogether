import styled from 'styled-components';

const MenuWrapper = styled.ul`
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 60px;
  display: flex;
  align-items: center;
  box-shadow: 0 -1px 10px ${(props) => props.theme.color.light_200};
  background-color: ${(props) => props.theme.color.white};

  li {
    width: 25%;
    height: 100%;
  }

  a {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    height: 100%;
    padding: 0.5rem;
    font-size: 0.75rem;
    color: ${(props) => props.theme.color.dark_100};
  }
`;

export { MenuWrapper };
