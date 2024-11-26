import React, { useState, useEffect } from 'react';
import tw from 'twrnc';
import { View, Modal, TouchableOpacity, FlatList} from 'react-native';
import CustomFont from '@/components/common/CustomFont';
import Ionicons from 'react-native-vector-icons/Ionicons';
import CustomButton from '@/components/common/CustomButton';
import DrinkItem from '@/components/drink/DrinkItem';
import { useAppStore } from '@/state/useAppStore';
import { getDrinkList, getDrinkCategory } from '@/api/drink';

const ITEMS_PER_PAGE = 8
const categories = [
  { id: 1, name: '증류주' },
  { id: 2, name: '맥주' },
  { id: 3, name: '와인' },
  { id: 4, name: '브랜디' },
  { id: 5, name: '위스키' },
  { id: 6, name: '진' },
  { id: 7, name: '럼' },
  { id: 8, name: '테킬라' },
  { id: 9, name: '리큐르' },
  { id: 10, name: '막걸리' },
  { id: 11, name: '보드카' },
  { id: 12, name: '소주' },
  { id: 13, name: '기타' },
];

const DrinkListScreen: React.FC = () => {
  const token = useAppStore((state) => state.token)
  const [ allDrinkData, setAllDrinkData ] = useState([])
  const [ displayedDrinkData, setDisplayedDrinkData ] = useState([])
  const [ currentPage, setCurrentPage ] = useState(1)
  const [ isFetching, setIsFetching ] = useState(false)
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [selectedCategory, setSelectedCategory] = useState<number | null>(null);

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

  const fetchDrinksByCategory = async (categoryId: number) => {
    try {
      setIsFetching(true);
      const data = await getDrinkCategory(token, categoryId);
      setAllDrinkData(data);
      setDisplayedDrinkData(data.slice(0, ITEMS_PER_PAGE));
      setCurrentPage(1); // 페이지 초기화
      setSelectedCategory(categoryId); // 선택된 카테고리 저장
    } catch (error) {
      console.error('카테고리별 데이터 불러오기 실패');
    } finally {
      setIsFetching(false);
      setIsModalVisible(false); // 모달 닫기
    }
  };

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

  const openCategoryModal = () => {
    setIsModalVisible(true);
  };

  const handleCategorySelect = (categoryId: number) => {
    fetchDrinksByCategory(categoryId);
  };

  return (
    <View style={tw`flex-1 bg-white`}>
      <Ionicons style={tw`text-[35px] text-right`} name='options' onPress={openCategoryModal} />
      <Modal visible={isModalVisible} transparent animationType="slide">
        <View style={tw`flex-1 justify-center items-center bg-black/50`}>
          <View style={tw`bg-white w-3/4 p-4 rounded-lg`}>
            <CustomFont style={tw`text-center text-xl mb-4`}>카테고리 선택</CustomFont>
            <FlatList
              data={categories}
              keyExtractor={(item) => item.id.toString()}
              renderItem={({ item }) => (
                <TouchableOpacity
                  style={tw`p-2 border-b border-gray-300`}
                  onPress={() => handleCategorySelect(item.id)}
                >
                  <CustomFont style={tw`text-lg text-center`}>{item.name}</CustomFont>
                </TouchableOpacity>
              )}
            />
            <CustomButton label="닫기" size='small' onPress={() => setIsModalVisible(false)} />
          </View>
        </View>
      </Modal>

      <DrinkItem 
      drinkData={displayedDrinkData}
      onLoadMore={loadMoreData}
      isFetching={isFetching}
      />
    </View>
  );
};

export default DrinkListScreen;