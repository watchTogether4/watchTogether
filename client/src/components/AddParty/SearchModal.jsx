/* eslint-disable react/prop-types */
import React, { useState } from 'react';
import axios from 'axios';
import { AiOutlineSearch } from 'react-icons/ai';
import { Wrapper, Container, SearchForm, SearchInput, SearchButton, SearchResult, SubmitButton } from './SearchModal.styles';

const Modal = ({ setIsOpen, inviteMember, setinviteMember }) => {
  const [searchMember, setSearchMember] = useState('');
  const [searchData, setSearchData] = useState('');
  const [searchErrors, setSearchErrors] = useState('');

  const handleChange = (e) => {
    const { value } = e.target;
    setSearchMember(value);
  };

  const handleClick = (e) => {
    e.preventDefault();
    setinviteMember([...inviteMember, searchData]);
    setIsOpen(false);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    axios({
      url: '/api/users/search-user',
      method: 'GET',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded;' },
      params: {
        useNickname: searchMember
      },
    })
      .then((res) => {
        if (searchErrors) setSearchErrors('');
        setSearchData(res.data);
      })
      .catch((error) => setSearchData(searchMember));
  };

  return (
    <Wrapper>
      <Container>
        <SearchForm onSubmit={handleSubmit} noValidate>
          <SearchInput type="search" placeholder="닉네임을 입력하세요" value={searchMember} onChange={handleChange} />
          <SearchButton type="submit">
            <AiOutlineSearch size={24} />
          </SearchButton>
        </SearchForm>
        <SearchResult>
          {searchErrors && <div>{searchErrors}</div>}
          {searchData && <div>{searchData}</div>}
        </SearchResult>
        <SubmitButton type="button" onClick={handleClick}>초대하기</SubmitButton>
      </Container>
    </Wrapper>
  );
};

export default Modal;
