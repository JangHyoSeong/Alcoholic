import React, { useState, useEffect } from 'react';
import tw from 'twrnc';
import { View } from 'react-native';
import CustomFont from '@/components/common/CustomFont';
import Ionicons from 'react-native-vector-icons/Ionicons'
import DrinkItem from '@/components/drink/DrinkItem';
import { useAppStore } from '@/state/useAppStore';
import { getDrinkList } from '@/api/drink';

const ITEMS_PER_PAGE = 4

const DrinkListScreen: React.FC = () => {
  const token = useAppStore((state) => state.token)
  const [ allDrinkData, setAllDrinkData ] = useState([])
  const [ displayedDrinkData, setDisplayedDrinkData ] = useState([])
  const [ currentPage, setCurrentPage ] = useState(1)
  const [ isFetching, setIsFetching ] = useState(false)

  const fetchDrinks = async () => {
    try {
      setIsFetching(true);
      const data = await getDrinkList(token)
      setAllDrinkData(data)
      setDisplayedDrinkData(data.slice(0, ITEMS_PER_PAGE))
    } catch (error) {
      console.error('데이터 불러오기 실패')
    } finally {
      setIsFetching(false)
    }
  }

  useEffect(() => {
    fetchDrinks()
  }, [])

  const loadMoreData = () => {
    if (isFetching) {
      return
    }

    const nextPage = currentPage + 1
    const startIdx = (nextPage - 1) * ITEMS_PER_PAGE
    const endIdx = nextPage * ITEMS_PER_PAGE

    setDisplayedDrinkData((prevData) => [
      ...prevData,
      ...allDrinkData.slice(startIdx, endIdx)
    ])
    setCurrentPage(nextPage)
  }

  return (
    <View style={tw`flex-1 bg-white`}>
     <DrinkItem 
      drinkData={displayedDrinkData}
      onLoadMore={loadMoreData}
      isFetching={isFetching}
      />
    </View>
  );
};

export default DrinkListScreen;