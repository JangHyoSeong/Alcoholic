import axiosInstance from "./axios";

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

const getCustomCock = async ( token: string ) => {
  try {
    const response = await axiosInstance.get(`/cocktails/stock`, {
      headers: {
        Authorization: token
      }
    })
    return response.data.result
  } catch (error) {
    console.error('커스텀 추천 실패')
  }
}

export { getPopularCock, getCustomCock }