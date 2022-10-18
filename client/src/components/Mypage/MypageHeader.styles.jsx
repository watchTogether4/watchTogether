import styled from 'styled-components';
import { Flex } from '../../styles/Common';

const Wrapper = styled.header`
  ${Flex}
  width: 100%;
  height: 80px;
  padding: 0 1rem;
`;

const SubTitle = styled.h2`
  font-size: 1.2rem;
  font-weight: 600;
`;

export { Wrapper, SubTitle };
