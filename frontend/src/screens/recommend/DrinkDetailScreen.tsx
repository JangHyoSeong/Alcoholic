import React, { useState, useEffect } from 'react';
import tw from 'twrnc';
import { View, ScrollView, TouchableOpacity } from 'react-native';
import { useRoute, RouteProp, useNavigation } from '@react-navigation/native';
import CustomFont from '@/components/common/CustomFont';
import { DrinkDetailProps } from '@/types/drink';
import { getDrinkDetail } from '@/api/drink';
import { useAppStore } from '@/state/useAppStore';
import LoadingSpinner from '@/components/common/LoadingSpinner';
import { RecoStackParamList } from '@/navigations/stack/RecoStackNavigator';
import Ionicons from 'react-native-vector-icons/Ionicons';

const DrinkDetailScreen: React.FC = () => {
  const route = useRoute<RouteProp<RecoStackParamList, 'DrinkDetail'>>()
  const token = useAppStore((state) => state.token)
  const navigation = useNavigation()
  const { drinkId } = route.params
  const [ drinkDetailData, setDrinkDetailData ] = useState<DrinkDetailProps | null>(null)
  const [ loading, setLoading ] = useState(false)

  useEffect(() => {
    fetchDrinkDetail(drinkId)
  }, [drinkId])

  const fetchDrinkDetail = async (drinkId: number) => {
    setLoading(true)
    try {
      const DrinkData = await getDrinkDetail(token, drinkId)
      setDrinkDetailData(DrinkData)
    } catch (error) {
      console.error('에러 발생', error)
    } finally {
      setLoading(false)
    }
  }

  if (loading) {
    return <LoadingSpinner />
  }


  return (
    <ScrollView style={tw`flex-1 bg-white`}>
      <TouchableOpacity
        onPress={() => navigation.goBack()}  // 뒤로가기
        style={tw`p-2`}>
        <Ionicons name={'arrow-back-outline'} size={30} />
      </TouchableOpacity>
      {/* 칵테일 정보 렌더링 */}
    {drinkDetailData !== null && (
      <View style={[tw`flex-1 h-180 justify-evenly bg-purple-200 rounded-lg mx-2`, {elevation: 7}]}>
        <View>
          <CustomFont style={tw`text-center text-[50px] py-2`}>{drinkDetailData.krDrinkName}<CustomFont style={tw`text-[30px] text-gray-400`}> ({drinkDetailData.enDrinkName})</CustomFont></CustomFont>
          <CustomFont style={tw`text-center text-black text-[20px] px-3`}>도수 : {drinkDetailData.alcoholDegree}</CustomFont>
        </View>
        <View>
          <Ionicons style={tw`text-center pb-5`} name={'beer'} size={50}/>
        <CustomFont style={tw`text-gray-500 text-[25px] px-3`}>{drinkDetailData.description}</CustomFont>
        </View>
      </View>
    )}
    </ScrollView>
  );
};

export default DrinkDetailScreen;
