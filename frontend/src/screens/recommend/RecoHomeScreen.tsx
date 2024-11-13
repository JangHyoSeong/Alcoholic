import React, { useState, useEffect } from 'react';
import tw from 'twrnc';
import { SafeAreaView, TouchableOpacity, View, FlatList, Image } from 'react-native';
import HomeBanner from '@/components/home/HomeBanner';
import CustomFont from '@/components/common/CustomFont';
import { useNavigation } from '@react-navigation/native';
import { RecommendNavigations } from '@/constants';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { RecoStackParamList } from '@/navigations/stack/RecoStackNavigator';
import { getPopularCock, getCustomCock } from '@/api/recommend';
import { useAppStore } from '@/state/useAppStore';

interface PopularCocktailData {
  id : number;
  enCocktailName : string;
  krCocktailName : string;
  value : number;
  image : string;
  instruction : string;
}

interface CustomCocktailData {
  id : number;
  enCocktailName : string;
  krCocktailName : string;
  value : number;
  image : string;
  instruction : string;
  alcoholCategoriesName: string[];
}

const RecoHomeScreen: React.FC = () => {
  const token = useAppStore((state) => state.token)
  const navigation = useNavigation<NativeStackNavigationProp<RecoStackParamList>>()
  const [ topPopCocktails, setTopPopCocktails ] = useState<PopularCocktailData[]>([])
  const [ topCusCocktails, setTopCusCocktails ] = useState<CustomCocktailData[]>([])

  useEffect(() => {
    const fetchData = async () => {
      const popData = await getPopularCock(token)
      const cusData = await getCustomCock(token)
      setTopPopCocktails(popData.slice(0, 5))
      setTopCusCocktails(cusData.slice(0, 5))
    }
    fetchData()
  }, [])

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

  const renderPopItem = ({ item }: { item: PopularCocktailData }) => (
    <TouchableOpacity onPress={() => handlePopularList()} style={tw`mr-4`}>
      <Image source={{ uri: item.image }} style={tw`w-32 h-32 rounded-lg`} />
      <CustomFont style={tw`text-center mt-1`}>{item.krCocktailName}</CustomFont>
    </TouchableOpacity>
  );

  const renderCusItem = ({ item }: { item: CustomCocktailData }) => (
    <TouchableOpacity onPress={() => handleCustomList()} style={tw`mr-4`}>
      <Image source={{ uri: item.image }} style={tw`w-32 h-32 rounded-full`} />
      <CustomFont style={tw`text-center mt-1`}>{item.krCocktailName}</CustomFont>
    </TouchableOpacity>
  );

 return (
   <SafeAreaView style={tw`flex-1 bg-white`}>
    <HomeBanner />
    <CustomFont fontSize={22} style={tw`mt-5 text-center text-blue-300`} onPress={handleDrinkList}>술 목록 보기</CustomFont>
    <View style={tw`pt-5`}>
        <CustomFont style={tw`text-[25px] text-center pb-5`}>인기 칵테일!</CustomFont>
        <FlatList
          data={topPopCocktails}
          renderItem={renderPopItem}
          keyExtractor={(item) => item.id.toString()}
          horizontal
          showsHorizontalScrollIndicator={false}
          contentContainerStyle={tw`pl-4`}
        />
    </View>
    <View style={tw`pt-5`}>
        <CustomFont style={tw`text-[25px] text-center pb-5`}>추천 칵테일!</CustomFont>
        <FlatList
          data={topCusCocktails}
          renderItem={renderCusItem}
          keyExtractor={(item) => item.id.toString()}
          horizontal
          showsHorizontalScrollIndicator={false}
          contentContainerStyle={tw`pl-4`}
        />
    </View>
   </SafeAreaView>
 )
}

export default RecoHomeScreen;
