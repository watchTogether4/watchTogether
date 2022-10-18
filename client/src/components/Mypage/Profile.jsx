import React from 'react';
import { Wrapper, ImageContainer, Image, UserName, UserEmail } from './Profile.styles';

function Profile() {
  return (
    <Wrapper direction="column" justifyContent="space-between">
      <ImageContainer>
        <Image />
      </ImageContainer>
      <UserName>가치와치</UserName>
      <UserEmail>watchTogether@gmail.com</UserEmail>
    </Wrapper>
  );
}

export default Profile;
