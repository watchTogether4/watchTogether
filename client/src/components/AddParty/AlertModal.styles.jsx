import styled from 'styled-components';
import { Button } from '../../styles/Common';

const Wrapper = styled.div`
  position: absolute; 
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  background-color: rgba(0,0,0,0.5);
  overflow: hidden;
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  position: absolute; 
  width: 80%;
  padding: 1rem;
  background-color: #fff;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border-radius: 12px;
`;

const AlertTitle = styled.h2`
  font-size: 1.2rem;
  font-weight: 700;
  text-align: center;
  margin-bottom: 1rem;
  white-space: pre-wrap;
`;

const AlertText = styled.p`
  font-size: 1rem;
  text-align: center;
  margin-bottom: 1.5rem;
  white-space: pre-wrap;
`;
const SubmitButton = styled(Button)`
  height: 2.5rem;
  margin-bottom: 0;
  border-radius: 40px;
`;

export { Wrapper, Container, AlertTitle, AlertText, SubmitButton };
