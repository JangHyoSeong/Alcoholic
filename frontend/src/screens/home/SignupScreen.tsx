import tw from 'twrnc';
import {View, Text, Image, TextInput, ScrollView, KeyboardAvoidingView, Alert} from 'react-native';
import {NativeStackScreenProps} from '@react-navigation/native-stack';
import {AuthStackParamList} from '@/navigations/stack/AuthStackNavigator';
import {AuthNavigations} from '@/constants';
import CustomButton from '@/components/common/CustomButton';
import CustomFont from '@/components/common/CustomFont';
import main_logo from '@/assets/main_logo.png';
import {useState} from 'react';
import {validateInputUser, validateUsername} from '@/utils';
import {ResponseUserProfile} from '@/types/domain';
import {registerUser} from '@/api/auth';
import axiosInstance from '@/api/axios';

type AuthHomeProps = NativeStackScreenProps<
  AuthStackParamList,
  typeof AuthNavigations.SIGNUP
>;

const SignupScreen = ({navigation}: AuthHomeProps) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [nickname, setNickname] = useState('');
  const [passwordCheck, setPasswordCheck] = useState('');
  const [ loading, setLoading ] = useState(false)
  const [usernameValid, setUsernameValid] = useState<boolean>(false);

  const [errors, setErrors] = useState({
    userName: '',
    password: '',
    nickName: '',
  });

  const handleUsernameChechPressed = async () => {
    const validationError = validateUsername(username);
    setErrors((prev) => ({
      ...prev,
      userName: validationError || '',
    }));

    if (validationError) {
      return;
    }
    const response = await checkUsernameDuplicated(username);
    if (response === 'True') {
      Alert.alert("사용 불가능한 아이디입니다")
      setUsernameValid(false);
    } else {
      Alert.alert("사용 가능한 아이디입니다.")
      setUsernameValid(true)
    }
  }

  const checkUsernameDuplicated = async (username: string) => {
    if (loading) return
    setLoading(true)

    try {
      const response = await axiosInstance.get(`/auth/check?username=${username}`)
      console.log(response.data.isDuplicated)
      return response.data.isDuplicated
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false)
    }
  }

  const handleSignup = async () => {
    if (loading) return
    setLoading(true)

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
        const response = await registerUser({ username, password, nickname });
      
        if (response.success) {
          console.log('회원가입이 완료되었습니다!');
          navigation.navigate('AuthHome');
        } else {
          console.error('회원가입 실패:', response.error);
          Alert.alert('회원가입 실패', '문제가 발생했습니다. 다시 시도해주세요.');
        }
      } catch (error) {
        console.error('예상치 못한 오류 발생:', error);
        Alert.alert('오류', '회원가입 중 문제가 발생했습니다. 네트워크 상태를 확인해주세요.');
      } finally {
        setLoading(false)
      }
    }
  };
  return (
    <KeyboardAvoidingView
      style={tw`flex`}
      behavior={'height'}
      keyboardVerticalOffset={30}
    >
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
                maxLength={20}
              />
              <CustomButton label="중복 확인" size="small" onPress={handleUsernameChechPressed}/>
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
              maxLength={20}
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
              maxLength={20}
            />
            {errors.password ? (
              <Text style={tw`text-red-500 mb-2`}>{errors.password}</Text>
            ) : null}
            <TextInput
              style={tw`border border-gray-400 rounded-lg p-3 mb-4`}
              placeholder="비밀번호 확인"
              secureTextEntry={true}
              onChangeText={setPasswordCheck}
              maxLength={20}
            />
            <CustomButton
              label="회원가입"
              size="small"
              onPress={handleSignup}
              style={tw`mx-4`}
              inValid={!usernameValid}
            />
          </View>
        </View>
      </ScrollView>
    </KeyboardAvoidingView>
  );
};

export default SignupScreen;
