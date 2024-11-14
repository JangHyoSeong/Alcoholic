import React, { useState, useCallback } from 'react';
import tw from 'twrnc';
import { TouchableOpacity, View, Image, Modal } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { useRoute, RouteProp, useFocusEffect } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { StorageStackParamList } from '@/navigations/stack/StorageStackNavigator';
import { MyStorageNavigations } from '@/constants';
import CustomFont from '@/components/common/CustomFont';
import { getDrinkRef, getDrinkDetailRef } from '@/api/refrigerator';
import { useAppStore } from '@/state/useAppStore';
import axiosInstance from '@/api/axios';
import Ionicons from 'react-native-vector-icons/Ionicons';

interface DrinkItem {
  id: number;
  name: string;
  koreanName: string;
  stockTime: string;
  position: number;
  imageUrl: string;
}

interface DrinkItemDetail {
  id: number;
  name: string;
  koreanName: string;
  degree: number;
  type: string;
  position: number;
  stockTime: Date;
}

type MyStorageDetailScreenRouteProp = RouteProp<StorageStackParamList, 'MyStorageDetail'>

const MyRefDetailScreen: React.FC = () => {
  const { params } = useRoute<MyStorageDetailScreenRouteProp>()
  const { refrigeratorId } = params
  const [ drinks, setDrinks ] = useState<DrinkItem[]>([])
  const [ selectedDrink, setSelectedDrink ] = useState<DrinkItemDetail>()
  const [ isModalVisible, setIsModalVisible ] = useState(false)
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

  const handleOpenModal = async (stockId: number) => {
    try {
      const drinkDetails = await getDrinkDetailRef(token, stockId)
      setSelectedDrink(drinkDetails)
      setIsModalVisible(true)
    } catch (error) {
      console.error('디테일 스크린에서 상세 정보 에러')
    }
  }

  useFocusEffect(
    useCallback(() => {
      const fetchDrinks = async (refrigeratorId: number) => {
        try {
          const data = await getDrinkRef(token, refrigeratorId);
          setDrinks(data);
        } catch (error) {
          console.error('술장고 상세에서 에러', error);
        }
      };
  
      fetchDrinks(refrigeratorId);
    }, [refrigeratorId])
  );

  const renderPosition = (position: number) => {
    const drinkAtPosition = drinks.find((drink) => drink.position === position);
    return (
      <View style={tw`items-center p-2`}>
        {drinkAtPosition ? (
          <>
            <TouchableOpacity onPress={() => handleOpenModal(drinkAtPosition.id)}>
              <View style={tw`p-4 border rounded-lg`}>
                <Image
                  source={{ uri: drinkAtPosition.imageUrl }}
                  style={tw`w-11 h-11 rounded-lg object-cover`}
                  resizeMode='cover'
                />
              </View>
            </TouchableOpacity>
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
    <View style={tw`flex-1 bg-white`}>
      <TouchableOpacity onPress={() => navigation.goBack()}>
        <Ionicons style={tw`p-2`} name="arrow-back-outline" size={30} />
      </TouchableOpacity>
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
      <Modal
        visible={isModalVisible}
        transparent={true}
        animationType="fade"
        onRequestClose={() => setIsModalVisible(false)}
      >
        <View style={tw`flex-1 justify-center items-center bg-black opacity-80`}>
          <View style={tw`bg-white p-6 rounded-lg w-[80%]`}>
            {selectedDrink && (
              <>
                <CustomFont style={tw`text-xl font-bold text-center`}>{selectedDrink.koreanName}</CustomFont>
                <CustomFont>도수: {selectedDrink.degree}%</CustomFont>
                <CustomFont>종류: {selectedDrink.type}</CustomFont>
                <CustomFont>위치: {selectedDrink.position}</CustomFont>
                <CustomFont>입고 시간: {selectedDrink.stockTime}</CustomFont>
                <TouchableOpacity onPress={() => setIsModalVisible(false)}>
                  <CustomFont style={tw`text-red-500 text-center mt-4`}>닫기</CustomFont>
                </TouchableOpacity>
              </>
            )}
          </View>
        </View>
      </Modal>

    </View>
  );
};

export default MyRefDetailScreen;