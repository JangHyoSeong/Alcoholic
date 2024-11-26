import axiosInstance from "./axios";

export interface Ref {
  id: number;
  name: string;
  main: boolean;
}

export interface AddDrink {
  drinkName: string;
  position: number;
  image?: File | null;
}

const addRef = async (token: string, serialNumber: string): Promise<Ref | void> => {
  try {
    const response = await axiosInstance.post('/refrigerators/connect', {
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

const delRef = async (token: string, refrigeratorId: number): Promise<Ref | void> => {
  try {
    await axiosInstance.delete(`/refrigerators/${refrigeratorId}`, {
      headers: {
        Authorization: `${token}`
      }
    })
  } catch (error) {
    console.error('술장고 삭제 실패', error)
  }
}

const patchRef = async (token: string, refrigeratorId: number, newname: string): Promise<Ref | void> => {
  try {
    await axiosInstance.patch(`/refrigerators/${refrigeratorId}`,{
      name: newname
    }, {
      headers: {
        Authorization: `${token}`
      }
    })
  } catch (error) {
    console.error('술장고 이름 변경 실패', error)
  }
}

const addDrinkRef = async (token: string, refrigeratorId: number, formData: FormData): Promise<void> => {
  try {
    await axiosInstance.post(`/refrigerators/${refrigeratorId}`, formData, {
      headers: {
        Authorization: token,
        'Content-Type': 'multipart/form-data',
      },
    });
  } catch (error) {
    console.error('냉장고에 술 등록 실패', error);
  }
};

const getDrinkRef = async (token:string, refrigeratorId: number) => {
  try {
    const response = await axiosInstance.get(`/refrigerators/${refrigeratorId}`, {
      headers: {
        Authorization: token,
      }
    })
    return response.data.results
  } catch (error) {
    console.error('술 재고 조회 에러 발생')
  }
}

const getDrinkDetailRef = async (token:string, stockId: number) => {
  try {
    const response = await axiosInstance.get(`/refrigerators/stocks/${stockId}`, {
      headers: {
        Authorization: token,
      }
    })
    console.log('술 상세 정보',response.data)
    return response.data
  } catch (error) {
    console.error('술 상세 정보 조회 에러')
  }
}

export { addRef, getRef, delRef, patchRef, addDrinkRef, getDrinkRef, getDrinkDetailRef}