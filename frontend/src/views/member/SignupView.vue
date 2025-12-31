<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { signup as signupApi } from '../../api/auth.js'
import { getSignupAgreements, clearSignupAgreements } from '../../utils/signupAgreements.js'

const router = useRouter()

const form = reactive({
  loginId: '',
  password: '',
  passwordConfirm: '',
  name: '',
  phone: '',
  email: '',
  birthDate: '', // YYYY-MM-DD
  address: '',   // optional
})

const touched = reactive({
  loginId: false,
  password: false,
  passwordConfirm: false,
  name: false,
  phone: false,
  email: false,
  birthDate: false,
  address: false,
})

const fieldErrors = reactive({
  loginId: '',
  password: '',
  passwordConfirm: '',
  name: '',
  phone: '',
  email: '',
  birthDate: '',
  address: '',
})

const errorMsg = ref('')
const loading = ref(false)

const validateLoginId = () => {
  if (!form.loginId) return '아이디를 입력해주세요.'
  if (form.loginId.length < 6 || form.loginId.length > 15) return '아이디는 6~15자여야 합니다.'
  if (!/^[a-zA-Z0-9]+$/.test(form.loginId)) return '아이디는 영문/숫자만 가능합니다.'
  return ''
}

const validatePassword = () => {
  if (!form.password) return '비밀번호를 입력해주세요.'
  if (form.password.length < 8 || form.password.length > 20) return '비밀번호는 8~20자여야 합니다.'
  // 서버 정규식 느낌 반영: 영문 + 숫자 포함(특수문자 가능, 공백 X)
  if (!/(?=.*[A-Za-z])(?=.*\d)/.test(form.password)) return '비밀번호는 영문과 숫자를 모두 포함해야 합니다.'
  if (/\s/.test(form.password)) return '비밀번호에 공백은 사용할 수 없습니다.'
  return ''
}

const validatePasswordConfirm = () => {
  if (!form.passwordConfirm) return '비밀번호 확인을 입력해주세요.'
  if (form.passwordConfirm !== form.password) return '비밀번호가 일치하지 않습니다.'
  return ''
}

const validateName = () => {
  if (!form.name) return '이름을 입력해주세요.'
  if (form.name.length < 2 || form.name.length > 50) return '이름은 2~50자여야 합니다.'
  if (!/^[가-힣]+$/.test(form.name)) return '이름은 한글만 입력 가능합니다.'
  return ''
}

const validatePhone = () => {
  if (!form.phone) return '휴대폰 번호를 입력해주세요.'
  if (form.phone.length > 20) return '휴대폰 번호는 20자 이하여야 합니다.'
  if (!/^[0-9]+$/.test(form.phone)) return '휴대폰 번호는 숫자만 입력해주세요.'
  return ''
}

const validateEmail = () => {
  if (!form.email) return '이메일을 입력해주세요.'
  if (form.email.length > 100) return '이메일은 100자 이하여야 합니다.'
  if (!/^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/.test(form.email)) return '이메일 형식이 올바르지 않습니다.'
  return ''
}

const validateBirthDate = () => {
  if (!form.birthDate) return '생년월일을 선택해주세요.'
  // 간단 체크: 미래 날짜 방지
  const today = new Date()
  const input = new Date(form.birthDate)
  if (Number.isNaN(input.getTime())) return '생년월일 형식이 올바르지 않습니다.'
  if (input > today) return '생년월일은 오늘 이전이어야 합니다.'
  return ''
}

const validateAddress = () => {
  // 주소는 optional
  if (!form.address) return ''
  if (form.address.length > 255) return '주소는 255자 이하여야 합니다.'
  return ''
}

const validateAll = () => {
  fieldErrors.loginId = validateLoginId()
  fieldErrors.password = validatePassword()
  fieldErrors.passwordConfirm = validatePasswordConfirm()
  fieldErrors.name = validateName()
  fieldErrors.phone = validatePhone()
  fieldErrors.email = validateEmail()
  fieldErrors.birthDate = validateBirthDate()
  fieldErrors.address = validateAddress()

  return !Object.values(fieldErrors).some(Boolean)
}

const touch = (key) => {
  touched[key] = true
  // 터치된 필드만 즉시 검증
  if (key === 'loginId') fieldErrors.loginId = validateLoginId()
  if (key === 'password') fieldErrors.password = validatePassword()
  if (key === 'passwordConfirm') fieldErrors.passwordConfirm = validatePasswordConfirm()
  if (key === 'name') fieldErrors.name = validateName()
  if (key === 'phone') fieldErrors.phone = validatePhone()
  if (key === 'email') fieldErrors.email = validateEmail()
  if (key === 'birthDate') fieldErrors.birthDate = validateBirthDate()
  if (key === 'address') fieldErrors.address = validateAddress()
}

const onSubmit = async () => {
  errorMsg.value = ''

  // 제출 시 전부 touched 처리 → 빨간 글씨 나오게
  Object.keys(touched).forEach((k) => (touched[k] = true))

  if (!validateAll()) return

  loading.value = true
  try {
    const payload = {
      loginId: form.loginId,
      password: form.password,
      name: form.name,
      phone: form.phone,
      email: form.email,
      birthDate: form.birthDate,         // "YYYY-MM-DD"
      address: form.address || null,     // optional → null 허용
    }

    await signupApi(payload)

    // 약관 상태는 더 이상 필요 없으니 삭제
    clearSignupAgreements()

    // 가입 성공 → 로그인으로 이동
    alert("회원가입에 성공했습니다. 로그인 후 이용해주세요.")
    router.replace('/login')
  } catch (e) {
    const message = e?.response?.data?.error?.message
    errorMsg.value = message || '회원가입에 실패했습니다.'
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  const agree = getSignupAgreements()
  if (!agree?.agreeTerms || !agree?.agreePrivacy) {
    // 1단계 안 거치면 약관 페이지로
    router.replace('/signup/terms')
  }
})
</script>

<template>
  <div class="page">
    <div class="card">
      <h2 class="title">회원가입</h2>
      <form @submit.prevent="onSubmit" class="form">
        <label class="label">아이디</label>
        <input v-model="form.loginId" class="input" type="text" @blur="touch('loginId')" placeholder="6~15자 영문/숫자" />
        <p v-if="touched.loginId && fieldErrors.loginId" class="error">{{ fieldErrors.loginId }}</p>

        <label class="label">비밀번호</label>
        <input v-model="form.password" class="input" type="password" @blur="touch('password')" placeholder="8~20자, 영문+숫자" />
        <p v-if="touched.password && fieldErrors.password" class="error">{{ fieldErrors.password }}</p>

        <label class="label">비밀번호 확인</label>
        <input v-model="form.passwordConfirm" class="input" type="password" @blur="touch('passwordConfirm')" placeholder="비밀번호 재입력" />
        <p v-if="touched.passwordConfirm && fieldErrors.passwordConfirm" class="error">{{ fieldErrors.passwordConfirm }}</p>

        <label class="label">이름</label>
        <input v-model="form.name" class="input" type="text" @blur="touch('name')" placeholder="한글 이름" />
        <p v-if="touched.name && fieldErrors.name" class="error">{{ fieldErrors.name }}</p>

        <label class="label">휴대폰</label>
        <input v-model="form.phone" class="input" type="text" @blur="touch('phone')" placeholder="숫자만 입력" />
        <p v-if="touched.phone && fieldErrors.phone" class="error">{{ fieldErrors.phone }}</p>

        <label class="label">이메일</label>
        <input v-model="form.email" class="input" type="email" @blur="touch('email')" placeholder="example@domain.com" />
        <p v-if="touched.email && fieldErrors.email" class="error">{{ fieldErrors.email }}</p>

        <label class="label">생년월일</label>
        <input v-model="form.birthDate" class="input" type="date" @blur="touch('birthDate')" />
        <p v-if="touched.birthDate && fieldErrors.birthDate" class="error">{{ fieldErrors.birthDate }}</p>

        <label class="label">주소 (선택)</label>
        <input v-model="form.address" class="input" type="text" @blur="touch('address')" placeholder="주소 전체 입력" />
        <p v-if="touched.address && fieldErrors.address" class="error">{{ fieldErrors.address }}</p>

        <button class="btn primary" type="submit" :disabled="loading">
          {{ loading ? '가입 중...' : '가입하기' }}
        </button>

        <p v-if="errorMsg" class="error center">{{ errorMsg }}</p>

        <button class="btn ghost" type="button" @click="router.push('/login')">
          이미 계정이 있어요 (로그인)
        </button>
      </form>
    </div>
  </div>
</template>

<style scoped>
.page {
  min-height: 100vh;
  display: grid;
  place-items: center;
  background: #fff;
}
/* ✅ 제목이 잘리는 경우(헤더가 fixed일 가능성) 대비해서 위 여백을 넉넉히 */
.card {
  width: 640px;
  max-width: calc(100vw - 40px);
  padding: 48px 48px 44px;
  margin-top: 40px; /* ← 여기로 잘림 방지 */
}
.title {
  margin: 0 0 22px;
  text-align: center;
  font-size: 18px;
  font-weight: 600;
}
.form {
  display: flex;
  flex-direction: column;
  gap: 10px;
}
.label {
  font-size: 13px;
  color: #111;
  margin-top: 8px;
}
.input {
  height: 44px;
  border: none;
  border-bottom: 2px solid #111;
  outline: none;
  font-size: 14px;
  padding: 6px 4px;
}
.btn {
  height: 46px;
  border: 1px solid #111;
  cursor: pointer;
  font-size: 14px;
}
.primary {
  background: #111;
  color: #fff;
  margin-top: 16px;
}
.primary:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
.ghost {
  background: #fff;
  color: #111;
  border: none;
  margin-top: 8px;
  text-decoration: underline;
}
.error {
  margin: 2px 0 0;
  color: #d32f2f;
  font-size: 12.5px;
}
.center {
  text-align: center;
  margin-top: 8px;
}
</style>
