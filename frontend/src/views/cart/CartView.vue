<template>
  <div class="cart-container">
    <h2 class="page-title">장바구니</h2>

    <div class="top-black-bar">
      <span>국내배송상품 ({{ items.length }})</span>
    </div>

    <div v-if="loading" class="loading">로딩중...</div>

    <div v-else>
      <div v-if="items.length === 0" class="empty-cart">
        장바구니에 담긴 상품이 없습니다.
      </div>

      <div v-else>
        <div class="cart-list-wrapper">
          <div v-for="item in items" :key="item.cartItemId" class="cart-item-card">
            <div class="item-main">
              <div class="item-check">
                <input
                    type="checkbox"
                    :checked="isChecked(item.cartItemId)"
                    @change="toggleOne(item.cartItemId)"
                />
              </div>

              <div class="item-image" @click="goDetail(item.productId)" style="cursor:pointer">
                <img :src="item.imageUrl" :alt="item.name" />
              </div>

              <div class="item-details">
                <div class="item-name" @click="goDetail(item.productId)" style="cursor:pointer">
                  {{ item.name }}
                </div>
                <div class="item-delivery-info">배송 : [무료] / 기본배송 (해외배송 가능)</div>
                <div class="item-price">상품구매금액: <strong>KRW {{ format(item.price) }}</strong></div>
                <div class="item-discount">할인금액: KRW -0</div>

                <div class="qty-control">
                  <button @click="decrease(item)">-</button>
                  <input type="text" :value="item.quantity" readonly />
                  <button @click="increase(item)">+</button>
                </div>
              </div>
            </div>

            <div class="item-footer">
              <div class="footer-left">
                <button class="btn-small" @click="deleteOne(item.cartItemId)">삭제</button>
              </div>
              <div class="footer-right">
                <button class="btn-order-single" @click="orderOne(item)">주문하기</button>
              </div>
            </div>
          </div>
        </div>

        <div class="list-controls">
          <button class="btn-text" @click="toggleAll">전체선택</button>
          <button class="btn-text" @click="deleteSelected">선택삭제</button>
        </div>

        <section class="payment-summary">
          <div class="summary-box">
            <div class="summary-header">
              <span>결제예정금액</span>
              <span>KRW {{ format(totalAmount) }}</span>
            </div>
            <div class="summary-content">
              <div class="summary-row">
                <span>총 상품금액</span>
                <span>KRW {{ format(totalAmount) }}</span>
              </div>
              <div class="summary-row bg-gray">
                <span>총 배송비</span>
                <span>KRW 0</span>
              </div>
            </div>
          </div>

          <div class="summary-box">
            <div class="summary-header">
              <span>적립예정금액</span>
              <span>최대 {{ format(rewardPoint) }}원</span>
            </div>
            <div class="summary-content">
              <div class="summary-row bg-gray">
                <span>회원 마일리지</span>
                <span>{{ format(rewardPoint) }}원</span>
              </div>
            </div>
          </div>
        </section>

        <div class="final-actions">
          <button class="btn-select-order" @click="orderSelected">선택상품주문</button>
          <button class="btn-all-order" @click="orderAll">전체상품주문</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { fetchMyCart, deleteCartItems, increaseCartItem, decreaseCartItem } from '../../api/cart'
import { useRouter } from 'vue-router'

const router = useRouter()
const loading = ref(true)
const items = ref([])
const checkedIds = ref(new Set())

const format = (n) => Number(n).toLocaleString()

// 체크박스 핸들러
const isChecked = (id) => checkedIds.value.has(id)

const toggleOne = (id) => {
  const next = new Set(checkedIds.value)
  if (next.has(id)) next.delete(id)
  else next.add(id)
  checkedIds.value = next
}

const toggleAll = () => {
  const allIds = items.value.map((it) => it.cartItemId)
  const isAllChecked =
      items.value.length > 0 && checkedIds.value.size === items.value.length

  checkedIds.value = isAllChecked ? new Set() : new Set(allIds)
}

// 금액 계산 로직 (아무것도 선택 안 했을 때 전체 금액 계산)
const activeItems = computed(() => {
  if (checkedIds.value.size === 0) return items.value
  return items.value.filter(it => checkedIds.value.has(it.cartItemId))
})

const totalAmount = computed(() => {
  return activeItems.value.reduce((sum, it) => sum + (Number(it.price) * it.quantity), 0)
})

const rewardPoint = computed(() => Math.floor(totalAmount.value * 0.01))

// API 통신 및 데이터 로드
const load = async () => {
  loading.value = true
  try {
    const res = await fetchMyCart()
    items.value = res.data?.data ?? []
    checkedIds.value = new Set() // 초기 진입 시 체크박스는 해제 (계산은 전체로 됨)
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}

const deleteOne = async (id) => {
  if (!confirm('해당 상품을 삭제하시겠습니까?')) return
  await deleteCartItems([id])
  await load()
}

const deleteSelected = async () => {
  if (checkedIds.value.size === 0) return alert('삭제할 상품을 선택해주세요.')
  await deleteCartItems(Array.from(checkedIds.value))
  await load()
}

const increase = async (it) => {
  const prevQty = it.quantity;
  it.quantity++; // 즉시 화면에 반영 (Optimistic Update)
  try { 
    await increaseCartItem(it.cartItemId); 
  } catch { 
    it.quantity = prevQty; // 실패 시 롤백
    alert('재고가 부족합니다.'); 
  }
}

const decrease = async (it) => {
  if (it.quantity <= 1) return;
  const prevQty = it.quantity;
  it.quantity--; // 즉시 화면에 반영 (Optimistic Update)
  try {
    await decreaseCartItem(it.cartItemId);
  } catch {
    it.quantity = prevQty; // 실패 시 롤백
  }
}

const orderOne = (item) => {
  router.push({
    path: '/order',
    query: {
      cartItemIds: item.cartItemId
    }
  })
}

const goDetail = (productId) => {
  router.push({ name: 'ProductDetail', params: { productId } })
  // 또는 router.push(`/product/${productId}`)
}

const orderSelected = () => {
  if (checkedIds.value.size === 0) {
    alert('상품을 선택해주세요.')
    return
  }

  router.push({
    path: '/order',
    query: {
      cartItemIds: Array.from(checkedIds.value).join(',')
    }
  })
}

const orderAll = () => {
  router.push({
    path: '/order',
    query: {
      cartItemIds: items.value.map(it => it.cartItemId).join(',')
    }
  })
}

onMounted(load)
</script>

<style scoped>
.cart-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 80px 20px;
  color: #000;
}

.page-title {
  text-align: center;
  font-size: 14px;
  font-weight: 700;
  margin-bottom: 30px;
  letter-spacing: 1px;
}

/* 상단 블랙 바 */
.top-black-bar {
  background: #000;
  color: #fff;
  padding: 15px;
  font-size: 12px;
  font-weight: 600;
  text-align: center;
}

/* 상품 카드 */
.cart-item-card {
  border: 1px solid #000;
  margin-top: -1px;
  padding: 25px 20px;
}

.item-main {
  display: flex;
  gap: 20px;
}

.item-check input {
  width: 16px;
  height: 16px;
  cursor: pointer;
}

.item-image img {
  width: 100px;
  height: 130px;
  object-fit: cover;
}

.item-details {
  flex: 1;
  font-size: 12px;
  line-height: 1.8;
}

.item-name {
  font-weight: 700;
  font-size: 13px;
  margin-bottom: 5px;
}

.item-delivery-info { color: #888; }

.qty-control {
  display: flex;
  margin-top: 15px;
}

.qty-control button {
  width: 28px;
  height: 28px;
  border: 1px solid #ddd;
  background: #fff;
  cursor: pointer;
}

.qty-control input {
  width: 35px;
  height: 28px;
  text-align: center;
  border: 1px solid #ddd;
  border-left: none;
  border-right: none;
}

/* 상품 하단 버튼부 */
.item-footer {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
  align-items: center;
}

.btn-small {
  padding: 6px 14px;
  border: 1px solid #000;
  background: #fff;
  font-size: 11px;
  cursor: pointer;
}

.btn-order-single {
  padding: 8px 25px;
  background: #000;
  color: #fff;
  border: none;
  font-size: 12px;
  cursor: pointer;
  font-weight: 600;
}

/* 하단 일괄 컨트롤 */
.list-controls {
  margin-top: 20px;
  display: flex;
  gap: 8px;
}

.btn-text {
  padding: 8px 18px;
  border: 1px solid #000;
  background: #fff;
  font-size: 11px;
  cursor: pointer;
}

/* 요약 박스 */
.payment-summary {
  margin-top: 50px;
}

.summary-box {
  border: 1px solid #000;
  margin-bottom: 15px;
}

.summary-header {
  background: #000;
  color: #fff;
  padding: 16px 20px;
  display: flex;
  justify-content: space-between;
  font-size: 14px;
  font-weight: 600;
}

.summary-row {
  display: flex;
  justify-content: space-between;
  padding: 15px 20px;
  font-size: 13px;
}

.bg-gray { background: #f6f6f6; border-top: 1px solid #eee; }

/* 최종 액션 버튼 */
.final-actions {
  display: flex;
  gap: 10px;
  margin-top: 40px;
}

.btn-select-order, .btn-all-order {
  flex: 1;
  height: 55px;
  font-size: 13px;
  font-weight: 700;
  cursor: pointer;
}

.btn-select-order { background: #fff; border: 1px solid #000; }
.btn-all-order { background: #000; color: #fff; border: none; }

.loading, .empty-cart {
  text-align: center;
  padding: 80px 0;
}
</style>