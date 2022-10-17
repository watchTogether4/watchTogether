/* eslint-disable react/jsx-props-no-spreading */
import React from 'react';
import styled from 'styled-components';
import Flex from './Flex';

const Button = styled.button`
  width: 100%;
  height: 3rem;
  margin-bottom: 1rem;
  font-size: 1rem;
  color: ${(props) => props.theme.color.white};
  background-color: ${(props) => props.theme.color.highlight_500};
  border-radius: 12px;
`;
const BtnLink = styled((props) => <Button {...props} />)`
  ${Flex}
  font-weight: 600;
  color: ${(props) => props.theme.color.white};
  border-radius: 40px;
`;

export { Button, BtnLink };
