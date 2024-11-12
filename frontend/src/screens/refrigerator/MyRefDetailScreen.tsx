import React, { useState, useEffect } from 'react';
import tw from 'twrnc';
import { TouchableOpacity, View, Image } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { useRoute, RouteProp } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { StorageStackParamList } from '@/navigations/stack/StorageStackNavigator';
import { MyStorageNavigations } from '@/constants';
import CustomFont from '@/components/common/CustomFont';
import { getDrinkRef } from '@/api/refrigerator';
import { useAppStore } from '@/state/useAppStore';
import axiosInstance from '@/api/axios';

interface DrinkItem {
  id: number;
  name: string;
  koreanName: string;
  stockTime: string;
  position: number;
  imageUrl: string;
}

type MyStorageDetailScreenRouteProp = RouteProp<StorageStackParamList, 'MyStorageDetail'>

const MyRefDetailScreen: React.FC = () => {
  const { params } = useRoute<MyStorageDetailScreenRouteProp>()
  const { refrigeratorId } = params
  const [ drinks, setDrinks ] = useState<DrinkItem[]>([])
  const navigation = useNavigation<NativeStackNavigationProp<StorageStackParamList>>()
  const token = useAppStore((state) => state.token)

  const handleMoveAddDrink = (refrigeratorId: number) => {
    navigation.navigate(MyStorageNavigations.WINE_REGISTER, {refrigeratorId})
  }

  const handleDelete = async (stockId: number) => {
    try {
      await axiosInstance.delete(`refrigerators/stocks/${stockId}`, {
        headers: {
          Authorization: token
        }
      })
      console.log('삭제 성공')
    } catch (error) {
      console.error('삭제 실패')
    }
  }

  useEffect(() => {
    const fetchDrinks = async (refrigeratorId: number) => {
      try {
        const data = await getDrinkRef(token, refrigeratorId)
        setDrinks(data)
      } catch (error) {
        console.error('술장고 상세에서 에러', error)
      }
    }

    fetchDrinks(refrigeratorId)
  }, [])

  const renderPosition = (position: number) => {
    const drinkAtPosition = drinks.find((drink) => drink.position === position);
    return (
      <View style={tw`items-center p-2`}>
        {drinkAtPosition ? (
          <>
            <View style={tw`p-4 border rounded-lg`}>
              <Image
                source={{ uri: drinkAtPosition.imageUrl }}
                style={tw`w-11 h-11 rounded-lg object-cover`}
                resizeMode='cover'
              />
            </View>
            <CustomFont style={tw`mt-2 text-center text-black`} ellipsizeMode='tail'>
              {drinkAtPosition.koreanName}
            </CustomFont>

            <TouchableOpacity onPress={() => handleDelete(drinkAtPosition.id)}>
              <CustomFont style={tw`text-[10px] text-red-300 text-center`}>
                술 삭제 하기
              </CustomFont>
            </TouchableOpacity>
          </>
        ) : (
          <>
            <View style={tw`w-20 h-20 bg-gray-300 rounded-lg`} />
            <CustomFont style={tw`text-center text-sm mt-1 text-gray-500`}>빈 자리</CustomFont>
          </>
        )}
      </View>
    );
  };

  return (
    <View style={tw`flex-1 bg-white justify-evenly`}>
      <View style={tw`flex-wrap flex-row mt-10 w-full justify-around p-4`}>
        {[1, 2, 3, 4].map((position) => (
          <View key={position} style={tw`w-[80px]`}>
            {renderPosition(position)}
          </View>
        ))}
      </View>
      <View style={tw`flex-row p-2`}>
      <TouchableOpacity onPress={() => handleMoveAddDrink(refrigeratorId)}>
        <CustomFont style={tw`text-[20px] text-blue-300 text-center`}>술 등록 하기</CustomFont>
      </TouchableOpacity>
      </View>
    </View>
  );
};

export default MyRefDetailScreen;