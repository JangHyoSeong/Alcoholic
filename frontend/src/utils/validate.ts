import { ResponseUserProfile } from "@/types/domain";

function isBlank(value: string) {
  return value.trim() === '';
}


function validateInputUser(values: ResponseUserProfile) {
  const errors = {
    userName: '',
    password: '',
    nickName: '',
  }

  if (isBlank(values.nickname)) {
    errors.userName = '유저 이름은 필수입니다!'
  } else if (values.username.length > 20) {
    errors.userName = '유저 이름은 20자를 넘길 수 없습니다!'
  } else if (values.username.length < 8) {
    errors.userName = '유저 이름은 8자를 넘겨야 합니다!'
  }

  if (isBlank(values.password)) {
    errors.password = '비밀번호는 필수입니다!'
  } else if (values.password.length > 20) {
    errors.password = '비밀번호는 20자를 넘길 수 없습니다!'
  } else if (values.password.length < 8) {
    errors.password = '비밀번호는 8자를 넘겨야 합니다!'
  }

  if (isBlank(values.nickname)) {
    errors.nickName = '닉네임은 필수입니다!'
  } else if (values.nickname.length > 20) {
    errors.nickName = '닉네임은 20자를 넘길 수 없습니다!'
  } else if (values.nickname.length < 2) {
    errors.nickName = '닉네임은 2자를 넘겨야 합니다!'
  }

  return errors
}

function validateUsername(values: string) {
  const errors = {
    userName : '',
  }

  if (isBlank(values)) {
    errors.userName = '유저 이름은 필수입니다!'
  } else if (values.length > 20) {
    errors.userName = '유저 이름은 20자를 넘길 수 없습니다!'
  } else if (values.length < 8) {
    errors.userName = '유저 이름은 8자를 넘겨야 합니다!'
  }

  return errors.userName
}

export { validateInputUser, validateUsername }