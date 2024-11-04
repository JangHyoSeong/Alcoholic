import axiosInstance from "./axios";
import { useUserStore } from "@/state/useUserStore";

const getUserInfo = async (): Promise<string | void> => {
  try {
    const response = await axiosInstance.get('/user')
    const { setNickname } = useUserStore()
    setNickname(response.data.nickname)
  } catch (error) {
    console.error('함수가 유저 닉네임을 가져오는 데 실패함',error)
  }
}

const patchUserInfo = async (): Promise<string | void> => {
  try {
    const response = await axiosInstance.patch('/user')
    const { setNickname } = useUserStore()
    setNickname(response.data.nickname)
  } catch (error) {
    console.error('함수가 유저 닉네임을 변경하는 데 실패함', error)
  }
}

export { getUserInfo, patchUserInfo }