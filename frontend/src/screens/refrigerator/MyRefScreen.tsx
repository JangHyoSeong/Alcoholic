import React, { useState, useEffect } from 'react';
import tw from 'twrnc';
import { FlatList, TouchableOpacity, View, ImageBackground } from 'react-native';
import CustomFont from '@/components/common/CustomFont';
import ref from '@/assets/ref.png'
import RefItem from '@/components/refrigerator/RefItem';
import AddRefModal from '@/components/refrigerator/AddRefModal';
import { useAppStore } from '@/state/useAppStore';
import { getRef } from '@/api/refrigerator';
import { useUserStore } from '@/state/useUserStore';

export interface RefItemData {
  id: number;
  name: string;
  main: boolean;
}

const MyRefScreen: React.FC = () => {
  const [refItems, setRefItems ] = useState<RefItemData[]>([])
  const [isModalVisible, setIsModalVisible ] = useState(false)
  const [ nextId, setNextId ] = useState(0)
  const token = useAppStore((state) => state.token)
  const [refetch, setRefetch ] = useState(false)
  const setIsRef = useUserStore((state) => state.setIsRef)

  useEffect(() => {
    // refItems의 길이에 따라 isRef 상태 업데이트
    setIsRef(refItems.length > 0);
  }, [refItems, setIsRef]);

  useEffect(() => {
    const fetchRefItems = async () => {
      const data = await getRef(token)
      // console.log('이거 술장고 아이템',data)
      if (data) {
        setRefItems(data.results)
        const maxId = data.results.reduce((max: number, item: RefItemData) => Math.max(max, item.id), 0);
        setNextId(maxId)
      }
    }
    fetchRefItems()
  }, [refetch])

  const handleAddSuccess = (serialNumber: string) => {

    const newId = nextId

    const newRefItem: RefItemData = {
      id: newId,
      name: `냉장고${newId}`,
      main: false
    };

    setRefItems((prevItems) => {
      // 현재 항목의 ID 배열을 가져옵니다.
      const existingIds = prevItems.map(item => item.id);
      
      let uniqueId = newId
      while (existingIds.includes(uniqueId)) {
        uniqueId++;
      }
  
      return [...prevItems, { ...newRefItem, id: uniqueId}];
    });
    setNextId(prevId => prevId + 1)
    setRefetch((prev) => !prev)
    setIsModalVisible(false)
  };

  const handleDelete = async (id: number) => {
    // 삭제 후 상태 업데이트
    setRefItems((prevItems) => prevItems.filter(item => item.id !== id));
    setRefetch((prev) => !prev); // 데이터 새로 고침
  };

  return (
    <View style={tw.style('mt-5')}>
      <CustomFont style={tw.style('ml-4')} fontSize={30} fontWeight='bold'>나의 술장고</CustomFont>
      <TouchableOpacity onPress={() => setIsModalVisible(true)}>
        <CustomFont style={tw.style('text-right', 'text-[purple]' ,'mr-2')} fontSize={15}>추가하기</CustomFont>
      </TouchableOpacity>
      {refItems.length > 0 ? (
        <FlatList
          data={refItems}
          keyExtractor={(item) => item.id.toString()}
          renderItem={({ item }) => <RefItem item={item} onDelete={handleDelete} />}
        />
      ) : (
      <ImageBackground
        source={ref}
        style={[tw.style('ml-[7px]', 'mt-5', 'w-[400px]', 'h-[680px]', 'rounded-md', 'overflow-hidden','opacity-80'), {elevation: 10}]}
        resizeMode='cover'>
        <View style={tw.style('p-4')}>
          <CustomFont style={tw.style('text-white', 'text-[28px]', 'ml-10', 'mt-10')}>술장고를 등록해 주세요!</CustomFont>
        </View>
      </ImageBackground>
      )}

      <AddRefModal
        visible={isModalVisible}
        onClose={() => setIsModalVisible(false)}
        onAddSuccess={handleAddSuccess}
        token={token}
      />
    </View>
  );
};

export default MyRefScreen;