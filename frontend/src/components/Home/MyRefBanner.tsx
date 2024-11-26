import tw from 'twrnc';
import { ImageBackground, TouchableOpacity, View } from 'react-native';
import CustomFont from '../common/CustomFont';
import mandrake from '@/assets/mandrake.png'
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { useNavigation } from '@react-navigation/native';
import { StorageStackParamList } from '@/navigations/stack/StorageStackNavigator';
import { MyStorageNavigations } from '@/constants';
import { Shadow } from 'react-native-shadow-2'

const MyRefBanner = () => {
  const navigation = useNavigation<NativeStackNavigationProp<StorageStackParamList>>()

  const handlePress = () => {
    navigation.navigate(MyStorageNavigations.STORAGE_HOME)
  }

 return (
  <TouchableOpacity onPress={handlePress} activeOpacity={0.7}>
    <View style={tw`bg-transparent`}>
      <CustomFont style={tw`mt-5 text-[25px] ml-5`}>나의 술장고</CustomFont>
    </View>
    <ImageBackground
      source={mandrake}
      style={[tw`ml-[7px] mt-5 w-[400px] h-[200px] rounded-[20px] overflow-hidden opacity-80`, {elevation: 10}]}
      resizeMode='cover'>
      <View style={tw`p-4`}>
        <CustomFont style={tw`text-white text-[28px] ml-10 mt-10`}>술장고를 등록해 주세요!</CustomFont>
      </View>
    </ImageBackground>
  </TouchableOpacity>
 );
};

export default MyRefBanner;