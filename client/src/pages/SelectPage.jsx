import React from 'react';
import Select from '../components/Select/Select';
import Header from '../components/Header/Header';

function SelectPage() {
  return (
    <>
      <Header title="이용 유형을 선택하세요." path="/mypage"></Header>
      <Select />
    </>
  );
}

export default SelectPage;
