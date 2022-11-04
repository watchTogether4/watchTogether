import styled from 'styled-components';
import { Flex } from '../../styles/Common';

const Wrapper = styled.div`
  ${Flex}
  padding: 1rem 2rem;
`;

const ImageContainer = styled.div`
  width: 130px;
  height: 130px;
  margin-bottom: 0.8rem;
  border: 1px solid grey;
  overflow: hidden;
  border-radius: 50%;
`;

const Image = styled.img`
  width: 100%;
  height: 100%;
`;

const UserName = styled.p`
  margin-bottom: 0.25rem;
  font-size: 1.1rem;
  font-weight: 600;
  text-align: center;
`;

const UserEmail = styled.p`
  font-size: 0.8rem;
  font-weight: 600;
`;

export { Wrapper, ImageContainer, Image, UserName, UserEmail };
