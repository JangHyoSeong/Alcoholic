import React, { useEffect, useRef } from 'react';
import { View, Image, Animated } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import mainLogo from '@/assets/main_logo.png'
import CustomFont from '@/components/common/CustomFont';
import { NativeStackNavigationProp } from '@react-navigation/native-stack/lib/typescript/src/types';
import { AuthStackParamList } from '@/navigations/stack/AuthStackNavigator';
import { AuthNavigations } from '@/constants';
import styled from 'styled-components/native';

const SplashScreen = () => {
  const navigation = useNavigation<NativeStackNavigationProp<AuthStackParamList>>();
  const logoAnim = useRef(new Animated.Value(0)).current

  useEffect(() => {
    const timer = setTimeout(() => {
      navigation.replace(AuthNavigations.AUTH_HOME);
    }, 3000)

    Animated.loop(
      Animated.sequence([
        Animated.timing(logoAnim, {
          toValue: 1, // 오른쪽 이동
          duration: 1000,
          useNativeDriver: true,
        }),
        Animated.timing(logoAnim, {
          toValue: 0,
          duration: 1000,
          useNativeDriver: true,
        }),
      ]),
    ).start()

    return () => clearTimeout(timer)
  }, [navigation])

  const logoInterpolate = logoAnim.interpolate({
    inputRange: [0, 1],
    outputRange: [-20, 10],
  });

 return (
 <SplashScreenBlock>
  <View >
      <CustomFont fontSize={35} fontWeight="bold">
        술Django!
      </CustomFont>
      <CustomFont fontSize={25} color="gray">
        와인과 칵테일을 쉽게!
      </CustomFont>
      <LogoImageBlock
        source={mainLogo} // 로고 이미지 경로
        style={{ transform: [{ translateY: logoInterpolate }] }}
      />
    </View>
 </SplashScreenBlock>
 );
};

const SplashScreenBlock = styled.View`
 flex: 1; 
 justify-content: center; 
 align-items: center;
  `;

const LogoImageBlock = styled(Animated.Image)`
marginTop: 50px;
marginLeft: 30px;
width: 150px;
height: 150px;
resizeMode: 'contain';
`;

export default SplashScreen;
