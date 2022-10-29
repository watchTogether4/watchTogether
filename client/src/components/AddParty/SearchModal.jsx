/* eslint-disable react/prop-types */
import React, { useState } from 'react';
import { AiOutlineSearch } from 'react-icons/ai';
import { Wrapper, Container, SearchForm, SearchInput, ButtonContainer, SearchResult, SubmitButton, CancleButton} from './SearchModal.styles';
import { searchUser } from '../../api/Users'
import { useEffect } from 'react';

const Modal = ({ setIsOpen, inviteMember, setinviteMember }) => {
  const [searchMember, setSearchMember] = useState('');
  const [searchData, setSearchData] = useState('');
  const [searchErrors, setSearchErrors] = useState('');
  const [isValidate, setIsValidate] = useState(false);

  const handleChange = (e) => {
    const { value } = e.target;
    setSearchMember(value);
  };

  const addMember = () => {
    const nickname  = searchMember;

      searchUser(nickname)
      .then((res) => {
        console.log(res.data)
        setinviteMember([...inviteMember, searchMember]);
        setIsOpen(false);
      })
      .catch((error) => {
        if (searchData) setSearchData('');
        setSearchErrors(error.response.data.message)
      });
  }

  

  const handleCancleClick = (e) => {
    e.preventDefault();
    setIsOpen(false);
  }


  const validateNickName = (value) => {
    let error = ''

    if (value === '') { 
      error = '파티원의 닉네임을 입력 해주세요.'
    }

    else if(inviteMember.length !== 0) {
      // eslint-disable-next-line array-callback-return
      inviteMember.map((member) => {
        if(member === value) {
          if (searchData) setSearchData('');
          error =  '이미 초대된 닉네임 입니다.'
        }
      })
    }
    
    return error
  }


  const handleSubmit = async (e) => {
    e.preventDefault();
    setSearchErrors(validateNickName(searchMember));

    if(validateNickName(searchMember) === '')  {
      setIsValidate(true)
      addMember();
    } 

    setIsValidate(true)
  };

  useEffect(() => {
    if(searchErrors === '' && isValidate)  {
      setIsValidate(true)
      addMember();
    } 
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [searchErrors])
  

  return (
    <Wrapper>
      <Container>
        <SearchForm onSubmit={handleSubmit} noValidate>
          <SearchInput type="search" placeholder="닉네임을 입력하세요" defalutValue={searchMember} onChange={handleChange} />
        <SearchResult>
          {searchErrors && <div>{searchErrors}</div>}
          {searchData && <div>{searchData}</div>}
        </SearchResult>
        <ButtonContainer>
          <CancleButton type="button" onClick={handleCancleClick}>뒤로가기</CancleButton>
          <SubmitButton type="submit">초대하기</SubmitButton>
        </ButtonContainer>
        </SearchForm>
      </Container>
    </Wrapper>
  );
};

export default Modal;
