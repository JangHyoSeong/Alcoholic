import tw from 'twrnc';
import { TouchableOpacity, View } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { useRoute, RouteProp } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { StorageStackParamList } from '@/navigations/stack/StorageStackNavigator';
import { MyStorageNavigations } from '@/constants';
import CustomFont from '@/components/common/CustomFont';


type MyStorageDetailScreenRouteProp = RouteProp<StorageStackParamList, 'MyStorageDetail'>

const MyRefDetailScreen: React.FC = () => {
  const { params } = useRoute<MyStorageDetailScreenRouteProp>()
  const { refrigeratorId } = params

  const navigation = useNavigation<NativeStackNavigationProp<StorageStackParamList>>()

  const handleMoveAddDrink = (refrigeratorId: number) => {
    navigation.navigate(MyStorageNavigations.WINE_REGISTER, {refrigeratorId})
  }

  return (
    <View style={tw``}>
      <TouchableOpacity onPress={() => handleMoveAddDrink(refrigeratorId)}>
        <CustomFont>술 등록 하기</CustomFont>
      </TouchableOpacity>
    </View>
  );
};

export default MyRefDetailScreen;