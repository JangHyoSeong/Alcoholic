import React, { useEffect, useState } from 'react';
import tw from 'twrnc';
import { View, TextInput, TouchableOpacity } from 'react-native';
import CustomFont from '@/components/common/CustomFont';
import Ionicons from 'react-native-vector-icons/Ionicons';
import CocktailItem from '@/components/cocktail/CocktailItem';
import { useAppStore } from '@/state/useAppStore';
import { getCocktailList, searchCocktailList } from '@/api/cocktail';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack'
import { RecipeStackParamList } from '@/navigations/stack/RecipeStackNavigator';
import { RecipeNavigations } from '@/constants';

const ITEMS_PER_PAGE = 8

const CocktailSearch: React.FC = () => {
  const navigation = useNavigation<NativeStackNavigationProp<RecipeStackParamList>>()
  const token = useAppStore((state) => state.token);
  const [allCocktailData, setAllCocktailData] = useState([]);
  const [displayedCocktailData, setDisplayedCocktailData] = useState([])
  const [ currentPage, setCurrentPage ] = useState(1)
  const [ isFetching, setIsFetching ] = useState(false)
  const [ searchQuery, setSearchQuery ] = useState('')
  const [ isSearching, setIsSearching ] = useState(false)

  const fetchCocktails = async () => {
    try {
      setIsFetching(true);
      const data = await getCocktailList(token);
      setAllCocktailData(data);
      setDisplayedCocktailData(data.slice(0, ITEMS_PER_PAGE)); // 첫 페이지 데이터만 표시
    } catch (error) {
      console.error('데이터 불러오기 실패:', error);
    } finally {
      setIsFetching(false);
    }
  };

  const handleAddCocktail = () => {
    navigation.navigate(RecipeNavigations.RECIPE_ADD)
  }

  const fetchSearchResults = async () => {
    if (!searchQuery.trim()) {
      fetchCocktails()
      return
    }
    try {
      setIsSearching(true)
      const data = await searchCocktailList(token, searchQuery)
      setAllCocktailData(data.result)
      setDisplayedCocktailData(data.result.slice(0, ITEMS_PER_PAGE))
      setCurrentPage(1)
    } catch (error) {
      console.error('메인 페이지 검색 실패', error)
    } finally {
      setIsSearching(false)
    }
  }

  useEffect(() => {
    fetchCocktails()
  }, [])

  const loadMoreData = () => {
    if (isFetching || isSearching) {
      return
    }

    const nextPage = currentPage + 1
    const startIdx = (nextPage - 1) * ITEMS_PER_PAGE
    const endIdx = nextPage * ITEMS_PER_PAGE

    setDisplayedCocktailData((prevData) => [
      ...prevData,
      ...allCocktailData.slice(startIdx, endIdx)
    ])
    setCurrentPage(nextPage)
  }

  return (
      <View style={tw`flex-1 bg-white`}>
        <View style={tw`flex-row bg-gray-100 mb-4`}>
          <TextInput
            style={tw`flex-1 border opacity-50 rounded-lg p-2 border-gray-300`}
            placeholder="칵테일 이름을 검색하세요"
            value={searchQuery}
            onChangeText={setSearchQuery}
            onSubmitEditing={fetchSearchResults}
            maxLength={50}
          />
          <Ionicons style={tw`absolute top-3 left-94 text-[22px]`} name='search' onPress={fetchSearchResults}/>
        </View>
        <TouchableOpacity style={tw`absolute top-12 left-65 pb-1`} onPress={handleAddCocktail}>
          <CustomFont style={tw`text-blue-300`}>칵테일을 추가하세요!</CustomFont>
        </TouchableOpacity>
        <View style={tw`pt-2`}>
          <CocktailItem
            cocktailData={displayedCocktailData}
            onLoadMore={loadMoreData}
            isFetching={isFetching}
          />
        </View>
      </View>
  );
};

export default CocktailSearch;
