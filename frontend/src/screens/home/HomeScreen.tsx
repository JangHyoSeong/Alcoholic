import tw from 'twrnc';
import {SafeAreaView, ScrollView, View} from 'react-native';
import HomeBanner from '@/components/home/HomeBanner';
import MyRefBanner from '@/components/home/MyRefBanner';
import RecoBanner from '@/components/home/RecoBanner';

const HomeScreen = () => {
  return (
    <ScrollView>
      <SafeAreaView style={tw`flex-1 bg-white py-10`}>
        <HomeBanner />
        <MyRefBanner />
        <RecoBanner />
      </SafeAreaView>
    </ScrollView>
  );
};

export default HomeScreen;
