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

export { getDrinkList }