import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { FaRegEnvelope, FaUserAlt, FaSearch, FaPen } from 'react-icons/fa';
import { MenuWrapper, Bedge } from './Menu.styles';
import { listAlertAPI } from './../../api/Alert';

const Menu = ({ page }) => {
  const { value } = useSelector((state) => state.user);
  const navigate = useNavigate();
  const [bedge, setBedge] = useState(0);
  const [data, setData] = useState();

  const handleClick = (path) => {
    navigate(path);
  };

  useEffect(() => {
    // 메뉴바가 렌더링 될 때 마다 읽지 않은 메시지가 있는지 확인 -> bedge 갯수 수정
    listAlertAPI(value.email)
      .then((res) => {
        setBedge(res.data.filter((a) => a.notificationOpen === false).length);
        setData(() => [...res.data]);
      })
      .catch((error) => console.log(error));
  }, []);

  return (
    <MenuWrapper>
      <li onClick={() => handleClick('/select')}>
        <FaPen size={22} color={page === 'select' ? '#006FFD' : '#71727A'} />
        파티 모집
      </li>
      <li onClick={() => handleClick('/partyList')}>
        <FaSearch size={22} color={page === 'partyList' ? '#006FFD' : '#71727A'} />
        파티 찾기
      </li>
      <li onClick={() => navigate('/message', { state: { data: data } })}>
        {bedge > 0 && <Bedge> {bedge} </Bedge>}
        <FaRegEnvelope size={22} color={page === 'alarm' ? '#006FFD' : '#71727A'} />
        쪽지함
      </li>
      <li onClick={() => handleClick('/mypage')}>
        <FaUserAlt size={22} color={page === 'mypage' ? '#006FFD' : '#71727A'} />
        마이 페이지
      </li>
    </MenuWrapper>
  );
};

export default Menu;
