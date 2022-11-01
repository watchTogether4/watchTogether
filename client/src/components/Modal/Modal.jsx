import React from 'react';
import { Wrapper, Container } from './Modal.styles';

const Modal = (props) => {
  const { children, handleSubmit } = props;

  return (
    <Wrapper>
      {handleSubmit ? (
        <Container onSubmit={handleSubmit}> {children} </Container>
      ) : (
        <Container> {children} </Container>
      )}
    </Wrapper>
  );
};

export default Modal;
