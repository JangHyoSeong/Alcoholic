import tw from 'twrnc';
import { Text, View } from 'react-native';
import MypageBanner from '@/components/mypage/MypageBanner';
import MypageForm from '@/components/mypage/MypageForm';
import CustomButton from '@/components/common/CustomButton';

const MyPageScreen = () => {
 return (
   <View style={tw``}>
     <MypageBanner />
     <MypageForm />
     <CustomButton label='logout' size='small' style={tw`mt-105`}></CustomButton>
   </View>
 );
};

export default MyPageScreen;
