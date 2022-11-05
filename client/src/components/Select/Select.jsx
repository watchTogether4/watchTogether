import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Wrapper,
  SelectSection,
  ButtonSection,
  RoleTitle,
  RoleText,
  TextBox,
  Leader,
  Follower,
  NextButton,
} from './Select.styles';
import { Button } from '../../styles/Common';
import { motion } from 'framer-motion';

function Select() {
  const navigate = useNavigate();
  const [role, setRole] = useState(true);

  const handleLeader = () => {
    setRole(true);
  };
  const handleFollower = () => {
    setRole(false);
  };

  return (
    <motion.div
      className="selectPage"
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      exit={{ opacity: 0 }}
    >
      <Wrapper direction="column" justifyContent="flex-start">
        <SelectSection direction="column" justifyContent="flex-start">
          <Leader
            direction="column"
            justifyContent="flex-start"
            role={role}
            onClick={() => handleLeader()}
          >
            <RoleTitle>파티장</RoleTitle>
            <TextBox>
              <RoleText>내 계정에 이용권을 먼저 결제해요.</RoleText>
              <RoleText>파티원을 모아서 이용료를 받아요.</RoleText>
              <RoleText>수수료: 월 500원</RoleText>
            </TextBox>
          </Leader>
          <Follower
            direction="column"
            justifyContent="flex-start"
            role={role}
            onClick={() => handleFollower()}
          >
            <RoleTitle>파티원</RoleTitle>
            <TextBox>
              <RoleText>모집중인 파티에 가입해요.</RoleText>
              <RoleText>파티장에게 이용료를 청구해요.</RoleText>
              <RoleText>수수료: 월 900원</RoleText>
            </TextBox>
          </Follower>
        </SelectSection>
        <ButtonSection>
          <Button onClick={() => navigate('/ott', { state: { role: role } })}>다음</Button>
        </ButtonSection>
      </Wrapper>
    </motion.div>
  );
}

export default Select;
