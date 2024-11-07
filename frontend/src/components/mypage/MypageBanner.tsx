import tw from 'twrnc';
import { View, ImageBackground } from 'react-native';
import mypagebanner from '@/assets/mypagebanner.png'
import MyProfile from './MyProfile';


const MypageBanner = () => {
 return (
  <ImageBackground
  source={mypagebanner}
  style={[tw`ml-[7px] mt-2 w-[400px] h-[200px] rounded-[20px] overflow-hidden`, {elevation: 5}]}
  resizeMode='cover'>
    <View style={tw`p-4`}>
      <MyProfile />
    </View>
  </ImageBackground>
 );
};

export default MypageBanner;
