import React from 'react';
import { Wrapper, Board} from './MyParty.styles';
import { Card } from './Card';

function MyParty() {

  return (
    <>
      <Wrapper>
        <Board>
          <Card>
            마이파티
          </Card>
        </Board>
      </Wrapper>
    </>
  );
}

export default MyParty;
