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

        <!-- 여기에 추가 섹션 작업 예정 -->

      </div>

      <!-- 하단 버튼 -->
      <div class="action-buttons">
        <button class="btn-secondary" @click="goToOrderList">주문내역 보기</button>
        <button class="btn-primary" @click="goToHome">쇼핑 계속하기</button>
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
const depositorName = computed(() => route.query.depositor || '주문자');

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
  font-weight: 400;
  color: #333;
  margin: 0;
  line-height: 26px;
}

.complete-subtitle {
  font-size: 11px;
  font-weight: 400;
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

/* 하단 버튼 */
.action-buttons {
  display: flex;
  gap: 10px;
  justify-content: center;
  padding: 30px 0;
}

.btn-secondary {
  padding: 12px 40px;
  border: 1px solid #000;
  background: #fff;
  font-size: 11px;
  font-family: 'Noto Sans KR', sans-serif;
  font-weight: 400;
  cursor: pointer;
}

.btn-primary {
  padding: 12px 40px;
  border: none;
  background: #000;
  color: #fff;
  font-size: 11px;
  font-family: 'Noto Sans KR', sans-serif;
  font-weight: 400;
  cursor: pointer;
}

.btn-secondary:hover {
  background: #f5f5f5;
}

.btn-primary:hover {
  background: #333;
}
</style>
