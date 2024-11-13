import tw from 'twrnc';
import { SafeAreaView, TouchableOpacity, View } from 'react-native';
import HomeBanner from '@/components/home/HomeBanner';
import CustomFont from '@/components/common/CustomFont';
import { useNavigation } from '@react-navigation/native';
import { RecommendNavigations } from '@/constants';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { RecoStackParamList } from '@/navigations/stack/RecoStackNavigator';


const RecoHomeScreen = () => {
  const navigation = useNavigation<NativeStackNavigationProp<RecoStackParamList>>()

  // 모든 술 정보 목록 페이지로 이동
  const handleDrinkList = () => {
    navigation.navigate(RecommendNavigations.DRINK_LIST)
  }
  
  // 인기 추천 리스트 페이지 이동
  const handlePopularList = () => {
    navigation.navigate(RecommendNavigations.POPULAR_RECO)
  }

  // 사용자 재고 기반 추천 리스트 페이지 이동
  const handleCustomList = () => {
    navigation.navigate(RecommendNavigations.MY_RECO)
  }

 return (
   <SafeAreaView style={tw`flex-1 bg-white`}>
    <HomeBanner />
    <CustomFont fontSize={22} style={tw`mt-5 text-center text-blue-300`} onPress={handleDrinkList}>술 목록 보기</CustomFont>
    <View>
      <TouchableOpacity onPress={handleCustomList}>
        <CustomFont>추천 칵테일 컴포넌트 자리!</CustomFont>
      </TouchableOpacity>
    </View>
    <View>
      <TouchableOpacity onPress={handlePopularList}>
        <CustomFont>인기 칵테일 컴포넌트 자리!</CustomFont>
      </TouchableOpacity>
    </View>
   </SafeAreaView>
 )
}

export default RecoHomeScreen;
