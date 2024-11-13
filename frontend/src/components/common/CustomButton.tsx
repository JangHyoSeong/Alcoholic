import styled from 'styled-components/native';
import React, { ReactNode } from 'react';
import {
  Text,
  View,
  Pressable,
  StyleProp,
  ViewStyle,
  TextStyle,
  Dimensions,
  PressableProps,
} from 'react-native';
import { colors } from '@/constants/colors';

interface CustomButtonProps extends PressableProps {
  label : string; // 버튼에 표시할 텍스트
  icon? : ReactNode; // 아이콘
  inValid? : boolean; // 기본값 false, 버튼이 유효하지 않을 시 비활성화
  size? : 'large' | 'medium' | 'small' // 버튼 크기 정의, 기본값 large
  variant? : 'filled' | 'outlined' // 버튼 스타일 정의, 기본값 filled
  style?: StyleProp<ViewStyle>; // 사용자 지정 스타일
  textStyle?: StyleProp<TextStyle>; // 사용자 지정 텍스트 스타일
}

const deviceHeight = Dimensions.get('screen').height

const CustomButton = ({
  label,
  icon = null,
  inValid = false,
  size = 'large',
  variant = 'filled',
  style = null,
  textStyle = null,
  ...props
}: CustomButtonProps) => {
  return (
    <CustomButtonBlock>
      <StyledPressable
        disabled={inValid}
        variant={variant}
        inValid={inValid}
        style={style}
        {...props}
      >
        <ButtonContent size={size}>
          {icon}
          <ButtonText variant={variant} style={textStyle}>{label}</ButtonText>
        </ButtonContent>
      </StyledPressable>
    </CustomButtonBlock>
  );
};

const CustomButtonBlock = styled.View``;

const StyledPressable = styled(Pressable)<{ variant: string, inValid: boolean }>`
  background-color: ${({ variant }) =>
    variant === 'filled' ? colors.PURPLE.BASE : 'transparent'};
  opacity: ${({ inValid }) => (inValid ? 0.5 : 1)};
  padding: 10px;
  border-radius: 5px;
  
  ${({ variant }) => variant === 'outlined' && `
    border: 2px solid ${colors.PURPLE.BASE};
  `}

  &:active {
    background-color: ${({ variant }) =>
      variant === 'filled' ? 'purple' : 'lightgrey'};
  }
`;

const ButtonContent = styled(View)<{ size: string }>`
  flex-direction: row;
  align-items: center;
  justify-content: center;
  ${({ size }) =>
    size === 'large'
      ? 'padding: 16px 32px;'
      : size === 'small'
      ? 'padding: 4px 18px;'
      : 'padding: 12px 24px;'}
`;

const ButtonText = styled(Text)<{ variant: string }>`
  color: ${({ variant }) => (variant === 'filled' ? 'white' : colors.PURPLE.BASE)};
  font-size: 16px;
  margin-bottom: 2px;
  /* margin-left: 8px; */
`;

export default CustomButton;
