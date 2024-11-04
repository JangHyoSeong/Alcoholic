import { create } from 'zustand'

interface Appstate {
  token : string;
  isLogin : boolean;

  setToken : (value: string) => void;
  setLogin : (value: boolean) => void;
}

export const useAppStore = create<Appstate>((set) => ({
  token : '',
  isLogin : false,

  setToken: (value: string) => set({ token : value }),
  setLogin: (value: boolean) => set({ isLogin : value }),
}))