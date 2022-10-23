import React from 'react';
import { useSelector } from 'react-redux';
import { Wrapper, ImageContainer, Image, UserName, UserEmail } from './Profile.styles';

function Profile() {
  const user = useSelector((state) => state.user.value);
  return (
    <Wrapper direction="column" justifyContent="space-between">
      <ImageContainer>
        <Image />
      </ImageContainer>
      <UserName>{user.nickname}</UserName>
      <UserEmail>{user.email}</UserEmail>
    </Wrapper>
  );
}

export default Profile;
