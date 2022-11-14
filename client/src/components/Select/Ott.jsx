import React, { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { Wrapper, OttButton, OttTitle, OttText, OttSection, ButtonSection } from './Ott.styles';
import { Button } from '../../styles/Common';
import { motion } from 'framer-motion';
import otts from '../../mocks/platform';

function Ott() {
  const navigate = useNavigate();
  const [ott, setOtt] = useState(-1);

  const handleClick = (id) => {
    setOtt(id);
  };

  const { state } = useLocation();
  const role = state.role;

  const leader = () => navigate('/addParty', { state: { role: role, ott: ott } });
  const follower = () => navigate('/partyList', { state: { ott: ott } });

  return (
    <motion.div
      className="selectPage"
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      exit={{ opacity: 0 }}
    >
      <Wrapper direction="column" justifyContent="flex-start">
        <OttSection direction="column" justifyContent="flex-start">
          {otts.map((platform) => (
            <OttButton
              key={ott.id}
              direction="column"
              ott={ott}
              clicked={ott === platform.id ? true : false}
              onClick={() => handleClick(platform.id)}
            >
              <OttTitle>{platform.name}</OttTitle>

              {ott === platform.id && (
                <div>
                  <OttText>월 이용료 : {platform.price.toLocaleString('ko-KR')}원</OttText>
                  <OttText>
                    가치와치에서는 월
                    {/* 월 이용료 / 4 금액에 파티장이면 500, 파티원이면 900원 추가한 금액으로 보여지게끔*/}
                    <span>
                      {' '}
                      {(role ? platform.price / 4 + 500 : platform.price / 4 + 900).toLocaleString(
                        'ko-KR',
                      )}{' '}
                    </span>
                    원
                  </OttText>
                </div>
              )}
            </OttButton>
          ))}
        </OttSection>
        <ButtonSection direction="column" justifyContent="flex-start">
          <Button onClick={role == true ? leader : follower}>다음</Button>
        </ButtonSection>
      </Wrapper>
    </motion.div>
  );
}

export default Ott;
