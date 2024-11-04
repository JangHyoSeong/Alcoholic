import { JwtPayload } from 'jwt-decode'

interface ResponseUserProfile {
  username: string;
  password: string;
  nickname: string;
}

export type { ResponseUserProfile }