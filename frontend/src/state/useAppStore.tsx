import { create } from 'zustand'

interface Appstate {
  token : string | null;
  isLogin : boolean;

  setToken : (value: string) => void;
  setLogin : (value: boolean) => void;
}

export const useAppStore = create<Appstate>((set) => ({
  token : '',
  isLogin : true,

  setToken: (value: string) => set({ token : value }),
  setLogin: (value: boolean) => set({ isLogin : value }),
}))