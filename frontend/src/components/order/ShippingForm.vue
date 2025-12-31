<template>
  <div class="shipping-form-container">
    <div class="form-header">
      <h2>주문서 작성</h2>
    </div>

    <div class="form-box">
      <!-- 배송지 헤더 -->
      <div class="section-title">
        <h3>배송지</h3>
        <span class="toggle-icon">^</span>
      </div>

      <!-- 탭 (Visual Only) -->
      <div class="tabs">
        <div class="tab inactive">최근 배송지</div>
        <div class="tab active">직접입력</div>
      </div>

      <!-- 옵션 선택 (회원 정보와 동일 / 새로운 배송지) -->
      <div class="shipping-options">
        <label class="radio-label">
          <input type="radio" value="same" v-model="shippingOption" @change="handleOptionChange" />
          <span class="radio-custom"></span>
          회원 정보와 동일
        </label>
        <label class="radio-label">
          <input type="radio" value="new" v-model="shippingOption" @change="handleOptionChange" />
          <span class="radio-custom"></span>
          새로운 배송지
        </label>
      </div>

      <!-- 입력 폼 -->
      <div class="input-group">
        <label>받는사람 <span class="required">*</span></label>
        <input type="text" v-model="form.receiverName" class="full-width" />
      </div>

      <div class="input-group address-group">
        <label>주소 <span class="required">*</span></label>
        <div class="address-inputs">
          <div class="zipcode-row">
            <input type="text" v-model="form.zipcode" placeholder="우편번호" />
            <button type="button" class="btn-search">주소검색</button>
          </div>
          <input type="text" v-model="form.basicAddress" placeholder="기본주소" class="full-width" />
          <input type="text" v-model="form.detailAddress" placeholder="나머지 주소" class="full-width" />
        </div>
      </div>

      <div class="input-group phone-group">
        <label>일반전화 <span class="required">*</span></label>
        <div class="phone-inputs">
          <select v-model="form.tel1">
            <option value="02">02</option>
            <option value="031">031</option>
            <option value="032">032</option>
          </select>
          <span class="dash">-</span>
          <input type="text" v-model="form.tel2" />
          <span class="dash">-</span>
          <input type="text" v-model="form.tel3" />
        </div>
      </div>

      <div class="input-group phone-group">
        <label>휴대전화 <span class="required">*</span></label>
        <div class="phone-inputs">
          <select v-model="form.phone1">
            <option value="010">010</option>
            <option value="011">011</option>
            <option value="016">016</option>
          </select>
          <span class="dash">-</span>
          <input type="text" v-model="form.phone2" />
          <span class="dash">-</span>
          <input type="text" v-model="form.phone3" />
        </div>
      </div>

      <div class="input-group email-group">
        <label>이메일 <span class="required">*</span></label>
        <div class="email-inputs">
          <input type="text" v-model="form.emailId" />
          <span class="at">@</span>
          <input type="text" v-model="form.emailDomain" />
          <select @change="handleEmailDomainChange" v-model="selectedEmailDomain">
            <option value="manual">직접입력</option>
            <option value="naver.com">naver.com</option>
            <option value="gmail.com">gmail.com</option>
            <option value="nate.com">nate.com</option>
          </select>
        </div>
      </div>

      <div class="input-group message-group">
        <select v-model="form.deliveryMessage" class="full-width message-select">
          <option value="">-- 메시지 선택 (선택사항) --</option>
          <option value="배송 전 연락주세요">배송 전 연락주세요</option>
          <option value="부재 시 경비실에 맡겨주세요">부재 시 경비실에 맡겨주세요</option>
          <option value="부재 시 문 앞에 놓아주세요">부재 시 문 앞에 놓아주세요</option>
          <option value="direct">직접 입력</option>
        </select>
        <textarea
          v-if="form.deliveryMessage === 'direct'"
          v-model="form.customMessage"
          placeholder="배송 메시지를 입력해주세요."
          class="full-width mt-2"
        ></textarea>
      </div>

      <div class="save-checkbox">
        <label>
          <input type="checkbox" />
          기본 배송지로 저장
        </label>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue';
import http from '../../api/http';

// [State] 배송지 정보
const form = reactive({
  receiverName: '',
  zipcode: '',
  basicAddress: '',
  detailAddress: '',
  tel1: '02', tel2: '', tel3: '',
  phone1: '010', phone2: '', phone3: '',
  emailId: '',
  emailDomain: '',
  deliveryMessage: '',
  customMessage: ''
});

// [State] UI 옵션
const shippingOption = ref('new'); // 'same' | 'new'
const selectedEmailDomain = ref('manual');

// [State] 회원 정보 (API Fetch)
const userInfo = ref(null);

// [Logic] 회원 정보 조회
const fetchUserInfo = async () => {
  try {
    const response = await http.get('/mypage/user-info');
    if (response.data && response.data.data) {
      userInfo.value = response.data.data;
    }
  } catch (error) {
    console.error('회원 정보 조회 실패:', error);
  }
};

onMounted(() => {
  fetchUserInfo();
});

// [Logic] 옵션 변경 핸들러 (회원 정보와 동일 / 새로운 배송지)
const handleOptionChange = async () => {
  if (shippingOption.value === 'same') {
    if (!userInfo.value) {
      await fetchUserInfo(); // 없을 경우 재시도
    }

    if (userInfo.value) {
      const u = userInfo.value;
      form.receiverName = u.name || '';
      
      // 주소 파싱 (예: "(12345) 서울 ... 상세")
      // 백엔드에서 주소가 어떻게 오는지에 따라 파싱 로직 조정 필요
      // 여기서는 단순하게 처리 (우편번호 파싱 시도)
      let addr = u.address || '';
      const zipMatch = addr.match(/^\(?(\d{5})\)?\s*(.*)$/);
      if (zipMatch) {
        form.zipcode = zipMatch[1];
        form.basicAddress = zipMatch[2]; // 상세주소 분리 어려움, 기본주소에 통으로 넣음
        form.detailAddress = ''; 
      } else {
        form.zipcode = '';
        form.basicAddress = addr;
        form.detailAddress = '';
      }

      // 전화번호 파싱 (010-1234-5678)
      if (u.phone) {
        const phones = u.phone.split('-');
        if (phones.length === 3) {
          form.phone1 = phones[0];
          form.phone2 = phones[1];
          form.phone3 = phones[2];
        } else {
            // 형식이 다르면 앞 3자리만이라도..
            if(u.phone.length >= 10) {
                 form.phone1 = u.phone.substring(0,3);
                 form.phone2 = u.phone.substring(3,7);
                 form.phone3 = u.phone.substring(7);
            }
        }
      }

      // 이메일 파싱
      if (u.email) {
        const emails = u.email.split('@');
        if (emails.length === 2) {
          form.emailId = emails[0];
          form.emailDomain = emails[1];
          // 도메인 자동 선택 로직
          if (['naver.com', 'gmail.com', 'nate.com'].includes(emails[1])) {
            selectedEmailDomain.value = emails[1];
          } else {
            selectedEmailDomain.value = 'manual';
          }
        }
      }
    }
  } else {
    // 초기화
    resetForm();
  }
};

// [Logic] 폼 초기화
const resetForm = () => {
  form.receiverName = '';
  form.zipcode = '';
  form.basicAddress = '';
  form.detailAddress = '';
  form.tel1 = '02'; form.tel2 = ''; form.tel3 = '';
  form.phone1 = '010'; form.phone2 = ''; form.phone3 = '';
  form.emailId = '';
  form.emailDomain = '';
  form.deliveryMessage = '';
  form.customMessage = '';
  selectedEmailDomain.value = 'manual';
};

// [Logic] 이메일 도메인 변경
const handleEmailDomainChange = () => {
  if (selectedEmailDomain.value !== 'manual') {
    form.emailDomain = selectedEmailDomain.value;
  } else {
    form.emailDomain = ''; // 직접 입력 전환 시 초기화? or 유지? -> 보통 빈값으로
  }
};

// [Logic] DTO 생성 (부모 컴포넌트에서 호출)
const getOrderDeliveryDto = () => {
  if (!validate()) return null;

  const fullAddress = `(${form.zipcode}) ${form.basicAddress} ${form.detailAddress}`;
  const fullPhone = `${form.phone1}-${form.phone2}-${form.phone3}`;
  const fullEmail = `${form.emailId}@${form.emailDomain}`;
  const finalMessage = form.deliveryMessage === 'direct' ? form.customMessage : form.deliveryMessage;

  return {
    receiverName: form.receiverName,
    address: fullAddress,
    receiverPhone: fullPhone,
    email: fullEmail,
    deliveryMessage: finalMessage
  };
};

// [Logic] 유효성 검사
const validate = () => {
  if (!form.receiverName.trim()) { alert('받는사람 이름을 입력해주세요.'); return false; }
  if (!form.zipcode.trim() || !form.basicAddress.trim()) { alert('주소를 입력해주세요.'); return false; }
  if (!form.phone2.trim() || !form.phone3.trim()) { alert('휴대전화 번호를 입력해주세요.'); return false; }
  if (!form.emailId.trim() || !form.emailDomain.trim()) { alert('이메일을 입력해주세요.'); return false; }
  return true;
};

// 외부 사용 함수 노출
defineExpose({
  getOrderDeliveryDto
});
</script>

<style scoped>
.shipping-form-container {
  max-width: 800px;
  margin: 0 auto;
  font-family: 'Pretendard', sans-serif;
}

.form-header h2 {
  text-align: center;
  font-size: 14px;
  margin-bottom: 20px;
}

.form-box {
  border: 1px solid #ddd;
  padding: 0;
  background: #fff;
}

.section-title {
  padding: 15px 20px;
  border-bottom: 1px solid #eee;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.section-title h3 {
  font-size: 13px;
  font-weight: bold;
  margin: 0;
}

.tabs {
  display: flex;
  border-bottom: 1px solid #eee;
}

.tab {
  flex: 1;
  text-align: center;
  padding: 12px 0;
  font-size: 13px;
  cursor: pointer;
  background: #f9f9f9;
  color: #999;
}

.tab.active {
  background: #fff;
  color: #000;
  font-weight: bold;
  border-bottom: 1px solid #fff; /* 하단 보더 가림 */
}

.shipping-options {
  padding: 20px;
  display: flex;
  gap: 20px;
  border-bottom: 1px solid #f5f5f5;
}

.radio-label {
  display: flex;
  align-items: center;
  font-size: 13px;
  cursor: pointer;
}

.radio-label input {
  margin-right: 5px;
}

.input-group {
  display: flex;
  align-items: flex-start; /* 상단 정렬 */
  padding: 15px 20px;
  border-bottom: 1px solid #f5f5f5;
}

.input-group label {
  width: 120px;
  font-size: 13px;
  font-weight: bold;
  padding-top: 10px; /* 인풋과 높이 맞춤 */
}

.required {
  color: #0078ff; /* 파란색 별 */
}

.input-group input[type="text"],
.input-group select,
.input-group textarea {
  border: 1px solid #ddd;
  padding: 8px 10px;
  font-size: 13px;
  height: 36px;
  box-sizing: border-box;
}

.full-width {
  flex: 1;
}

/* 주소 인풋 그룹 */
.address-inputs {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.zipcode-row {
  display: flex;
  gap: 5px;
}

.zipcode-row input {
  width: 150px;
}

.btn-search {
  background: #fff;
  border: 1px solid #ddd;
  padding: 0 15px;
  font-size: 12px;
  cursor: pointer;
}

/* 전화번호 인풋 그룹 */
.phone-inputs {
  flex: 1;
  display: flex;
  align-items: center;
  gap: 5px;
}

.phone-inputs select {
  width: 150px;
}

.phone-inputs input {
  flex: 1;
}

.dash {
  color: #ddd;
}

/* 이메일 인풋 그룹 */
.email-inputs {
  flex: 1;
  display: flex;
  align-items: center;
  gap: 5px;
}

.email-inputs input {
  flex: 1;
}

.email-inputs select {
  width: 150px;
}

.at {
  color: #666;
}

.message-select {
  width: 100%;
}

.mt-2 {
  margin-top: 8px;
  height: 80px !important;
  resize: none;
}

.save-checkbox {
  padding: 15px 20px;
  font-size: 12px;
  color: #666;
  display: flex;
  align-items: center;
}

.save-checkbox input {
  margin-right: 5px;
}
</style>
