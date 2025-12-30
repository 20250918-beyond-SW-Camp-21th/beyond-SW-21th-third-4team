<template>
  <div class="mypage">
    <div class="mypage-container">
      <h1 class="page-title">마이페이지</h1>

      <!-- Loading State -->
      <div v-if="loading" class="loading">
        <div class="spinner"></div>
        <p>정보를 불러오는 중...</p>
      </div>

      <!-- Error State -->
      <div v-else-if="error" class="error">
        <p>{{ error }}</p>
        <button @click="fetchData">다시 시도</button>
      </div>

      <!-- Content -->
      <div v-else>
        <UserGreeting
          :user="user"
          :mileage="mileage"
          :deposits="deposits"
          :couponCount="couponCount"
        />

        <OrderStatusDashboard
          :statusCounts="orderStatusCounts"
        />

        <QuickMenu
          :couponCount="couponCount"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import api from '@/services/api'
import UserGreeting from '@/components/mypage/UserGreeting.vue'
import OrderStatusDashboard from '@/components/mypage/OrderStatusDashboard.vue'
import QuickMenu from '@/components/mypage/QuickMenu.vue'

const router = useRouter()

// State
const loading = ref(false)
const error = ref(null)
const user = ref(null)
const mileage = ref(0)
const deposits = ref(0)
const couponCount = ref(0)
const orderStatusCounts = ref({})

// Fetch all data
async function fetchData() {
  loading.value = true
  error.value = null

  try {
    // Fetch user info
    const userResponse = await api.get('/api/users/me')
    user.value = userResponse.data

    // Fetch order status counts
    const statusResponse = await api.get('/api/mypage/order-status-counts')
    orderStatusCounts.value = statusResponse.data

    // Fetch account info (mileage, deposits, coupons)
    const accountResponse = await api.get('/api/mypage/account-info')
    mileage.value = accountResponse.data.mileage || 0
    deposits.value = accountResponse.data.deposits || 0
    couponCount.value = accountResponse.data.couponCount || 0

  } catch (err) {
    console.error('Failed to fetch mypage data:', err)
    error.value = '정보를 불러오는데 실패했습니다.'

    // Redirect to login if unauthorized
    if (err.response?.status === 401) {
      router.push('/login')
    }
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchData()
})
</script>

<style scoped>
.mypage {
  min-height: 80vh;
  padding: var(--layout-padding);
  padding-top: 2rem;
  padding-bottom: 4rem;
}

.mypage-container {
  max-width: 1200px;
  margin: 0 auto;
}

.page-title {
  font-size: 24px;
  font-weight: var(--font-weight-bold);
  margin-bottom: 2rem;
  padding-bottom: 1rem;
  border-bottom: 2px solid #000;
}

/* Loading State */
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

.loading p {
  font-size: var(--font-size-medium);
  color: #666;
}

/* Error State */
.error {
  text-align: center;
  padding: 4rem 0;
}

.error p {
  font-size: 14px;
  color: #dc3545;
  margin-bottom: 1rem;
}

.error button {
  padding: 0.75rem 2rem;
  background: #000;
  color: #fff;
  border: none;
  font-size: var(--font-size-medium);
  cursor: pointer;
  transition: opacity 0.2s;
}

.error button:hover {
  opacity: 0.8;
}

@media (max-width: 1024px) {
  .page-title {
    font-size: 20px;
  }
}
</style>