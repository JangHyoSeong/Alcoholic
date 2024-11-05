import React from 'react';
import tw from 'twrnc';
import { View } from 'react-native';
import CustomFont from '../common/CustomFont';

const RefItem = ({ item }) => {
 return (
  <View style={tw`bg-gray-800 m-2 h-[200px] rounded-md overflow-hidden`}>
    <CustomFont style={tw`text-white p-4`} fontSize={20}>
      {item.name}
    </CustomFont>
    {item.main && (
      <CustomFont style={tw`text-yellow-500 p-4`} fontSize={14}>
        메인 술장고
      </CustomFont>
    )}
  </View>
 );
};

export default RefItem;