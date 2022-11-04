import React from 'react';
import { MenuWrapper } from './Menu.styles';
import { Link } from 'react-router-dom';
import { FaRegEnvelope, FaUserAlt, FaSearch, FaPen } from 'react-icons/fa';
const Menu = ({ page }) => {
  return (
    <MenuWrapper>
      <li>
        <Link to="/select">
          <FaPen size={22} color={page === 'select' ? '#006FFD' : '#71727A'} />
          파티 등록
        </Link>
      </li>
      <li>
        <Link to="/partyList">
          <FaSearch size={22} color={page === 'partyList' ? '#006FFD' : '#71727A'} />
          파티 찾기
        </Link>
      </li>
      <li>
        <Link to="/">
          <FaRegEnvelope size={22} color={page === 'alarm' ? '#006FFD' : '#71727A'} />
          쪽지함
        </Link>
      </li>
      <li>
        <Link to="/mypage">
          <FaUserAlt size={22} color={page === 'mypage' ? '#006FFD' : '#71727A'} />
          마이 페이지
        </Link>
      </li>
    </MenuWrapper>
  );
};

export default Menu;
