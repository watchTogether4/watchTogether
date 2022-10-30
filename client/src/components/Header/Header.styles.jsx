import styled from 'styled-components';
import { Flex } from './../../styles/Common';

const Wrapper = styled.header`
  ${Flex}
  position: relative;
  width: 100%;
  height: 4rem;
  padding: 0 1rem;
`;

const Title = styled.h2`
  font-size: 1.2rem;
  font-weight: 600;
`;

const BackBtn = styled.button`
  width: 3rem;
  height: 3rem;
  background-color: transparent;
  position: absolute;
  left: 0.5rem;
`;


export { Wrapper, Title, BackBtn };
