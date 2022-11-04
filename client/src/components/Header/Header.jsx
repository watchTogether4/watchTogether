import React from 'react';
import { useNavigate } from 'react-router-dom';

import { Wrapper, Title, BackBtn } from './Header.styles';
import { IoIosArrowBack } from 'react-icons/io';

function Header(props) {
  const navigate = useNavigate();
  const { children, path, title } = props;

  return (
    <Wrapper>
      {children}
      {path && (
        <BackBtn type="button" onClick={() => navigate(`${path}`)}>
          <IoIosArrowBack size={30} />
        </BackBtn>
      )}
      <Title>{title}</Title>
    </Wrapper>
  );
}

export default Header;
