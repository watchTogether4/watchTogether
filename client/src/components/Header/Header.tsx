import React from 'react';
import { useNavigate } from 'react-router-dom';

import { Wrapper, Title, BackBtn } from './Header.styles';
import { IoIosArrowBack } from 'react-icons/io';

interface HeaderProps {
  children?: React.ReactNode;
  path: string;
  title: string;
}
function Header({ children, path, title }: HeaderProps) {
  const navigate = useNavigate();

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
