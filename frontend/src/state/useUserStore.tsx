import { create } from 'zustand'

interface UserInfo {
  nickname: string

  setNickname: (value: string) => void;
}

export const useUserStore = create<UserInfo>((set) => ({
  nickname : '',

  setNickname: (value: string) => set({ nickname: value}),
}))