import React from 'react';
import { useQuery } from 'react-query';
import { useNavigate } from 'react-router-dom';
import { findMyParties } from './../../api/Parties';
import { getInfo } from './../../api/Users';
import { Wrapper, Board} from './MyParty.styles';
import { Card } from './Card';

function MyParty() {
  const accessToken = localStorage.getItem('access-token');
  const myParties = () => {
    return findMyParties(body, accessToken).then((res) => res.data);
  };
  const getUserInfo = () => {
    return getInfo(accessToken).then((res) => res.data);
  };

  const { data } = useQuery('getInfo', getUserInfo);
  const { data2 } = useQuery('findMyParties', myParties);

  let nickName = '';
  if (data) {
    nickName = data.nickname;
  };
  const navigate = useNavigate();
  const body = {nickName: nickName};
  console.log(data)
  console.log(data2)

  return (
    <>
      <Wrapper>
        {data2 && (
          <Board>
            {data2.list.map((myParty) => (
            <Card
            title={myParty.title}
            body={myParty.body}
            state={myParty.state}
            member={myParty.member}
            />
            ))}
          </Board>
        )}
      </Wrapper>
    </>
  );
}

export default MyParty;
