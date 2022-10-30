import styled from 'styled-components';
import { Button, Input } from '../../styles/Common';

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

const SearchForm = styled.form`
  display: flex;
  align-items: center;
  flex-direction: column;
  width: 100%;
  border-radius: 12px;  
`;

const SearchInput = styled(Input)`
  border: 0;
  background-color: ${(props) => props.theme.color.light_200};
  border-radius: 1rem;
`;

const SearchResult = styled.div`
  width: 100%;
  height: 5rem;
  padding: 1rem;
  margin-bottom: 1rem;
  border-radius: 12px;
  background-color: ${(props) => props.theme.color.light_200}
`;
const SearchButton = styled.button`
  width: 2rem;
  height: 2rem;
  border: 0;
  background-color: transparent;
`;

const ButtonContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
`;

const SubmitButton = styled(Button)`
  width: 48%;
  height: 2.5rem;
  margin-bottom: 0;
  border-radius: 1rem;
`;

const CancleButton = styled(SubmitButton)`
background-color: ${(props) => props.theme.color.warning_300}
`;

export { Wrapper, Container, SearchForm, SearchButton, SearchInput, SearchResult, ButtonContainer, SubmitButton, CancleButton };
