import React, { useState } from 'react';
import tw from 'twrnc';
import { TouchableOpacity, View, Alert, TextInput } from 'react-native';
import CustomFont from '../common/CustomFont';
import { delRef, patchRef } from '@/api/refrigerator';
import { useAppStore } from '@/state/useAppStore';

interface RefItemProps {
  item: {
    id: number;
    name: string;
    main: boolean;
  };
  onDelete: (id: number) => void;
}

const RefItem: React.FC<RefItemProps> = ({ item, onDelete }) => {
  const token = useAppStore((state) => state.token); // 토큰 가져오기
  const [isEditing, setIsEditing ] = useState(false)
  const [newname, setNewname ] = useState(item.name)

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
    <View style={tw`bg-gray-800 m-2 h-[200px] rounded-md overflow-hidden`}>
      {isEditing ? (
        <View style={tw`flex-row items-center`}>
          <TextInput
            style={tw`border p-2 rounded ml-1 mr-2 flex-1 text-white`}
            value={newname}
            onChangeText={setNewname}
            onBlur={handleRefnamePut} // 포커스가 벗어날 때 변경 요청
          />
          <TouchableOpacity onPress={handleRefnamePut}>
            <CustomFont color='cyan' style={tw`pr-2`} fontSize={20}>확인</CustomFont>
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
        <CustomFont style={tw`text-yellow-500 p-4`} fontSize={14}>
          메인 술장고
        </CustomFont>
      )}
      <TouchableOpacity onPress={handleDelete}>
        <CustomFont style={tw`text-[crimson] p-4`}>삭제하기</CustomFont>
      </TouchableOpacity>
    </View>

  );
};


export default RefItem;