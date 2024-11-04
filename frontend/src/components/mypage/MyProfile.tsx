import { useEffect } from 'react';
import tw from 'twrnc';
import { View, Image } from 'react-native';
import { getUserInfo } from '@/api/myprofile';
import { useUserStore } from '@/state/useUserStore';
import CustomFont from '../common/CustomFont';
import profilePicture from '@/assets/profile.png'

const MyProfile = () => {
  const { nickname } = useUserStore()

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        await getUserInfo()
      } catch (error) {
        console.error('유저 닉네임 받아오는 데 실패', error)
      }
    }

    fetchUserInfo()
  }, [])

 return (
   <View style={tw`p-4`}>
      <Image style={[tw`rounded-full w-[90px] h-[90px] mr-[10px]`, { zIndex: 2}]} source={profilePicture}></Image>
      <CustomFont style={tw`text-lg`}>{nickname}</CustomFont>
   </View>
 );
};

export default MyProfile;
