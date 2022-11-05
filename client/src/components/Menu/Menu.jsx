import React, { useState, useEffect } from 'react';
import { MenuWrapper, Bedge } from './Menu.styles';
import { Link } from 'react-router-dom';
import { FaRegEnvelope, FaUserAlt, FaSearch, FaPen } from 'react-icons/fa';
import { useSelector, useDispatch } from 'react-redux';
import { toRead } from '../../store/Message';

const Menu = ({ page }) => {
  const dispatch = useDispatch();
  const [bedge, setBedge] = useState(1);

  useEffect(() => {
    // 메뉴바가 렌더링 될 때 마다 읽지 않은 메시지가 있는지 확인 -> bedge 갯수 수정

    dispatch(toRead(bedge));
  }, []);

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
        <Link to="/message">
          {bedge > 0 && <Bedge> {bedge} </Bedge>}
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
