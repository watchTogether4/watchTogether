import styled from 'styled-components';

const Wrapper = styled.div`
  width: 100%;
  padding: 1rem 1.5rem 60px 1.5rem;
`;

const Board = styled.div`
  width: 100%;
`;

const ButtonSection = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  flex-direction: column;
`;

const Withdrawal = styled.button`
  font-size: 0.9rem;
  font-weight: 600;
  margin-top: 1rem;
  padding: 0.5rem 1rem;
  align-items: center;
  color: ${(props) => props.theme.color.highlight_500};
  border: 2px solid ${(props) => props.theme.color.highlight_300};
  border-radius: 1.2rem;
  background-color: ${(props) => props.theme.color.white};
`;

const InfoList = styled.ul`
  display: flex;
  flex-direction: column;
  padding: 1rem 1rem;
  align-items: center;
  width: 100%;
  border: 2px solid ${(props) => props.theme.color.highlight_300};
  border-radius: 1.2rem;

  li {
    width: 100%;
    padding: 0.5rem 0;
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 0.95rem;
    font-weight: 400;
    line-height: 1.2rem;
  }
`;

const Highlight = styled.p`
  color: ${(props) => props.theme.color.highlight_500};
  font-weight: 600;
`;

const HighlightTwo = styled.p`
  color: ${(props) => props.theme.color.success_300};
  font-weight: 600;
`;

const HighlightRed = styled.p`
  color: ${(props) => props.theme.color.warning_300};
  font-weight: 600;
`;

export { Wrapper, Board, Withdrawal, InfoList, Highlight, HighlightRed, HighlightTwo, ButtonSection, };