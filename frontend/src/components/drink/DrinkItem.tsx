import React from 'react';
import tw from 'twrnc';
import { SafeAreaView, View, Image, FlatList, ActivityIndicator, TouchableOpacity } from 'react-native';
import CustomFont from '../common/CustomFont';
import please from '@/assets/default_image.png';
import Ionicons from 'react-native-vector-icons/Ionicons'
import { colors } from '@/constants';
import { RecoStackParamList } from '@/navigations/stack/RecoStackNavigator';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { useNavigation } from '@react-navigation/native';
import { RecommendNavigations } from '@/constants';

interface DrinkData {
  id: number;
  categoryId: number;
  enDrinkName: string;
  krDrinkName: string;
}

interface DrinkItemProps {
  drinkData: DrinkData[];
  onLoadMore: () => void;
  isFetching: boolean;
}

const DrinkItem: React.FC<DrinkItemProps> = ({ drinkData, onLoadMore, isFetching }) => {
  const navigation = useNavigation<NativeStackNavigationProp<RecoStackParamList>>()

  const handlePressButton = async (drinkId: number) => {
    navigation.navigate(RecommendNavigations.DRINK_DETAIL, {drinkId})
  }
  const renderItem = ({ item }: { item: DrinkData }) => (
    <View key={item.id} style={[tw`flex flex-row bg-purple-200 
    mb-4 rounded-lg mx-2 items-center`, {elevation: 3}]}>
      <View style={tw`flex flex-col w-2/3`}>
        <CustomFont style={tw`pl-7 pt-1 text-[22px]`} fontWeight="bold">{item.krDrinkName}</CustomFont>
        <CustomFont style={tw`pl-7 pb-2 text-gray-400 text-[17px] `}>{item.enDrinkName}</CustomFont>
      </View>
      <TouchableOpacity style={tw`ml-17`} onPress={() => handlePressButton(item.id)}>
        <Ionicons name={'arrow-forward-circle'} size={27} color={colors.PURPLE.BASE}/>
      </TouchableOpacity>
    </View>
  );
  return (
    <SafeAreaView>
      <FlatList
        data={drinkData}
        renderItem={renderItem}
        keyExtractor={(item) => item.id.toString()}
        onEndReached={onLoadMore} // 스크롤 끝에 도달 시 데이터 불러오기
        ListFooterComponent={isFetching ? <ActivityIndicator size="small" color="#0000ff" /> : null}
        contentContainerStyle={{ paddingBottom: 100 }}
      />
    </SafeAreaView>
  );
};

export default DrinkItem;