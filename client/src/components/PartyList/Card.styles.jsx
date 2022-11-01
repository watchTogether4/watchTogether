import styled from 'styled-components';

const CardWrapper = styled.div`
  width: 100%;
  height: 25%;
  display: flex;
  flex-direction: row;
  padding: 1rem 1.2rem;
  background-color: #fff;
  border-radius: 12px;
  align-items: center;
  border-top: 1px solid lightgrey;
  border-bottom: 1px solid lightgrey;
`; 

const CardOtt = styled.img`
  height: 100%;
  width: 15%;
  padding: 0.4rem;
`;

const CardTitle = styled.p`
  height: 100%;
  font-size: 1.2rem;
  font-weight: 600;
  line-height: 1.2rem;
  padding: 1rem 1.4rem;
  width: 70%;
`;

const CardPerson = styled.p`
  height: 100%;
  font-size: 1.2rem;
  font-weight: 400;
  line-height: 1.2rem;
  padding: 1rem;
  color: grey;
  width: 15%;
`;

export {
    CardWrapper,
    CardOtt,
    CardTitle,
    CardPerson,
};