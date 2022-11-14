import React from 'react';
import { useQuery } from 'react-query';
import { getListAPI } from './../../api/Transaction';
import { Wrapper, Board } from './PaymentList.styles';
import { Card } from './Card';

function PaymentList() {
  const showTransaction = () => {
    return getListAPI().then((res) => res.data);
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
