<template>
  <div class="shipping-form-container">
    <div class="form-header">
      <h2>주문서 작성</h2>
    </div>

    <!-- [SECTION 1] 배송지 정보 입력 (User's Exact Div Layout) -->
    <div class="form-box">
      <!-- 섹션 타이틀 -->
      <div class="section-title">
        <h3>배송지</h3>
        <span class="toggle-icon">^</span>
      </div>

      <!-- 탭 -->
      <div class="tabs">
        <div class="tab inactive">최근 배송지</div>
        <div class="tab active">직접입력</div>
      </div>

      <!-- 옵션 선택 -->
      <div class="shipping-options">
        <label class="radio-label">
          <input type="radio" value="same" v-model="shippingOption" @change="handleOptionChange" />
          <span class="custom-radio"></span>
          회원 정보와 동일
        </label>
        <label class="radio-label">
          <input type="radio" value="new" v-model="shippingOption" @change="handleOptionChange" />
          <span class="custom-radio"></span>
          새로운 배송지
        </label>
      </div>

      <!-- 입력 폼 -->
      <div class="form-table">
        <div class="input-row">
          <div class="label-cell">
            <span class="required">*</span>받는사람
          </div>
          <div class="input-cell px-2">
            <input type="text" v-model="form.receiverName" class="full-width square-input" />
          </div>
        </div>

        <div class="input-row address-row">
          <div class="label-cell">
            <span class="required">*</span>주소
          </div>
          <div class="input-cell column-cell px-2">
            <div class="flex-row mb-2">
              <input type="text" v-model="form.zipcode" placeholder="우편번호" class="square-input width-150" readonly />
              <button type="button" @click="handleAddressSearch" class="btn-search square-btn">주소검색</button>
            </div>
            <input type="text" v-model="form.basicAddress" placeholder="기본주소" class="full-width square-input mb-2" />
            <input type="text" v-model="form.detailAddress" placeholder="나머지 주소" class="full-width square-input" />
          </div>
        </div>

        <div class="input-row">
          <div class="label-cell">
            <span class="required">*</span>일반전화
          </div>
          <div class="input-cell flex-row px-2">
            <select v-model="form.tel1" class="square-input width-80">
              <option value="02">02</option>
              <option value="031">031</option>
              <option value="032">032</option>
              <option value="033">033</option>
              <option value="041">041</option>
              <option value="042">042</option>
              <option value="043">043</option>
              <option value="044">044</option>
              <option value="051">051</option>
              <option value="052">052</option>
              <option value="053">053</option>
              <option value="054">054</option>
              <option value="055">055</option>
              <option value="061">061</option>
              <option value="062">062</option>
              <option value="063">063</option>
              <option value="064">064</option>
              <option value="0502">0502</option>
              <option value="0503">0503</option>
              <option value="0504">0504</option>
              <option value="0505">0505</option>
              <option value="0506">0506</option>
              <option value="0507">0507</option>
              <option value="070">070</option>
              <option value="010">010</option>
              <option value="011">011</option>
              <option value="016">016</option>
              <option value="017">017</option>
              <option value="018">018</option>
              <option value="019">019</option>
              <option value="0508">0508</option>
            </select>
            <span class="dash">-</span>
            <input type="text" v-model="form.tel2" class="square-input flex-1" maxlength="4" />
            <span class="dash">-</span>
            <input type="text" v-model="form.tel3" class="square-input flex-1" maxlength="4" />
          </div>
        </div>

        <div class="input-row">
          <div class="label-cell">
            <span class="required">*</span>휴대전화
          </div>
          <div class="input-cell flex-row px-2">
            <select v-model="form.phone1" class="square-input width-80">
              <option value="010">010</option>
              <option value="011">011</option>
              <option value="016">016</option>
              <option value="017">017</option>
              <option value="018">018</option>
              <option value="019">019</option>
            </select>
            <span class="dash">-</span>
            <input type="text" v-model="form.phone2" class="square-input flex-1" maxlength="4" />
            <span class="dash">-</span>
            <input type="text" v-model="form.phone3" class="square-input flex-1" maxlength="4" />
          </div>
        </div>

        <div class="input-row">
          <div class="label-cell">
            <span class="required">*</span>이메일
          </div>
          <div class="input-cell email-cell px-2">
            <input type="text" v-model="form.emailId" class="square-input" />
            <span class="at">@</span>
            <template v-if="selectedEmailDomain === 'manual'">
               <input type="text" v-model="form.emailDomain" class="square-input" placeholder="직접입력" />
            </template>
            <select @change="handleEmailDomainChange" v-model="selectedEmailDomain" class="square-input">
              <option value="manual">직접입력</option>
              <option value="naver.com">naver.com</option>
              <option value="daum.net">daum.net</option>
              <option value="nate.com">nate.com</option>
              <option value="hotmail.com">hotmail.com</option>
              <option value="yahoo.com">yahoo.com</option>
              <option value="empas.com">empas.com</option>
              <option value="korea.com">korea.com</option>
              <option value="dreamwiz.com">dreamwiz.com</option>
              <option value="gmail.com">gmail.com</option>
            </select>
          </div>
        </div>
        
        <div class="input-row no-border">
          <div class="input-cell full" style="padding: 15px 20px;">
             <select v-model="form.deliveryMessage" class="full-width square-input message-select">
              <option value="">-- 메시지 선택 (선택사항) --</option>
              <option value="배송 전 연락주세요">배송 전 연락주세요</option>
              <option value="부재 시 경비실에 맡겨주세요">부재 시 경비실에 맡겨주세요</option>
              <option value="부재 시 문 앞에 놓아주세요">부재 시 문 앞에 놓아주세요</option>
              <option value="빠른 배송 부탁드립니다.">빠른 배송 부탁드립니다.</option>
              <option value="택배함에 보관해 주세요.">택배함에 보관해 주세요.</option>
              <option value="direct">직접 입력</option>
            </select>
            <textarea
              v-if="form.deliveryMessage === 'direct'"
              v-model="form.customMessage"
              placeholder="배송 메시지를 입력해주세요."
              class="full-width square-input mt-2"
            ></textarea>
          </div>
        </div>
      </div>

      <div class="save-checkbox" style="margin-top: 0px; padding-bottom: 20px; padding-left: 20px;">
        <label class="checkbox-label" style="display: flex; align-items: center; cursor: pointer;">
          <input type="checkbox" class="custom-checkbox-input" />
          <span class="custom-checkbox-box"></span>
          <span style="font-size: 11px; color: #353535; font-weight: 400;">기본 배송지로 저장</span>
        </label>
      </div>
    </div>
    
    <!-- [SECTION 2] 주문 상품 리스트 (Product List added as requested) -->
    <div class="product-list-container">
        <div class="form-box">
            <!-- Section Title -->
            <div class="section-title">
                <h3>주문상품</h3>
                <span class="toggle-icon">^</span>
            </div>

            <!-- Product List -->
            <div class="product-list">
                <div v-if="products.length === 0" class="empty-list">
                    주문할 상품이 없습니다.
                </div>
                <div v-else v-for="(item, index) in products" :key="index" class="product-item">
                    <!-- Image -->
                    <div class="p-image">
                        <img :src="item.image" :alt="item.name" />
                    </div>
                    <!-- Info -->
                    <div class="p-info">
                        <p class="p-name">{{ item.name }}</p>
                        <p class="p-option">[옵션: {{ item.option }}]</p>
                        <p class="p-qty">수량: {{ item.quantity }}개</p>
                        <p class="p-price">KRW {{ item.price.toLocaleString() }}</p>
                    </div>
                </div>
            </div>

            <!-- Shipping Fee -->
            <div class="shipping-fee-area">
                <span class="label">배송비</span>
                <span class="price">KRW {{ shippingFee.toLocaleString() }} ({{ shippingFee === 0 ? '무료' : '유료' }})</span>
            </div>
        </div>
    </div>

    <!-- [SECTION 3] 할인/부가결제 (Discount & Payment) -->
    <div class="discount-section-container">
        <div class="form-box">
             <!-- Section Title -->
            <div class="section-title no-border-bottom">
                <h3>할인/부가결제</h3>
                <span class="toggle-icon">^</span>
            </div>

            <!-- 1. Discount Code (Horizontal) -->
            <div class="horizontal-row">
                <div class="row-label fixed-label">할인코드 적용</div>
                <div class="input-group">
                    <input type="text" v-model="discountCode" class="square-input flex-1" />
                    <button class="square-btn width-100">코드 적용</button>
                </div>
            </div>

            <!-- 2. Coupon (Horizontal Layout) -->
            <div class="horizontal-row top-align">
                <div class="row-label fixed-label">쿠폰 할인</div>
                <div class="flex-column flex-1 align-end">
                     <div class="flex-row items-center gap-10">
                         <span class="price-text font-bold margin-right-10">KRW 0</span>
                         <button class="square-btn width-100">쿠폰 적용</button>
                     </div>
                     <div class="sub-text mt-2 text-blue">보유쿠폰 {{ couponCount }}개</div>
                </div>
            </div>

            <!-- 3. Mileage (Horizontal) -->
            <div class="horizontal-row top-align">
                 <div class="row-label fixed-label">마일리지</div>
                 <div class="flex-1 flex-column full-width">
                     <div class="input-group">
                         <input type="text" v-model="usedMileage" class="square-input flex-1" placeholder="" />
                         <button class="square-btn width-100">전액 사용</button>
                     </div>
                     <div class="sub-text text-right mt-2 text-blue">보유 잔액 {{ mileageBalance.toLocaleString() }}원</div>
                     <div class="info-box">
                         1회 구매시 마일리지 최대 사용금액은 5,000원입니다.
                         <span class="toggle-arrow">∨</span>
                     </div>
                 </div>
            </div>

            <!-- 4. Deposit (Horizontal) -->
             <div class="horizontal-row top-align">
                 <div class="row-label fixed-label">예치금</div>
                 <div class="flex-1 flex-column full-width">
                     <div class="input-group">
                         <input type="text" v-model="usedDeposit" class="square-input flex-1" placeholder="" />
                         <button class="square-btn width-100">전액 사용</button>
                     </div>
                     <div class="sub-text text-right mt-2 text-blue">보유 잔액 {{ depositBalance.toLocaleString() }}KRW</div>
                     <div class="info-box">
                         예치금은 사용제한 없이 언제든 결제가 가능합니다.
                         <span class="toggle-arrow">∨</span>
                     </div>
                 </div>
            </div>

     <!-- 4. 적용금액 -->
            <div class="total-discount-wrapped">
                <div class="row-label">적용금액</div>
                <span class="price-text font-bold text-blue" style="font-size: 11px;">-KRW {{ totalDiscount.toLocaleString() }}</span>
            </div>

            <!-- [섹션 4] 결제정보 -->
            <!-- 섹션 타이틀 -->
            <div class="section-title no-border-bottom" style="border-top: 1px solid #ddd; margin-top: 0;">
                <h3>결제정보</h3>
                <span class="toggle-icon">^</span>
            </div>

            <!-- 결제 요약 정보 -->
            <div class="payment-summary-rows" style="padding: 0 20px 20px 20px;">
                <div class="summary-row" style="display: flex; justify-content: space-between; margin-bottom: 15px; font-size: 11px;">
                    <span style="color: #333;">주문상품</span>
                    <span class="font-bold">KRW {{ totalProductPrice.toLocaleString() }}</span>
                </div>
                <div class="summary-row" style="display: flex; justify-content: space-between; margin-bottom: 15px; font-size: 11px;">
                    <span style="color: #333;">배송비</span>
                    <span class="font-bold">+KRW {{ shippingFee.toLocaleString() }}</span>
                </div>
                <div class="summary-row" style="display: flex; justify-content: space-between; margin-bottom: 30px; font-size: 11px;">
                    <span style="color: #333;">할인/부가결제</span>
                    <span class="text-red font-bold" style="color: #d9534f;">-KRW {{ totalDiscount.toLocaleString() }}</span>
                </div>
                 <div class="summary-row final-row" style="display: flex; justify-content: space-between; padding-top: 20px; border-top: 1px solid #eee; font-size: 12px; font-weight: 700;">
                    <span>최종 결제 금액</span>
                    <span style="font-size: 13px;">KRW {{ finalPaymentAmount.toLocaleString() }}</span>
                </div>
            </div>

            <!-- [섹션 5] 결제수단 -->
            <!-- 섹션 타이틀 -->
            <div class="section-title no-border-bottom" style="border-top: 1px solid #ddd; margin-top: 0;">
                <h3>결제수단</h3>
                <span class="toggle-icon">^</span>
            </div>

            <div class="payment-methods-list" style="padding: 0 20px 20px 20px;">
                <div class="payment-method-header" style="padding: 5px 0; font-size: 11px; font-weight: bold; margin-bottom: 25px;">
                    결제수단 선택
                </div>
                <!-- Table Wrapper -->
                <div class="payment-table-wrapper" style="border: 1px solid #eee;">
                    <!-- Radio List -->
                    <div class="radio-list">
                        <label class="payment-radio-item" :class="{ 'selected': paymentMethod === 'card' }">
                            <input type="radio" value="card" v-model="paymentMethod" name="payment_method">
                            <span>카드 (CARD)</span>
                        </label>
                        <label class="payment-radio-item" :class="{ 'selected': paymentMethod === 'escrow' }">
                            <input type="radio" value="escrow" v-model="paymentMethod" name="payment_method">
                            <span>에스크로(실시간 계좌이체)</span>
                        </label>
                        <label class="payment-radio-item" :class="{ 'selected': paymentMethod === 'transfer' }">
                            <input type="radio" value="transfer" v-model="paymentMethod" name="payment_method">
                            <span>무통장 입금 (transfer)</span>
                        </label>
                        <label class="payment-radio-item" :class="{ 'selected': paymentMethod === 'payco' }">
                            <input type="radio" value="payco" v-model="paymentMethod" name="payment_method">
                            <span>페이코(간편결제)</span>
                        </label>
                        <label class="payment-radio-item" :class="{ 'selected': paymentMethod === 'samsung' }">
                            <input type="radio" value="samsung" v-model="paymentMethod" name="payment_method">
                            <span>삼성페이</span>
                        </label>
                        <label class="payment-radio-item" :class="{ 'selected': paymentMethod === 'kakaopay' }">
                            <input type="radio" value="kakaopay" v-model="paymentMethod" name="payment_method">
                            <span>카카오페이(간편결제)</span>
                        </label>
                        <label class="payment-radio-item" :class="{ 'selected': paymentMethod === 'toss' }">
                            <input type="radio" value="toss" v-model="paymentMethod" name="payment_method">
                            <span>토스</span>
                        </label>
                    </div>
                </div>

                <!-- Payment Method Help Text -->
                <div class="payment-help-box" v-if="currentHelpText.length > 0">
                    <ul class="help-list">
                        <li v-for="(text, index) in currentHelpText" :key="index" v-html="text"></li>
                    </ul>
            </div> <!-- Close payment-help-box -->

            <!-- Save Payment Info Checkbox -->
            <div class="save-payment-info" style="margin-top: 20px; padding: 0;">
                <label class="checkbox-label" style="display: flex; align-items: center; cursor: pointer;">
                    <input type="checkbox" v-model="savePaymentInfo" class="custom-checkbox-input">
                    <span class="custom-checkbox-box"></span>
                    <span style="font-size: 11px; color: #353535; font-weight: 400;">결제수단과 입력정보를 다음에도 사용</span>
                </label>
            </div>

            <!-- Reward Benefits Section -->
            <div class="reward-section" style="border-top: 1px solid #eee; padding: 20px 20px 20px 5px; display: flex; justify-content: space-between; align-items: center; margin-top: 20px;">
                <span style="font-size: 11px; font-weight: bold; color: #333;">적립 혜택</span>
                <div style="display: flex; align-items: center;">
                    <span style="font-size: 12px; font-weight: bold; margin-right: 5px;">{{ rewardPoints.toLocaleString() }}원</span>
                    <span style="font-size: 10px; color: #999;">▼</span>
                </div>
            </div>

            <!-- Payment Button -->
            <div class="payment-button-area" style="margin: 0 -20px 20px -20px; width: calc(100% + 40px);">
                <button type="button" @click="handleSubmit" style="width: 100%; background: #000; color: #fff; border: none; padding: 15px 0; font-size: 11px; cursor: pointer;">
                    KRW {{ finalPaymentAmount.toLocaleString() }} 결제하기
                </button>
            </div>

            <!-- Notice Message -->
            <div class="payment-notice" style="padding: 0 20px 20px 5px; color: #555; font-size: 11px; line-height: 1.5;">
                <div style="margin-bottom: 10px;">
                    - 무이자할부가 적용되지 않은 상품과 무이자할부가 가능한 상품을 동시에 구매할 경우 전체 주문 상품 금액에 대해 무이자할부가 적용되지 않습니다. 무이자할부를 원하시는 경우 장바구니에서 무이자할부 상품만 선택하여 주문하여 주시기 바랍니다.
                </div>
                <div>
                    - 최소 결제 가능 금액은 결제금액에서 배송비를 제외한 금액입니다.
                </div>
            </div>
        </div>
    </div>
</div>

  </div>
</template>

<script setup>
import { reactive, ref, onMounted, computed } from 'vue';
import { useRoute } from 'vue-router';
import http from '../../api/http';

const route = useRoute();

/* [1. Shipping Info Logic] */
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

const shippingOption = ref('new');
const selectedEmailDomain = ref('manual');
const userInfo = ref(null);

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

const handleAddressSearch = () => {
    alert("주소 검색 기능은 현재 구현 중입니다.");
};

const handleOptionChange = async () => {
  if (shippingOption.value === 'same') {
    if (!userInfo.value) await fetchUserInfo();
    if (userInfo.value) {
      const u = userInfo.value;
      form.receiverName = u.name || '';
      let addr = u.address || '';
      const zipMatch = addr.match(/^\(?(\d{5})\)?\s*(.*)$/);
      if (zipMatch) {
        form.zipcode = zipMatch[1];
        form.basicAddress = zipMatch[2]; 
        form.detailAddress = ''; 
      } else {
        form.zipcode = '';
        form.basicAddress = addr;
        form.detailAddress = '';
      }
      if (u.phone) {
         if(u.phone.length > 9) {
             form.phone2 = u.phone.substring(3,7);
             form.phone3 = u.phone.substring(7);
         }
      }
      if (u.email) {
        const emails = u.email.split('@');
        if(emails.length === 2) {
             form.emailId = emails[0];
             form.emailDomain = emails[1];
        }
      }
    }
  } else {
    resetForm();
  }
};

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

const handleEmailDomainChange = () => {
  if (selectedEmailDomain.value !== 'manual') {
    form.emailDomain = selectedEmailDomain.value;
  } else {
    form.emailDomain = ''; 
  }
};

const getOrderDeliveryDto = () => {
  // Zipcode is kept for display input only; actual address = basicAddress + detailAddress
  const fullAddress = `${form.basicAddress} ${form.detailAddress}`.trim();
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

defineExpose({
  getOrderDeliveryDto
});

/* [2. Product List Data (Mock)] */
/* [2. Product List Data (API Fetch)] */
const products = ref([]);
const shippingFee = ref(0);

const fetchCartItems = async () => {
    try {
        const response = await http.get('/cart/my');
        if (response.data && response.data.data) {
            let cartItems = response.data.data;
            
            // query parameter로 전달된 cartItemIds가 있으면 해당 상품들만 필터링
            const queryIds = route.query.cartItemIds;
            if (queryIds) {
                const selectedIds = queryIds.split(',').map(id => parseInt(id));
                cartItems = cartItems.filter(item => 
                    selectedIds.includes(item.cartItemId || item.id)
                );
            }
            
            products.value = cartItems.map(item => ({
                cartItemId: item.cartItemId || item.id,
                productId: item.productId, 
                name: item.productName || item.name || '상품명 없음',
                option: item.optionName || item.option || '옵션 없음',
                quantity: item.quantity || item.qty || 1,
                price: item.price || item.productPrice || 0,
                image: item.imageUrl || item.image || item.thumbnail || 'https://via.placeholder.com/90x100?text=No+Image' 
            }));
            
            // 배송비 계산 (예: 5만원 이상 무료)
            const total = products.value.reduce((sum, p) => sum + (p.price * p.quantity), 0);
            shippingFee.value = total >= 50000 ? 0 : 3000;
        }
    } catch (error) {
        console.error("장바구니 조회 실패:", error);
    }
};

/* [3. Discount & Payment Logic] */
const discountCode = ref('');
const couponCount = ref(0); // 기본값 0
const mileageBalance = ref(0); // 기본값 0
const depositBalance = ref(0); // 기본값 0
const usedMileage = ref(''); // [수정] 기본값 0 -> 공란
const usedDeposit = ref(''); // 기본값 빈 문자열
const paymentMethod = ref('card'); // 기본값 카드
const savePaymentInfo = ref(true); // Save Payment Info Checkbox (Default: true)

/* Helper Text Logic */
const helpTextMap = {
    card: [
        "최소 결제 가능 금액은 총 결제금액에서 배송비를 제외한 금액입니다.",
        "소액 결제의 경우 PG사 정책에 따라 결제 금액 제한이 있을 수 있습니다."
    ],
    escrow: [
        "최소 결제 가능 금액은 총 결제금액에서 배송비를 제외한 금액입니다.",
        "소액 결제의 경우 PG사 정책에 따라 결제 금액 제한이 있을 수 있습니다."
    ],
    transfer: [
        "최소 결제 가능 금액은 총 결제금액에서 배송비를 제외한 금액입니다.",
        "소액 결제의 경우 PG사 정책에 따라 결제 금액 제한이 있을 수 있습니다."
    ],
    samsung: [
        "최소 결제 가능 금액은 총 결제금액에서 배송비를 제외한 금액입니다.",
        "소액 결제의 경우 PG사 정책에 따라 결제 금액 제한이 있을 수 있습니다."
    ],
    payco: [
        "페이코(<a href='//www.payco.com' target='_blank'>www.payco.com</a>)에 회원가입 후, 최초 1회 카드 및 계좌 정보를 등록하셔야 사용 가능합니다."
    ],
    kakaopay: [
        "카카오톡 앱을 설치한 후, 최초 1회 카드정보를 등록하셔야 사용 가능합니다.",
        "인터넷 익스플로러는 8 이상에서만 결제 가능합니다.",
        "카카오머니로 결제 시, 현금영수증 발급은 ㈜카카오페이에서 발급가능합니다."
    ],
    toss: [
        "토스는 간편하게 지문 또는 비밀번호만으로 결제할 수 있는 빠르고 편리한 간편 결제 서비스입니다.",
        "토스 결제 후 취소/반품 등이 발생할 경우 토스를 통한 신용카드 취소/토스머니 환불이 이루어집니다.",
        "토스 이용가능 결제수단 : 국내 발행 신용/체크카드, 토스머니"
    ]
};

const currentHelpText = computed(() => {
    return helpTextMap[paymentMethod.value] || [];
});

const totalDiscount = computed(() => {
    const miles = Number(usedMileage.value) || 0;
    const deposit = Number(usedDeposit.value) || 0;
    return miles + deposit;
});

const totalProductPrice = computed(() => {
    return products.value.reduce((sum, p) => sum + (p.price * p.quantity), 0);
});

const finalPaymentAmount = computed(() => {
  return totalProductPrice.value + shippingFee.value - totalDiscount.value;
});

// Reward Points (1% of final payment amount)
const rewardPoints = computed(() => {
    return Math.floor(finalPaymentAmount.value * 0.01);
});

const handleSubmit = async () => {
    // 1. Validation
    if (!form.receiverName || !form.phone2 || !form.phone3 || !form.detailAddress) {
        alert("배송지 정보를 모두 입력해주세요.");
        return;
    }

    // 2. 결제 확인 대화상자
    const formattedAmount = finalPaymentAmount.value.toLocaleString();
    const confirmMessage = `총 결제 금액: KRW ${formattedAmount}\n\n결제하시겠습니까?`;
    if (!confirm(confirmMessage)) {
        return; // 사용자가 '아니오'를 선택한 경우
    }

    // 2. Prepare Data
    const deliveryDto = getOrderDeliveryDto();
    
    // Create Payload for /api/v1/orders/cart
    // Backend expects OrderCartRequestDto which extends OrderDeliveryDto
    const orderRequest = {
        receiverName: deliveryDto.receiverName,
        receiverPhone: deliveryDto.receiverPhone,
        address: deliveryDto.address,
        deliveryMessage: deliveryDto.deliveryMessage,
        email: deliveryDto.email, // Backend DTO expects 'email', not 'orderEmail'
        cartItemIds: products.value.map(p => p.cartItemId), // Required: List of cartItemIds
        // Payment Info is currently not handled by backend OrderService, 
        // but we send the request to create the order.
    };

    // 3. API Call
    try {
        // Changed endpoint to /api/v1/orders/cart (POST)
        const response = await http.post('/api/v1/orders/cart', orderRequest);
        if (response.status === 200 || response.status === 201) {
            alert("주문이 정상적으로 접수되었습니다. (주문번호: " + response.data.data + ")");
            // router.push('/order/complete'); 
        }
    } catch (error) {
        console.error("주문 생성 실패:", error);
        alert("주문 처리 중 오류가 발생했습니다. 다시 시도해주세요.");
    }
};

onMounted(() => {
  fetchUserInfo();
  fetchCartItems();
});
</script>

<style scoped>
/* [User Provided Specific CSS] */
/* [Global Box Sizing] */
* {
  box-sizing: border-box;
}

.shipping-form-container {
  max-width: 1000px; 
  margin: 0 auto;
  font-family: 'Pretendard', sans-serif;
  padding-right: 20px; /* 우측 여백 확보 */
}

/* ... existing styles ... */

.px-2 { padding-left: 10px; padding-right: 10px; }
.py-3 { padding-top: 15px; padding-bottom: 15px; }
.mb-2 { margin-bottom: 12px; } /* 간격 확대 (8px -> 12px) */


.form-header h2 {
  text-align: center;
  font-size: 13px; /* 14 -> 13 */
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
  margin-bottom: 15px;
  margin-top: 10px;
}

.section-title h3 {
  font-size: 12px; /* 13 -> 12 */
  font-weight: bold;
  margin: 0;
}

.section-title.no-border-bottom {
    border-bottom: none;
}

/* Toggle Icon Style (Not in provided CSS but used in template) */
.toggle-icon {
    font-size: 10px;
    cursor: pointer;
    color: #999;
}

.tabs {
  display: flex;
  border-bottom: 1px solid #eee;
}

.tab {
  flex: 1;
  text-align: center;
  padding: 12px 0;
  font-size: 12px; /* 13 -> 12 */
  cursor: pointer;
  background: #f9f9f9;
  color: #999;
  border-right: 1px solid #eee;
}
.tab:last-child {
  border-right: none;
}

.tab.active {
  background: #fff;
  color: #000;
  font-weight: bold;
  border-bottom: 1px solid #fff; 
  margin-bottom: -1px; /* 선 가림 */
}

.shipping-options {
  padding: 20px;
  display: flex;
  gap: 20px;
  border-bottom: 1px solid #eee;
}

/* [Custom Radio Button] */
.radio-label {
  display: flex;
  align-items: center;
  font-size: 12px;
  cursor: pointer;
  font-weight: 700;
  position: relative;
  /* padding-left: 25px; input hide 후 custom으로 대체 */
}

.radio-label input {
  display: none; /* 기본 라디오 숨김 */
}

.custom-radio {
  width: 18px;
  height: 18px;
  border: 1px solid #ddd;
  border-radius: 50%;
  margin-right: 8px;
  position: relative;
  display: inline-block;
  background-color: #f9f9f9; /* 기본 회색 배경 */
}

/* Checked State for Radio */
.radio-label input:checked + .custom-radio {
  border: 5px solid #000; /* 검은색 테두리 두껍게 */
  background-color: #fff; /* 내부는 파이 모양처럼 보이게? or just circle */
  border: 1px solid #000; /* 외곽선 */
}

.radio-label input:checked + .custom-radio::after {
  content: '';
  width: 10px;
  height: 10px;
  background: #000;
  border-radius: 50%;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

/* [Custom Checkbox] */
.save-checkbox {
  padding: 15px 20px;
  font-size: 11px;
  color: #666;
  display: flex;
  align-items: center;
  /* border-top: 1px solid #eee;  [수정] 선 제거 (이메일 밑으로 이동) */
}

.checkbox-label {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.checkbox-label input {
  display: none; /* 기본 체크박스 숨김 */
}

.custom-checkbox {
  width: 16px;
  height: 16px;
  border: 1px solid #ddd;
  margin-right: 8px;
  display: inline-block;
  position: relative;
  background-color: #fff;
  border-radius: 0; /* Square styling */
}

/* Checked State for Checkbox */
.checkbox-label input:checked + .custom-checkbox {
  border-color: #000;
  background-color: #000; /* 검은색 배경 */
}

.checkbox-label input:checked + .custom-checkbox::after {
  content: '✔'; /* 심플 체크 문자 or CSS draw */
  color: #fff;
  font-size: 11px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}


/* [Form Table Layout] */
.form-table {
  width: 100%;
}

.input-row {
  display: flex;
  /* border-bottom: 1px solid #eee;  [수정] 선 제거 */
  min-height: 60px; /* [수정] 행 높이 넉넉하게 (52px -> 60px) */
  align-items: center; /* 수직 중앙 정렬 확실하게 */
}
.input-row.no-border {
  border-bottom: none;
}
.input-row.address-row {
  height: auto;
  min-height: 160px; 
  align-items: flex-start; /* 주소는 상단 정렬 */
  padding-top: 10px;
}

/* [Label Cell] */
.label-cell {
  width: 140px; /* 고정 너비 */
  background: #fff;
  font-size: 12px; /* 13 -> 12 */
  font-weight: 700;
  color: #333;
  display: flex;
  align-items: center;
  padding-left: 20px;
  position: relative;
  /* border-bottom 제거로 인해 높이 맞춤 필요 없음 */
}

.required {
  color: #0078ff;
  font-size: 13px; /* 14 -> 13 */
  margin-right: 2px;
  position: relative;
  top: 2px; /* 위치 미세 조정 */
}

/* [Input Cell] */
.input-cell {
  flex: 1;
  display: flex;
  align-items: center;
  padding: 5px 0; /* 패딩 조정 */
}
.input-cell.flex-row {
  display: flex;
  gap: 5px;
  align-items: center;
}
/* [Generic Flex Row] */
.flex-row {
  display: flex;
  gap: 5px;
  align-items: center;
}

.input-cell.column-cell {
  flex-direction: column;
  align-items: flex-start;
  justify-content: center;
}
.input-cell.email-cell {
  gap: 5px;
}
.input-cell.full {
    width: 100%;
    flex-direction: column;
    align-items: stretch;
}

/* [Square Styles] */
.square-input, .square-btn, .message-select {
  border-radius: 0 !important;
  border: 1px solid #ddd;
  height: 45px; /* [수정] 32px -> 45px (상하 확대) */
  padding: 0 10px;
  font-size: 12px; /* 13 -> 12 */
  outline: none;
  font-family: 'Pretendard', sans-serif;
  box-sizing: border-box; /* 확실하게 지정 */
  vertical-align: middle; /* flex 안 쓸 경우 대비 */
}

.square-input:focus, .message-select:focus, textarea.square-input:focus {
  border-color: #333; /* [포인트] 포커스 시 검은색/진회색 */
}

.square-btn {
  background: #fff;
  border: 1px solid #333;
  color: #333;
  cursor: pointer;
  height: 45px; /* [수정] 32px -> 45px */
  line-height: 43px; /* height(45) - border(2) = 43px */
  padding: 0 15px;
  font-weight: 500;
  font-size: 12px; /* 13 -> 12 */
  box-sizing: border-box;
  display: inline-flex; /* 텍스트 수직 정렬 용이 */
  align-items: center;
  justify-content: center;
}

/* [Alignments & Spacing] */
.full-width { width: 100%; }
.width-150 { width: 150px; }
.width-80 { width: 110px; } /* [수정] 80px -> 110px (전액 사용 글자 잘림 방지) */
.flex-1 { flex: 1; }

.dash, .at {
  color: #999;
  margin: 0 5px;
  font-size: 11px; /* 12 -> 11 */
}

.mt-2 {
  margin-top: 8px;
  height: 80px !important;
  resize: none;
}

/* --- [Product List Custom Styles] --- */
.product-list-container {
    margin-top: -1px; /* [수정] 위 섹션과 연결되도록 겹침 처리 */
}

/* [New] Override section-title border for product list */
.product-list-container .section-title {
    border-bottom: none;
}
/* Re-use form-box, section-title styles from above */

.product-list {
    padding: 0 20px;
}
.empty-list {
    padding: 40px 0;
    text-align: center;
    color: #999;
    font-size: 12px;
    border-bottom: 1px dotted #e8e8e8;
}
.product-item {
    display: flex;
    padding: 20px 0;
    border-bottom: 1px dotted #e8e8e8;
    align-items: flex-start;
}
.product-item:last-child {
    border-bottom: 0;
}
.p-image { width: 90px; margin-right: 20px; flex-shrink: 0; }
.p-image img { width: 100%; vertical-align: middle; border: 1px solid #e8e8e8; }
.p-info { flex: 1; display: flex; flex-direction: column; justify-content: center; padding-top: 5px; }
/* [수정] 상품명 폰트/스타일: 선 추가 및 폰트 조정 */
.p-name { 
    font-size: 12px; 
    color: #353535; 
    margin: 0 0 10px; /* 간격 줄임 */
    line-height: 1.4; 
    padding-bottom: 10px; /* 선과의 간격 */
    border-bottom: 1px solid #e8e8e8; /* [포인트] 상품명 밑에 선 추가 */
    width: 100%; /* 전체 너비 */
}

.p-option { color: #707070; margin: 0 0 10px; font-size: 11px; } /* 폰트 사이즈/간격 미세조정 */
.p-qty { color: #353535; margin: 0 0 10px; font-size: 11px; }
.p-price { font-weight: 700; color: #000; margin: 0; font-size: 12px; }

.shipping-fee-area {
    background: #fbfbfb;
    padding: 15px 20px;
    border-top: 1px solid #e8e8e8;
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 12px;
}
/* --- [Discount Section Styles] --- */
.discount-section-container {
    margin-top: -1px; /* 메인 박스와 연결 */
}

/* Override section title border for discount section to merge perfectly if needed */
.discount-section-container .section-box {
    border-top: none;
}

/* New style for payment method section title */
.payment-method-section-container .section-title {
    margin-top: 0 !important;
}

/* Payment Help Box */
.payment-help-box {
    margin-top: 20px;
    padding: 20px;
    border: 1px solid #e8e8e8;
    background-color: #fbfbfb; /* Matching the reference gray */
    color: #707070;
    font-size: 11px;
}

.help-list {
    list-style: none;
    padding: 0;
    margin: 0;
}

.help-list li {
    padding-left: 10px;
    position: relative;
    line-height: 1.8;
}

.help-list li::before {
    content: "-";
    position: absolute;
    left: 0;
    top: 0;
}

.justify-between {
    justify-content: space-between;
}
.text-right {
    text-align: right;
}
.mt-1 {
    margin-top: 5px;
}
.sub-text {
    font-size: 11px;
    color: #0078ff; /* 보유 수량 파란색 (참고 이미지 느낌) or #666 */
}
/* Revert blue for now, match image usually typical gray or blue link color. 
   User image shows blue for coupon count, blue for mileage balance. */
.sub-text {
    color: #4c80f1; 
    height: auto !important; /* [수정] 강제 높이 초기화 */
    line-height: normal; /* 줄간격 초기화 */
    padding: 0; /* 패딩 초기화 */
    margin-bottom: 20px;
}

.price-text {
    font-weight: 700;
    font-size: 12px;
}

.info-box {
    margin-top: 0 !important;
    background: #f9f9f9;
    padding: 15px 12px;
    font-size: 11px;
    color: #666;
    width: calc(100% + 90px); /* [수정] margin-left(-90px) 만큼 너비 보정하여 우측 끝 맞춤 */
    margin-left: -90px; /* 사용자가 설정한 값 유지 */
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.toggle-arrow {
    color: #999;
    font-size: 10px;
    cursor: pointer;
}

/* New Layout Styles for Discount Section */
.horizontal-row {
    padding: 15px 15px; /* [수정] 10px -> 5px (공백 축소) */
    border-bottom: 1px solid #eee;
    display: flex;
    flex-direction: row;
    align-items: center;
    margin-bottom: 0px;
}

.horizontal-row.top-align {
    align-items: flex-start;
    padding-top: 30px; /* [수정] 15px -> 10px */
    padding-bottom: 10px;
}

.horizontal-row.top-align .row-label {
    padding-top: 15px; /* [수정] 라벨을 입력창 텍스트 높이에 맞게 내림 */
}

.flex-column {
    display: flex;
    flex-direction: column;
}

.vertical-row {
    padding: 12px 15px; /* [수정] 패딩 축소 (20px -> 12px 15px) */
    border-bottom: 1px solid #eee;
    display: flex;
    flex-direction: column;
}

.row-label {
    font-size: 11px; /* [수정] 폰트 사이즈 미세 축소 */
    font-weight: 700;
    color: #333;
    margin-bottom: 6px; /* [수정] 간격 축소 (15px -> 6px) */
    text-align: left;
}

.fixed-label {
    width: 100px; /* [수정] 120px -> 100px (왼쪽 공간 늘림) */
    margin-bottom: 0;
    flex-shrink: 0;
}

.input-group {
    display: flex;
    gap: 0; /* [수정] 10px -> 0 (테두리 맞닿음) */
    width: 100%;
}

.width-100 {
    width: 80px; /* [수정] 110px -> 80px (버튼 너비 축소, 입력창 확장) */
    /* 테두리 겹침 방지 (선택사항, 테두리가 2줄로 보이면 border-left: 0 추가 고려) */
}

/* Coupon Specific Layout */
.coupon-section {
    padding: 10px 15px;
    padding-bottom: 8px; /* [수정] 하단 패딩 별도 축소 */
    border-bottom: 1px solid #eee;
}

.coupon-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 4px; /* [수정] 6px -> 4px */
}

.coupon-actions {
    display: flex;
    flex-direction: column;
    align-items: flex-end; /* Right align button and text */
}

.text-blue {
    color: #4c80f1;
}

.font-bold {
    font-weight: 700;
}

.applied-amount-row {
    background: #fbfbfb;
    border-top: 1px solid #e8e8e8; /* Ensure separation if needed, though no-border class might override bottom */
    padding: 12px 15px; /* [수정] 패딩 축소 */
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-top: 1px solid #e8e8e8;
}

.total-discount-wrapped {
    /* [수정] HTML 구조 변경에 따른 스타일 수정 */
    background: #fbfbfb;
    border-top: 1px solid #e8e8e8;
    padding: 12px 15px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;

}

/* Payment Method Styles */
.payment-radio-item {
    display: flex;
    align-items: center;
    padding: 20px; /* 여백 확대 */
    border-bottom: 1px solid #eee;
    cursor: pointer;
    font-size: 11px; /* 폰트 사이즈 조정 */
    color: #888; /* 기본 컬러 연하게 */
}
.payment-radio-item:last-child {
    border-bottom: none;
}

/* Hide default radio input */
.payment-radio-item input {
    display: none;
}

.payment-radio-item.selected {
    border: 1px solid #000; /* Selected Box Border */
    margin-top: -1px; 
    margin-bottom: -1px;
    position: relative;
    z-index: 10; /* 위로 올라오게 */
    font-weight: bold;
    color: #000; /* 선택된 텍스트 진하게 */
    background: #fff;
}

.mt-2 {
    margin-top: 2px;
}

/* Duplicate .info-box removed */

/* Utility Classes for Coupon Alignment */
.align-end {
    align-items: flex-end;
}
.items-center {
    align-items: center;
}
.gap-10 {
    gap: 10px;
}
.margin-right-10 {
    margin-right: 15px; /* KRW와 버튼 사이 간격 */
}
.flex-row {
    display: flex;
    flex-direction: row;
}

/* Custom Checkbox Styling */
.custom-checkbox-input {
    display: none; /* Hide default checkbox */
}

.custom-checkbox-box {
    width: 20px;
    height: 20px;
    background-color: #ddd; /* Light gray usually, or #e0e0e0 */
    border-radius: 4px; /* Rounded corners */
    margin-right: 8px;
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: background-color 0.2s;
}

/* Checked State */
.custom-checkbox-input:checked + .custom-checkbox-box {
    background-color: #ddd; /* Keep gray or change if needed based on reference, looking at image it seems gray with white check */
}

/* Checkmark Icon (CSS) */
.custom-checkbox-input:checked + .custom-checkbox-box::after {
    content: '';
    width: 6px;
    height: 10px;
    border: solid white;
    border-width: 0 2px 2px 0;
    transform: rotate(45deg);
    position: absolute;
    top: 3px;
}

</style>
