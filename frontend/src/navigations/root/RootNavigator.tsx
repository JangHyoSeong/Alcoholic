import React from "react";
import { NavigationContainer } from "@react-navigation/native";
import AuthStackNavigator from "../stack/AuthStackNavigator";
import { useAppStore } from "@/state/useAppStore";
import MainTabNavigator from "../tab/MainTabNavigator";

const RootNavigator: React.FC = () => {
  const isLogin = useAppStore((state) => state.isLogin)

  return (
    <NavigationContainer>
      {isLogin ? <MainTabNavigator /> : <AuthStackNavigator />}
    </NavigationContainer>
  )
}

export default RootNavigator