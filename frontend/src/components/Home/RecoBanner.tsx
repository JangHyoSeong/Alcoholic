import tw from 'twrnc';
import { ImageBackground, TouchableOpacity, View, Text } from 'react-native';
import CustomFont from '../common/CustomFont';
import wineReco from '@/assets/wine_reco.png'
import recipe from '@/assets/recipe.png'
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { useNavigation } from '@react-navigation/native';
import { RecoStackParamList } from '@/navigations/stack/RecoStackNavigator';
import { RecipeNavigations, RecommendNavigations } from '@/constants';
import { RecipeStackParamList } from '@/navigations/stack/RecipeStackNavigator';
import { mainNavigations } from '@/constants';

const RecoBanner = () => {
  const navigation = useNavigation<NativeStackNavigationProp<RecoStackParamList>>()
  const navigation_2 = useNavigation<NativeStackNavigationProp<RecipeStackParamList>>()

  const handleRecoPress = () => {
    navigation.navigate(mainNavigations.RECOMMEND, {screen: RecommendNavigations.RECO_HOME})
  }

  const handleRecipePress = () => {
    navigation_2.navigate(mainNavigations.RECIPE, {screen: RecipeNavigations.RECIPE_HOME})
  }

  return (
    <View style={tw`flex-row mt-3`}>
      <TouchableOpacity
        style={tw`mr-2`}
        onPress={handleRecoPress}
        activeOpacity={0.7}
      >
        <CustomFont style={tw`mt-10 ml-5 text-[25px]`}>
          추천
        </CustomFont>
        <ImageBackground
          source={wineReco}
          style={[tw`w-[190px] h-[200px] rounded-[20px] ml-2 mt-5 overflow-hidden`, { elevation: 5 }]}
          resizeMode='cover'
        />
      </TouchableOpacity>

      <TouchableOpacity
        style={tw`ml-2`}
        onPress={handleRecipePress}
        activeOpacity={0.7}
      >
        <CustomFont style={tw`mt-10 ml-5 text-[25px]`}>
          칵테일 레시피
        </CustomFont>
        <ImageBackground
          source={recipe}
          style={[tw`w-[190px] h-[200px] rounded-[20px] mt-5 overflow-hidden`, { elevation: 5 }]}
          resizeMode='cover'
        />
      </TouchableOpacity>
    </View>
  )
};

export default RecoBanner;