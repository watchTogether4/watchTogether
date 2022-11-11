import styled from 'styled-components';

const MessageContainer = styled.div`
  height: calc(100vh - 60px - 2rem);
  padding: 1rem;
  margin: 1rem;
`;

const MessageList = styled.ul`
  width: 100%;
  min-height: 70vh;
  max-height: 70vh;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Message = styled.div`
  width: 100%;
  padding: 1rem;
  margin-bottom: 1rem;
  border: 2px solid
    ${(props) => (props.read ? props.theme.color.white : props.theme.color.highlight_500)};
  border-radius: 1rem;
  overflow: hidden;
  background-color: ${(props) =>
    props.read ? props.theme.color.light_200 : props.theme.color.white};
  p {
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
  }
`;

const MessageType = styled.h3`
  font-size: 1rem;
  font-weight: 600;
  margin-bottom: 0.5rem;
`;

const MessageButton = styled.button``;

export { MessageContainer, MessageList, Message, MessageType, MessageButton };
