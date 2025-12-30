<template>
  <div class="profile-edit-page">
    <div class="page-container">
      <h1 class="page-title">회원정보 수정</h1>

      <div v-if="loading" class="loading">
        <div class="spinner"></div>
        <p>정보를 불러오는 중...</p>
      </div>

      <form v-else @submit.prevent="handleSubmit" class="profile-form">
        <div class="form-section">
          <h2>기본 정보</h2>

          <div class="form-group">
            <label>아이디</label>
            <input
              type="text"
              :value="user?.username"
              disabled
              class="form-input disabled"
            />
            <p class="form-help">아이디는 변경할 수 없습니다.</p>
          </div>

          <div class="form-group">
            <label>이름 <span class="required">*</span></label>
            <input
              v-model="formData.name"
              type="text"
              required
              class="form-input"
            />
          </div>

          <div class="form-group">
            <label>이메일 <span class="required">*</span></label>
            <input
              v-model="formData.email"
              type="email"
              required
              class="form-input"
            />
          </div>

          <div class="form-group">
            <label>전화번호</label>
            <input
              v-model="formData.phone"
              type="tel"
              placeholder="010-1234-5678"
              class="form-input"
            />
          </div>
        </div>

        <div class="form-section">
          <h2>비밀번호 변경</h2>
          <p class="section-desc">비밀번호를 변경하지 않으려면 비워두세요.</p>

          <div class="form-group">
            <label>현재 비밀번호</label>
            <input
              v-model="formData.currentPassword"
              type="password"
              class="form-input"
            />
          </div>

          <div class="form-group">
            <label>새 비밀번호</label>
            <input
              v-model="formData.newPassword"
              type="password"
              placeholder="최소 6자 이상"
              class="form-input"
            />
          </div>

          <div class="form-group">
            <label>새 비밀번호 확인</label>
            <input
              v-model="formData.confirmPassword"
              type="password"
              class="form-input"
            />
            <p v-if="passwordError" class="error-message">
              {{ passwordError }}
            </p>
          </div>
        </div>

        <div class="form-actions">
          <button
            type="submit"
            :disabled="saving"
            class="btn-save"
          >
            {{ saving ? '저장 중...' : '저장하기' }}
          </button>
          <button
            type="button"
            @click="router.back()"
            class="btn-cancel"
          >
            취소
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import api from '@/services/api'

const router = useRouter()

// State
const loading = ref(false)
const saving = ref(false)
const user = ref(null)

const formData = ref({
  name: '',
  email: '',
  phone: '',
  currentPassword: '',
  newPassword: '',
  confirmPassword: ''
})

// Computed
const passwordError = computed(() => {
  if (!formData.value.newPassword) return null

  if (formData.value.newPassword.length < 6) {
    return '비밀번호는 최소 6자 이상이어야 합니다.'
  }

  if (formData.value.newPassword !== formData.value.confirmPassword) {
    return '비밀번호가 일치하지 않습니다.'
  }

  return null
})

// Methods
async function fetchUser() {
  loading.value = true
  try {
    const response = await api.get('/api/users/me')
    user.value = response.data

    // Fill form
    formData.value.name = user.value.name
    formData.value.email = user.value.email
    formData.value.phone = user.value.phone || ''
  } catch (error) {
    console.error('Failed to fetch user:', error)
    if (error.response?.status === 401) {
      router.push('/login')
    }
  } finally {
    loading.value = false
  }
}

async function handleSubmit() {
  // Validate password if changing
  if (formData.value.newPassword) {
    if (!formData.value.currentPassword) {
      alert('현재 비밀번호를 입력해주세요.')
      return
    }

    if (passwordError.value) {
      alert(passwordError.value)
      return
    }
  }

  saving.value = true
  try {
    const updateData = {
      name: formData.value.name,
      email: formData.value.email,
      phone: formData.value.phone
    }

    // Include password if changing
    if (formData.value.newPassword) {
      updateData.currentPassword = formData.value.currentPassword
      updateData.newPassword = formData.value.newPassword
    }

    await api.put(`/api/users/${user.value.id}`, updateData)
    alert('회원정보가 수정되었습니다.')

    // Clear password fields
    formData.value.currentPassword = ''
    formData.value.newPassword = ''
    formData.value.confirmPassword = ''

    // Refresh user data
    await fetchUser()
  } catch (error) {
    console.error('Failed to update profile:', error)

    if (error.response?.data?.message) {
      alert(error.response.data.message)
    } else {
      alert('회원정보 수정에 실패했습니다.')
    }
  } finally {
    saving.value = false
  }
}

onMounted(() => {
  fetchUser()
})
</script>

<style scoped>
.profile-edit-page {
  min-height: 80vh;
  padding: var(--layout-padding);
  padding-top: 2rem;
  padding-bottom: 4rem;
}

.page-container {
  max-width: 800px;
  margin: 0 auto;
}

.page-title {
  font-size: 24px;
  font-weight: var(--font-weight-bold);
  margin-bottom: 2rem;
  padding-bottom: 1rem;
  border-bottom: 2px solid #000;
}

.loading {
  text-align: center;
  padding: 4rem 0;
}

.spinner {
  width: 40px;
  height: 40px;
  border: 3px solid #f3f3f3;
  border-top: 3px solid #000;
  border-radius: 50%;
  margin: 0 auto 1rem;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.profile-form {
  background: #fff;
  padding: 2rem;
  border: 1px solid var(--color-border);
}

.form-section {
  margin-bottom: 3rem;
}

.form-section h2 {
  font-size: 16px;
  font-weight: var(--font-weight-bold);
  margin-bottom: 1rem;
  padding-bottom: 0.5rem;
  border-bottom: 1px solid var(--color-border);
}

.section-desc {
  font-size: var(--font-size-medium);
  color: #666;
  margin-bottom: 1rem;
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  font-size: var(--font-size-medium);
  font-weight: var(--font-weight-medium);
}

.required {
  color: #dc3545;
}

.form-input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid var(--color-border);
  font-size: var(--font-size-medium);
  transition: border-color 0.2s;
}

.form-input:focus {
  outline: none;
  border-color: #000;
}

.form-input.disabled {
  background: #f5f5f5;
  color: #999;
  cursor: not-allowed;
}

.form-help {
  font-size: 10px;
  color: #666;
  margin-top: 0.25rem;
}

.error-message {
  font-size: 11px;
  color: #dc3545;
  margin-top: 0.25rem;
}

.form-actions {
  display: flex;
  gap: 1rem;
  justify-content: flex-end;
  padding-top: 2rem;
  border-top: 1px solid var(--color-border);
}

.btn-save,
.btn-cancel {
  padding: 0.75rem 2rem;
  font-size: var(--font-size-medium);
  font-weight: var(--font-weight-medium);
  cursor: pointer;
  transition: all 0.2s;
  border: none;
}

.btn-save {
  background: #000;
  color: #fff;
}

.btn-save:hover:not(:disabled) {
  opacity: 0.8;
}

.btn-save:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.btn-cancel {
  background: #fff;
  color: #000;
  border: 1px solid var(--color-border);
}

.btn-cancel:hover {
  background: #f8f8f8;
}

@media (max-width: 768px) {
  .profile-form {
    padding: 1.5rem;
  }

  .form-actions {
    flex-direction: column;
  }

  .btn-save,
  .btn-cancel {
    width: 100%;
  }
}
</style>