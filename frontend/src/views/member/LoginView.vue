<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { login as loginApi } from '../../api/auth.js'
import { setToken } from '../../utils/token.js'

const router = useRouter()

const loginId = ref('')
const password = ref('')
const errorMsg = ref('')
const loading = ref(false)

const onSubmit = async () => {
  errorMsg.value = ''
  loading.value = true

  try {
    const res = await loginApi(loginId.value, password.value)

    // ✅ 토큰 경로: res.data.data.accessToken
    const accessToken = res?.data?.data?.accessToken

    if (!accessToken) {
      errorMsg.value = '토큰이 응답에 없습니다. 백엔드 응답을 확인하세요.'
      return
    }

    setToken(accessToken)

    // ✅ 로그인 성공 → 메인으로
    router.push('/')
  } catch (e) {
    // 너희 실패 응답 구조: { success:false, data:null, error:{ status, message, code, timestamp } }
    const message = e?.response?.data?.error?.message
    errorMsg.value = message || '로그인에 실패했습니다.'
  } finally {
    loading.value = false
  }
}

const goSignup = () => {
  router.push('/signup')
}
</script>

<template>
  <div class="page">
    <div class="card">
      <h2 class="title">로그인</h2>

      <form @submit.prevent="onSubmit" class="form">
        <label class="label">아이디</label>
        <input
            v-model="loginId"
            class="input"
            type="text"
            autocomplete="username"
            placeholder="아이디"
        />

        <label class="label">패스워드</label>
        <input
            v-model="password"
            class="input"
            type="password"
            autocomplete="current-password"
            placeholder="비밀번호"
        />

        <button class="btn primary" type="submit" :disabled="loading">
          {{ loading ? '로그인 중...' : '로그인' }}
        </button>

        <p v-if="errorMsg" class="error">{{ errorMsg }}</p>

        <button class="btn ghost" type="button" @click="goSignup">
          회원가입
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
.card {
  width: 520px;
  max-width: calc(100vw - 40px);
  padding: 40px 48px;
  text-align: center;
}
.title {
  margin: 0 0 28px;
  font-size: 18px;
  font-weight: 600;
}
.form {
  display: flex;
  flex-direction: column;
  gap: 14px;
  text-align: left;
}
.label {
  font-size: 13px;
  color: #111;
  margin-top: 6px;
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
  margin-top: 12px;
}
.ghost {
  background: #fff;
  color: #111;
  border: none;
  margin-top: 6px;
  text-decoration: underline;
}
.error {
  margin: 6px 0 0;
  color: #d32f2f;
  font-size: 13px;
}
</style>
