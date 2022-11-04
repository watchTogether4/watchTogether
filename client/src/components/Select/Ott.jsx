import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { Wrapper, OttButton, OttSection, ButtonSection } from './Ott.styles';
import { Button } from '../../styles/Common';
import { motion } from 'framer-motion';

function Ott() {
  const navigate = useNavigate();
  const [ott, setOtt] = useState(-1);
  const handleNetflix = ()=> {setOtt(1);};
  const handleTving = ()=> {setOtt(2);};
  const handleWatcha = ()=> {setOtt(3);};
  const handleDisney = ()=> {setOtt(4);};
  const handleWavve = ()=> {setOtt(5);};
  const {state} = useLocation();
  const role = state.role;
  
  return(
    <motion.div
      className='selectPage'
      initial={{ opacity: 0}}
      animate={{ opacity: 1 }}
      exit={{ opacity: 0 }}
    >
      <Wrapper direction='column' justifyContent='flex-start'>
        <OttSection direction='column' justifyContent='flex-start'>
          <OttButton ott={ott} clicked={(ott === 1) ? true : false} onClick={()=> handleNetflix()}>
            넷플릭스
          </OttButton>
          <OttButton ott={ott} clicked={(ott === 2) ? true : false} onClick={()=> handleTving()}>
            티빙
          </OttButton>
          <OttButton ott={ott} clicked={(ott === 3) ? true : false} onClick={()=> handleWatcha()}>
            왓챠
          </OttButton>
          <OttButton ott={ott} clicked={(ott === 4) ? true : false} onClick={()=> handleDisney()}>
            디즈니플러스
          </OttButton>
          <OttButton ott={ott} clicked={(ott === 5) ? true : false} onClick={()=> handleWavve()}>
            웨이브
          </OttButton>
        </OttSection>
        <ButtonSection direction='column' justifyContent='flex-start'>
          <Button onClick={()=> navigate('/addParty', {state: {role: {role}, ott: {ott}}})}>
              다음
          </Button>
        </ButtonSection>
      </Wrapper>
    </motion.div>
  );
}
  
export default Ott;