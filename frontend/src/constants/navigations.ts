const mainNavigations = {
  HOME : 'Home',
  RECOMMEND : 'Recommend',
  MYSTORAGE : 'Mystorage',
  RECIPE : 'Recipe',
  MYPAGE : 'Mypage',
} as const

const AuthNavigations = {
  AUTH_HOME : 'AuthHome',
  SIGNUP : 'SignUp',
  SPLASH : 'Spalsh',
} as const

const RecommendNavigations = {
  RECO_HOME : 'RecoHome',
  POPULAR_RECO : 'PopularReco',
  MY_RECO : 'MyReco',
  DRINK_LIST : 'DrinkList',
  DRINK_DETAIL : 'DrinkDetail',
} as const

const MyStorageNavigations = {
  STORAGE_HOME : 'StorageHome',
  WINE_REGISTER : 'WineRegister',
  MYSTORAGE_DETAIL : 'MyStorageDetail',
  WINE_DETAIL : 'WineDetail',
} as const

const RecipeNavigations = {
  RECIPE_HOME : 'RecipeHome',
  RECIPE_DETAIL : 'RecipeDetail',
  RECIPE_ADD : 'RecipeAdd',
} as const

const MyPageNavigations = {
  MYPAGE_HOME : 'MyPageHome'
} as const

export { mainNavigations, AuthNavigations, RecommendNavigations, MyStorageNavigations, RecipeNavigations, MyPageNavigations }