<template>
  <div class="order-list-page">
    <div class="page-container">
      <h1 class="page-title">주문조회</h1>

      <!-- Filter Tabs -->
      <div class="filter-tabs">
        <button
          v-for="filter in filters"
          :key="filter.key"
          @click="activeFilter = filter.key"
          :class="{ active: activeFilter === filter.key }"
          class="filter-tab"
        >
          {{ filter.label }}
        </button>
      </div>

      <!-- Loading State -->
      <div v-if="loading" class="loading">
        <div class="spinner"></div>
        <p>주문내역을 불러오는 중...</p>
      </div>

      <!-- Empty State -->
      <div v-else-if="filteredOrders.length === 0" class="empty-state">
        <p>주문 내역이 없습니다.</p>
        <router-link to="/products" class="shop-now-btn">
          쇼핑하러 가기
        </router-link>
      </div>

      <!-- Order List -->
      <div v-else class="order-list">
        <OrderCard
          v-for="order in filteredOrders"
          :key="order.id"
          :order="order"
          @view-detail="viewOrderDetail"
          @cancel-order="handleCancelOrder"
        />
      </div>

      <!-- Pagination -->
      <div v-if="totalPages > 1" class="pagination">
        <button
          @click="currentPage--"
          :disabled="currentPage === 1"
          class="page-btn"
        >
          이전
        </button>
        <span class="page-info">{{ currentPage }} / {{ totalPages }}</span>
        <button
          @click="currentPage++"
          :disabled="currentPage === totalPages"
          class="page-btn"
        >
          다음
        </button>
      </div>
    </div>

    <!-- Order Detail Modal -->
    <OrderDetailModal
      v-if="showDetailModal"
      :orderId="selectedOrderId"
      @close="closeDetailModal"
    />
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import api from '@/services/api'
import OrderCard from '@/components/mypage/OrderCard.vue'
import OrderDetailModal from '@/components/mypage/OrderDetailModal.vue'

const route = useRoute()
const router = useRouter()

// State
const loading = ref(false)
const orders = ref([])
const activeFilter = ref('ALL')
const currentPage = ref(1)
const totalPages = ref(1)
const showDetailModal = ref(false)
const selectedOrderId = ref(null)

// Filters
const filters = [
  { key: 'ALL', label: '전체' },
  { key: 'PENDING', label: '입금전' },
  { key: 'PREPARING', label: '배송준비중' },
  { key: 'SHIPPED', label: '배송중' },
  { key: 'DELIVERED', label: '배송완료' },
  { key: 'CANCELLED', label: '취소/반품' }
]

// Computed
const filteredOrders = computed(() => {
  if (activeFilter.value === 'ALL') {
    return orders.value
  }
  if (activeFilter.value === 'CANCELLED') {
    return orders.value.filter(o =>
      ['CANCELLED', 'RETURNED', 'EXCHANGED'].includes(o.status)
    )
  }
  return orders.value.filter(o => o.status === activeFilter.value)
})

// Methods
async function fetchOrders() {
  loading.value = true
  try {
    const params = {
      page: currentPage.value,
      status: activeFilter.value !== 'ALL' ? activeFilter.value : undefined
    }

    const response = await api.get('/api/orders', { params })
    orders.value = response.data.orders || []
    totalPages.value = response.data.totalPages || 1
  } catch (error) {
    console.error('Failed to fetch orders:', error)
    alert('주문 내역을 불러오는데 실패했습니다.')
  } finally {
    loading.value = false
  }
}

function viewOrderDetail(orderId) {
  selectedOrderId.value = orderId
  showDetailModal.value = true
}

function closeDetailModal() {
  showDetailModal.value = false
  selectedOrderId.value = null
}

async function handleCancelOrder(orderId) {
  if (!confirm('정말 주문을 취소하시겠습니까?')) {
    return
  }

  try {
    await api.put(`/api/orders/${orderId}/cancel`)
    alert('주문이 취소되었습니다.')
    fetchOrders()
  } catch (error) {
    console.error('Failed to cancel order:', error)
    alert('주문 취소에 실패했습니다.')
  }
}

// Watch for filter/page changes
watch([activeFilter, currentPage], () => {
  fetchOrders()
})

// Initialize from route query
onMounted(() => {
  if (route.query.status) {
    activeFilter.value = route.query.status
  }
  fetchOrders()
})
</script>

<style scoped>
.order-list-page {
  min-height: 80vh;
  padding: var(--layout-padding);
  padding-top: 2rem;
  padding-bottom: 4rem;
}

.page-container {
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

.filter-tabs {
  display: flex;
  gap: 0.5rem;
  margin-bottom: 2rem;
  overflow-x: auto;
}

.filter-tab {
  padding: 0.75rem 1.5rem;
  background: #fff;
  border: 1px solid var(--color-border);
  font-size: var(--font-size-medium);
  cursor: pointer;
  white-space: nowrap;
  transition: all 0.2s;
}

.filter-tab:hover {
  background: #f8f8f8;
}

.filter-tab.active {
  background: #000;
  color: #fff;
  border-color: #000;
  font-weight: var(--font-weight-bold);
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

.empty-state {
  text-align: center;
  padding: 4rem 0;
}

.empty-state p {
  font-size: 14px;
  color: #666;
  margin-bottom: 1.5rem;
}

.shop-now-btn {
  display: inline-block;
  padding: 0.75rem 2rem;
  background: #000;
  color: #fff;
  text-decoration: none;
  font-size: var(--font-size-medium);
  transition: opacity 0.2s;
}

.shop-now-btn:hover {
  opacity: 0.8;
}

.order-list {
  margin-bottom: 2rem;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 1rem;
  margin-top: 2rem;
}

.page-btn {
  padding: 0.5rem 1rem;
  background: #fff;
  border: 1px solid var(--color-border);
  font-size: var(--font-size-medium);
  cursor: pointer;
  transition: all 0.2s;
}

.page-btn:hover:not(:disabled) {
  background: #000;
  color: #fff;
  border-color: #000;
}

.page-btn:disabled {
  opacity: 0.3;
  cursor: not-allowed;
}

.page-info {
  font-size: var(--font-size-medium);
}

@media (max-width: 768px) {
  .page-title {
    font-size: 20px;
  }

  .filter-tabs {
    overflow-x: scroll;
    -webkit-overflow-scrolling: touch;
  }
}
</style>