import tw from 'twrnc';
import { SafeAreaView } from 'react-native';
import HomeBanner from '@/components/home/HomeBanner';
import CustomFont from '@/components/common/CustomFont';
import { useNavigation } from '@react-navigation/native';
import { RecommendNavigations } from '@/constants';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { RecoStackParamList } from '@/navigations/stack/RecoStackNavigator';


const RecoHomeScreen = () => {
  const navigation = useNavigation<NativeStackNavigationProp<RecoStackParamList>>()

  const handleDrinkList = () => {
    navigation.navigate(RecommendNavigations.DRINK_LIST)
  }

 return (
   <SafeAreaView style={tw`flex-1 bg-white`}>
    <HomeBanner />
    <CustomFont fontSize={22} style={tw`mt-5 text-center`} onPress={handleDrinkList}>술 목록 보기!</CustomFont>
   </SafeAreaView>
 )
}

export default RecoHomeScreen;
