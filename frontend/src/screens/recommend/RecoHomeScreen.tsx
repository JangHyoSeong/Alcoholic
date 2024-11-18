import React, { useState, useCallback } from 'react';
import tw from 'twrnc';
import { SafeAreaView, TouchableOpacity, View, FlatList, Image } from 'react-native';
import HomeBanner from '@/components/home/HomeBanner';
import CustomFont from '@/components/common/CustomFont';
import { useNavigation, useFocusEffect } from '@react-navigation/native';
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

  
useFocusEffect(
  useCallback(() => {
    const fetchData = async () => {
      try {
        const popData = await getPopularCock(token);
        const cusData = await getCustomCock(token);
        setTopPopCocktails(popData.slice(0, 5));
        setTopCusCocktails(cusData.slice(0, 5));
      } catch (error) {
        console.error('데이터 로드 실패', error);
      }
    };

    fetchData();

    return () => {
      setTopPopCocktails([]);
      setTopCusCocktails([]);
    };
  }, [token])
);

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

      {topCusCocktails.length === 0 ? (
        // topCusCocktails 배열이 비어 있을 때 보여줄 메시지
        <View style={tw`justify-center items-center py-10`}>
          <CustomFont style={tw`mt-10 text-[25px] text-red-400`}>
            추천을 받으시려면 냉장고를 채워주세요!
          </CustomFont>
        </View>
      ) : (
        // topCusCocktails 배열에 데이터가 있을 때 FlatList 렌더링
        <FlatList
          data={topCusCocktails}
          renderItem={renderCusItem}
          keyExtractor={(item) => item.id.toString()}
          horizontal
          showsHorizontalScrollIndicator={false}
          contentContainerStyle={tw`pl-4`}
        />
      )}
    </View>
   </SafeAreaView>
 )
}

export default RecoHomeScreen;
