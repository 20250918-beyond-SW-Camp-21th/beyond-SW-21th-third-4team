<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { setSignupAgreements } from '../../utils/signupAgreements.js'

const router = useRouter()

const agreeTerms = ref(false)   // 이용약관(필수)
const agreePrivacy = ref(false) // 개인정보처리방침(필수)

const errorMsg = ref('')

const canNext = computed(() => agreeTerms.value && agreePrivacy.value)

const onNext = () => {
  errorMsg.value = ''

  if (!canNext.value) {
    errorMsg.value = '필수 약관에 동의해야 다음 단계로 진행할 수 있습니다.'
    return
  }

  setSignupAgreements({
    agreeTerms: agreeTerms.value,
    agreePrivacy: agreePrivacy.value,
  })

  router.push('/signup')
}
</script>

<template>
  <div class="page">
    <div class="card">
      <h2 class="title">회원가입</h2>

      <div class="section">
        <label class="check">
          <input type="checkbox" v-model="agreeTerms" />
          <span>이용약관 동의 (필수)</span>
        </label>

        <label class="check">
          <input type="checkbox" v-model="agreePrivacy" />
          <span>개인정보처리방침 동의 (필수)</span>
        </label>

        <p v-if="errorMsg" class="error">{{ errorMsg }}</p>
      </div>

      <button class="btn primary" :disabled="!canNext" @click="onNext">
        다음
      </button>

      <button class="btn ghost" type="button" @click="router.push('/login')">
        취소
      </button>
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
}
.title {
  margin: 0 0 20px;
  text-align: center;
  font-size: 18px;
  font-weight: 600;
}
.section {
  display: flex;
  flex-direction: column;
  gap: 14px;
  margin-bottom: 18px;
}
.check {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 14px;
}
.btn {
  width: 100%;
  height: 46px;
  border: 1px solid #111;
  cursor: pointer;
  font-size: 14px;
}
.primary {
  background: #111;
  color: #fff;
  margin-top: 10px;
}
.primary:disabled {
  opacity: 0.4;
  cursor: not-allowed;
}
.ghost {
  background: #fff;
  color: #111;
  border: none;
  margin-top: 10px;
  text-decoration: underline;
}
.error {
  color: #d32f2f;
  font-size: 13px;
  margin: 0;
}
</style>
