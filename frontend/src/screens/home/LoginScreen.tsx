import { Image, SafeAreaView, View, TextInput } from 'react-native';
import tw from 'twrnc';
import main_logo from '@/assets/main_logo.png';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { AuthStackParamList } from '@/navigations/stack/AuthStackNavigator';
import { AuthNavigations, mainNavigations } from '@/constants';
import CustomFont from '@/components/common/CustomFont';
import CustomButton from '@/components/common/CustomButton';
import { useState } from 'react';


type AuthHomeProps = NativeStackScreenProps<
  AuthStackParamList,
  typeof AuthNavigations.AUTH_HOME
>;

const LoginScreen = ({ navigation }: AuthHomeProps) => {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')

  const handleLogin = () => {
    const correctUsername = 'test'
    const correctPassword = 'abcd'

    if ( username === correctUsername && password === correctPassword) {
      navigation.navigate(mainNavigations.HOME)
    } else {
      setError('아이디, 비밀번호가 틀렸습니다!')
      console.error('에러발생!')
    }
  }

  return (
    <SafeAreaView>
      <View style={tw`items-center h-full mt-10`}>
        <Image
          style={tw`w-full h-70 mt-5`} 
          resizeMode="contain"
          source={main_logo}
        />
        <CustomFont style={[
          tw`mb-15`,
          { 
            textShadowColor: 'rgba(0, 0, 0, 0.65)', 
            textShadowOffset: { width: -1, height: 1 }, 
            textShadowRadius: 10
          }  // 커스텀 쉐도우 스타일
          ]} fontSize={30} fontWeight="bold"
        >
          알코홀릭!
        </CustomFont>
        <TextInput 
        style={tw`border border-gray-400 rounded-lg w-3/4 p-3 mb-4`}
        placeholder='아이디'
        onChangeText={setUsername}
        />
        <TextInput 
        style={tw`border border-gray-400 rounded-lg w-3/4 p-3 mb-6`}
        placeholder='비밀번호'
        secureTextEntry={true}
        onChangeText={setPassword}
        />
        <View style={tw`flex-row`}>
          <CustomButton
            label="로그인"
            size="small"
            onPress={handleLogin}
            style={[
              tw`mx-4`, // twrnc 스타일 적용
              { 
                shadowColor: '#000', // 커스텀 쉐도우 스타일
                shadowOffset: { width: 0, height: 2 }, 
                shadowOpacity: 0.8, 
                shadowRadius: 5,
                elevation: 5 // Android에서 쉐도우 효과
              }
            ]}
          />
          <CustomButton
            label="회원가입"
            size="small"
            onPress={() => navigation.navigate('SignUp')}
            style={[
              tw`mx-4`, // twrnc 스타일 적용
              { 
                shadowColor: '#000', // 커스텀 쉐도우 스타일
                shadowOffset: { width: 0, height: 2 }, 
                shadowOpacity: 0.8, 
                shadowRadius: 5,
                elevation: 5 // Android에서 쉐도우 효과
              }
            ]}
          />
        </View>
      </View>
    </SafeAreaView>
  );
};

export default LoginScreen;