import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
// component
import Header from './components/Header/Header';
import MainPage from './pages/MainPage';
import SignUpPage from './pages/SignUpPage';
// route

function App() {
  return (
    <BrowserRouter>
      <Header />
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/signUp" element={<SignUpPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
