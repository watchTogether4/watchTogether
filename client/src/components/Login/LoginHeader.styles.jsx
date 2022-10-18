import styled from 'styled-components';
import { Flex } from '../../styles/Common';

const Wrapper = styled.header`
  ${Flex}
  position: relative;
  width: 100%;
  height: 4rem;
  padding: 0 1rem;
  background-color: ${(props) => props.theme.color.light_100};
`;

const Title = styled.h2`
  font-size: 1.2rem;
  font-weight: 600;
`;

const BackBtn = styled.button`
  width: 3rem;
  height: 3rem;
  position: absolute;
  left: 0.5rem;
  background-color: transparent;
`;

export { Wrapper, Title, BackBtn };
