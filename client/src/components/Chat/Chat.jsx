import axios from 'axios';
import { string } from 'prop-types';
import React, { useState, useEffect, useRef } from 'react';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import {
  Wrapper,
  ChatContainer,
  ChatAlert,
  ChatBubble,
  Chatform,
  ChatInput,
  SubmitButton,
} from './Chat.styles';
import chat from '../../mocks/chatTest';

const Chat = () => {
  const scrollRef = useRef();
  const params = useParams();
  console.log(params.id);
  const { value } = useSelector((state) => state.user);
  const accessToken = localStorage.getItem('access-token');

  const [serverState, setServerState] = useState('Loading...');
  const [editDone, setEditDone] = useState(false);
  const serverMessagesList = [];
  const [messageText, setMessageText] = useState('');
  const [serverMessages, setServerMessages] = useState([]);

  const body = {
    nickName: value.nickname,
  };

  const socket = useRef(null);

  function isOpen(ws) {
    return ws.readyState === ws.OPEN;
  }

  const handleSubmit = (e) => {
    e.preventDefault();

    const message = {
      type: 'TALK',
      roomId: String(params.id),
      sender: value.nickname,
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
      ws = new WebSocket(`ws://localhost:8080/ws/chat`);
    }
  };

  useEffect(() => {
    scrollRef.current.scrollIntoView({ behavior: 'smooth', block: 'end', inline: 'nearest' });
  }, []);

  useEffect(() => {
    axios({
      baseURL: 'http://localhost:8080',
      url: '/api/v1/chat',
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      data: JSON.stringify({ roomId: String(params.id) }),
    }).then((res) => console.log(res));
    // 서버 연결
    socket.current = new WebSocket('ws://localhost:8080/ws/chat');
    socket.current.onopen = () => {
      console.log('Connected to the server');
    };

    socket.current.onclose = () => {
      console.log('Disconnected. Check internet or server');
      setTimeout(connect(socket.current), 300);
    };

    socket.current.onmessage = (e) => {
      let parse = JSON.parse(e.data);
      serverMessagesList.push(parse);
      setServerMessages([...serverMessagesList]);
    };

    socket.current.onerror = (e) => {
      setServerState(e.message);
    };
  }, []);

  return (
    <Wrapper>
      <ChatContainer>
        <ChatAlert>누가 들어옴 </ChatAlert>
        {chat?.map((a) => (
          <ChatBubble user={a.sender === value.nickname ? true : false}>
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
