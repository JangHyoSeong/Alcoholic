import axiosInstance from "./axios";

interface RecoCocktail {
  id : number;
  enCocktailName : string;
  krCocktailName : string;
  value : number; // 검색량
  image : string;
  instruction : string;
}

const getPopularCock = async ( token: string ) => {
  try {
    const response = await axiosInstance.get(`/cocktails/popularity`, {
      headers: {
        Authorization: token
      }
    })
    return response.data.result
  } catch (error) {
    console.error('인기 추천 실패')
  }
}

export { getPopularCock }