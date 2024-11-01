module.exports = {
  presets: ['module:@react-native/babel-preset'],
  plugins: [
    [
      'module-resolver',
      {
        root: ['./'],
        alias: {
          '@': './src',  // '@' 별칭이 './src'를 가리키도록 설정
        },
      },
    ],
  ],
};
