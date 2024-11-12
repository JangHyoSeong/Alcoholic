import tw from 'twrnc';
import { ImageBackground, View } from 'react-native';
import CustomFont from '../common/CustomFont';
import bannerImage from '@/assets/banner.png'

const HomeBanner = () => {
 return (
  <ImageBackground
    source={bannerImage}
    style={[tw`ml-[7px] mt-2 w-[400px] h-[200px] rounded-[20px] overflow-hidden`, {elevation: 5}]}
    resizeMode='cover'>
    <View style={tw`p-4`}>
      <CustomFont style={[tw`text-black text-[30px] ml-8 mt-10`, {elevation: 5}]}>여기는</CustomFont>
      <CustomFont style={[tw`text-black text-[30px] ml-8`, {elevation: 5}]}>알코홀릭!</CustomFont>
    </View>
  </ImageBackground>
 );
};

export default HomeBanner;

