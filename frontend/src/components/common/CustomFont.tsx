import React from "react";
import styled from "styled-components/native";
import { Text, TextProps } from "react-native";
import { colors } from "@/constants";

type CustomFontProps = TextProps & {
  fontWeight?: 'bold' | 'regular';
  fontSize?: number;
  color?: string;
}

const CustomFont: React.FC<CustomFontProps> = ({
  fontWeight = 'regular',
  fontSize,
  color,
  children,
  ...props
}) => {
  return (
    <StyledText fontWeight={fontWeight} fontSize={fontSize} color={color} {...props}>
      {children}
    </StyledText>
  )
}

const StyledText = styled(Text)<{
  fontWeight: 'bold' | 'regular';
  fontSize?: number;
  color?: string;
}>`
  font-family: ${({ fontWeight }) => 
    fontWeight === 'bold' ? 'Hakgyoansim Dunggeunmiso OTF B' : 'Hakgyoansim Dunggeunmiso OTF R'};
  font-size: ${({ fontSize }) => ( fontSize ? `${fontSize}px` : '14px')};
  color: ${({ color }) => ( color ? color : colors.BLACK)};
`;

export default CustomFont