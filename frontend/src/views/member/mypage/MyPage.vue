<template>
  <div id="contents">
    <div id="titleArea">
      <h2>마이쇼핑</h2>
    </div>

    <div class="myshopMain">
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

        <ReferralSection
          :userId="user?.id"
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
import api from '../../../api/http.js'
import UserGreeting from '../../../components/mypage/UserGreeting.vue'
import OrderStatusDashboard from '../../../components/mypage/OrderStatusDashboard.vue'
import ReferralSection from '../../../components/mypage/ReferralSection.vue'
import QuickMenu from '../../../components/mypage/QuickMenu.vue'

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
    const userResponse = await api.get('/mypage/user-info')
    user.value = userResponse.data.data

    // Fetch order history to count statuses
    const ordersResponse = await api.get('/mypage/orders')
    const orders = ordersResponse.data.data || []

    // Calculate order status counts from orders
    orderStatusCounts.value = {
      pending: orders.filter(o => o.status === 'ORDERED' || o.status === 'PAYMENT_COMLETED').length,
      preparing: orders.filter(o => o.status === 'PREPARING').length,
      shipped: orders.filter(o => o.status === 'SHIPPING').length,
      delivered: orders.filter(o => o.status === 'DELIVERED').length,
      cancelled: orders.filter(o => o.status === 'CANCELLED').length,
      exchanged: 0, // Not in current OrderStatus enum
      returned: 0,  // Not in current OrderStatus enum
    }

    // Set account info (TODO: backend needs to add these fields to UserDTO)
    mileage.value = 0
    deposits.value = 0
    couponCount.value = 0

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
#contents {
  padding: var(--layout-padding, 0 40px);
  padding-top: 80px;
  min-height: calc(100vh - 80px);
}

#titleArea {
  margin-bottom: 1.5rem;
  text-align: center;
}

#titleArea h2 {
  font-size: 14px;
  font-weight: 700;
  margin: 0;
  padding-bottom: 0;
  border-bottom: none;
  line-height: 18px;
}

.myshopMain {
  max-width: 550px;
  margin: 0 auto;
  padding-bottom: 3rem;
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
  font-size: 11px;
  color: #666;
  line-height: 18px;
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
  line-height: 18px;
}

.error button {
  padding: 0.75rem 2rem;
  background: #000;
  color: #fff;
  border: none;
  font-size: 11px;
  cursor: pointer;
  transition: opacity 0.2s;
  line-height: 18px;
}

.error button:hover {
  opacity: 0.8;
}

@media (max-width: 1024px) {
  #contents {
    padding: 0 10px;
    padding-top: 60px;
  }

  #titleArea h2 {
    font-size: 20px;
  }
}
</style>