<template>
  <div id="contents">
    <!-- Title -->
    <div id="titleArea">
      <h2>주문조회</h2>
    </div>

    <!-- Tabs -->
    <div class="ec-base-tab gBlank7">
      <ul>
        <li :class="{ selected: activeTab === 'orders' }">
          <a href="#" @click.prevent="activeTab = 'orders'">
            <span>주문내역 조회<span class="number">({{ orderCount }}건)</span></span>
          </a>
        </li>
        <li :class="{ selected: activeTab === 'cs' }">
          <a href="#" @click.prevent="activeTab = 'cs'">
            <span>취소/교환/반품 내역<span class="number">({{ csCount }}건)</span></span>
          </a>
        </li>
      </ul>
    </div>

    <!-- Search Filters -->
    <div class="xans-myshop-orderhistoryhead">
      <fieldset>
        <legend>검색기간설정</legend>

        <!-- Status Filter -->
        <div class="stateSelect">
          <span class="state">상태</span>
          <select v-model="activeFilter" class="fSelect">
            <option value="ALL">전체 주문처리상태</option>
            <option value="ORDERED">입금전</option>
            <option value="PREPARING">배송준비중</option>
            <option value="SHIPPING">배송중</option>
            <option value="DELIVERED">배송완료</option>
            <option value="CANCELLED">취소</option>
          </select>
        </div>

        <!-- Period Buttons -->
        <span class="term">기간</span>
        <div class="ec-base-button gColumn">
          <button
            v-for="period in datePeriods"
            :key="period.key"
            @click="activePeriod = period.key"
            :class="{ selected: activePeriod === period.key }"
            class="btnNormal pc"
          >
            {{ period.label }}
          </button>
        </div>

        <!-- Help Text -->
        <ul class="ec-base-help">
          <li>완료 후 36개월 이상 경과한 주문은 <a href="#" @click.prevent>[과거주문내역]</a>에서 확인할 수 있습니다.</li>
          <li>리뉴얼 전에 주문한 내역은 <a href="#" @click.prevent>[이전 주문내역]</a>에서 확인할 수 있습니다.</li>
          <li>취소/교환/반품 신청은 배송완료일 기준 7일까지 가능합니다.</li>
        </ul>
      </fieldset>
    </div>

    <!-- Order List -->
    <div class="xans-myshop-orderhistorylistitem">
      <!-- Loading State -->
      <div v-if="loading" class="loading">
        <div class="spinner"></div>
        <p>주문내역을 불러오는 중...</p>
      </div>

      <!-- Empty State -->
      <p v-else-if="filteredOrders.length === 0" class="empty">
        주문 내역이 없습니다.
      </p>

      <!-- Orders -->
      <div v-else class="orderList">
        <OrderCard
          v-for="order in filteredOrders"
          :key="order.id"
          :order="order"
          @view-detail="viewOrderDetail"
          @cancel-order="handleCancelOrder"
        />
      </div>
    </div>

    <!-- Pagination -->
    <div v-if="totalPages > 1" class="ec-base-paginate typeList">
      <button @click="currentPage--" :disabled="currentPage === 1" class="btnPrev">
        이전 페이지
      </button>
      <ol>
        <li v-for="page in totalPages" :key="page">
          <a
            href="#"
            @click.prevent="currentPage = page"
            :class="{ this: currentPage === page }"
          >
            {{ page }}
          </a>
        </li>
      </ol>
      <button
        @click="currentPage++"
        :disabled="currentPage === totalPages"
        class="btnNext"
      >
        다음 페이지
      </button>
    </div>

    <!-- Order Detail Modal (페이지 이동으로 변경되어 미사용) -->
    <!--
    <OrderDetailModal
      v-if="showDetailModal"
      :orderId="selectedOrderId"
      @close="closeDetailModal"
    />
    -->
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import api from '../../../api/http.js'
import OrderCard from '../../../components/mypage/OrderCard.vue'
import OrderDetailModal from '../../../components/mypage/OrderDetailModal.vue'

const route = useRoute()
const router = useRouter()

// State
const loading = ref(false)
const orders = ref([])
const activeTab = ref('orders')
const activeFilter = ref('ALL')
const activePeriod = ref('MONTH')
const currentPage = ref(1)
const totalPages = ref(1)
const showDetailModal = ref(false)
const selectedOrderId = ref(null)

// Date Periods
const datePeriods = [
  { key: 'TODAY', label: '오늘' },
  { key: 'MONTH', label: '1개월' },
  { key: 'THREE_MONTHS', label: '3개월' },
  { key: 'SIX_MONTHS', label: '6개월' },
  { key: 'CUSTOM', label: '기간설정' }
]

// Computed counts
const orderCount = computed(() => {
  if (activeTab.value === 'orders') {
    return orders.value.filter(o =>
      !['CANCELLED', 'RETURNED', 'EXCHANGED'].includes(o.status)
    ).length
  }
  return 0
})

const csCount = computed(() => {
  return orders.value.filter(o =>
    ['CANCELLED', 'RETURNED', 'EXCHANGED'].includes(o.status)
  ).length
})

// Helper: Calculate date range based on period
function getDateRange(period) {
  if (period === 'CUSTOM') return null

  const now = new Date()
  const startDate = new Date()

  switch (period) {
    case 'TODAY':
      startDate.setHours(0, 0, 0, 0)
      break
    case 'MONTH':
      startDate.setMonth(now.getMonth() - 1)
      break
    case 'THREE_MONTHS':
      startDate.setMonth(now.getMonth() - 3)
      break
    case 'SIX_MONTHS':
      startDate.setMonth(now.getMonth() - 6)
      break
    default:
      return null
  }

  return startDate.toISOString().split('T')[0]
}

// Computed
const filteredOrders = computed(() => {
  let filtered = orders.value

  // Filter by tab
  if (activeTab.value === 'orders') {
    filtered = filtered.filter(o =>
      !['CANCELLED', 'RETURNED', 'EXCHANGED'].includes(o.status)
    )
  } else {
    filtered = filtered.filter(o =>
      ['CANCELLED', 'RETURNED', 'EXCHANGED'].includes(o.status)
    )
  }

  // Filter by status
  if (activeFilter.value !== 'ALL') {
    filtered = filtered.filter(o => o.status === activeFilter.value)
  }

  // Filter by date period (client-side filtering)
  if (activePeriod.value && activePeriod.value !== 'CUSTOM') {
    const startDate = getDateRange(activePeriod.value)
    if (startDate) {
      filtered = filtered.filter(o => {
        const orderDate = new Date(o.orderDate)
        return orderDate >= new Date(startDate)
      })
    }
  }

  return filtered
})

// Product cache - stores all products by ID
const productCache = new Map()

// Helper: Fetch all products and cache them
async function fetchAllProducts() {
  if (productCache.size > 0) {
    return // Already cached
  }

  try {
    const response = await api.get('/products')
    const products = response.data.data || response.data || []

    // Cache all products by ID
    products.forEach(product => {
      productCache.set(product.id, {
        name: product.name || '상품명 없음',
        imageUrl: product.imageUrl || null,
        price: product.price || 0
      })
    })

    console.log(`Cached ${productCache.size} products`)
  } catch (error) {
    console.error('Failed to fetch products:', error)
  }
}

// Helper: Get product details by ID from cache
function getProductDetails(productId) {
  if (productCache.has(productId)) {
    return productCache.get(productId)
  }

  // Return fallback if not found
  return {
    name: '상품 정보 없음',
    imageUrl: null,
    price: 0
  }
}

// Methods
async function fetchOrders() {
  loading.value = true
  try {
    // First, fetch and cache all products
    await fetchAllProducts()

    // Then fetch orders
    const response = await api.get('/mypage/orders')

    // Handle response structure (same as MyPage)
    if (response.data && response.data.data) {
      const rawOrders = response.data.data || []

      // Transform backend data to match OrderCard expectations
      // Backend uses: orderId, orderedAt, items (with productId only)
      const transformedOrders = rawOrders.map((order) => {
        // Get product details for each item from cache
        const itemsWithDetails = (order.items || []).map((item) => {
          const productDetails = getProductDetails(item.productId)

          return {
            id: item.id,
            productName: productDetails.name,
            productImage: productDetails.imageUrl,
            size: item.size || null,
            quantity: item.quantity || 1,
            price: item.unitPrice || productDetails.price || 0
          }
        })

        return {
          id: order.orderId,
          orderNumber: order.orderNumber || `ORD-${order.orderId}`,
          orderDate: order.orderedAt || new Date().toISOString(),
          status: order.status,
          totalAmount: order.totalPrice || 0,
          items: itemsWithDetails
        }
      })

      orders.value = transformedOrders
      totalPages.value = 1
    } else {
      orders.value = []
      totalPages.value = 1
    }
  } catch (error) {
    console.error('Failed to fetch orders:', error)

    // More detailed error handling
    if (error.response) {
      console.error('Server error:', error.response.status, error.response.data)
    } else if (error.request) {
      console.error('No response from server')
    } else {
      console.error('Request setup error:', error.message)
    }

    // Set empty state instead of showing alert
    orders.value = []
    totalPages.value = 1
  } finally {
    loading.value = false
  }
}

function viewOrderDetail(orderId) {
  // 모달 대신 페이지 이동으로 변경
  router.push({
    path: '/order/complete',
    query: {
      orderId: orderId,
      mode: 'detail' // 상세 조회 모드
    }
  });
}
/* 모달 관련 코드 주석 처리 또는 제거 */
// function closeDetailModal() {
//   showDetailModal.value = false
//   selectedOrderId.value = null
// }

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

// Initialize from route query
onMounted(() => {
  if (route.query.status) {
    activeFilter.value = route.query.status
  }
  if (route.query.mode === 'cs') {
    activeTab.value = 'cs'
  }
  fetchOrders()
})
</script>

<style scoped>
/* Container */
#contents {
  padding: var(--layout-padding, 0 20px);
  padding-top: 80px;
  min-height: calc(100vh - 80px);
  max-width: 640px;
  margin: 0 auto;
}

/* Title Area */
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

/* Tabs */
.ec-base-tab {
  margin-bottom: 1.5rem;
}

.ec-base-tab.gBlank7 {
  margin-bottom: 20px;
}

.ec-base-tab ul {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  border: 1px solid #000;
}

.ec-base-tab li {
  flex: 1;
  text-align: center;
}

.ec-base-tab li a {
  display: block;
  padding: 15px 20px;
  text-decoration: none;
  color: #000;
  background: #fff;
  font-size: 13px;
  font-weight: normal;
  transition: all 0.2s;
  border-right: 1px solid #000;
}

.ec-base-tab li:last-child a {
  border-right: none;
}

.ec-base-tab li.selected a {
  background: #000;
  color: #fff;
  font-weight: 500;
}

.ec-base-tab .number {
  margin-left: 5px;
}

/* Search Filters */
.xans-myshop-orderhistoryhead {
  background: #fff;
  margin-bottom: 2rem;
  padding: 20px;
  border: 1px solid #e0e0e0;
}

.xans-myshop-orderhistoryhead fieldset {
  border: none;
  padding: 0;
  margin: 0;
}

.xans-myshop-orderhistoryhead legend {
  position: absolute;
  width: 1px;
  height: 1px;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
}

/* Status Select */
.stateSelect {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 15px;
}

.stateSelect .state {
  font-size: 13px;
  font-weight: 500;
  min-width: 40px;
}

.stateSelect .fSelect {
  flex: 1;
  max-width: 300px;
  padding: 8px 12px;
  border: 1px solid #d5d5d5;
  font-size: 13px;
  background: #fff;
  cursor: pointer;
}

/* Period Buttons */
.term {
  display: inline-block;
  font-size: 13px;
  font-weight: 500;
  margin-right: 10px;
  margin-bottom: 10px;
  vertical-align: middle;
}

.ec-base-button.gColumn {
  display: inline-flex;
  gap: 5px;
  margin-bottom: 15px;
}

.btnNormal {
  padding: 8px 16px;
  border: 1px solid #d5d5d5;
  background: #fff;
  font-size: 12px;
  color: #333;
  cursor: pointer;
  transition: all 0.2s;
}

.btnNormal:hover {
  background: #f8f8f8;
}

.btnNormal.selected {
  background: #000;
  color: #fff;
  border-color: #000;
  font-weight: 500;
}

/* Help Text */
.ec-base-help {
  list-style: none;
  padding: 15px 0 0;
  margin: 15px 0 0;
  border-top: 1px solid #e0e0e0;
  counter-reset: help-counter;
}

.ec-base-help li {
  font-size: 11px;
  color: #666;
  line-height: 24px;
  padding-left: 25px;
  position: relative;
  counter-increment: help-counter;
}

.ec-base-help li:before {
  content: counter(help-counter);
  position: absolute;
  left: 0;
  top: 2px;
  width: 18px;
  height: 18px;
  background: #999;
  color: #fff;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 10px;
  font-weight: bold;
}

.ec-base-help a {
  color: #666;
  text-decoration: underline;
}

.ec-base-help a:hover {
  color: #000;
}

/* Order List Container */
.xans-myshop-orderhistorylistitem {
  margin: 0;
}

/* Loading State */
.loading {
  text-align: center;
  padding: 60px 0;
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
  font-size: 13px;
  color: #666;
}

/* Empty State */
.empty {
  text-align: center;
  padding: 80px 20px;
  font-size: 13px;
  color: #666;
  background: #fff;
  border: 1px solid #d5d5d5;
  margin: 0;
}

/* Order List */
.orderList {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

/* Pagination */
.ec-base-paginate.typeList {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
  margin-top: 30px;
  padding: 20px 0;
}

.ec-base-paginate ol {
  display: flex;
  gap: 5px;
  list-style: none;
  padding: 0;
  margin: 0;
}

.ec-base-paginate a {
  display: inline-block;
  min-width: 30px;
  padding: 5px 10px;
  text-align: center;
  border: 1px solid #d5d5d5;
  background: #fff;
  color: #333;
  text-decoration: none;
  font-size: 12px;
  transition: all 0.2s;
}

.ec-base-paginate a:hover {
  background: #f8f8f8;
}

.ec-base-paginate a.this {
  background: #000;
  color: #fff;
  border-color: #000;
  font-weight: 500;
}

.btnPrev,
.btnNext {
  padding: 6px 12px;
  border: 1px solid #d5d5d5;
  background: #fff;
  font-size: 12px;
  color: #333;
  cursor: pointer;
  transition: all 0.2s;
}

.btnPrev:hover:not(:disabled),
.btnNext:hover:not(:disabled) {
  background: #f8f8f8;
}

.btnPrev:disabled,
.btnNext:disabled {
  opacity: 0.4;
  cursor: not-allowed;
}

/* Mobile Responsive */
@media (max-width: 1024px) {
  #contents {
    padding: 0 10px;
    padding-top: 60px;
  }

  #titleArea h2 {
    font-size: 20px;
  }
}

@media (max-width: 768px) {
  .ec-base-button.gColumn {
    flex-wrap: wrap;
  }

  .stateSelect {
    flex-direction: column;
    align-items: flex-start;
  }

  .stateSelect .fSelect {
    max-width: 100%;
    width: 100%;
  }

  .ec-base-tab li a {
    padding: 12px 10px;
    font-size: 12px;
  }
}
</style>