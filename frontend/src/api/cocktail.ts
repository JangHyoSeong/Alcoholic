import axiosInstance from "./axios";

const getCocktailList = async (token: string) => {
  try {
    const response = await axiosInstance.get('/cocktails',{
      headers: {
        Authorization: `${token}`
      }
    })
    return response.data
  } catch (error) {
    console.error('칵테일 목록 조회 실패')
  }
}

export { getCocktailList }