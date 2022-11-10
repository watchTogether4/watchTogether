import styled from 'styled-components';

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  padding: 1rem 2rem;
`;

const Profile = styled.div`
  width: 100px;
  height: 100px;
  margin: 1rem auto;
  border-radius: 50%;
  border: 1px solid grey;
  overflow: hidden;

  img {
    width: 100%;
    height: 100%;
  }
`;

const InfoList = styled.ul`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  margin-bottom: 1rem;
  border-bottom: 1px solid ${(props) => props.theme.color.light_300};

  li {
    width: 100%;
    padding: 1rem 0;
    display: flex;
    justify-content: space-between;
    align-items: center;
    display: ${props=>(props.trader === null) ? 'none' : '' };
  }
`;

const Button = styled.button`
  padding: 0.5rem;
  color: ${(props) => props.theme.color.white};
  background-color: ${(props) => props.theme.color.highlight_500};
  border-radius: 10px;
`;

const ChargeButton = styled.button`
  padding: 0.5rem;
  font-size: 0.9rem;
  color: ${(props) => props.theme.color.white};
  background-color: ${(props) => props.theme.color.highlight_500};
  border-radius: 10px;
  margin-left: auto;
  margin-bottom: 1rem;
`;

const Withdrawal = styled.button`
  font-size: 0.9rem;
  color: ${(props) => props.theme.color.error_300};
  background-color: transparent;
`;

export { Wrapper, Profile, InfoList, Button, Withdrawal, ChargeButton, };
