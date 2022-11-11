import styled from 'styled-components';

const Wrapper = styled.div`
  width: 100%;
  padding: 1rem 1rem 60px 1rem;
`;

const Board = styled.div`
  width: 100%;
`;

const Withdrawal = styled.button`
  font-size: 0.9rem;
  color: ${(props) => props.theme.color.error_300};
  background-color: transparent;
`;

const InfoList = styled.ul`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;

  li {
    width: 100%;
    padding: 0.5rem 0;
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 0.8rem;
    font-weight: 400;
    line-height: 1.2rem;
  }
`;

export { Wrapper, Board, Withdrawal, InfoList };