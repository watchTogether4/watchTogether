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

const CardDesc = styled.div`
  width: 100%;
  padding-right: 1rem;
`;

const InfoList = styled.ul`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;

  li {
    width: 100%;
    padding: 0.5rem 0;
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 0.8rem;
    font-weight: 400;
    line-height: 1.2rem;
  }
`;

export { CardWrapper, CardDesc, InfoList, };
