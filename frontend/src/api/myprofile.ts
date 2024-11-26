import axiosInstance from "./axios";

const getUserInfo = async (token: string): Promise<{ nickname: string; username: string } | undefined> => {
  try {
    const { data } = await axiosInstance.get('/user', {
      headers: {
        Authorization: `${token}`
      }
    })
    return data
  } catch (error) {
    console.error('함수가 유저 닉네임을 가져오는 데 실패함',error)
  }
}

const patchUserInfo = async (token: string, newNickname: string): Promise<{ nickname: string; username: string } | void> => {
  try {
    const response = await axiosInstance.patch('/user', {
      nickname: newNickname
    },{
      headers: {
        Authorization: `${token}`
      }
    })
    return response.data
  } catch (error) {
    console.error('함수가 유저 닉네임을 변경하는 데 실패함', error)
  }
}

export { getUserInfo, patchUserInfo }