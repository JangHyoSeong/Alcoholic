import React, { useState, useEffect } from 'react';
import tw from 'twrnc';
import { View, Image, ScrollView, TouchableOpacity } from 'react-native';
import { useRoute, RouteProp, useNavigation } from '@react-navigation/native';
import CustomFont from '@/components/common/CustomFont';
import { CocktailDetailProps, Ingredients } from '@/types/cocktail';
import { getCocktailDetail } from '@/api/cocktail';
import { useAppStore } from '@/state/useAppStore';
import LoadingSpinner from '@/components/common/LoadingSpinner';
import { RecipeStackParamList } from '@/navigations/stack/RecipeStackNavigator';
import Ionicons from 'react-native-vector-icons/Ionicons';

const CocktailDetail: React.FC = () => {
  const route = useRoute<RouteProp<RecipeStackParamList, 'RecipeDetail'>>();
  const token = useAppStore((state) => state.token)
  const navigation = useNavigation()
  const { cocktailId } = route.params
  const [ cocktailDetailData , setCocktailDetailData] = useState<CocktailDetailProps | null>(null)
  const [ loading, setLoading ] = useState(true)
  const [ ingredientData, setIngredientData ] = useState<Ingredients | null>(null)

  useEffect(() => {
    fetchCocktailDetail(cocktailId)
  }, [cocktailId])

  const fetchCocktailDetail = async ( CocktailId: number) => {
    setLoading(true)
    try {
      const CocktailData = await getCocktailDetail(token, CocktailId)
      setCocktailDetailData(CocktailData)
      setIngredientData(CocktailData.ingredients)
    } catch (error) {
      console.error('칵테일 디테일 정보 못받음')
    } finally {
      setLoading(false)
    }
  }

  if (loading) {
    return <LoadingSpinner />
  }

  return (
    <ScrollView style={tw`bg-white`}>
      <TouchableOpacity
        onPress={() => navigation.goBack()}  // 뒤로가기
        style={tw`p-2`}>
        <Ionicons name={'arrow-back-outline'} size={30} />
      </TouchableOpacity>
  {/* 칵테일 정보 렌더링 */}
  {cocktailDetailData !== null && (
    <View style={[tw`flex-1 bg-purple-200 rounded-lg mx-2`, {elevation: 7}]}>
      {/* 이미지 렌더링 */}
      {cocktailDetailData.image && (
        <Image
          source={{ uri: cocktailDetailData.image }}
          style={tw`w-[95%] h-64 rounded-lg ml-2 mt-2`}
        />
      )}
      <CustomFont style={tw`text-center text-[30px] py-2`}>{cocktailDetailData.krCocktailName}</CustomFont>
      <CustomFont style={tw`text-gray-500 text-[17px] px-3 mb-5`}>{cocktailDetailData.instruction}</CustomFont>
    </View>
  )}
  
  {/* 칵테일 재료 렌더링 */}
  <View style={[tw`flex-1 mt-5 bg-purple-200 rounded-lg mx-2`, {elevation: 7}]}>
    <CustomFont style={tw`px-2 text-[30px] text-center pb-3`}>재료</CustomFont>
    {cocktailDetailData !== null && cocktailDetailData.ingredients.length > 0 && (
      <View>
        {cocktailDetailData?.ingredients.map((ingredient, id) => (
          <View key={id} style={tw`flex-row`}>
            <CustomFont style={tw`text-[purple] text-[20px] px-2 py-2`}>{ingredient.ingredient} :</CustomFont>
            <CustomFont style={tw`text-gray-600 text-[16px] py-[10px]`}>{ingredient.measure}</CustomFont>
          </View>
        ))}
      </View>
    )}
  </View>
</ScrollView>
  );
};

export default CocktailDetail;