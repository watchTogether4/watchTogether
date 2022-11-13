/* eslint-disable react/jsx-props-no-spreading */
import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import icons from '../../mocks/platform';
import { Wrapper, Inner, Title, Desc, Carousel, Icon, StartBtn } from './Main.styles';
import { myPageAPI } from '../../api/User';
import { info } from '../../store/User';

function Main() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  // slick
  const initialSettings = {
    dots: false,
    infinite: true,
    slidesToShow: 6,
    slidesToScroll: 1,
    autoplay: true,
    speed: 1000,
    autoplaySpeed: 1000,
    cssEase: 'linear',
    responsive: [
      // 반응형
      {
        breakpoint: 600,
        settings: {
          slidesToShow: 3,
          slidesToScroll: 1,
        },
      },
    ],
  };

  const isValidateToken = async (e) => {
    e.preventDefault();

    myPageAPI()
      .then((res) => {
        navigate('/mypage');
      })
      .catch((error) => {
        console.log(error);
        console.log(error.response.data.message);
        navigate('/signIn');
      });
  };

  return (
    <Wrapper>
      <Inner>
        <Title>
          같이봐요,
          <br />
          가치와치!
        </Title>
        <Desc>
          더 이상 비싼 비용으로
          <br />
          OTT 서비스를 이용하지 마세요.
          <br />
          같이 보면 부담없는 가격에
          <br />
          같이 보는 가치, 가치와치!
        </Desc>
        <Carousel {...initialSettings}>
          {icons.map((item) => (
            <div key={item.id}>
              <Icon bgImg={item.image} />
            </div>
          ))}
          {icons.map((item) => (
            <div key={item.id}>
              <Icon bgImg={item.image} />
            </div>
          ))}
        </Carousel>
        <StartBtn onClick={isValidateToken}>Get Start</StartBtn>
      </Inner>
    </Wrapper>
  );
}

export default Main;
