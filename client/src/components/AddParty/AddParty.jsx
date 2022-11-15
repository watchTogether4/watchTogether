import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation, useOutletContext } from 'react-router-dom';
import { useQuery } from 'react-query';
import { toast, ToastContainer } from 'react-toastify';
import { motion } from 'framer-motion';
import { AiOutlineSearch } from 'react-icons/ai';
import {
  Wrapper,
  GatherForm,
  Label,
  CustomInput,
  ButtonContainer,
  CustomButton,
  Text,
  SubmitButton,
  ErrorMessage,
} from './AddParty.styles';
import SearchModal from './SearchModal';
import { createPartyAPI } from '../../api/Parties';
import { alertAPI } from '../../api/Alert';
import { myPageAPI } from '../../api/User';
import { ottAuthAPI } from '../../api/OttAuth';
import otts from '../../mocks/platform';

const AddParty = () => {
  const { userInfo } = useOutletContext();
  const navigate = useNavigate();
  const { state } = useLocation();
  const initialValues = {
    ottId: state.ott,
    title: '',
    body: '',
    partyOttId: '',
    partyOttPassword: '',
    leaderNickName: userInfo.nickname,
  };
  const [formValues, setFormValues] = useState(initialValues);
  const [formErrors, setFormErrors] = useState('');
  const [isOpen, setIsOpen] = useState(false);
  const [isVaildate, setIsValidate] = useState(false);
  const [inviteMember, setinviteMember] = useState([]);

  const createForm = (body) => {
    createPartyAPI(body)
      .then((res) => {
        res.data && sendAlert(res.data);
        toast.success(<h1>모집 글이 등록되었습니다</h1>, {
          position: 'top-center',
          autoClose: 1500,
        });
        setTimeout(() => {
          navigate('/partyList');
        }, 1500);
      })
      .catch((error) => {
        toast.error(error.response.data.message, {
          position: 'top-center',
          autoClose: 1000,
        });
      });
  };

  const sendAlert = (body) => {
    let member = {};
    // eslint-disable-next-line array-callback-return
    body.map((data) => {
      const ottFilter = otts.filter((a) => a.id === state.ott);
      const ottType = ottFilter[0].name;
      member = {
        nickName: [data.nickName],
        inviteId: data.uuid,
        type: data.alertType,
        partyId: data.partyId,
        message: `${data.sender} 님께서 ${data.nickName} 님을 ${ottType} 파티에 초대하셨습니다.`,
      };

      alertAPI(member)
        .then((res) => console.log(res))
        .catch((error) => console.log(error.response.data.message));
    });
  };

  const getUserInfo = () => {
    return myPageAPI().then((res) => res.data);
  };

  const { data } = useQuery('getInfo', getUserInfo);

  if (data) {
    formValues.leaderNickName = data.nickname;
  }

  const submitForm = () => {
    const invite = inviteMember.length !== 0 ? inviteMember.join() : null;
    const createData = { ...formValues, receiversNickName: invite };
    const filterType = otts.filter((a) => a.id === state.ott);
    const authData = {
      id: formValues.partyOttId,
      password: formValues.partyOttPassword,
      ottType: filterType[0].type,
    };

    ottAuthAPI(authData).then((res) => {
      if (res.data.loginResult === '1') {
        createForm(createData);
      } else {
        toast.error(<h1>일치하는 플랫폼 계정이 없습니다.</h1>, {
          position: 'top-center',
          autoClose: 1000,
        });
      }
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
      toast.error(
        <>
          <h1>파티 초대 정원 초과</h1>
          <p>파티원 초대는 최대 3명까지만 가능합니다.</p>
        </>,
        {
          position: 'top-center',
          autoClose: 1000,
        },
      );
    } else {
      setIsOpen(true);
    }
  };

  return (
    <motion.div
      className="selectPage"
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      exit={{ opacity: 0 }}
    >
      <Wrapper>
        <ToastContainer />
        <GatherForm onSubmit={handleSubmit}>
          {formErrors && <ErrorMessage className="error">{formErrors}</ErrorMessage>}
          <Label htmlFor="title">모집 제목</Label>
          <CustomInput
            type="text"
            name="title"
            defalutValue={formValues.title}
            onChange={handleChange}
          />
          <ButtonContainer>
            <AiOutlineSearch size={24} />
            <CustomButton onClick={handleClick}>파티원 초대하기</CustomButton>
          </ButtonContainer>
          <CustomInput
            name="searchMember"
            placeholder="초대하기를 클릭하면 닉네임을 검색할 수 있어요!"
            defaultValue={inviteMember}
            readonly
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
            nickname={userInfo.nickname}
            setinviteMember={setinviteMember}
            inviteMember={inviteMember}
            setIsOpen={setIsOpen}
          />
        )}
      </Wrapper>
    </motion.div>
  );
};

export default AddParty;
