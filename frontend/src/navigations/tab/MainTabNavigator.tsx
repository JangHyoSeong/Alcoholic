import React from 'react';
import { TouchableOpacity } from 'react-native';
import { RouteProp } from '@react-navigation/native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { mainNavigations, colors } from '@/constants';
import Ionicons from 'react-native-vector-icons/Ionicons';
import HomeScreen from '@/screens/home/HomeScreen' 
import MyPageScreen from '@/screens/home/MyPageScreen';
import StorageStackNavigator from '../stack/StorageStackNavigator';
import RecoStackNavigator from '../stack/RecoStackNavigator';
import RecipeStackNavigator from '../stack/RecipeStackNavigator';

type MainTabParamList = {
  [mainNavigations.HOME] : undefined;
  [mainNavigations.RECOMMEND] : undefined;
  [mainNavigations.MYSTORAGE] : undefined;
  [mainNavigations.RECIPE] : undefined;
  [mainNavigations.MYPAGE] : undefined;  
}

const Tab = createBottomTabNavigator<MainTabParamList>();

function TabBarIcons(route: RouteProp<MainTabParamList>, focused: boolean) {
  let iconName = '';

  switch (route.name) {
    case mainNavigations.HOME: {
      iconName= focused ? 'home' : 'home-outline';
      break
    }
    case mainNavigations.RECOMMEND: {
      iconName= focused ? 'thumbs-up' : 'thumbs-up-outline';
      break
    }
    case mainNavigations.MYSTORAGE: {
      iconName = focused ? 'pint' : 'pint-outline';
      break
    }
    case mainNavigations.RECIPE: {
      iconName = focused ? 'cafe' : 'cafe-outline';
      break
    }
    case mainNavigations.MYPAGE: {
      iconName = focused ? 'person' : 'person-outline';
      break
    }
  }

  return (
    <Ionicons name={iconName} color={focused ? colors.PURPLE.BASE : colors.GRAY_300} size={35} />
  );
}

const MainTabNavigator = () => {
  return (
    <Tab.Navigator
      screenOptions={({ route }) => ({
        tabBarActiveTintColor: `${colors.PURPLE.BASE}`,
        tabBarInactiveTintColor: `${colors.GRAY_300}`,
        headerStyle: {
          backgroundColor: colors.WHITE,
          shadowColor: colors.GRAY_200,
        },
        tabBarStyle: {
          height: 80,
          paddingTop: 10,
          paddingBottom: 10,
        },
        tabBarLabelStyle: {
          fontSize: 12,
          fontFamily: 'Hakgyoansim Dunggeunmiso OTF B',
        },
        tabBarButton(props) {
          return <TouchableOpacity {...props} />;
        },
        headerShown: false,
        tabBarIcon: ({ focused }) => TabBarIcons(route, focused),
      })}>
        <Tab.Screen name={mainNavigations.HOME} component={HomeScreen} options={{ title: '홈' }} />
      <Tab.Screen name={mainNavigations.RECOMMEND} component={RecoStackNavigator} options={{ title: '추천' }} />
      <Tab.Screen
        name={mainNavigations.MYSTORAGE}
        component={StorageStackNavigator}
        options={{ title: '내 술장고' }}
      />
      <Tab.Screen
        name={mainNavigations.RECIPE}
        component={RecipeStackNavigator}
        options={{ title: '레시피' }}
      />
      <Tab.Screen
        name={mainNavigations.MYPAGE}
        component={MyPageScreen}
        options={{ title: '내 정보' }}
      />
    </Tab.Navigator>
  )
}

export default MainTabNavigator;