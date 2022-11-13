import React, { useState } from 'react';
import { useLocation } from 'react-router-dom';
import { MessageContainer, MessageList, Message, MessageType } from './Alarm.styles';
import Pagination from './Pagination';
import MessageModal from './MessageModal';

const Alarm = () => {
  const { state } = useLocation();

  const [data, setData] = useState({});
  const [isOpen, setIsOpen] = useState(false);
  const [page, setPage] = useState(1);
  const limit = 5;
  const offset = (page - 1) * limit;

  const total = state.data.length;

  const handleSubmit = (e) => {
    e.preventDefault();
  };

  const handleClick = (data) => {
    setIsOpen(true);
    setData({ ...data });
  };

  return (
    <MessageContainer handleSubmit={handleSubmit}>
      <MessageList>
        {state.data &&
          state.data.slice(offset, offset + limit).map((data) => (
            <Message read={data.notificationOpen} onClick={() => handleClick(data)}>
              <MessageType>{data.type}</MessageType>
              <p>{data.message}</p>
            </Message>
          ))}
      </MessageList>

      <div>
        <Pagination total={total} limit={limit} page={page} setPage={setPage} />
      </div>

      {isOpen && <MessageModal data={data} modal={setIsOpen} />}
    </MessageContainer>
  );
};

export default Alarm;
