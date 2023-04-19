import React from 'react';
import Header from '../components/Header/Header';
import Chat from '../components/Chat/Chat';

const ChatPage = () => {
  return (
    <>
      <Header title="그룹 채팅방" path="/mypage/myparty"></Header>
      <Chat />
    </>
  );
};

export default ChatPage;
