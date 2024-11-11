import axiosInstance from "./axios";

const getDrinkList = async (token: string) => {
  try {
    const response = await axiosInstance.get(`drinks`, {
      headers: {
        Authorization: token
      }
    })
    return response.data
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
    console.log('디테일 데이터', response.data)
    return response.data
  } catch (error) {
    console.error('술 디테일 못 가져옴', error)
  }
}

export { getDrinkList, getDrinkDetail }