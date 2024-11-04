import tw from 'twrnc';
import { Text, View } from 'react-native';
import MypageBanner from '@/components/mypage/MypageBanner';
import MypageForm from '@/components/mypage/MypageForm';

const MyPageScreen = () => {
 return (
   <View style={tw``}>
     <MypageBanner />
     <MypageForm />
   </View>
 );
};

export default MyPageScreen;
