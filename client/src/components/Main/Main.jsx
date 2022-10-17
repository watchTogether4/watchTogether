/* eslint-disable react/jsx-props-no-spreading */
import React from 'react';
import { Link } from 'react-router-dom';

import icons from '../../mocks/icons';
import { Wrapper, Inner, Title, Desc, Carousel, Icon } from './Main.style';
import { BtnLink } from '../../styles/Common';

function Main() {
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
        <BtnLink to="/login" props={Link}>
          Get Start
        </BtnLink>
      </Inner>
    </Wrapper>
  );
}

export default Main;
