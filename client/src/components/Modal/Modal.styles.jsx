import styled from 'styled-components';

const Wrapper = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5);
  overflow: hidden;
`;

const Container = styled.form`
  display: flex;
  flex-direction: column;
  position: absolute;
  width: 80%;
  padding: 1rem;
  background-color: #fff;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border-radius: 12px;
`;

const Title = styled.h2`
  margin-bottom: 1rem;
  font-weight: 600;
  font-size: 1.1rem;
  text-align: center;
`;

const Description = styled.p`
  font-size: 0.85rem;
  color: ${(props) => props.theme.color.dark_300};
`;

const AlertTitle = styled.h2`
  font-size: 1.2rem;
  font-weight: 700;
  text-align: center;
  margin-bottom: 1rem;
  white-space: pre-wrap;
`;

const AlertText = styled.p`
  font-size: 1rem;
  text-align: center;
  margin-bottom: 1rem;
  white-space: pre-wrap;
`;

const SearchResult = styled.div`
  width: 100%;
  height: 5rem;
  padding: 1rem;
  margin-bottom: 1rem;
  border-radius: 12px;
  background-color: ${(props) => props.theme.color.light_200};
`;

const ErrorMessage = styled.p`
  padding-left: 0.5rem;
  margin-bottom: 0.5rem;
  font-size: 0.85rem;
  color: ${(props) => props.theme.color.error_300};
`;

export {
  Wrapper,
  Container,
  Title,
  Description,
  SearchResult,
  AlertTitle,
  AlertText,
  ErrorMessage,
};
