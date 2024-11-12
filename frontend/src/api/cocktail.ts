import axiosInstance from "./axios";

export interface Ingredient {
  categoryId: number;
  ingredient: string;
  measure: string;
}

export interface Cocktail {
  enCocktailName: string;
  krCocktailName: string;
  image?: string;
  instruction: string;
  ingredients: Ingredient[]
}

const getCocktailList = async (token: string) => {
  try {
    const response = await axiosInstance.get('/cocktails',{
      headers: {
        Authorization: `${token}`
      }
    })
    return response.data.result
  } catch (error) {
    console.error('칵테일 목록 조회 실패')
  }
}

const getCocktailDetail = async (token: string, CocktailId: number) => {
  try {
    const response = await axiosInstance.get(`/cocktails/${CocktailId}`,{
      headers: {
        Authorization: token
      }
    })
    return response.data
  } catch (error) {
    console.error('상세 정보 조회 실패', error)
  }
}

const searchCocktailList = async (token: string, name: string) => {
  try {
    const response = await axiosInstance.get(`/cocktails/search`, {
      headers: {
        Authorization: token
      },
      params: {
        name: name
      }
    })
    console.log('api ts에서 데이터 수신', response.data)
    return response.data
  } catch (error) {
    console.error('api ts에서 검색 실패')
  }
}

const addCocktail = async (token: string, cocktail:Cocktail ) => {
  try {
    await axiosInstance.post(`/cocktails`,{
      enCocktailName: cocktail.enCocktailName,
      krCocktailName: cocktail.krCocktailName,
      image: cocktail.image,
      instruction: cocktail.instruction,
      ingredients: cocktail.ingredients,
    },
    {
      headers: {
        Authorization: token
      }
    })
  } catch (error) {
    console.error('칵테일 커스텀 등록 실패')
  }
}

export { getCocktailList, getCocktailDetail, searchCocktailList, addCocktail }