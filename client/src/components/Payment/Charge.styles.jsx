import styled from 'styled-components';
import { Flex } from '../../styles/Common';

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  padding: 0rem 2rem;
`;

const Border = styled.div`
  width: 100%;
  margin-bottom: 1rem;
  padding: 0rem 1rem;
  border: 1px solid ${(props) => props.theme.color.light_400};
  border-top: 4px solid ${(props) => props.theme.color.highlight_500};
  border-radius: 0.8rem;
`;

const Section = styled.div`
  ${Flex}
  width: 100%;
  padding: 0rem;
  margin: 0rem;
`;

const MainTitle = styled.p`
  padding: 0.5rem 0;
  margin-bottom: 0.25rem;
  font-size: 1.1rem;
  font-weight: 600;
  text-align: center;
  color: ${(props) => props.theme.color.dark_500};
  border-bottom: 1px solid ${(props) => props.theme.color.light_400};
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

const InfoList = styled.ul`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  margin-bottom: 1rem;

  li {
    width: 100%;
    padding: 1rem 0;
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 0.8rem;
    font-weight: 500;
    color: ${(props) => props.theme.color.dark_300};
  }
`;

const ButtonSection = styled.div`
  width: 100%;
  height: 100%;
  padding: 0rem;
  margin: 1rem 0rem;
`;

const Button = styled.button`
  width: 100%;
  height: 3rem;
  margin: 0.75rem 0.25rem;
  font-size: 1rem;
  color: ${(props) => (props.clicked ? '#ffffff' : props.theme.color.highlight_500)};
  background-color: ${(props) => (props.clicked ? props.theme.color.highlight_500 : '#ffffff')};
  border: 1px solid ${(props) => (props.clicked ? props.theme.color.white : props.theme.color.highlight_500)};
  border-radius: 12px;
`;

const SmallButton = styled.button`
  width: 100%;
  padding: 0rem 0.75rem;
  height: 1.5rem;
  font-size: 0.8rem;
  color: ${(props) => props.theme.color.white};
  background-color: ${(props) => props.theme.color.highlight_500};
  border-radius: 12px;
`;

const SubmitButton = styled.button`
  width: 100%;
  height: 3rem;
  margin-bottom: 1rem;
  font-size: 1rem;
  color: ${(props) => props.theme.color.white};
  background-color: ${(props) => props.theme.color.highlight_500};
  border-radius: 12px;
`;

export { Wrapper, Border, MainTitle, Highlight, HighlightTwo, HighlightRed, InfoList, ButtonSection, Section, Button, SubmitButton, SmallButton, };