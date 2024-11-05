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
        <View style={tw`bg-white p-5 rounded-md w-3/4`}>
          <CustomFont style={tw`text-lg mb-4`} fontSize={20}>
            술장고 추가하기
          </CustomFont>
          
          <TextInput
            placeholder="Serial Number"
            value={serialNumber}
            onChangeText={setSerialNumber}
            style={tw`border-b border-gray-400 mb-4 p-2`}
          />

          <TouchableOpacity onPress={handleAdd} style={tw`bg-blue-500 p-3 rounded-md`}>
            <CustomFont style={tw`text-white text-center`} fontSize={15}>
              추가하기
            </CustomFont>
          </TouchableOpacity>

          <TouchableOpacity onPress={onClose} style={tw`mt-3`}>
            <CustomFont style={tw`text-center text-red-500`} fontSize={15}>
              닫기
            </CustomFont>
          </TouchableOpacity>
        </View>
      </View>
    </Modal>
  )
}

export default AddRefModal