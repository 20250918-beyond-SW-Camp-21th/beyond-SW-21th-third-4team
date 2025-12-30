<template>
  <div class="referral-section">
    <h3 class="section-title">쇼핑몰 주소 복사하기</h3>

    <div class="referral-input-group">
      <input
        type="text"
        :value="referralUrl"
        readonly
        class="referral-input"
      />
      <button @click="copyUrl" class="copy-btn">주소복사</button>
    </div>

    <div class="referral-info">
      <span class="info-icon">ⓘ</span>
      <div class="info-text">
        <p>쇼핑몰 주소를 친구에게 보내 쇼핑몰에 초대해보세요.</p>
        <p>가입즉시 친구에게는 3,000원의 적립금이 지급되고,</p>
        <p>회원님에게는 친구가 가입즉시 3,000원의 적립금이 지급됩니다.</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  userId: {
    type: [String, Number],
    default: ''
  }
})

const referralUrl = computed(() => {
  return `//insilence.co.kr/?reco_id=466915465658k`
})

function copyUrl() {
  navigator.clipboard.writeText(referralUrl.value)
    .then(() => {
      alert('주소가 복사되었습니다.')
    })
    .catch(() => {
      alert('복사에 실패했습니다.')
    })
}
</script>

<style scoped>
.referral-section {
  background: #fff;
  border: 1px solid #e0e0e0;
  padding: 30px;
  margin-bottom: 20px;
}

.section-title {
  font-size: 13px;
  font-weight: 400;
  color: #000;
  margin-bottom: 15px;
}

.referral-input-group {
  display: flex;
  gap: 10px;
  margin-bottom: 15px;
}

.referral-input {
  flex: 1;
  padding: 10px 15px;
  border: 1px solid #e0e0e0;
  font-size: 11px;
  color: #757575;
  background: #fafafa;
  font-family: 'Noto Sans KR', sans-serif;
}

.referral-input:focus {
  outline: none;
  border-color: #000;
}

.copy-btn {
  padding: 10px 25px;
  background: #000;
  color: #fff;
  border: none;
  font-size: 11px;
  font-weight: 400;
  cursor: pointer;
  white-space: nowrap;
  font-family: 'Noto Sans KR', sans-serif;
}

.copy-btn:hover {
  background: #333;
}

.referral-info {
  display: flex;
  align-items: flex-start;
  gap: 8px;
  padding: 12px 15px;
  background: #f5f5f5;
  border-radius: 4px;
}

.info-icon {
  font-size: 14px;
  font-weight: bold;
  color: #757575;
  flex-shrink: 0;
}

.info-text {
  font-size: 10px;
  color: #757575;
  line-height: 1.6;
  font-weight: 300;
}

.info-text p {
  margin: 0;
}
</style>