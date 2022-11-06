import styled from 'styled-components';
import { Flex } from '../../styles/Common';

const CardOtt = styled.img`
  height: 100%;
  width: 12%;
  margin-right: 1rem;
`;

const DescContainer = styled.div`
  ${Flex}
  width: 100%;
  padding: 0rem;
  margin: 0rem;
`;

const Desc1 = styled.div`
  ${Flex}
  width: 100%;
`;

const Desc2 = styled.div`
  ${Flex}
  width: 100%;
  margin: 1rem;
  padding: 1rem;
  border: 0.2px solid ${(props) => props.theme.color.dark_100};
  border-radius: 12px;
`;

const CardDesc = styled.div`
  width: 100%;
  padding-right: 1rem;
`;

const CardText = styled.p`
  font-size: 1rem;
  font-weight: 600;
  line-height: 1.2rem;
  color: ${(props) => props.theme.color.dark_500};
`;

export { CardOtt, DescContainer, CardDesc, CardText, Desc1, Desc2 };