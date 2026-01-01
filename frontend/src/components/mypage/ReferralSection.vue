<template>
  <div class="invite">
    <h3>쇼핑몰 주소 복사하기</h3>

    <div class="inputBox">
      <input type="text" :value="referralUrl" readonly>
      <a href="#none" class="btnStrong" @click.prevent="copyUrl">주소복사</a>
    </div>

    <p class="ec-base-help">
      쇼핑몰 주소를 친구에게 보내 쇼핑몰에 초대해보세요.<br>
      가입즉시 친구에게는 <span class="notranslate">3,000원</span>의 적립금이 지급되고,<br>
      회원님에게는 친구가 가입즉시 <span class="notranslate">3,000원</span>의 적립금이 지급됩니다.
    </p>
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
.invite {
  background: #fff;
  border: 1px solid #e0e0e0;
  padding: 24px;
  margin-bottom: 16px;
}

.invite h3 {
  font-size: 12px;
  font-weight: 400;
  color: #000;
  margin: 0 0 12px 0;
  line-height: 18px;
}

.inputBox {
  display: flex;
  gap: 10px;
  margin-bottom: 15px;
}

.inputBox input {
  flex: 1;
  padding: 10px 15px;
  border: 1px solid #e0e0e0;
  font-size: 11px;
  color: #757575;
  background: #fafafa;
  font-family: 'Noto Sans KR', sans-serif;
  line-height: 18px;
}

.inputBox input:focus {
  outline: none;
  border-color: #000;
}

.btnStrong {
  padding: 10px 25px;
  background: #000;
  color: #fff;
  border: none;
  font-size: 11px;
  font-weight: 400;
  cursor: pointer;
  white-space: nowrap;
  font-family: 'Noto Sans KR', sans-serif;
  text-decoration: none;
  line-height: 18px;
  display: inline-block;
}

.btnStrong:hover {
  background: #333;
}

.ec-base-help {
  font-size: 11px;
  color: #757575;
  line-height: 18px;
  margin: 0;
  font-weight: 300;
}

.notranslate {
  font-weight: 300;
}
</style>