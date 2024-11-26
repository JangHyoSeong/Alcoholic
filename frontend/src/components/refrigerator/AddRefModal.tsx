import React, { useState } from "react";
import { Modal, View, TextInput,TouchableOpacity } from 'react-native'; 
import CustomFont from "../common/CustomFont";
import tw from 'twrnc'
import { addRef } from "@/api/refrigerator";

interface AddRefModalProps {
  visible: boolean;
  onClose: () => void;
  onAddSuccess: (serialNumber: string) => void;
  token: string;
}

const AddRefModal: React.FC<AddRefModalProps> = ({ visible, onClose, onAddSuccess, token}) => {
  const [serialNumber, setSerialNumber] = useState('')

  const handleAdd = async () => {
    if (!serialNumber) return

    try {
      const response = await addRef(token, serialNumber)
      console.log('술장고 추가 성공')
      onAddSuccess(serialNumber)
      onClose()
    } catch (error) {
      console.error('술장고 추가 실패', error)
    }
  }

  return (
    <Modal visible={visible} transparent animationType="slide" onRequestClose={onClose}>
      <View style={tw`flex-1 justify-center items-center bg-black bg-opacity-50`}>
        <View style={[tw`bg-gray-200 p-5 rounded-md w-3/4`, {elevation: 10}]}>
          <CustomFont style={tw`text-lg mb-4`} fontSize={22}>
            술장고 추가하기
          </CustomFont>
          
          <TextInput
            placeholder="시리얼 넘버 입력"
            value={serialNumber}
            onChangeText={setSerialNumber}
            style={tw`border-b border-gray-400 mb-4 p-2 text-black`}
          />

          <TouchableOpacity onPress={handleAdd} style={tw`bg-purple-500 p-1 rounded-md`}>
            <CustomFont style={tw`text-white text-center`} fontSize={15}>
              추가하기
            </CustomFont>
          </TouchableOpacity>

          <TouchableOpacity onPress={onClose} style={tw`bg-purple-500  mt-1 p-1 rounded-md`}>
            <CustomFont style={tw`text-center text-white`} fontSize={15}>
              닫기
            </CustomFont>
          </TouchableOpacity>
        </View>
      </View>
    </Modal>
  )
}

export default AddRefModal