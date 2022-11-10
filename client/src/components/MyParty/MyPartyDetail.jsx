import React from 'react';
import { useNavigate, useParams, useLocation } from 'react-router-dom';
import { Wrapper, Board} from './MyPartyDetail.styles';

function MyPartyDetail() {
  const navigate = useNavigate();
  const params = useParams();
  const { data } = useLocation();

  console.log(params.id)
  console.log(data)

  return (
    <>
      {data.partyId == parms.id && (
      <Wrapper>
        <Board>
          하이
        </Board>
      </Wrapper>
      )}
    </>
  );
}

export default MyPartyDetail;