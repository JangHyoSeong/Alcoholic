import React, { useEffect } from "react";
import { NavigationContainer } from "@react-navigation/native";
import AuthStackNavigator from "../stack/AuthStackNavigator";
import { useAppStore } from "@/state/useAppStore";
import MainTabNavigator from "../tab/MainTabNavigator";

const RootNavigator: React.FC = () => {
  let navigatorToShow: JSX.Element;
  const isLogin = useAppStore((state) => state.isLogin)
  const setLogin = useAppStore((state) => state.setLogin)

  useEffect(() => {
    setLogin(isLogin)
  }, [isLogin, setLogin]);

  if (isLogin) {
    navigatorToShow = <MainTabNavigator />
  } else {
    navigatorToShow = <AuthStackNavigator />
  }

  return <NavigationContainer>{navigatorToShow}</NavigationContainer>
}

export default RootNavigator