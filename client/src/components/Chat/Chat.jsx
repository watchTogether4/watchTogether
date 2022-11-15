import React, { useState, useEffect, useRef } from 'react';
import { useQuery } from 'react-query';
import { useOutletContext, useParams } from 'react-router-dom';
import { myPageAPI } from '../../api/User';
import {
  Wrapper,
  ChatContainer,
  ChatBubble,
  Chatform,
  ChatInput,
  SubmitButton,
} from './Chat.styles';

const Chat = () => {
  const { userInfo } = useOutletContext();
  const scrollRef = useRef();
  const params = useParams();

  const [messageText, setMessageText] = useState('');
  const [serverMessages, setServerMessages] = useState([]);

  const socket = useRef(null);

  function isOpen(ws) {
    return ws.readyState === ws.OPEN;
  }

  const handleSubmit = (e) => {
    e.preventDefault();

    const message = {
      type: 'TALK',
      roomId: params.id,
      sender: userInfo.nickname,
      message: messageText,
    };

    if (!isOpen(socket.current)) {
      connect(socket.current);
    } else {
      socket.current.send(JSON.stringify(message));
      setMessageText('');
    }
  };

  const handleChange = (e) => {
    const { value } = e.target;
    setMessageText(value);
  };

  const connect = (ws) => {
    if (ws === undefined || (ws && ws.readyState === 3)) {
      ws = new WebSocket(`ws://3.38.9.104:8080/ws/chat`);
    }
  };

  useEffect(() => {
    const message = {
      type: 'ENTER',
      roomId: params.id,
      sender: userInfo.nickname,
      message: messageText,
    };
    // 서버 연결
    socket.current = new WebSocket('ws://3.38.9.104:8080/ws/chat');
    socket.current.onopen = () => {
      console.log('Connected to the server');
      socket.current.send(JSON.stringify(message));
    };

    socket.current.onclose = () => {
      console.log('Disconnected. Check internet or server');
      setTimeout(connect(socket.current), 300);
    };

    socket.current.onerror = (error) => {
      console.log(error);
    };
  }, []);

  useEffect(() => {
    socket.current.onmessage = (event) => {
      const data = JSON.parse(event.data);
      setServerMessages((cur) => [...cur, { sender: data.sender, message: data.message }]);
    };
  }, [onmessage]);

  useEffect(() => {
    scrollRef.current.scrollIntoView({ behavior: 'smooth', block: 'end', inline: 'nearest' });
  }, [serverMessages]);

  return (
    <Wrapper>
      <ChatContainer>
        {serverMessages?.map((a) => (
          <ChatBubble user={a.sender === userInfo.nickname ? true : false}>
            <div>
              <span>{a.sender}</span>
              <p>{a.message}</p>
            </div>
          </ChatBubble>
        ))}
        <div ref={scrollRef} />
      </ChatContainer>

      <Chatform onSubmit={handleSubmit}>
        <ChatInput
          type="text"
          placeholder="메시지를 입력하세요"
          onChange={handleChange}
          value={messageText}
        />
        <SubmitButton type="submit">전송</SubmitButton>
      </Chatform>
    </Wrapper>
  );
};

export default Chat;
