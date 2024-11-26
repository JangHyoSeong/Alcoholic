import axiosInstance from "./axios";

const getDrinkList = async (token: string) => {
  try {
    const response = await axiosInstance.get(`drinks`, {
      headers: {
        Authorization: token
      }
    })
    return response.data.result
  } catch (error) {
    console.error('api에서 술 목록 조회 실패', error)
  }
}

const getDrinkDetail = async (token: string, drinkId: number) => {
  try {
    const response = await axiosInstance.get(`drinks/${drinkId}`, {
      headers: {
        Authorization: token
      }
    })
    return response.data
  } catch (error) {
    console.error('술 디테일 못 가져옴', error)
  }
}

const getDrinkCategory = async (token: string, categoryId: number) => {
  try {
    const response = await axiosInstance.get(`drinks/category/${categoryId}`,{
      headers: {
        Authorization: token
      }
    })
    return response.data.result
  } catch (error) {
    console.error('카테고리 못 가져옴', error)
  }
}

export { getDrinkList, getDrinkDetail, getDrinkCategory }