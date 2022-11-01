import React, { useState } from 'react';
import { Card } from './Card'
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function PartyList() {
  const navigate = useNavigate();
  
  return (
    <>
      <Card/>
      <Card/>
      <Card/>
      <Card/>
      <Card/>
      <Card/>
      <Card/>
      <Card/>
    </>
  );
}

export default PartyList;