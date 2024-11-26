import { useEffect } from 'react';
import tw from 'twrnc';
import { View, Image } from 'react-native';
import { getUserInfo } from '@/api/myprofile';
import { useAppStore } from '@/state/useAppStore';
import CustomFont from '../common/CustomFont';
import profilePicture from '@/assets/profile.png'
import { useUserStore } from '@/state/useUserStore';

const MyProfile = () => {
  const token = useAppStore((state) => state.token)
  const nickname = useUserStore((state) => state.nickname)
  const setNickname = useUserStore((state) => state.setNickname)

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const data = await getUserInfo(token)
        if (data) {
          setNickname(data.nickname)
        }
      } catch (error) {
        console.error('유저 닉네임 받아오는 데 실패', error)
      }
    }

    fetchUserInfo()
  }, [])

 return (
   <View style={tw`p-4`}>
      <Image style={[tw`rounded-full w-[90px] h-[90px] mr-[10px]`, { zIndex: 2}]} source={profilePicture}></Image>
      <CustomFont style={tw`text-lg ml-[8px]`}>{nickname}</CustomFont>
   </View>
 );
};

export default MyProfile;
