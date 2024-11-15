import axiosInstance from './axios';
import { Alert } from 'react-native';
import { jwtDecode } from 'jwt-decode';

import { getEncryptStorage } from '@/utils';
import { storageKeys } from '@/constants';


export interface User {
  username: string;
  password: string;
  nickname: string;
}

const registerUser = async (user:User): Promise<User | string> => {
  try {
    const response = await axiosInstance.post<User>(`/auth/regist`, {
      username: user.username,
      password: user.password,
      nickname: user.nickname,
    })
    return response.data
  } catch (error: any) {
    return error.message
  }
}

const loginUser = async (username: string, password: string) => {
  try {
    const {data, headers} = await axiosInstance.post(`/auth/login`, {
      username,
      password,
    })

    const token = headers.authorization;
    console.log('토큰입니당', token)
    return token
  } catch (error: any) {
    Alert.alert('아이디나 비밀번호가 틀렸습니다.')
    console.error('로그인 에러', error.message)
  }
}

const getAccessToken  = async (): Promise<string> => {
  const accessToken = await getEncryptStorage(storageKeys.ACCESS_TOKEN)
  return accessToken
}

export { registerUser, loginUser, getAccessToken }