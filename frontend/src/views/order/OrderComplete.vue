<template>
  <div class="order-complete-page">
    <div class="page-container">
      <!-- 전체 테이블 형태의 컨테이너 -->
      <div class="order-table">
        <!-- 주문 완료 아이콘 & 메시지 -->
        <div class="complete-header">
          <div class="complete-icon">
            <svg xmlns="http://www.w3.org/2000/svg" width="38" height="42" viewBox="0 0 24 24" fill="none" stroke="#4A90D9" stroke-width="1.2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M6 2L3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4z"></path>
              <line x1="3" y1="6" x2="21" y2="6"></line>
              <path d="M16 10a4 4 0 0 1-8 0"></path>
            </svg>
          </div>
          <p class="complete-title">고객님의 주문이</p>
          <p class="complete-subtitle">정상적으로 완료되었습니다.</p>
        </div>

        <!-- 주문 정보 -->
        <div class="order-info-section">
          <div class="info-row">
            <span class="info-label">주문번호</span>
            <span class="info-value">{{ orderNumber }}</span>
          </div>
          <div class="info-row">
            <span class="info-label">결제금액</span>
            <span class="info-value">KRW {{ formattedAmount }}</span>
          </div>
        </div>

        <!-- 결제수단 -->
        <div class="payment-section">
          <div class="section-header">
            <span class="section-title">결제수단</span>
          </div>
          <div class="payment-detail">
            <div class="detail-row">
              <span class="detail-label">결제수단</span>
              <div class="detail-content">
                <span class="payment-method"><strong>무통장 입금 (transfer)</strong></span><br>
                <span class="account-info">입금자 : <strong>{{ depositorName }}</strong>, 계좌번호 : <strong>국민은행 80170104229829</strong> (주식회사 앰비언트)</span>
              </div>
            </div>
            <button class="receipt-btn">현금영수증 신청</button>
          </div>
        </div>

        <!-- 배송지 -->
        <div class="shipping-section">
          <div class="section-header">
            <span class="section-title">배송지</span>
          </div>
          <div class="shipping-detail">
            <div class="shipping-row">
              <span class="shipping-label">받는사람</span>
              <span class="shipping-value">{{ receiverName }}({{ email }})</span>
            </div>
            <div class="shipping-row">
              <span class="shipping-label">주소</span>
              <span class="shipping-value">{{ address }}</span>
            </div>
            <div class="shipping-row">
              <span class="shipping-label">연락처</span>
              <span class="shipping-value">{{ receiverPhone }}</span>
            </div>
            <div class="shipping-row">
              <span class="shipping-label">배송요청</span>
              <span class="shipping-value">{{ deliveryMessage }}</span>
            </div>
          </div>
        </div>

        <!-- 주문상품 -->
        <div class="products-section">
          <div class="section-header">
            <span class="section-title">주문상품</span>
          </div>
          <div class="products-list">
            <div class="product-item" v-for="(product, index) in products" :key="index">
              <div class="product-image">
                <img :src="product.image" :alt="product.name" />
              </div>
              <div class="product-info">
                <p class="product-name">{{ product.name }}</p>
                <p class="product-option">[옵션: {{ product.option }}]</p>
                <p class="product-qty">수량: {{ product.quantity }}개</p>
                <p class="product-price">KRW {{ (product.price * product.quantity).toLocaleString() }}</p>
              </div>
            </div>
          </div>
          <div class="shipping-fee-row">
            <span class="fee-label">배송비</span>
            <span class="fee-value">KRW {{ shippingFee.toLocaleString() }}</span>
          </div>
        </div>

        <!-- 결제정보 -->
        <div class="payment-info-section">
          <div class="section-header">
            <span class="section-title">결제정보</span>
          </div>
          <div class="payment-info-detail">
            <div class="payment-info-row">
              <span class="payment-info-label">주문상품</span>
              <span class="payment-info-value">KRW {{ totalProductPrice.toLocaleString() }}</span>
            </div>
            <div class="payment-info-row">
              <span class="payment-info-label">배송비</span>
              <span class="payment-info-value">+KRW {{ shippingFee.toLocaleString() }}</span>
            </div>
            <div class="payment-info-row">
              <span class="payment-info-label">할인/부가결제</span>
              <span class="payment-info-value discount">-KRW {{ discount.toLocaleString() }}</span>
            </div>
          </div>
          <div class="final-payment-row">
            <span class="final-label">결제금액</span>
            <span class="final-value">KRW {{ finalAmount.toLocaleString() }}</span>
          </div>
        </div>

        <!-- 적립 혜택 -->
        <div class="reward-section">
          <div class="section-header">
            <span class="section-title">적립 혜택</span>
          </div>
          <div class="reward-detail">
            <div class="reward-row">
              <span class="reward-label">회원 마일리지</span>
              <span class="reward-value">KRW {{ rewardPoints.toLocaleString() }}원</span>
            </div>
          </div>
          <div class="reward-total-row">
            <span class="reward-total-label">적립 예정금액</span>
            <span class="reward-total-value">{{ rewardPoints.toLocaleString() }}원</span>
          </div>
          <!-- 하단 버튼 -->
          <div class="action-buttons">
            <button class="btn-secondary" @click="goToOrderList">주문확인하기</button>
            <button class="btn-primary" @click="goToHome">쇼핑계속하기</button>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();

// query에서 주문 정보 가져오기
const orderId = computed(() => route.query.orderId || '0');
const amount = computed(() => route.query.amount || '0');
const receiverName = computed(() => route.query.receiverName || '주문자');
const email = computed(() => route.query.email || '');
const address = computed(() => route.query.address || '');
const receiverPhone = computed(() => route.query.receiverPhone || '');
const deliveryMessage = computed(() => route.query.deliveryMessage || '');

// 주문 상품 데이터
const products = computed(() => {
  try {
    return JSON.parse(route.query.products || '[]');
  } catch {
    return [];
  }
});
const shippingFee = computed(() => Number(route.query.shippingFee) || 0);
// 주문상품 총 금액 - products에서 직접 계산
const totalProductPrice = computed(() => {
  return products.value.reduce((sum, p) => sum + (p.price * p.quantity), 0);
});
const discount = computed(() => Number(route.query.discount) || 0);
const finalAmount = computed(() => Number(route.query.amount) || 0);

// 적립 마일리지 (총 상품가격의 1%)
const rewardPoints = computed(() => {
  return Math.floor(totalProductPrice.value * 0.01);
});

// 결제수단 - 입금자(주문자 이름 사용)
const depositorName = receiverName;

// 주문번호 포맷: 20260102-0000{orderId}
const orderNumber = computed(() => {
  const today = new Date();
  const year = today.getFullYear();
  const month = String(today.getMonth() + 1).padStart(2, '0');
  const day = String(today.getDate()).padStart(2, '0');
  const dateStr = `${year}${month}${day}`;
  const paddedId = String(orderId.value).padStart(4, '0');
  return `${dateStr}-0000${paddedId}`;
});

// 금액 포맷
const formattedAmount = computed(() => {
  return Number(amount.value).toLocaleString();
});

const goToOrderList = () => {
  router.push('/mypage/orders');
};

const goToHome = () => {
  router.push('/');
};
</script>

<style scoped>
/* 레퍼런스 CSS 기반 스타일 */
.order-complete-page {
  padding-top: 55px;
  background-color: #fff;
  min-height: 100vh;
  font-family: 'Noto Sans KR', sans-serif;
  font-weight: 400;
  font-size: 11px;
  letter-spacing: 0;
  -webkit-text-size-adjust: none;
  text-rendering: optimizeLegibility;
  color: #333;
}

.page-container {
  max-width: 900px;
  margin: 0 auto;
  padding: 30px 20px;
}

/* 테이블 형태 외곽 테두리 */
.order-table {
  border: 1px solid #ddd;
}

/* 완료 헤더 */
.complete-header {
  text-align: center;
  padding: 50px 20px 30px 20px;
  line-height: 26px;
  border-bottom: 1px solid #ddd;
}

.complete-icon {
  margin-bottom: 15px;
}

.complete-title {
  font-size: 11px;
  font-weight: 800;
  color: #333;
  margin: 0;
  line-height: 26px;
}

.complete-subtitle {
  font-size: 11px;
  font-weight: 800;
  color: #333;
  margin: 0;
  line-height: 26px;
}

/* 주문 정보 */
.order-info-section {
  border-bottom: 1px solid #ddd;
  padding: 0;
}

.info-row {
  display: flex;
  justify-content: space-between;
  padding: 12px 20px;
  font-size: 11px;
  line-height: 26px;
  border-bottom: 1px solid #eee;
}

.info-row:last-child {
  border-bottom: none;
}

.info-label {
  color: #666;
  font-weight: 400;
}

.info-value {
  color: #333;
  font-weight: 400;
}

/* 결제수단 */
.payment-section {
  padding: 0;
  border-bottom: 1px solid #ddd;
}

.section-header {
  padding: 15px 20px;
  border-bottom: 1px solid #eee;
}

.section-title {
  font-size: 11px;
  font-weight: 700;
  color: #333;
}

.payment-detail {
  padding: 20px;
}

.detail-row {
  display: flex;
  gap: 20px;
  margin-bottom: 15px;
  font-size: 11px;
  line-height: 22px;
}

.detail-label {
  color: #666;
  min-width: 50px;
  font-weight: 400;
}

.detail-content {
  flex: 1;
  line-height: 22px;
}

.payment-method {
  color: #333;
  font-size: 11px;
}

.account-info {
  color: #333;
  font-size: 11px;
}

.receipt-btn {
  padding: 8px 14px;
  border: 1px solid #ddd;
  background: #fff;
  font-size: 11px;
  font-family: 'Noto Sans KR', sans-serif;
  cursor: pointer;
}

.receipt-btn:hover {
  background: #f9f9f9;
}

/* 배송지 */
.shipping-section {
  padding: 0;
}

.shipping-detail {
  padding: 0;
}

.shipping-row {
  display: flex;
  padding: 20px 20px;
  font-size: 12px;
  line-height: 1.8;
  border-bottom: 1px solid #eee;
}

.shipping-row:last-child {
  border-bottom: none;
}

.shipping-label {
  color: #888;
  min-width: 80px;
  font-weight: 400;
  font-size: 11px;
}

.shipping-value {
  flex: 1;
  color: #333;
  font-weight: 400;
  font-size: 11px;
  margin-left: 30px;
}

/* 주문상품 */
.products-section {
  padding: 0;
  border-top: 1px solid #ddd;
}

.products-list {
  padding: 20px;
}

.product-item {
  display: flex;
  gap: 20px;
  padding: 15px 0;
  border-bottom: 1px solid #eee;
}

.product-item:last-child {
  border-bottom: none;
}

.product-image {
  width: 90px;
  height: 100px;
  border: 1px solid #eee;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #fff;
}

.product-image img {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
}

.product-info {
  flex: 1;
}

.product-name {
  font-size: 11px;
  color: #333;
  margin: 0 0 8px 0;
}

.product-option {
  font-size: 11px;
  color: #888;
  margin: 0 0 5px 0;
}

.product-qty {
  font-size: 11px;
  color: #888;
  margin: 0 0 8px 0;
}

.product-price {
  font-size: 11px;
  color: #333;
  font-weight: 500;
  margin: 0;
}

.shipping-fee-row {
  display: flex;
  justify-content: space-between;
  padding: 15px 20px;
  margin: 0 20px 20px 20px;
  background-color: #f4f4f4;
  font-size: 11px;
}

.fee-label {
  color: #888;
}

.fee-value {
  color: #333;
}

/* 결제정보 */
.payment-info-section {
  padding: 0;
  border-top: 1px solid #ddd;
}

.payment-info-detail {
  padding: 20px;
}

.payment-info-row {
  display: flex;
  justify-content: space-between;
  padding: 8px 20px;
  font-size: 11px;
  padding-bottom: 2px;
}

.payment-info-label {
  color: #888;
  padding-left: 1px;
}

.payment-info-value {
  color: #333;
  padding-right: 1px;
}

.final-payment-row {
  display: flex;
  justify-content: space-between;
  padding: 15px 20px;
  margin: 0 20px 20px 20px;
  background-color: #f4f4f4;
  font-size: 12px;
}

.final-label {
  color: #333;
  font-weight: 700;
}

.final-value {
  color: #333;
  font-weight: 900;
}

/* 적립 혜택 */
.reward-section {
  padding: 0;
  border-top: 1px solid #ddd;
}

.reward-detail {
  padding: 20px;
}

.reward-row {
  display: flex;
  justify-content: space-between;
  padding: 8px 20px;
  font-size: 11px;
}

.reward-label {
  color: #888;
  padding-left: 1px;
}

.reward-value {
  color: #333;
  padding-right: 1px;
}

.reward-total-row {
  display: flex;
  justify-content: space-between;
  padding: 15px 20px;
  margin: 0 20px 5px 20px;
  background-color: #f4f4f4;
  font-size: 12px;
}

.reward-total-label {
  color: #333;
  font-weight: 700;
}

.reward-total-value {
  color: #333;
  font-weight: 900;
}

/* 하단 버튼 */
.action-buttons {
  display: flex;
  gap: 10px;
  justify-content: center;
  padding: 10px 20px 20px 20px;
}

.btn-secondary {
  flex: 1;
  padding: 15px 40px;
  border: 1px solid #333;
  background: #fff;
  font-size: 12px;
  font-family: 'Noto Sans KR', sans-serif;
  font-weight: 400;
  cursor: pointer;
}

.btn-primary {
  flex: 1;
  padding: 15px 40px;
  border: none;
  background: #3a4a5a;
  color: #fff;
  font-size: 12px;
  font-family: 'Noto Sans KR', sans-serif;
  font-weight: 400;
  cursor: pointer;
}

.btn-secondary:hover {
  background: #f5f5f5;
}

.btn-primary:hover {
  background: #2a3a4a;
}
</style>
