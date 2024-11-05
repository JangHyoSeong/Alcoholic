import axiosInstance from "./axios";

export interface Ref {
  id: number;
  name: string;
  main: boolean;
}

const addRef = async (token: string, serialNumber: string): Promise<Ref | void> => {
  try {
    const response = await axiosInstance.post('/refrigerators', {
      serialNumber
    },
      {
      headers: {
        Authorization: `${token}`
      }
    })
    return response.data
  } catch (error) {
    console.error('술장고 등록 실패', error)
  }
}

const getRef = async (token: string): Promise<{ results: Ref[] } | void> => {
  try { 
    const response = await axiosInstance.get('/refrigerators',{
      headers: {
        Authorization: `${token}`
      }
    })
    return response.data
  } catch (error) {
    console.error('술장고 조회 실패', error)
  }
}

export { addRef, getRef }