import React, { useEffect, useState } from 'react';
import tw from 'twrnc';
import { View } from 'react-native';
import CustomFont from '@/components/common/CustomFont';
import CocktailItem from '@/components/cocktail/CocktailItem';
import { useAppStore } from '@/state/useAppStore';
import { getCocktailList } from '@/api/cocktail';

const ITEMS_PER_PAGE = 4

const CocktailSearch: React.FC = () => {
  const token = useAppStore((state) => state.token);
  const [allCocktailData, setAllCocktailData] = useState([]);
  const [displayedCocktailData, setDisplayedCocktailData] = useState([])
  const [ currentPage, setCurrentPage ] = useState(1)
  const [ isFetching, setIsFetching ] = useState(false)

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

  useEffect(() => {
    fetchCocktails()
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
      ...allCocktailData.slice(startIdx, endIdx)
    ])
    setCurrentPage(nextPage)
  }

  return (
      <View style={tw`flex-1`}>
        <CustomFont style={tw`flex text-center pr-2`} fontSize={30}>
          레시피 메인홈
        </CustomFont>
        <CocktailItem
          cocktailData={displayedCocktailData}
          onLoadMore={loadMoreData}
          isFetching={isFetching}
        />
      </View>
  );
};

export default CocktailSearch;
