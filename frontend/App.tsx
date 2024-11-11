import React from "react";
import { StatusBar } from "react-native";
import { ThemeProvider } from "styled-components";
import RootNavigator from "./src/navigations/root/RootNavigator"

const theme = {
  colors: {
    primary: '#6200ee',
    background: '#f5f5f5',
    text: '#000000'
  },
};

function App(): React.JSX.Element {
  return (
    <ThemeProvider theme={theme}>
      <StatusBar backgroundColor={theme.colors.background} />
      <RootNavigator />
    </ThemeProvider>
  )
}

export default App