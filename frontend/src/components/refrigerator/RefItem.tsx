import React, { useState } from 'react';
import tw from 'twrnc';
import { TouchableOpacity, View, Alert, TextInput } from 'react-native';
import CustomFont from '../common/CustomFont';
import { delRef, patchRef } from '@/api/refrigerator';
import { useAppStore } from '@/state/useAppStore';
import Ionicons from 'react-native-vector-icons/Ionicons';
import { MyStorageNavigations, colors } from '@/constants';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { StorageStackParamList } from '@/navigations/stack/StorageStackNavigator';

interface RefItemProps {
  item: {
    id: number;
    name: string;
    main: boolean;
  };
  onDelete: (id: number) => void;
}

const RefItem: React.FC<RefItemProps> = ({ item, onDelete }) => {
  const navigation = useNavigation<NativeStackNavigationProp<StorageStackParamList>>()
  const token = useAppStore((state) => state.token); // 토큰 가져오기
  const [isEditing, setIsEditing ] = useState(false)
  const [newname, setNewname ] = useState(item.name)

  const handleTouchRef = (refrigeratorId: number) => {
    navigation.navigate(MyStorageNavigations.MYSTORAGE_DETAIL, {refrigeratorId})
  }

  const handleEditing = () => {
    setIsEditing(true)
  }

  const handleDelete = async () => {
    Alert.alert(
      "삭제 확인",
      `${item.name}을(를) 정말 삭제하시겠습니까?`, // 확인 메시지
      [
        {
          text: "취소",
          style: "cancel"
        },
        {
          text: "삭제",
          onPress: async () => {
            await delRef(token, item.id); // 삭제 요청
            onDelete(item.id); // 삭제 후 상위 컴포넌트에서 상태 업데이트
          },
          style: "destructive"
        }
      ]
    );
  };

  const handleRefnamePut = async () => {
    try {
      await patchRef(token, item.id, newname)
      item.name = newname
      setIsEditing(false)
    } catch (error) {
      console.error('이름 변경 실패', error)
    }
  }

  return (
    <TouchableOpacity onPress={() => handleTouchRef(item.id)} style={[tw`bg-gray-400 m-2 h-[200px] rounded-md overflow-hidden`, {elevation: 4}]}>
        {isEditing ? (
          <View style={tw`flex-row items-center`}>
            <TextInput
              style={tw`border border-white p-2 rounded ml-1 mr-2 flex-1 text-white`}
              value={newname}
              maxLength={20}
              onChangeText={setNewname}
              onBlur={handleRefnamePut} // 포커스가 벗어날 때 변경 요청
            />
            <TouchableOpacity onPress={handleRefnamePut}>
              <CustomFont style={tw`pr-2 text-[purple]`} fontSize={17}>확인</CustomFont>
            </TouchableOpacity>
          </View>
        ) : (
          <TouchableOpacity onPress={handleEditing} style={tw`ml-1 rounded-md`}>
            <CustomFont style={tw`text-white p-2`} fontSize={20}>
              {item.name}
            </CustomFont>
          </TouchableOpacity>
        )}
        {item.main && (
          <CustomFont style={tw`text-yellow-400 p-4`} fontSize={14}>
            메인 술장고
          </CustomFont>
        )}
        <Ionicons style={tw`absolute top-14 right-6`} name={'wine'} color={colors.PURPLE.BASE} size={45} />
        <TouchableOpacity onPress={handleDelete} style={tw`absolute right-4 top-42`}>
          <CustomFont style={tw`text-[crimson]`}>삭제하기</CustomFont>
      </TouchableOpacity>
    </TouchableOpacity>

  );
};


export default RefItem;