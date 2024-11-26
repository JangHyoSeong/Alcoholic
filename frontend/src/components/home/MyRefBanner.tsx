import React from 'react';
import tw from 'twrnc';
import { ImageBackground, TouchableOpacity, View } from 'react-native';
import CustomFont from '../common/CustomFont';
import mandrake from '@/assets/mandrake.png'
import refImage from '@/assets/ref.png'
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { useNavigation } from '@react-navigation/native';
import { StorageStackParamList } from '@/navigations/stack/StorageStackNavigator';
import { MyStorageNavigations, mainNavigations } from '@/constants';
import { useUserStore } from '@/state/useUserStore';

const MyRefBanner = () => {
  const navigation = useNavigation<NativeStackNavigationProp<StorageStackParamList>>()

  const handlePress = () => {
    navigation.navigate(mainNavigations.MYSTORAGE, {screen: MyStorageNavigations.STORAGE_HOME})
  }
  const isRef = useUserStore((state) => state.isRef)

  const backgroundImage = isRef ? refImage : mandrake
  const bannerText = isRef ? '바로 가기' : '술장고를 등록해 주세요!'

 return (
  <TouchableOpacity onPress={handlePress} activeOpacity={0.7}>
    <View style={tw`bg-transparent`}>
      <CustomFont style={tw`mt-5 text-[25px] ml-5`}>나의 술장고</CustomFont>
    </View>
    <ImageBackground
      source={backgroundImage}
      style={[tw`ml-[7px] mt-5 w-[400px] h-[200px] rounded-[20px] overflow-hidden opacity-80`, {elevation: 10}]}
      resizeMode='cover'>
      <View style={tw`p-4`}>
        <CustomFont style={tw`text-white text-[28px] ml-10 mt-10`}>{bannerText}</CustomFont>
      </View>
    </ImageBackground>
  </TouchableOpacity>
 );
};

export default MyRefBanner;