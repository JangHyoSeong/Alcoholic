import React from "react";
import { RecommendNavigations } from "@/constants";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import MyRecoScreen from "@/screens/recommend/MyRecoScreen";
import PopularRecoScreen from "@/screens/recommend/PopularRecoScreen";
import RecoHomeScreen from "@/screens/recommend/RecoHomeScreen";
import DrinkListScreen from "@/screens/recommend/DrinkListScreen";
import DrinkDetailScreen from "@/screens/recommend/DrinkDetailScreen";

export type RecoStackParamList = {
  [RecommendNavigations.RECO_HOME]: undefined;
  [RecommendNavigations.MY_RECO]: undefined;
  [RecommendNavigations.POPULAR_RECO]: undefined;
  [RecommendNavigations.DRINK_LIST]: undefined;
  [RecommendNavigations.DRINK_DETAIL]: {drinkId : number};
}

const RecoStack = createNativeStackNavigator<RecoStackParamList>()

const RecoStackNavigator: React.FC = () => {
  return(
    <RecoStack.Navigator screenOptions={{ headerShown: false}}>
      <RecoStack.Screen name={RecommendNavigations.RECO_HOME} component={RecoHomeScreen} />
      <RecoStack.Screen name={RecommendNavigations.MY_RECO} component={MyRecoScreen} />
      <RecoStack.Screen name={RecommendNavigations.POPULAR_RECO} component={PopularRecoScreen} />
      <RecoStack.Screen name={RecommendNavigations.DRINK_LIST} component={DrinkListScreen} />
      <RecoStack.Screen name={RecommendNavigations.DRINK_DETAIL} component={DrinkDetailScreen} />
    </RecoStack.Navigator>
  )
}

export default RecoStackNavigator;