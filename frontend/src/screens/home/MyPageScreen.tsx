import tw from 'twrnc';
import { Text, View } from 'react-native';
import MypageBanner from '@/components/mypage/MypageBanner';
import MypageForm from '@/components/mypage/MypageForm';
import CustomButton from '@/components/common/CustomButton';
import { useAppStore } from '@/state/useAppStore';
import { useState } from 'react';

const MyPageScreen = () => {

  const setToken = useAppStore((state) => state.setToken)
  const setLogin = useAppStore((state) => state.setLogin)

  const handleLogout = () => {
    setToken('')
    setLogin(false)
  }

  return (
    <View style={tw``}>
     <MypageBanner />
     <MypageForm />
     <CustomButton onPress={handleLogout} label='logout' size='small' style={tw`mt-105`}></CustomButton>
    </View>
  );
};

export default MyPageScreen;
