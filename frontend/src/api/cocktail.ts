import axiosInstance from "./axios";
import axios from "axios";

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
  const formData = new FormData();
  
  const cocktailData = {
    enCocktailName : cocktail.enCocktailName,
    krCocktailName : cocktail.krCocktailName,
    instruction : cocktail.instruction,
    ingredients : cocktail.ingredients,
  }

  formData.append('cocktailData', {"string": JSON.stringify(cocktail), type: "application/json"})

  if (cocktail.image) {
    formData.append('image', {
      uri: cocktail.image,
      type: 'image/jpeg',
      name: 'cocktail_image.jpg',
    });
  } else {
    formData.append('image', null);
  }

  try {
    const response = await axiosInstance.post(`/cocktails`, formData,{
      headers: {
        Authorization: token,
        "Content-Type": "multipart/form-data",
      }
    })
    console.log('커스텀 칵테일 등록 성공', response.data)
  } catch (error) {
    console.error('커스텀 칵테일 등록 실패', error)
  }
}

export { getCocktailList, getCocktailDetail, searchCocktailList, addCocktail }