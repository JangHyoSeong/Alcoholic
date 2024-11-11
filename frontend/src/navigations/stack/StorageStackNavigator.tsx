import React from 'react';
import { MyStorageNavigations } from '@/constants';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import MyRefScreen from '@/screens/refrigerator/MyRefScreen';
import AddDrinkScreen from '@/screens/refrigerator/AddDrinkScreen';
import MyRefDetailScreen from '@/screens/refrigerator/MyRefDetailScreen';
import WineDetailScreen from '@/screens/refrigerator/WineDetailScreen';

export type StorageStackParamList = {
  [MyStorageNavigations.STORAGE_HOME]: undefined;
  [MyStorageNavigations.WINE_REGISTER]: { refrigeratorId: number};
  [MyStorageNavigations.MYSTORAGE_DETAIL]: { refrigeratorId: number};
  [MyStorageNavigations.WINE_DETAIL]: undefined;
}

const StorageStack = createNativeStackNavigator<StorageStackParamList>();

const StorageStackNavigator: React.FC = () => {
  return (
    <StorageStack.Navigator screenOptions={{ headerShown: false}}>
      <StorageStack.Screen name={MyStorageNavigations.STORAGE_HOME} component={MyRefScreen} />
      <StorageStack.Screen name={MyStorageNavigations.WINE_REGISTER} component={AddDrinkScreen} />
      <StorageStack.Screen name={MyStorageNavigations.MYSTORAGE_DETAIL} component={MyRefDetailScreen} />
      <StorageStack.Screen name={MyStorageNavigations.WINE_DETAIL} component={WineDetailScreen} />
    </StorageStack.Navigator>
  )
}

export default StorageStackNavigator;