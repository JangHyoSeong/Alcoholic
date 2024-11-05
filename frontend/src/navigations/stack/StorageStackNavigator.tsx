import React from 'react';
import { MyStorageNavigations } from '@/constants';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import MyRefScreen from '@/screens/refrigerator/MyRefScreen';
import AddWineScreen from '@/screens/refrigerator/AddWineScreen';
import MyRefDetailScreen from '@/screens/refrigerator/MyRefDetailScreen';
import WineDetailScreen from '@/screens/refrigerator/WineDetailScreen';

export type StorageStackParamList = {
  [MyStorageNavigations.STORAGE_HOME]: undefined;
  [MyStorageNavigations.WINE_REGISTER]: undefined;
  [MyStorageNavigations.MYSTORAGE_DETAIL]: undefined;
  [MyStorageNavigations.WINE_DETAIL]: undefined;
}

const StorageStack = createNativeStackNavigator<StorageStackParamList>();

const StorageStackNavigator: React.FC = () => {
  return (
    <StorageStack.Navigator screenOptions={{ headerShown: false}}>
      <StorageStack.Screen name={MyStorageNavigations.STORAGE_HOME} component={MyRefScreen} />
      <StorageStack.Screen name={MyStorageNavigations.WINE_REGISTER} component={AddWineScreen} />
      <StorageStack.Screen name={MyStorageNavigations.MYSTORAGE_DETAIL} component={MyRefDetailScreen} />
      <StorageStack.Screen name={MyStorageNavigations.WINE_DETAIL} component={WineDetailScreen} />
    </StorageStack.Navigator>
  )
}

export default StorageStackNavigator;