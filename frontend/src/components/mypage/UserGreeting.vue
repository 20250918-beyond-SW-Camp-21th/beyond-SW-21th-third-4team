<template>
  <div class="user-greeting">
    <div class="info">
      <div class="member-info-wrapper">
        <div class="memberInfo">
          <span class="user-icon">
            <img src="" alt="" onerror="this.src='//img.echosting.cafe24.com/skin/base_ko_KR/member/img_member_default.gif';" width="35">
          </span>
          <ul>
            <li>
              <strong class="name">{{ user?.name || '*****' }}님</strong>의 구매등급은 <br>
              <strong class="group">[{{ user?.grade || 'BRONZE' }} / 배송비 무료입니다.]</strong>입니다.
            </li>
          </ul>
        </div>
        <div class="help-separator">
          <p class="txt11">
            KRW {{ formatNumber(10000) }} 이상 구매시 1%를 추가적립 받으실 수 있습니다.
          </p>
        </div>
      </div>

      <ul class="account-summary">
        <li>마일리지 : <a href="/myshop/mileage/historyList.html"><span class="notranslate">{{ formatNumber(mileage) }}원</span></a></li>
        <li>예치금 : <a href="/myshop/deposits/historyList.html"><span class="notranslate">{{ formatNumber(deposits) }}KRW</span></a></li>
        <li>쿠폰 : <a href="/myshop/coupon/coupon.html">{{ couponCount }}개</a></li>
      </ul>
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
.user-greeting {}

.info {
  background: #fff;
  border: 1px solid #e0e0e0;
  padding: 24px;
  margin-bottom: 16px;
}

.info > div {
  margin-bottom: 16px;
}

.info > div:last-child {
  margin-bottom: 0;
}

.member-info-wrapper {}

.memberInfo {
  display: flex;
  align-items: flex-start;
  gap: 10px;
  margin-bottom: 0;
}

.memberInfo .user-icon {
  width: 36px;
  height: 36px;
  background: #9e9e9e;
  border-radius: 3px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  flex-shrink: 0;
}

.memberInfo .user-icon img {
  width: 32px;
  height: 32px;
  border-radius: 3px;
}

.memberInfo ul {
  flex: 1;
  list-style: none;
  padding: 0;
  margin: 0;
}

.memberInfo li {
  font-size: 12px;
  color: #000;
  line-height: 18px;
  font-weight: 300;
}

.memberInfo .name {
  color: #4a90e2;
  text-decoration: underline;
  font-weight: 400;
}

.memberInfo .group {
  font-size: 10px;
  color: #757575;
  font-weight: 300;
}

.help-separator {
  padding-bottom: 14px;
  margin-bottom: 14px;
  border-bottom: 1px solid #e0e0e0;
}

.help-separator .txt11 {
  font-size: 10px;
  color: #757575;
  line-height: 16px;
  margin: 0;
  font-weight: 300;
}

.account-summary {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  gap: 30px;
}

.account-summary li {
  font-size: 11px;
  color: #000;
  font-weight: 300;
  white-space: nowrap;
}

.account-summary a {
  color: #000;
  text-decoration: none;
}

.account-summary a:hover {
  text-decoration: underline;
}

.notranslate {
  font-weight: 300;
}
</style>