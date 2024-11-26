interface Ingredients {
  id : number;
  cocktailId : number;
  categoryId : number;
  ingredient : string;
  measure : string;
}

interface CocktailDetailProps {
  id : number;
  enCocktailName : string;
  krCocktailName : string;
  image : string;
  instruction : string;
  ingredients : Ingredients[]
}

export type { CocktailDetailProps , Ingredients }