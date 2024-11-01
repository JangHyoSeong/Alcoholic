import tw from 'twrnc';
import { SafeAreaView, View } from 'react-native';
import HomeBanner from '@/components/home/HomeBanner';
import MyRefBanner from '@/components/home/MyRefBanner';
import RecoBanner from '@/components/home/RecoBanner';

const HomeScreen = () => {
 return (
 <SafeAreaView style={tw`flex-1 bg-white`}>
  <HomeBanner />
  <MyRefBanner />
  <RecoBanner />
 </SafeAreaView>
 );
};

export default HomeScreen;