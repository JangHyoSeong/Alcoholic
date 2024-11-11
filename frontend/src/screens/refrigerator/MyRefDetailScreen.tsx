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

interface DrinkItem {
  stockId: number;
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
    const drinkAtPosition = drinks.find(drink => drink.position === position);

    if (drinkAtPosition) {
      return (
        <View style={tw`flex-1 justify-center items-center p-4 border rounded-lg`}>
          <Image source={{ uri: drinkAtPosition.imageUrl }} style={tw`w-30 h-30 rounded-lg`} />
          <CustomFont style={tw`mt-2 text-center`}>{drinkAtPosition.koreanName}</CustomFont>
        </View>
      );
    } else {
      return (
        <View style={tw`flex-1 justify-center items-center bg-gray-200 p-4 border rounded-lg`}>
        </View>
      );
    }
  };

  return (
    <View style={tw``}>
      <TouchableOpacity onPress={() => handleMoveAddDrink(refrigeratorId)}>
        <CustomFont>술 등록 하기</CustomFont>
      </TouchableOpacity>
      <View style={tw`flex-1 h-200 justify-center items-center p-4`}>
        <View style={tw`flex-row w-full justify-between mb-4`}>
          <View style={tw`w-[20px] p-2`}>{renderPosition(1)}</View>
          <View style={tw`w-[20px] p-2`}>{renderPosition(2)}</View>
        </View>
        <View style={tw`flex-row w-full justify-between`}>
          <View style={tw`w-[20px] p-2`}>{renderPosition(3)}</View>
          <View style={tw`w-[20px] p-2`}>{renderPosition(4)}</View>
        </View>
      </View>
    </View>
  );
};

export default MyRefDetailScreen;