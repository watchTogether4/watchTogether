import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Wrapper,
  Header,
  Description,
  GatherForm,
  Label,
  CustomInput,
  Text,
  SubmitButton,
  ErrorMessage,
} from './AddParty.styles';
import SearchModal from './SearchModal';
import AlertModal from './AlertModal';
import { createParty } from '../../api/Parties';

const AddParty = () => {
  const intialValues = {
    ottId: 0,
    title: '',
    body: '',
    partyOttId: '',
    partyOttPassword: '',
  };

  const [formValues, setFormValues] = useState(intialValues);
  const [formErrors, setFormErrors] = useState('');
  const [isOpen, setIsOpen] = useState(false);
  const [isVaildate, setIsValidate] = useState(false);
  const [isAlert, setIsAlert] = useState(false);
  const [alertMsg, setAlertMsg] = useState({});
  const [inviteMember, setinviteMember] = useState([]);

  const submitForm = () => {
    const invite = inviteMember.join();
    const body = { ...formValues, receiversNickName: invite, leaderNickName: '' };

    createParty(body)
      .then((res) => {
        setAlertMsg({
          title: '모집이 완료 되었습니다!',
          message: '파티 모집 글이 정상적으로 \n등록되었습니다.',
        });
        setIsAlert(true);
      })
      .catch((error) => {
        console.log(error.response.data.message);
      });
  };

  const validate = (values) => {
    let error = '';

    if (!values.partyOttPassword) {
      error = 'OTT 플랫폼 비밀번호를 등록해주세요.';
    }
    if (!values.partyOttId) {
      error = 'OTT 플랫폼 계정을 등록해주세요.';
    }
    if (!values.title) {
      error = '제목을 입력해주세요';
    }
    return error;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setFormErrors(validate(formValues));

    if (validate(formValues) === '') {
      submitForm();
    }

    setIsValidate(true);
  };

  useEffect(() => {
    if (formErrors === undefined && isVaildate) {
      submitForm();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [formErrors]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormValues({ ...formValues, [name]: value });
  };

  const handleClick = (e) => {
    e.preventDefault();
    if (inviteMember.length === 3) {
      setAlertMsg({
        title: '파티 초대 정원 초과',
        message: '파티원 초대는 최대 3명까지만 \n가능합니다.',
      });
      setIsAlert(true);
    } else {
      setIsOpen(true);
    }
  };

  return (
    <Wrapper>
      <Header>
        <Description>파티원을 모집하거나, 원하는 지인을 초대할 수 있어요.</Description>
      </Header>

      <GatherForm onSubmit={handleSubmit}>
        {formErrors && <ErrorMessage className="error">{formErrors}</ErrorMessage>}
        <Label htmlFor="title">모집 제목</Label>
        <CustomInput
          type="text"
          name="title"
          defalutValue={formValues.title}
          onChange={handleChange}
        />

        <Label htmlFor="searchMember">파티원 초대하기</Label>
        <CustomInput
          type="text"
          name="searchMember"
          placeholder="찾으려는 파티원의 닉네임을 입력해주세요."
          defalutValue={inviteMember}
          onClick={handleClick}
        />

        <Label htmlFor="ottId">OTT 플랫폼 계정</Label>
        <CustomInput
          mb="0"
          type="text"
          name="partyOttId"
          placeholder="ID"
          defalutValue={formValues.partyOttId}
          onChange={handleChange}
        />
        <CustomInput
          type="password"
          name="partyOttPassword"
          placeholder="Password"
          defalutValue={formValues.partyOttPassword}
          onChange={handleChange}
        />

        <Label htmlFor="body">모집 글</Label>
        <Text
          name="body"
          placeholder="여기에 입력하세요"
          defalutValue={formValues.body}
          onChange={handleChange}
        />

        <SubmitButton type="submit">등록하기</SubmitButton>
      </GatherForm>

      {isOpen && (
        <SearchModal
          setinviteMember={setinviteMember}
          inviteMember={inviteMember}
          setIsOpen={setIsOpen}
        />
      )}

      {isAlert && <AlertModal modal={setIsAlert} alert={alertMsg} />}
    </Wrapper>
  );
};

export default AddParty;
