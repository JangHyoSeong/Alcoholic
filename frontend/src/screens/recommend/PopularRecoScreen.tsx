import React, { useEffect, useState } from 'react';
import tw from 'twrnc';
import { View, TouchableOpacity, FlatList } from 'react-native';
import CustomFont from '@/components/common/CustomFont';
import { useNavigation } from '@react-navigation/native';
import Ionicons from 'react-native-vector-icons/Ionicons'
import { useAppStore } from '@/state/useAppStore';
import { getPopularCock } from '@/api/recommend';
import { SafeAreaView } from 'react-native-safe-area-context';
import PopularRecoItem from '@/components/recommend/PopularRecoItem';

const ITEMS_PER_PAGE = 8

const PopularRecoScreen: React.FC = () => {
  const navigation = useNavigation()
  const token = useAppStore((state) => state.token)
  const [ popCocktailData, setPopCocktailData ] = useState([])
  const [ displayedCocktailData , setDisplayedCocktailData ] = useState([])
  const [ currentPage, setCurrentPage ] = useState(1)
  const [ isFetching, setIsFetching ] = useState(false)
  
  const fetchPopCocktails = async () => {
    try {
      setIsFetching(true)
      const data = await getPopularCock(token)
      setPopCocktailData(data)
      setDisplayedCocktailData(data.slice(0, ITEMS_PER_PAGE))
    } catch (error) {
      console.error('인기 추천 받아오기 실패')
    } finally {
      setIsFetching(false)
    }
  }

  useEffect(() => {
    fetchPopCocktails()
  }, [])

  const loadMoreData = () => {
    if (isFetching) {
      return
    }

    const nextPage = currentPage + 1
    const startIdx = (nextPage - 1) * ITEMS_PER_PAGE
    const endIdx = nextPage * ITEMS_PER_PAGE

    setDisplayedCocktailData((prevData) => [
      ...prevData,
      ...popCocktailData.slice(startIdx, endIdx)
    ])
    setCurrentPage(nextPage)
  }
  
  return (
    <SafeAreaView style={tw`bg-white`}>
      <TouchableOpacity onPress={() => navigation.goBack()}>
        <Ionicons style={tw`p-2`} name="arrow-back-outline" size={30} />
      </TouchableOpacity>
      <View style={tw`pt-2`}>
        <PopularRecoItem
          result={displayedCocktailData}
          onLoadMore={loadMoreData}
          isFetching={isFetching}
        />
      </View>
    </SafeAreaView>
  );
};

export default PopularRecoScreen;