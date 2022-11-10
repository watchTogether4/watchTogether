import React, {useState} from 'react';
import { useNavigate } from 'react-router-dom'; 
import { useQuery } from 'react-query';
import { transactions } from './../../api/Transaction';
import { Wrapper, Board, } from './PaymentList.styles';
import { Card } from './Card';

function PaymentList () {
  const accessToken = localStorage.getItem('access-token');
  const navigate = useNavigate();
  const showTransaction = () => {
    return transactions(accessToken).then((res) => res.data);
  };
  const { data } = useQuery('transactions', showTransaction);

  return (
    <>
    <Wrapper>
      {data && (
        <Board>
        {data.list.map((payment) => (
            <Card
            trader={payment.traderNickname}
            dt={payment.transactionDt}
            result={payment.transactionResultType}
            type={payment.transactionType}
            />
          ))}
        </Board>
      )}
    </Wrapper>
    </>
  );
}

export default PaymentList;
