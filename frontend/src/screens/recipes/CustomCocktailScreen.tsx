import React, { useState } from 'react';
import tw from 'twrnc';
import { View, TextInput, Alert, ScrollView } from 'react-native';
import { Picker } from '@react-native-picker/picker';
import CustomButton from '@/components/common/CustomButton';
import CustomFont from '@/components/common/CustomFont';
import { useNavigation } from '@react-navigation/native';
import { useAppStore } from '@/state/useAppStore';
import { addCocktail } from '@/api/cocktail';
import { Ingredient, Cocktail } from '@/api/cocktail';
import { NativeStackNavigationProp } from '@react-navigation/native-stack'
import { RecipeStackParamList } from '@/navigations/stack/RecipeStackNavigator';

const CustomCocktailScreen: React.FC = () => {
  const [ enCocktailName, setEnCocktailName ] = useState('')
  const [ krCocktailName, setKrCocktailName ] = useState('')
  const [ image, setImage ] = useState('')
  const [ instruction, setInstruction ] = useState('')
  const [ ingredients, setIngredients ] = useState<Ingredient[]>([
    { categoryId: 0, ingredient: '', measure: '' }
  ])
  const [ categoryId, setCategoryId ] = useState(1)
  const token = useAppStore((state) => state.token)
  const navigation = useNavigation<NativeStackNavigationProp<RecipeStackParamList>>()

  const handleSubmit = async () => {
    if (!enCocktailName || !krCocktailName || !instruction) {
      Alert.alert('모든 필드를 채워주세요!')
      return
    }

    const formattedIngredients: Ingredient[] = ingredients.map((item) => ({
      categoryId: item.categoryId, // 여기에 올바른 categoryId를 넣어줌
      ingredient: item.ingredient, // 여기서 ingredient는 string 타입으로 사용
      measure: item.measure, // 예시로 measure 추가
    }));

    const cocktail:Cocktail = {
      enCocktailName,
      krCocktailName,
      image,
      instruction,
      ingredients: formattedIngredients
    }

    try {
      await addCocktail(token, cocktail)
      console.log('커스텀 칵테일 등록 성공')
      navigation.goBack()
    } catch (error) {
      console.error('커스텀 칵테일 등록 실패')
    }

  }

  const addIngredientField = () => {
    setIngredients([...ingredients, { categoryId: categoryId, ingredient: '', measure: '' }]);
  };

  return (
    <ScrollView style={tw`flex-1 bg-white p-5`}>
      <CustomFont style={tw`text-2xl font-bold mb-4 text-center`}>칵테일 등록</CustomFont>

      <TextInput
        style={tw`border border-gray-300 rounded p-3 mb-3`}
        placeholder="영어 칵테일 이름"
        value={enCocktailName}
        onChangeText={setEnCocktailName}
      />
      <TextInput
        style={tw`border border-gray-300 rounded p-3 mb-3`}
        placeholder="한국어 칵테일 이름"
        value={krCocktailName}
        onChangeText={setKrCocktailName}
      />
      <TextInput
        style={tw`border border-gray-300 rounded p-3 mb-3`}
        placeholder="이미지 URL"
        value={image}
        onChangeText={setImage}
      />
      <TextInput
        style={tw`border border-gray-300 rounded p-3 mb-3`}
        placeholder="조리 방법"
        value={instruction}
        onChangeText={setInstruction}
      />

      {/* 재료 입력 필드 */}
      {ingredients.map((ingredient, index) => (
        <View key={index} style={tw`mb-3`}>
          {/* 첫 번째 재료 항목에만 카테고리 입력 필드 추가 */}
          {index === 0 && (
            <Picker
              selectedValue={ingredient.categoryId}
              onValueChange={(value) => {
                setCategoryId(value);  // 선택한 카테고리 값을 상태로 저장
                const newIngredients = [...ingredients];
                newIngredients[index].categoryId = value;
                setIngredients(newIngredients);
              }}
              style={tw`border border-gray-300 rounded mb-2`}
            >
              {[...Array(13)].map((_, i) => (
                <Picker.Item key={i + 1} label={`카테고리 ${i + 1}`} value={i + 1} />
              ))}
            </Picker>
          )}

          {/* 재료 이름 입력 */}
          <TextInput
            style={tw`border border-gray-300 rounded p-3 mb-2`}
            placeholder={`재료 이름 ${index + 1}`}
            value={ingredient.ingredient}
            onChangeText={(text) => {
              const newIngredients = [...ingredients];
              newIngredients[index].ingredient = text;
              setIngredients(newIngredients);
            }}
          />

          {/* 측정 단위 입력 */}
          <TextInput
            style={tw`border border-gray-300 rounded p-3 mb-2`}
            placeholder={`측정 단위 ${index + 1}`}
            value={ingredient.measure}
            onChangeText={(text) => {
              const newIngredients = [...ingredients];
              newIngredients[index].measure = text;
              setIngredients(newIngredients);
            }}
          />
        </View>
      ))}
      <CustomButton label="재료 추가" size='small' onPress={addIngredientField} />
      <View style={tw`mt-3`}>
        <CustomButton label="칵테일 등록" size='small' onPress={handleSubmit} />
      </View>
    </ScrollView>
  );
};

export default CustomCocktailScreen;