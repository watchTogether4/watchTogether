import styled from 'styled-components';
import { Input } from '../../styles/Common';

const Wrapper = styled.div`
  padding: 0.5rem 1.5rem;
`;

const ChatContainer = styled.ul`
  height: 75vh;
  padding: 1.5rem 1rem;
  margin-bottom: 1rem;
  border-radius: 1rem;
  background-color: ${(props) => props.theme.color.highlight_100};
  overflow-y: auto;
`;

const ChatAlert = styled.p`
  width: 100%;
  padding: 0.5rem;
  margin-bottom: 1rem;
  font-size: 0.9rem;
  text-align: center;
  background-color: ${(props) => props.theme.color.highlight_200};
  border-radius: 1rem;
`;

const ChatBubble = styled.li`
  display: flex;
  justify-content: ${(props) => (props.user ? 'flex-end' : 'flex-start')};
  margin-bottom: 0.8rem;

  div {
    max-width: 50%;
    display: flex;
    flex-direction: column;
    align-items: ${(props) => (props.user ? 'flex-end' : 'flex-start')};

    span {
      margin-bottom: 0.5rem;
      font-size: 0.85rem;
    }
  }

  p {
    font-size: 0.9rem;
    padding: 0.5rem 0.8rem;
    color: ${(props) => (props.user ? props.theme.color.white : props.theme.color.black)};
    background-color: ${(props) =>
      props.user ? props.theme.color.highlight_400 : props.theme.color.light_100};
    border-radius: 1rem;
  }
`;

const Chatform = styled.form`
  display: flex;
  align-items: center;
  width: 100%;
  overflow: hidden;
`;

const ChatInput = styled(Input)`
  margin: 0;
  border-radius: 1rem 0 0 1rem;
`;

const SubmitButton = styled.button`
  width: 5rem;
  height: 3rem;
  color: ${(props) => props.theme.color.white};
  background-color: ${(props) => props.theme.color.highlight_500};
  border-radius: 0 1rem 1rem 0;
`;

export { Wrapper, ChatContainer, ChatAlert, ChatBubble, Chatform, ChatInput, SubmitButton };
