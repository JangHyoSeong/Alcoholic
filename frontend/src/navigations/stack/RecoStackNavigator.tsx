import React from "react";
import { RecommendNavigations } from "@/constants";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import MyRecoScreen from "@/screens/recommend/MyRecoScreen";
import PopularRecoScreen from "@/screens/recommend/PopularRecoScreen";
import RecoHomeScreen from "@/screens/recommend/RecoHomeScreen";

export type RecoStackParamList = {
  [RecommendNavigations.RECO_HOME]: undefined;
  [RecommendNavigations.MY_RECO]: undefined;
  [RecommendNavigations.POPULAR_RECO]: undefined;
}

const RecoStack = createNativeStackNavigator<RecoStackParamList>()

const RecoStackNavigator: React.FC = () => {
  return(
    <RecoStack.Navigator screenOptions={{ headerShown: false}}>
      <RecoStack.Screen name={RecommendNavigations.RECO_HOME} component={RecoHomeScreen} />
      <RecoStack.Screen name={RecommendNavigations.MY_RECO} component={MyRecoScreen} />
      <RecoStack.Screen name={RecommendNavigations.POPULAR_RECO} component={PopularRecoScreen} />
    </RecoStack.Navigator>
  )
}

export default RecoStackNavigator;