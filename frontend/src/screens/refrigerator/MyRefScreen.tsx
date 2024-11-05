import React, { useState, useEffect } from 'react';
import tw from 'twrnc';
import { FlatList, TouchableOpacity, View, ImageBackground } from 'react-native';
import CustomFont from '@/components/common/CustomFont';
import ref from '@/assets/ref.png'
import RefItem from '@/components/refrigerator/RefItem';
import AddRefModal from '@/components/refrigerator/AddRefModal';
import { useAppStore } from '@/state/useAppStore';
import { getRef } from '@/api/refrigerator';

interface RefItemData {
  id: number;
  name: string;
  main: boolean;
}

const MyRefScreen: React.FC = () => {
  const [refItems, setRefItems ] = useState<RefItemData[]>([])
  const [isModalVisible, setIsModalVisible ] = useState(false)
  const [nextId, setNextId ] = useState(0)
  const token = useAppStore((state) => state.token)
  const [refetch, setRefetch ] = useState(false)

  useEffect(() => {
    const fetchRefItems = async () => {
      const data = await getRef(token)
      console.log('이거 술장고 아이템',data)
      if (data) {
        setRefItems(data.results)
        setNextId(data.results.length)
      }
    }
    fetchRefItems()
  }, [refetch])

  const handleAddSuccess = (serialNumber: string) => {
    const newRefItem: RefItemData = {
      id: nextId,
      name: `냉장고${nextId}`,
      main: false
    };

    setRefItems((prevItems) => [...prevItems, newRefItem]);
    setNextId((prevId) => prevId + 1); // nextId 증가
    setRefetch((prev) => !prev)
    setIsModalVisible(false)
  };

  return (
    <View style={tw`mt-5`}>
      <CustomFont style={tw`ml-4`} fontSize={30} fontWeight='bold'>나의 술장고</CustomFont>
      <TouchableOpacity onPress={() => setIsModalVisible(true)}>
        <CustomFont style={tw`text-right text-[blue] mr-2`} fontSize={15}>추가하기</CustomFont>
      </TouchableOpacity>
      {refItems.length > 0 ? (
        <FlatList
          data={refItems}
          keyExtractor={(item) => item.id.toString()}
          renderItem={({ item }) => <RefItem item={item} />}
        />
      ) : (
      <ImageBackground
        source={ref}
        style={[tw`ml-[7px] mt-5 w-[400px] h-[680px] rounded-md overflow-hidden opacity-80`, {elevation: 10}]}
        resizeMode='cover'>
        <View style={tw`p-4`}>
          <CustomFont style={tw`text-white text-[28px] ml-10 mt-10`}>술장고를 등록해 주세요!</CustomFont>
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