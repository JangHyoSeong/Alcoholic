import React from "react";
import { RecipeNavigations } from "@/constants";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import CocktailSearch from "@/screens/recipes/CocktailSearch";
import CocktailDetail from "@/screens/recipes/CocktailDetail";

export type RecipeStackParamList = {
  [RecipeNavigations.RECIPE_HOME]: undefined;
  [RecipeNavigations.RECIPE_DETAIL]: undefined;
}

const RecipeStack = createNativeStackNavigator<RecipeStackParamList>()

const RecipeStackNavigator: React.FC = () => {
  return(
  <RecipeStack.Navigator screenOptions={{ headerShown: false}}>
    <RecipeStack.Screen name={RecipeNavigations.RECIPE_HOME} component={CocktailSearch} />
    <RecipeStack.Screen name={RecipeNavigations.RECIPE_DETAIL} component={CocktailDetail} />
  </RecipeStack.Navigator>
  )
}

export default RecipeStackNavigator;