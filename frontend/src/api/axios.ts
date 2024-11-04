import axios from 'axios'

const axiosInstance = axios.create({
  baseURL: 'https://k11e206.p.ssafy.io/api/v1', // 기본 URL을 설정 (추후 변경 필요)
  withCredentials: true, // 요청 시 쿠키 및 인증 정보를 함께 전송 (CORS를 고려한 설정)
})

export default axiosInstance