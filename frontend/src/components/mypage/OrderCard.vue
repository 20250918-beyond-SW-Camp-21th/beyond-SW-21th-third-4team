<template>
  <div class="order-card">
    <div class="order-header">
      <div class="order-info">
        <span class="order-number">{{ order.orderNumber }}</span>
        <span class="order-date">{{ formatDate(order.orderDate) }}</span>
      </div>
      <span class="order-status" :class="getStatusClass(order.status)">
        {{ getStatusText(order.status) }}
      </span>
    </div>

    <div class="order-items">
      <div
        v-for="item in order.items"
        :key="item.id"
        class="order-item"
      >
        <img
          :src="item.productImage || 'https://via.placeholder.com/80'"
          :alt="item.productName"
          class="item-image"
        />
        <div class="item-details">
          <h4 class="item-name">{{ item.productName }}</h4>
          <p class="item-option">
            사이즈: {{ item.size }} / 수량: {{ item.quantity }}개
          </p>
          <p class="item-price">{{ formatPrice(item.price) }}원</p>
        </div>
      </div>
    </div>

    <div class="order-footer">
      <div class="order-total">
        <span>총 결제금액</span>
        <strong>{{ formatPrice(order.totalAmount) }}원</strong>
      </div>
      <div class="order-actions">
        <button @click="viewDetail" class="btn-detail">
          상세보기
        </button>
        <button
          v-if="canCancel(order.status)"
          @click="cancelOrder"
          class="btn-cancel"
        >
          주문취소
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  order: {
    type: Object,
    required: true
  }
})

const emit = defineEmits(['view-detail', 'cancel-order'])

function formatDate(dateString) {
  const date = new Date(dateString)
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  return `${year}.${month}.${day}`
}

function formatPrice(price) {
  return price?.toLocaleString('ko-KR') || '0'
}

function getStatusText(status) {
  const statusMap = {
    'PENDING': '입금전',
    'PAID': '결제완료',
    'PREPARING': '배송준비중',
    'SHIPPED': '배송중',
    'DELIVERED': '배송완료',
    'CANCELLED': '취소됨',
    'EXCHANGED': '교환',
    'RETURNED': '반품'
  }
  return statusMap[status] || status
}

function getStatusClass(status) {
  return `status-${status.toLowerCase()}`
}

function canCancel(status) {
  return ['PENDING', 'PAID', 'PREPARING'].includes(status)
}

function viewDetail() {
  emit('view-detail', props.order.id)
}

function cancelOrder() {
  emit('cancel-order', props.order.id)
}
</script>

<style scoped>
.order-card {
  background: #fff;
  border: 1px solid var(--color-border);
  padding: 1.5rem;
  margin-bottom: 1rem;
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 1rem;
  margin-bottom: 1rem;
  border-bottom: 1px solid #f0f0f0;
}

.order-info {
  display: flex;
  gap: 1rem;
  align-items: center;
}

.order-number {
  font-size: 13px;
  font-weight: var(--font-weight-bold);
}

.order-date {
  font-size: var(--font-size-medium);
  color: #666;
}

.order-status {
  padding: 0.4rem 1rem;
  border-radius: 20px;
  font-size: 11px;
  font-weight: var(--font-weight-medium);
}

.status-pending { background: #fff3cd; color: #856404; }
.status-paid { background: #d1ecf1; color: #0c5460; }
.status-preparing { background: #d1ecf1; color: #0c5460; }
.status-shipped { background: #cce5ff; color: #004085; }
.status-delivered { background: #d4edda; color: #155724; }
.status-cancelled { background: #f8d7da; color: #721c24; }

.order-items {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-bottom: 1rem;
}

.order-item {
  display: flex;
  gap: 1rem;
}

.item-image {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border: 1px solid var(--color-border);
}

.item-details {
  flex: 1;
}

.item-name {
  font-size: 13px;
  font-weight: var(--font-weight-medium);
  margin-bottom: 0.5rem;
}

.item-option {
  font-size: var(--font-size-medium);
  color: #666;
  margin-bottom: 0.5rem;
}

.item-price {
  font-size: 12px;
  font-weight: var(--font-weight-bold);
}

.order-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 1rem;
  border-top: 1px solid #f0f0f0;
}

.order-total {
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
}

.order-total span {
  font-size: 11px;
  color: #666;
}

.order-total strong {
  font-size: 16px;
  font-weight: var(--font-weight-bold);
}

.order-actions {
  display: flex;
  gap: 0.5rem;
}

.btn-detail,
.btn-cancel {
  padding: 0.6rem 1.2rem;
  border: 1px solid #ddd;
  background: #fff;
  font-size: var(--font-size-medium);
  cursor: pointer;
  transition: all 0.2s;
}

.btn-detail:hover {
  background: #000;
  color: #fff;
  border-color: #000;
}

.btn-cancel {
  color: #dc3545;
  border-color: #dc3545;
}

.btn-cancel:hover {
  background: #dc3545;
  color: #fff;
}

@media (max-width: 768px) {
  .order-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 0.5rem;
  }

  .order-footer {
    flex-direction: column;
    align-items: flex-start;
    gap: 1rem;
  }

  .order-actions {
    width: 100%;
  }

  .btn-detail,
  .btn-cancel {
    flex: 1;
  }
}
</style>