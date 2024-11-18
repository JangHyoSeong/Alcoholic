import React, { useState } from 'react';
import { View, TextInput, Image, TouchableOpacity, Alert } from 'react-native';
import { Picker } from '@react-native-picker/picker'
import * as ImagePicker from 'react-native-image-picker';
import tw from 'twrnc';
import { useAppStore } from '@/state/useAppStore';
import { addDrinkRef } from '@/api/refrigerator';
import CustomFont from '@/components/common/CustomFont';
import CustomButton from '@/components/common/CustomButton';
import { RouteProp, useRoute } from '@react-navigation/native';
import { StorageStackParamList } from '@/navigations/stack/StorageStackNavigator';
import { useNavigation } from '@react-navigation/native';
import { MyStorageNavigations } from '@/constants';

const AddDrinkScreen: React.FC = () => {
  const navigation = useNavigation()
  const { params } = useRoute<RouteProp<StorageStackParamList, 'WineRegister'>>()
  const { refrigeratorId } = params
  const token = useAppStore((state) => state.token)

  const [ drinkName, setDrinkName ] = useState('')
  const [ position, setPosition ] = useState<number>(1)
  const [ imageUri, setImageUri ] = useState<string | null>(null)
  const [ isSubmitting, setIsSubmitting ] = useState(false)

  const handleChooseImage = async () => {
    const result = await ImagePicker.launchImageLibrary({ mediaType: 'photo' });
    const uri = result.assets?.[0]?.uri;
    if (uri) {
      setImageUri(uri);
    } else {
      console.log('이미지 선택 취소 또는 오류 발생');
    }
  };

  const handleAddDrink = async () => {
    if (isSubmitting) return

    if (!drinkName || !position) {
      Alert.alert('입력 오류', '모든 필드를 입력해 주십시오!');
      return;
    }
  
    const formData = new FormData();
    formData.append('drinkName', drinkName);
    formData.append('position', String(position));
    
    // 이미지 파일이 있는 경우 추가
    if (imageUri) {
      formData.append('image', {
        uri: imageUri,
        type: 'image/jpeg', // 이미지 타입 설정
        name: 'drink.jpg',
      } as any);
    }
  
    try {
      setIsSubmitting(true);
      await addDrinkRef(token, refrigeratorId, formData); // FormData 전송
      Alert.alert('등록 성공');
      setDrinkName('');
      setPosition(1);
      setImageUri(null);
    } catch (error: any) {
      console.error('등록 실패', error);
      if (error.response?.status == 400) {
        Alert.alert('이미 위치에 술이 있습니다.')
      } else {
        Alert.alert('알 수 없는 오류 발생')
      }
    } finally {
      setIsSubmitting(false);
      navigation.goBack()
    }
  };

  return (
    <View style={tw`flex-1 p-4 bg-white`}>
      <CustomFont style={tw`text-xl mb-4`}>술 등록</CustomFont>
      
      <TextInput
        style={tw`border p-2 mb-4`}
        placeholder="술 이름"
        value={drinkName}
        onChangeText={setDrinkName}
        maxLength={50}
      />

      <View style={tw`border p-2 mb-4`}>
        <Picker
          selectedValue={position}
          onValueChange={(itemValue: number) => setPosition(itemValue)}
        >
          <Picker.Item label="위치 1" value={1} />
          <Picker.Item label="위치 2" value={2} />
          <Picker.Item label="위치 3" value={3} />
          <Picker.Item label="위치 4" value={4} />
        </Picker>
      </View>

      <TouchableOpacity onPress={handleChooseImage} style={tw`mb-4`}>
        {imageUri ? (
          <Image source={{ uri: imageUri }} style={tw`w-40 h-40 rounded`} />
        ) : (
          <View style={tw`w-40 h-40 bg-gray-200 rounded justify-center items-center`}>
            <CustomFont>이미지 선택</CustomFont>
          </View>
        )}
      </TouchableOpacity>

      <CustomButton
        label="등록하기"
        onPress={handleAddDrink}
        disabled={isSubmitting}
      />
    </View>
  );
}

export default AddDrinkScreen