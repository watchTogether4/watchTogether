import React from 'react';
import { useQuery } from 'react-query';
import { Wrapper, ImageContainer, Image, UserName, UserEmail } from './Profile.styles';
import Avvvatars from 'avvvatars-react';

import { myPageAPI } from './../../api/User';

function Profile() {
  const getUserInfo = () => {
    return myPageAPI().then((res) => res.data);
  };
  const { data } = useQuery('getInfo', getUserInfo);

  return (
    <Wrapper direction="column" justifyContent="space-between">
      {data && (
        <div>
          <ImageContainer>
            <Avvvatars value={data.email} style="shape" size={130} />
          </ImageContainer>
          <UserName>{data.nickname}</UserName>
          <UserEmail>{data.email}</UserEmail>
        </div>
      )}
    </Wrapper>
  );
}

export default Profile;
