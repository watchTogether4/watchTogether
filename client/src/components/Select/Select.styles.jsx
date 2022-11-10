import styled from 'styled-components';
import { Flex } from '../../styles/Common';
import LeaderImg from './Image/leader.png';
import FollowerImg from './Image/follower.png';

const Wrapper = styled.div`
  ${Flex}
  padding: 1rem 2rem;
  width: 100%;
  height: calc(100vh-80px);
`;

const SelectSection = styled.div`
  ${Flex}
  width: 100%;
  height: calc(75vh);
  padding: 0rem;
  margin: 0rem;
`;

const ButtonSection = styled.div`
  ${Flex}
  width: 100%;
  height: 100%;
  padding: 0rem;
  margin: 1rem 0rem;
`;

const RoleTitle = styled.p`
  width: 100%;
  text-align: center;
  padding: 0rem 2rem;
  font-size: 1.4rem;
  font-weight: 600;
`;

const TextBox = styled.ul`
  width: 100%;
  height: 100%;
  padding: 1rem;
  text-align: left;
  list-style-type: '★';
`;

const RoleText = styled.li`
  font-size: 0.8rem;
  font-weight: 400;
  line-height: 2rem;
  padding-inline: 1rem;
  margin-inline-end: 1rem;
  
  &::marker{
    color: ${(props) => props.theme.color.highlight_500}
  }
`;

const Leader = styled.div`
  ${Flex}
  margin: 1rem;
  padding: 1rem;
  text-align: center;
  width: 100%;
  height: 100%;
  border-radius: 12px;
  background: url(${LeaderImg}) no-repeat;
  background-size: 55%;
  background-position: 100% 100%;
  background-color: #F8F9FE;
  background-blend-mode: multiply;
  box-shadow: 0 5px 10px -7px rgba(0,0,0,1);
  border: 2px solid ${props=>props.role ? props.theme.color.highlight_500: '#ffffff'};

  &:hover{
    border: 2px solid ${(props) => props.theme.color.highlight_500};
  }
`;

const Follower = styled.div`
  ${Flex}
  margin: 1rem;
  padding: 1rem;
  text-align: center;
  width: 100%;
  height: 100%;
  border-radius: 12px;
  background: url(${FollowerImg}) no-repeat;
  background-size: 45%;
  background-position: 100% 100%;
  background-color: #F8F9FE;
  background-blend-mode: multiply;
  box-shadow: 0 5px 10px -7px rgba(0,0,0,1);
  border: 2px solid ${props=>props.role ? '#ffffff': props.theme.color.highlight_500 };

  &:hover{
    border: 2px solid ${(props) => props.theme.color.highlight_500};
`;

export {
    Wrapper,
    SelectSection,
    ButtonSection,
    RoleTitle,
    RoleText,
    TextBox,
    Leader,
    Follower,
};