import axiosInstance from './axios';
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
    const response = await axiosInstance.post<User>(`/auth/register`, {
      username: user.username,
      password: user.password,
      nickname: user.nickname,
    })
    return response.data
  } catch (error: any) {
    return error.message
  }
}

const getAccessToken  = async (): Promise<string> => {
  const accessToken = await getEncryptStorage(storageKeys.ACCESS_TOKEN)
  return accessToken
}

export { registerUser, getAccessToken }