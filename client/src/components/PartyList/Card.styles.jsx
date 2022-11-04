import styled from 'styled-components';

const CardWrapper = styled.div`
  width: 100%;
  margin-bottom: 1rem;
  display: flex;
  flex-direction: row;
  padding: 1rem 1.2rem;
  background-color: #fff;
  border-radius: 1rem;
  align-items: center;
  border: 1px solid ${(props) => props.theme.color.light_300};
`;

const CardOtt = styled.img`
  height: 100%;
  width: 15%;
  margin-right: 1rem;
`;

const CardDesc = styled.div`
  width: 100%;
  padding-right: 1rem;
`;

const CardTitle = styled.p`
  margin-bottom: 0.2rem;
  font-size: 1rem;
  font-weight: 600;
  line-height: 1.2rem;
`;

const CardPerson = styled.p`
  font-size: 0.85rem;
  font-weight: 400;
  line-height: 1.2rem;
  color: grey;
`;

export { CardWrapper, CardOtt, CardDesc, CardTitle, CardPerson };
