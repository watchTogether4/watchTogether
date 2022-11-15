import React, { useState, useEffect } from 'react';
import Modal from '../Modal/Modal';

import { ButtonContainer, SubmitButton, CancleButton, ModalInput } from '../../styles/Common';
import { SearchResult } from '../Modal/Modal.styles';
import { searchUserAPI } from '../../api/User';

const AddPartyModal = ({ nickname, setIsOpen, inviteMember, setinviteMember }) => {
  const [searchMember, setSearchMember] = useState('');
  const [searchData, setSearchData] = useState('');
  const [searchErrors, setSearchErrors] = useState('');
  const [isValidate, setIsValidate] = useState(false);

  const validateNickName = (value) => {
    let error = '';

    if (value === '') {
      error = '파티원의 닉네임을 입력 해주세요.';
    } else if (value === nickname) {
      error = '자기 자신은 초대할 수 없어요!';
    } else if (inviteMember.length !== 0) {
      // eslint-disable-next-line array-callback-return
      inviteMember.map((member) => {
        if (member === value) {
          if (searchData) setSearchData('');
          error = '이미 초대된 닉네임 입니다.';
        }
      });
    }

    return error;
  };

  const addMember = () => {
    const nickname = searchMember;
    const accessToken = localStorage.getItem('access-token');

    searchUserAPI(nickname, accessToken)
      .then((res) => {
        console.log(res.data);
        setinviteMember([...inviteMember, searchMember]);
        setIsOpen(false);
      })
      .catch((error) => {
        if (searchData) setSearchData('');
        setSearchErrors(error.response.data.message);
      });
  };

  const handleChange = (e) => {
    const { value } = e.target;
    setSearchMember(value);
  };

  const handleClick = (e) => {
    e.preventDefault();
    setIsOpen(false);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setSearchErrors(validateNickName(searchMember));

    if (validateNickName(searchMember) === '') {
      setIsValidate(true);
      addMember();
    }

    setIsValidate(true);
  };

  useEffect(() => {
    if (searchErrors === '' && isValidate) {
      setIsValidate(true);
      addMember();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [searchErrors]);

  return (
    <>
      <Modal handleSubmit={handleSubmit}>
        <ModalInput
          type="search"
          placeholder="닉네임을 입력하세요"
          default={searchMember}
          onChange={handleChange}
        />
        <SearchResult>
          {searchErrors && <div>{searchErrors}</div>}
          {searchData && <div>{searchData}</div>}
        </SearchResult>
        <ButtonContainer>
          <CancleButton type="button" onClick={handleClick}>
            취소하기
          </CancleButton>
          <SubmitButton type="submit">초대하기</SubmitButton>
        </ButtonContainer>
      </Modal>
    </>
  );
};

export default AddPartyModal;
