import React from 'react';
import tw from 'twrnc';
import { SafeAreaView, View, Image, FlatList, ActivityIndicator, TouchableOpacity } from 'react-native';
import CustomFont from '../common/CustomFont';
import please from '@/assets/default_image.png';
import Ionicons from 'react-native-vector-icons/Ionicons'
import { colors } from '@/constants';
import { RecipeStackParamList } from '@/navigations/stack/RecipeStackNavigator';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { useNavigation, useRoute } from '@react-navigation/native';
import { RecipeNavigations } from '@/constants';


interface CocktailData {
  id: number;
  enCocktailName: string;
  krCocktailName: string;
  image: string;
  instruction: string;
}

interface CocktailItemProps {
  cocktailData: CocktailData[];
  onLoadMore: () => void;
  isFetching: boolean;
}

const CocktailItem: React.FC<CocktailItemProps> = ({ cocktailData, onLoadMore, isFetching }) => {
  const navigation = useNavigation<NativeStackNavigationProp<RecipeStackParamList>>()

  const handlePressButton = async (cocktailId: number) => {
    navigation.navigate(RecipeNavigations.RECIPE_DETAIL, {cocktailId})
  }
  const renderItem = ({ item }: { item: CocktailData }) => (
    <View key={item.id} style={[tw`flex flex-row bg-purple-200 
    mb-4 rounded-lg mx-2 items-center`, {elevation: 3}]}>
      <Image
        source={item.image ? { uri: item.image } : please}
        style={[tw`rounded-md`, { width: 100, height: 100 }]}
      />
      <View style={tw`flex flex-col w-2/3`}>
        <CustomFont style={tw`pl-7 pb-2 text-[20px]`} fontWeight="bold">{item.krCocktailName}</CustomFont>
        <CustomFont style={tw`pl-7 pb-2 text-gray-400 text-[17px] `}>{item.enCocktailName}</CustomFont>
      </View>
      <TouchableOpacity onPress={() => handlePressButton(item.id)}>
        <Ionicons name={'arrow-forward-circle'} size={27} color={colors.PURPLE.BASE}/>
      </TouchableOpacity>
    </View>
  );
  
  return (
    <SafeAreaView>
      <FlatList
        data={cocktailData}
        renderItem={renderItem}
        keyExtractor={(item) => item.id.toString()}
        onEndReached={onLoadMore} // 스크롤 끝에 도달 시 데이터 불러오기
        ListFooterComponent={isFetching ? <ActivityIndicator size="small" color="#0000ff" /> : null}
        contentContainerStyle={{ paddingBottom: 100 }}
      />
    </SafeAreaView>
  );
};

export default CocktailItem;
