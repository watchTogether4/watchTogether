/* eslint-disable react/jsx-props-no-spreading */
import React from 'react';
import { useNavigate } from 'react-router-dom';
import icons from '../../mocks/platform';
import { Wrapper, Inner, Title, Desc, Carousel, Icon, StartBtn } from './Main.styles';
import { getCookieToken, setRefreshToken, removeCookieToken } from '../../utils/Cookie';
import { useSelector } from 'react-redux';
import axios from 'axios';

function Main() {
  const navigate = useNavigate();

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

  const isValidateToken = (e) => {
    e.preventDefault();
    const accessToken = localStorage.getItem('access-token');
    const refreshToken = getCookieToken();

    const body = {
      accessToken,
      refreshToken,
    };

    axios({
      url: '/api/v1/refresh-token',
      method: 'POST',
      headers: {
        'Content-Type': 'application/json;charset=UTF-8',
        Authorization: `Bearer ${accessToken}`,
      },
      data: JSON.stringify(body),
    })
      .then((response) => {
        console.log(response.data);
        setRefreshToken(response.data.refreshToken);
        localStorage.setItem('access-token', response.data.accessToken);
        navigate('/partyList');
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
