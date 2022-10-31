import styled from 'styled-components';
import { Flex } from '../../styles/Common';

const InfoList = styled.ul`
  ${Flex}
  padding: 2rem;
`;

const List = styled.li`
  ${Flex}
  width: 100%;
  border-bottom: 1px solid ${(props) => props.theme.color.light_300};
  cursor: pointer;

  a {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    height: 100%;
    padding: 1.2rem 1rem;
  }
`;

export { InfoList, List };
