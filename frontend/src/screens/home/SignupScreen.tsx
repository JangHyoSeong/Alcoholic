import tw from 'twrnc';
import {View, Text, SafeAreaView, Image, TextInput, ScrollView} from 'react-native';
import {NativeStackScreenProps} from '@react-navigation/native-stack';
import {AuthStackParamList} from '@/navigations/stack/AuthStackNavigator';
import {AuthNavigations} from '@/constants';
import CustomButton from '@/components/common/CustomButton';
import CustomFont from '@/components/common/CustomFont';
import main_logo from '@/assets/main_logo.png';
import {useState} from 'react';
import {validateInputUser} from '@/utils';
import {ResponseUserProfile} from '@/types/domain';
import {registerUser} from '@/api/auth';

type AuthHomeProps = NativeStackScreenProps<
  AuthStackParamList,
  typeof AuthNavigations.SIGNUP
>;

const SignupScreen = ({navigation}: AuthHomeProps) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [nickname, setNickname] = useState('');
  const [passwordCheck, setPasswordCheck] = useState('');

  const [errors, setErrors] = useState({
    userName: '',
    password: '',
    nickName: '',
  });

  const handleSignup = async () => {
    const values: ResponseUserProfile = {
      username,
      password,
      nickname,
    };

    const validationErrors = validateInputUser(values);
    setErrors(validationErrors);

    if (password !== passwordCheck) {
      setErrors(prevErrors => ({
        ...prevErrors,
        password: '비밀번호와 비밀번호 확인이 다릅니다!',
      }));
      return;
    }

    if (
      !validationErrors.userName &&
      !validationErrors.password &&
      !validationErrors.nickName
    ) {
      try {
        const response = await registerUser({username, password, nickname});
        if (response) {
          // 회원가입 성공 여부를 확인합니다.
          console.log('회원가입이 완료되었습니다!');
          navigation.navigate('AuthHome');
        } else {
          console.error(response); // 실패 메시지 로깅
        }
      } catch (error) {
        console.error('회원가입 중 오류 발생:', error);
      }
    }
  };
  return (
    <SafeAreaView>
      <ScrollView>
        <View style={tw`items-center h-full mt-10`}>
          <Image
            style={tw`w-full h-70 mt-5`}
            resizeMode="contain"
            source={main_logo}
          />
          <CustomFont style={tw`mb-15`} fontSize={30} fontWeight="bold">
            알코홀릭!
          </CustomFont>
          <View style={tw`w-75`}>
            <View style={tw`flex-row justify-between mb-4`}>
              <TextInput
                style={tw`border border-gray-400 rounded-lg w-42 p-3 ${
                  errors.userName ? 'border-red-500' : ''
                }`}
                placeholder="아이디"
                onChangeText={setUsername}
              />
              <CustomButton label="중복 확인" size="small" />
            </View>
            {errors.userName ? (
              <Text style={tw`text-red-500 mb-2`}>{errors.userName}</Text>
            ) : null}
            <TextInput
              style={tw`border border-gray-400 rounded-lg p-3 mb-4 ${
                errors.nickName ? 'border-red-500' : ''
              }`}
              placeholder="닉네임"
              onChangeText={setNickname}
            />
            {errors.nickName ? (
              <Text style={tw`text-red-500 mb-2`}>{errors.nickName}</Text>
            ) : null}
            <TextInput
              style={tw`border border-gray-400 rounded-lg p-3 mb-4 ${
                errors.password ? 'border-red-500' : ''
              }`}
              placeholder="비밀번호"
              secureTextEntry={true}
              onChangeText={setPassword}
            />
            {errors.password ? (
              <Text style={tw`text-red-500 mb-2`}>{errors.password}</Text>
            ) : null}
            <TextInput
              style={tw`border border-gray-400 rounded-lg p-3 mb-4`}
              placeholder="비밀번호 확인"
              secureTextEntry={true}
              onChangeText={setPasswordCheck}
            />
            <CustomButton
              label="회원가입"
              size="small"
              onPress={handleSignup}
              style={tw`mx-4`}
            />
          </View>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
};

export default SignupScreen;
