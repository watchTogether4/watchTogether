import React from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { Hidden } from '../../styles/Common';

const Title = styled.h1`
  width: 100%;
  height: 100%;
  font-weight: bold;
  font-size: 1rem;
  color: ${(props) => props.theme.color.black};
  background: url('https://user-images.githubusercontent.com/101559564/196141057-98a4920e-113f-4847-90bb-2fcc856ed46a.png')
    0 no-repeat;
  background-size: auto 30px;

  a {
    display: block;
    width: 100%;
    height: 100%;
  }
`;

function Logo() {
  return (
    <Title>
      <Link to="/">
        <Hidden>Logo</Hidden>
      </Link>
    </Title>
  );
}

export default Logo;
