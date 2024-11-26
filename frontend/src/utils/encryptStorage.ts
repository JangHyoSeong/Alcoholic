// 암호화된 저장소에 데이터 저장, 로드, 삭제 
import EncryptedStorage from 'react-native-encrypted-storage'

// 데이터 저장 함수
const setEncryptStorage = async <T>(key: string, data: T) => {
  await EncryptedStorage.setItem(key, JSON.stringify(data))
}

// 데이터 로드 함수
const getEncryptStorage = async (key: string) => {
  const storedData = await EncryptedStorage.getItem(key)

  // 데이터를 JSON 형식으로 파싱하여 변환, 데이터가 없으면 null
  return storedData ? JSON.parse(storedData) : null
}

// 데이터 삭제 함수
const removeEncryptStorage = async (key: string) => {
  const data = await getEncryptStorage(key)

  if (data) {
    await EncryptedStorage.removeItem(key)
  }
}

export { setEncryptStorage, getEncryptStorage, removeEncryptStorage }