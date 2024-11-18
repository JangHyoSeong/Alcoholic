import React,{ useState } from 'react';
import tw from 'twrnc';
import { TouchableOpacity, View, TextInput } from 'react-native';
import CustomFont from '../common/CustomFont';
import { useAppStore } from '@/state/useAppStore';
import { useUserStore } from '@/state/useUserStore';
import { patchUserInfo } from '@/api/myprofile';

const MypageForm = () => {
  const token = useAppStore((state) => state.token)
  const nickname = useUserStore((state) => state.nickname)
  const setNickname = useUserStore((state) => state.setNickname)
  const [ isEditing, setIsEditing ] = useState(false)
  const [newNickname, setNewNickname ] = useState(nickname)

  const handleEditing = () => {
    setIsEditing(true)
  }

  const handleNicknamePut = async () => {
    try {
      const response = await patchUserInfo(token, newNickname)
      if (response) {
        setNickname(newNickname)
      }
    } catch (error) {
      console.error('닉변 실패', error)
    } finally {
      setIsEditing(false)
    }
  }

 return (
  <View style={tw`mt-[30px]`}>
  {isEditing ? (
    <View style={tw`flex-row items-center bg-gray-100`}>
      <TextInput
        style={tw`border p-2 rounded ml-1 mr-2 flex-1`}
        value={newNickname}
        maxLength={20}
        onChangeText={setNewNickname}
        onBlur={handleNicknamePut} // 포커스가 벗어날 때 변경 요청
      />
      <TouchableOpacity onPress={handleNicknamePut}>
        <CustomFont style={tw`text-purple-500 mr-1`} fontSize={17}>확인</CustomFont>
      </TouchableOpacity>
    </View>
  ) : (
    <TouchableOpacity onPress={handleEditing} style={[tw`ml-1 bg-gray-300 rounded-md`, {elevation: 5}]}>
      <CustomFont style={tw`text-center `} fontSize={40} fontWeight='bold'>{nickname}</CustomFont>
    </TouchableOpacity>
  )}
</View>
 );
};

export default MypageForm;
