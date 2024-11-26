import { create } from 'zustand'

interface UserInfo {
  nickname: string
  isRef: boolean

  setNickname: (value: string) => void;
  setIsRef: (value: boolean) => void;
}

export const useUserStore = create<UserInfo>((set) => ({
  nickname : '',
  isRef : false,

  setNickname: (value: string) => set({ nickname: value}),
  setIsRef: (value: boolean) => set({ isRef: value})
}))