import styled from 'styled-components';
import { Flex } from '../../styles/Common';

const Wrapper = styled.div`
  ${Flex}
  padding: 1rem 2rem;
  width: 100%;
  height: calc(100vh-80px);
`;

const OttSection = styled.div`
  ${Flex}
  width: 100%;
  height: calc(75vh);
  padding: 0rem;
  margin: 0rem;
`;

const ButtonSection = styled.div`
  ${Flex}
  width: 100%;
  height: 100%;
  padding: 0rem;
  margin: 1rem 0rem;
`;

const OttButton = styled.div`
  ${Flex}
  margin: 0.5rem;
  padding: 0.5rem;
  text-align: center;
  width: 100%;
  height: 100%;
  border-radius: 12px;
  background-color: #F8F9FE;
  box-shadow: 0 5px 10px -7px rgba(0,0,0,1);
  overflow: hidden;
  border: 2px solid ${props=>props.clicked ? props.theme.color.highlight_500: '#ffffff' };

  &:hover{
    border: 2px solid ${(props) => props.theme.color.highlight_500};
  }
`;

export {
    Wrapper,
    OttSection,
    ButtonSection,
    OttButton,
};