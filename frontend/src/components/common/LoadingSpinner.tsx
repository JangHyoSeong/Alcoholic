import React from 'react';
import { Image } from 'react-native';
import styled from 'styled-components/native';
import mainLogo from '@/assets/main_logo.png'

const LoadingSpinner = () => {
 return (
 <LoadingSpinnerBlock>
  <Image source={mainLogo} style={{width: 100, height: 100}} />
 </LoadingSpinnerBlock>
 );
};

const LoadingSpinnerBlock = styled.View`
  flex : 1;
  justify-content : center;
  align-items: center;
`;

export default LoadingSpinner;
