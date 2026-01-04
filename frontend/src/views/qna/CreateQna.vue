<template>
  <div class="qna-write-container">
    <div class="write-header">
      <h2>Q&A</h2>
    </div>

    <div class="product-info-card" v-if="prodInfo.name">
      <div class="prod-img">
        <img :src="prodInfo.img" alt="product">
      </div>
      <div class="prod-details">
        <p class="prod-name">{{ prodInfo.name }}</p>
        <p class="prod-price">KRW {{ Number(prodInfo.price).toLocaleString() }}</p>
        <div class="prod-btns">
          <button class="btn-sm" @click="goToProduct">상품상세보기</button>
        </div>
      </div>
    </div>

    <div class="write-form">
      <div class="form-group row-line">
        <label>질문유형</label>
        <div class="input-flex">
          <select v-model="form.category" class="select-underlined">
            <option value="">질문의 유형을 선택해주세요</option>
            <option value="product">상품 문의</option>
            <option value="delivery">배송 문의</option>
            <option value="cancel_before">발송 전 변경/취소</option>
            <option value="exchange_after">배송 후 교환/반품</option>
            <option value="payment_error">입금/환불/결제오류</option>
            <option value="etc">기타 문의</option>
          </select>
        </div>
      </div>

      <div class="editor-container">
        <textarea v-model="form.content" class="editor-textarea" placeholder="내용을 입력해주세요."></textarea>
        <div class="char-count">문자 : {{ form.content.length }}</div>
      </div>

      <div class="form-group row-line">
        <label>UCC URL</label>
        <div class="input-flex">
          <input type="text" v-model="form.uccUrl" class="input-underlined">
        </div>
      </div>

      <div class="file-section">
        <div v-for="n in 5" :key="n" class="form-group row-line file-row">
          <label>첨부파일{{ n }}</label>
          <div class="file-input-wrapper">
            <input
                type="file"
                @change="handleFileChange($event, n)"
                class="real-file-input"
            >
          </div>
        </div>
      </div>

      <div class="form-group row-line">
        <label>비밀번호</label>
        <div class="input-flex">
          <input type="password" v-model="form.password" class="input-underlined" placeholder="비밀번호 4자리">
        </div>
      </div>

      <div class="form-group row-line">
        <label>비밀글설정</label>
        <div class="radio-group">
          <label class="radio-label disabled">
            <input type="radio" disabled> <span>공개글</span>
          </label>
          <label class="radio-label">
            <input type="radio" checked> <span>비밀글</span>
          </label>
        </div>
      </div>
    </div>

    <div class="write-notice">
      <p>- 상품과 관련없는 내용 또는 이미지, 욕설/비방, 개인정보유출, 광고/홍보글 등 부적절한 게시물은 별도의 고지없이 비공개 처리 될 수 있습니다.</p>
      <p>- 작성된 게시물(사진, 동영상 포함)은 운영 및 마케팅에 활용될 수 있습니다.</p>
    </div>

    <div class="action-btns">
      <button class="btn-submit" @click="submitForm">등록</button>
      <button class="btn-cancel" @click="goBack">취소</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';

const route = useRoute();
const router = useRouter();

const prodInfo = ref({ id: null, name: '', price: '', img: '' });
const form = ref({
  productId: null,
  category: '',
  content: '',
  uccUrl: '',
  password: '',
  isPrivate: true,
  files: [null, null, null, null, null]
});

onMounted(() => {
  if (route.query.prodId) {
    prodInfo.value = {
      id: route.query.prodId,
      name: route.query.prodName,
      price: route.query.prodPrice,
      img: route.query.prodImg
    };
    form.value.productId = route.query.prodId;
  }
});

const handleFileChange = (event, n) => {
  const file = event.target.files[0];
  if (file) form.value.files[n - 1] = file;
};

<<<<<<< HEAD
const submitForm = async () => {
  try {
    const payload = {
      productId: Number(form.value.productId) || 1, // 테스트용 ID
      category: "ETC", // 일단 단순 문자열
      content: form.value.content,
      password: form.value.password,
      isPrivate: true
      // 일단 subject나 uccUrl 등은 빼고 최소한의 데이터만 보내보세요.
    };

    const response = await axios.post('/api/v1/qnas', payload, {
      withCredentials: true // 👈 요청할 때마다 "나 로그인 정보 여기 있어!"라고 외치는 옵션
    });
    console.log("성공 응답:", response);
    alert('성공!');
  } catch (error) {
    // 콘솔에 찍히는 에러가 CORS인지 확인하세요.
    console.error("에러 발생 원인:", error);
=======
// Qna.vue의 submitForm 함수 내부를 이렇게 바꿔보세요
const submitForm = async () => {
  console.log("버튼 클릭됨!"); // 👈 1. 함수가 실행은 되는지 확인
  try {
    const payload = { ... };
    console.log("보내는 데이터:", payload); // 👈 2. 데이터가 뽑히는지 확인

    const response = await axios.post('http://localhost:8080/api/v1/qnas', payload, {
      withCredentials: true
    });

    console.log("서버 응답:", response); // 👈 3. 성공 응답 확인
  } catch (error) {
    // 👈 여기서 에러가 잡혀서 화면엔 무반응처럼 보일 수 있습니다.
    console.error("실제 에러 원인:", error.response || error);
    alert("에러 발생! 콘솔을 확인하세요.");
>>>>>>> 242ab905e5671ce7e574a2fa354e898d67943099
  }
};

const goBack = () => router.back();
const goToProduct = () => router.push(`/product/${prodInfo.value.id}`);
</script>

<style scoped>
.qna-write-container { max-width: 800px; margin: 0 auto; padding: 100px 20px; color: #333; }
.write-header h2 { text-align: center; font-size: 1.5rem; letter-spacing: 3px; margin-bottom: 40px; }

.product-info-card { border: 1px solid #eee; display: flex; padding: 20px; gap: 20px; margin-bottom: 30px; }
.prod-img img { width: 100px; height: 120px; object-fit: cover; }
.prod-name { font-weight: bold; font-size: 0.95rem; margin-bottom: 10px; }

.row-line { border-bottom: 1px solid #eee; padding: 15px 0; display: flex; align-items: center; }
.row-line label { width: 140px; font-size: 0.85rem; font-weight: bold; }
.input-flex { flex: 1; }
.select-underlined, .input-underlined { width: 100%; border: none; border-bottom: 1px solid #ccc; padding: 8px 0; outline: none; }

.editor-container { border: 1px solid #ddd; margin: 30px 0; position: relative; }
.editor-textarea { width: 100%; min-height: 300px; border: none; padding: 20px; resize: none; outline: none; }
.char-count { text-align: right; padding: 10px; border-top: 1px solid #eee; font-size: 0.8rem; color: #999; }

/* 첨부파일 레이아웃 핵심 */
.file-section { margin-top: 10px; }
.file-row { display: flex; align-items: center; }
.file-input-wrapper { flex: 1; }
.real-file-input { font-size: 0.8rem; cursor: pointer; }

.radio-group { display: flex; gap: 40px; }
.radio-label { display: flex; align-items: center; gap: 10px; font-size: 0.9rem; }
.radio-label.disabled { opacity: 0.5; cursor: not-allowed; }

.write-notice { font-size: 0.8rem; color: #777; line-height: 1.8; margin: 40px 0; }
.action-btns { display: flex; gap: 15px; }
.action-btns button { flex: 1; padding: 20px; font-weight: bold; cursor: pointer; border: 1px solid #000; }
.btn-submit { background: #000; color: #fff; }
.btn-cancel { background: #fff; color: #000; }
</style>