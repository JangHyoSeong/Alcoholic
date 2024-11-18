import React, { useState } from 'react';
import tw from 'twrnc';
import { View, TextInput, Alert, ScrollView, TouchableOpacity, Image } from 'react-native';
import { Picker } from '@react-native-picker/picker';
import * as ImagePicker from 'react-native-image-picker';
import CustomButton from '@/components/common/CustomButton';
import CustomFont from '@/components/common/CustomFont';
import { useNavigation } from '@react-navigation/native';
import { useAppStore } from '@/state/useAppStore';
import { addCocktail } from '@/api/cocktail';
import { Ingredient, Cocktail } from '@/api/cocktail';
import { NativeStackNavigationProp } from '@react-navigation/native-stack'
import { RecipeStackParamList } from '@/navigations/stack/RecipeStackNavigator';

const categories = [
  { id: 1, name: '증류주' },
  { id: 2, name: '맥주' },
  { id: 3, name: '와인' },
  { id: 4, name: '브랜디' },
  { id: 5, name: '위스키' },
  { id: 6, name: '진' },
  { id: 7, name: '럼' },
  { id: 8, name: '테킬라' },
  { id: 9, name: '리큐르' },
  { id: 10, name: '막걸리' },
  { id: 11, name: '보드카' },
  { id: 12, name: '소주' },
  { id: 13, name: '기타' },
];

const CustomCocktailScreen: React.FC = () => {
  const [ enCocktailName, setEnCocktailName ] = useState('')
  const [ krCocktailName, setKrCocktailName ] = useState('')
  const [ imageUri, setImageUri ] = useState<string | undefined>(undefined)
  const [ instruction, setInstruction ] = useState('')
  const [ ingredients, setIngredients ] = useState<Ingredient[]>([
    // { categoryId: 0, ingredient: '', measure: '' }
  ])
  const [ categoryId, setCategoryId ] = useState(1)
  const [ loading, setLoading ] = useState(false)
  const token = useAppStore((state) => state.token)
  const navigation = useNavigation<NativeStackNavigationProp<RecipeStackParamList>>()

  const handleChooseImage = async () => {
    const result = await ImagePicker.launchImageLibrary({ mediaType: 'photo'})
    const uri = result.assets?.[0]?.uri
    if (uri) {
      setImageUri(uri)
    } else {
      console.log('이미지 선택 취소 또는 오류 발생');
    }
  }

  const handleSubmit = async () => {
    if (loading) return
    setLoading(true)

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
      image: imageUri,
      instruction,
      ingredients: formattedIngredients
    }

    try {
      await addCocktail(token, cocktail)
      Alert.alert('커스텀 칵테일 등록 성공!')
      navigation.goBack()
    } catch (error) {
      console.error('커스텀 칵테일 등록 실패')
    } finally {
      setLoading(false)
    }

  }

  // 재료 필드 추가
  const addIngredientField = () => {
    setIngredients([...ingredients, { categoryId: categoryId, ingredient: '', measure: '' }]);
  };

  // 재료 필드 삭제
  const removeIngredientField = (index: number) => {
    const newIngredients = ingredients.filter((_, i) => i !== index);
    setIngredients(newIngredients);
  };

  return (
    <ScrollView style={tw`flex-1 bg-white p-5`}>
      <CustomFont style={tw`text-2xl font-bold mb-4 text-center`}>칵테일 등록</CustomFont>

      <TextInput
        style={tw`border border-gray-300 rounded p-3 mb-3`}
        placeholder="영어 칵테일 이름"
        value={enCocktailName}
        onChangeText={setEnCocktailName}
        maxLength={50}
      />
      <TextInput
        style={tw`border border-gray-300 rounded p-3 mb-3`}
        placeholder="한국어 칵테일 이름"
        value={krCocktailName}
        onChangeText={setKrCocktailName}
        maxLength={50}
      />
      <TouchableOpacity onPress={handleChooseImage} style={tw`mb-4`}>
        {imageUri ? (
          <Image source={{ uri: imageUri }} style={tw`w-40 h-40 rounded`} />
        ) : (
          <View style={tw`w-40 h-40 bg-gray-200 rounded justify-center items-center`}>
            <CustomFont>이미지 선택</CustomFont>
          </View>
        )}
      </TouchableOpacity>
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
              {categories.map((category) => (
                    <Picker.Item
                      key={category.id}
                      label={category.name}  // 사용자에게 표시될 이름
                      value={category.id}   // 선택 시 저장할 값
                    />
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
            maxLength={50}
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
            maxLength={10}
          />
          <TouchableOpacity onPress={() => removeIngredientField(index)}>
            <CustomFont style={tw`text-[red] text-right`}>재료 삭제</CustomFont>
          </TouchableOpacity>
        </View>
      ))}
      <CustomButton label="재료 추가" size='small' onPress={addIngredientField} />
      <View style={tw`mt-3 mb-8`}>
        <CustomButton label="칵테일 등록" size='small' onPress={handleSubmit} />
      </View>
    </ScrollView>
  );
};

export default CustomCocktailScreen;