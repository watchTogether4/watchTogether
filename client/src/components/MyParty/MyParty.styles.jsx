import styled from 'styled-components';

const Wrapper = styled.div`
  width: 100%;
  padding: 1rem 1.5rem 60px 1.5rem;
`;

const Board = styled.div`
  width: 100%;
`;

const Highlight = styled.p`
  color: ${(props) => props.theme.color.highlight_500};
  font-weight: 600;
  font-size: 1.1rem;
  padding: 0.75rem 0.5rem;
`;

const HighlightTwo = styled.p`
  color: ${(props) => props.theme.color.success_300};
  font-weight: 600;
`;

const HighlightRed = styled.p`
  color: ${(props) => props.theme.color.warning_300};
  font-weight: 600;
`;

export { Wrapper, Board, Highlight, HighlightRed, HighlightTwo, };
