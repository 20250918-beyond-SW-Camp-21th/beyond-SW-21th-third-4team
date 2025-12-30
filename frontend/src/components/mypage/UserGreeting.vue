<template>
  <div class="user-greeting">
    <div class="user-header">
      <div class="user-icon">
        <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
          <circle cx="12" cy="7" r="4"></circle>
        </svg>
      </div>
      <div class="user-info">
        <p class="user-name">
          <a href="#" class="highlight">{{ user?.name }}님</a>의 구매등급은
        </p>
        <p class="user-grade">[{{ user?.grade || 'BRONZE' }} / 배송비 무료입니다.]</p>
      </div>
    </div>

    <div class="mileage-info">
      <span class="info-icon">ⓘ</span>
      <span>KRW {{ formatNumber(10000) }} 이상 구매시 1%를 추가적립 받으실 수 있습니다.</span>
    </div>

    <div class="account-summary">
      <span>마일리지 : {{ formatNumber(mileage) }}원</span>
      <span>예치금 : {{ formatNumber(deposits) }}KRW</span>
      <span>쿠폰 : {{ couponCount }}개</span>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  user: {
    type: Object,
    required: true
  },
  mileage: {
    type: Number,
    default: 0
  },
  deposits: {
    type: Number,
    default: 0
  },
  couponCount: {
    type: Number,
    default: 0
  }
})

function formatNumber(num) {
  return num?.toLocaleString('ko-KR') || '0'
}
</script>

<style scoped>
.user-greeting {
  background: #fff;
  border: 1px solid #e0e0e0;
  padding: 30px;
  margin-bottom: 20px;
}

.user-header {
  display: flex;
  align-items: flex-start;
  gap: 15px;
  margin-bottom: 20px;
}

.user-icon {
  width: 40px;
  height: 40px;
  background: #9e9e9e;
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  flex-shrink: 0;
}

.user-info {
  flex: 1;
}

.user-name {
  font-size: 13px;
  color: #000;
  margin-bottom: 5px;
  font-weight: 300;
}

.user-name .highlight {
  color: #4a90e2;
  text-decoration: underline;
  font-weight: 400;
}

.user-grade {
  font-size: 11px;
  color: #757575;
  font-weight: 300;
}

.mileage-info {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px 15px;
  background: #f5f5f5;
  border-radius: 4px;
  font-size: 11px;
  color: #757575;
  margin-bottom: 15px;
}

.info-icon {
  font-size: 14px;
  font-weight: bold;
}

.account-summary {
  display: flex;
  gap: 30px;
  font-size: 11px;
  color: #000;
  font-weight: 300;
}

.account-summary span {
  white-space: nowrap;
}
</style>