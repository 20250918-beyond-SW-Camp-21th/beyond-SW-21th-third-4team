<template>
  <div class="qna-write-container">
    <div class="write-header">
      <h2>Q&A</h2>
    </div>

    <div class="product-info-card">
      <div class="prod-img">
        <img src="https://via.placeholder.com/100x120" alt="product">
      </div>
      <div class="prod-details">
        <p class="prod-name">마펠 캐시미어 발마칸 코트 BLACK</p>
        <p class="prod-price">KRW 319,200</p>
        <div class="prod-btns">
          <button class="btn-sm">상품상세보기</button>
          <button class="btn-sm">상품정보선택</button>
        </div>
      </div>
    </div>

    <div class="write-form">
      <div class="form-group row-line">
        <label>제목</label>
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

      <div class="editor-container">
        <textarea
            v-model="form.content"
            class="editor-textarea"
            placeholder="문의하실 내용을 입력해주세요."
        ></textarea>
        <div class="char-count">문자 : {{ form.content.length }}</div>
      </div>

      <div class="form-group row-line">
        <label>UCC URL</label>
        <input type="text" v-model="form.uccUrl" class="input-underlined">
      </div>

      <div class="file-uploads">
        <div v-for="n in 5" :key="n" class="form-group row-line file-row">
          <label>첨부파일{{ n }}</label>
          <input type="file" @change="handleFileChange($event, n)" class="file-input">
        </div>
      </div>

      <div class="form-group row-line">
        <label>비밀번호</label>
        <input type="password" v-model="form.password" class="input-underlined">
      </div>

      <div class="form-group row-line">
        <label>비밀글설정</label>
        <div class="radio-group">
          <label class="radio-label disabled-label">
            <input type="radio" :value="false" disabled>
            <span class="text-disabled">공개글</span>
          </label>
          <label class="radio-label">
            <input type="radio" v-model="form.isPrivate" :value="true">
            <span>비밀글</span>
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
import { ref } from 'vue';

const emit = defineEmits(['submit-success', 'cancel']);

const form = ref({
  category: '',
  content: '',
  uccUrl: '',
  password: '',
  isPrivate: true, // 기본값을 true로 고정
  files: []
});

const handleFileChange = (event, index) => {
  const file = event.target.files[0];
  if (file) {
    form.value.files[index - 1] = file;
  }
};

const submitForm = () => {
  if (!form.value.category) {
    alert('질문의 유형을 선택해주세요.');
    return;
  }
  if (!form.value.content.trim()) {
    alert('내용을 입력해주세요.');
    return;
  }
  if (!form.value.password) {
    alert('비밀번호를 입력해주세요.');
    return;
  }

  console.log('등록 데이터:', form.value);
  alert('등록되었습니다.');
  emit('submit-success');
};

const goBack = () => {
  if (confirm('작성을 취소하시겠습니까?')) {
    emit('cancel');
  }
};
</script>

<style scoped>
/* 기존 스타일 유지 */
.qna-write-container { max-width: 800px; margin: 0 auto; padding: 20px; font-family: 'Noto Sans KR', sans-serif; color: #333; }
.write-header h2 { text-align: center; font-size: 1.2rem; letter-spacing: 2px; margin-bottom: 30px; font-weight: 700; }
.product-info-card { border: 1px solid #eee; display: flex; padding: 20px; gap: 20px; margin-bottom: 30px; }
.prod-img img { width: 100px; height: 120px; object-fit: cover; }
.prod-details { display: flex; flex-direction: column; justify-content: center; }
.prod-name { font-size: 0.9rem; font-weight: bold; margin-bottom: 8px; }
.prod-price { font-size: 0.85rem; color: #666; margin-bottom: 15px; }
.prod-btns { display: flex; gap: 8px; }
.btn-sm { background: #fff; border: 1px solid #ddd; font-size: 0.75rem; padding: 6px 12px; cursor: pointer; }
.form-group { display: flex; align-items: center; }
.row-line { border-bottom: 1px solid #eee; padding: 15px 0; }
.row-line label { width: 130px; font-size: 0.8rem; font-weight: bold; }
.select-underlined, .input-underlined { flex: 1; border: none; outline: none; font-size: 0.85rem; padding: 5px; background: transparent; }
.editor-container { border: 1px solid #eee; margin: 25px 0; }
.editor-textarea { width: 100%; min-height: 350px; border: none; outline: none; padding: 20px; resize: none; box-sizing: border-box; font-size: 0.9rem; }
.char-count { text-align: right; padding: 10px 15px; font-size: 0.75rem; color: #bbb; border-top: 1px solid #f9f9f9; }

/* 라디오 버튼 스타일 수정 */
.radio-group { display: flex; gap: 30px; }
.radio-label { display: flex; align-items: center; gap: 8px; cursor: pointer; font-size: 0.85rem; }
.disabled-label { cursor: not-allowed; opacity: 0.5; }
.text-disabled { background: #fff; color: #000; }

.write-notice { font-size: 0.75rem; color: #888; line-height: 1.8; margin: 30px 0; }
.action-btns { display: flex; gap: 10px; margin-top: 20px; }
.action-btns button { flex: 1; padding: 18px; font-size: 0.9rem; cursor: pointer; border: 1px solid #000; font-weight: bold; }
.btn-submit { background: #000; color: #fff; }
.btn-cancel { background: #fff; color: #000; }
</style>