import Slider from 'react-slick';
import styled from 'styled-components';
import { Button } from '../../styles/Common';

const Wrapper = styled.div`
  width: 100%;
  height: calc(100vh - 80px);
`;

const Inner = styled.div`
  padding: 4rem 1rem 2rem 1rem;
`;

const Title = styled.h1`
  margin-bottom: 20px;
  font-size: 2rem;
  font-weight: 700;
`;
const Desc = styled.p`
  margin-bottom: 20px;
  font-size: 0.9rem;
  line-height: 1.5;
  color: ${(props) => props.theme.color.dark_200};
`;

const Carousel = styled(Slider)`
  margin: 80px auto;
`;

const Icon = styled.div`
  width: 100px;
  height: 100px;
  background: url(${(props) => props.bgImg}) center no-repeat;
  background-size: cover;
  border-radius: 50%;
`;

const StartBtn = styled(Button)`
  border-radius: 40px;
`;

export { Wrapper, Inner, Title, Desc, Carousel, Icon, StartBtn };
