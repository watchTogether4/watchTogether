import React, { useState } from 'react';
import { MessageContainer, MessageList, Message, MessageType, MessageButton } from './Alarm.styles';
import Modal from './../Modal/Modal';
import alerts from '../../mocks/alerts';
import Pagination from './Pagination';
import MessageModal from './MessageModal';

const Alarm = () => {
  const [data, setData] = useState({});
  const [isOpen, setIsOpen] = useState(false);
  const [limit, setLimit] = useState(5);
  const [page, setPage] = useState(1);
  const offset = (page - 1) * limit;

  const total = alerts.length;

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
        {alerts.slice(offset, offset + limit).map((data) => (
          <Message onClick={() => handleClick(data)}>
            <MessageType>{data.type}</MessageType>
            <p>{data.message}</p>
          </Message>
        ))}
      </MessageList>

      <div>
        <Pagination total={alerts.length} limit={limit} page={page} setPage={setPage} />
      </div>

      {isOpen && <MessageModal data={data} modal={setIsOpen} />}
    </MessageContainer>
  );
};

export default Alarm;
