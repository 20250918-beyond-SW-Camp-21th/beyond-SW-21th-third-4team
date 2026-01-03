<template>
  <div class="qna-container">
    <div class="qna-header">
      <h2>{{ isNoticeMode ? 'NOTICE' : 'Q&A' }}</h2>
    </div>

    <template v-if="viewMode === 'list'">
      <div v-if="!isNoticeMode" class="qna-filter">
        <select v-model="selectedFilter" class="filter-select" @change="currentPage = 1">
          <option value="all">전체글보기</option>
          <option value="N">답변전 글보기</option>
          <option value="Y">답변완료 글보기</option>
        </select>
      </div>

      <ul class="qna-list">
        <template v-if="!isNoticeMode">
          <li v-for="item in noticeList" :key="'fix-'+item.id" class="qna-item notice-fixed" @click="handlePostClick(item)">
            <div class="qna-content-wrapper">
              <div class="subject"><strong>■ {{ item.subject }}</strong></div>
              <div class="info">
                <span class="author">{{ item.author }}</span>
                <span class="date">{{ item.createdAt }}</span>
              </div>
            </div>
          </li>
        </template>

        <li v-for="item in paginatedList" :key="item.id" class="qna-item" @click="handlePostClick(item)">
          <div class="qna-content-wrapper">
            <div v-if="!isNoticeMode" class="thumbnail">
              <img :src="item.thumbUrl || 'https://via.placeholder.com/50x65'" alt="product">
            </div>
            <div class="post-info">
              <div class="subject">
                <span v-if="!isNoticeMode && item.status === 'Y'" class="re-tag">+ RE</span>
                <i v-if="!isNoticeMode && item.isPrivate" class="xi-lock-o"></i>
                {{ item.subject }}
              </div>
              <div class="info">
                <span class="author">{{ item.author }}</span>
                <span class="date">{{ item.createdAt }}</span>
              </div>
            </div>
          </div>
        </li>
      </ul>

      <div class="pagination" v-if="totalPages > 0">
        <span class="prev" @click="changePage(currentPage - 1)" :class="{ disabled: currentPage === 1 }">PREV</span>
        <span v-for="page in totalPages" :key="page" class="page-num" :class="{ active: currentPage === page }" @click="changePage(page)">{{ page }}</span>
        <span class="next" @click="changePage(currentPage + 1)" :class="{ disabled: currentPage === totalPages }">NEXT</span>
      </div>

      <div class="search-section">
        <div class="search-row">
          <select v-model="searchPeriod" class="search-select period-select">
            <option value="all">전체기간</option>
            <option value="7">일주일</option>
            <option value="30">한 달</option>
            <option value="90">세 달</option>
          </select>
          <select v-model="searchType" class="search-select">
            <option value="subject">제목</option>
            <option value="author">작성자</option>
          </select>
          <div class="search-input-wrapper">
            <input v-model="searchKeyword" type="text" class="search-input" @keyup.enter="handleSearch">
            <button class="search-btn" @click="handleSearch">SEARCH</button>
          </div>
        </div>
      </div>

      <div v-if="!isNoticeMode" class="floating-write-btn" @click="goToWrite"><i class="xi-pen"></i></div>
    </template>

    <template v-else-if="viewMode === 'passwordCheck'">
      <div class="password-check-container">
        <div class="password-box">
          <h3>🔒 PRIVATE POST</h3>
          <p>비밀번호를 입력해 주세요.</p>
          <input v-model="inputPassword" type="password" class="pw-input" @keyup.enter="validatePassword">
          <div class="pw-btns">
            <button @click="viewMode = 'list'" class="btn-pw-cancel">CANCEL</button>
            <button @click="validatePassword" class="btn-pw-confirm">CONFIRM</button>
          </div>
        </div>
      </div>
    </template>

    <template v-else-if="viewMode === 'detail'">
      <div class="qna-detail" v-if="selectedPost">
        <div class="detail-header">
          <p class="category" v-if="selectedPost.category">[{{ selectedPost.category }}]</p>
          <h3>{{ selectedPost.subject }}</h3>
          <div class="detail-info"><span>{{ selectedPost.author }}</span> | <span>{{ selectedPost.createdAt }}</span></div>
        </div>
        <div class="detail-body">{{ selectedPost.content }}</div>
        <div class="answer-section">
          <div v-if="selectedPost.answer" class="admin-answer">
            <div class="answer-header"><strong>관리자 답변</strong> <span>{{ selectedPost.answeredAt }}</span></div>
            <div class="answer-content">{{ selectedPost.answer }}</div>
          </div>
          <div v-else class="no-answer">등록된 답변이 없습니다.</div>
        </div>
        <div class="detail-actions"><button @click="viewMode = 'list'" class="btn-list">LIST</button></div>
      </div>
    </template>

    <template v-else-if="viewMode === 'write'">
      <div class="qna-write-container">
        <div class="product-info-card" v-if="displayProdInfo.name">
          <div class="prod-img"><img :src="displayProdInfo.img" alt="product"></div>
          <div class="prod-details">
            <p class="prod-name">{{ displayProdInfo.name }}</p>
            <p class="prod-price">KRW {{ displayProdInfo.price }}</p>
          </div>
        </div>

        <div class="write-form">
          <div class="form-group row-line">
            <label>질문유형</label>
            <select v-model="writeForm.category" class="select-underlined">
              <option value="">질문의 유형을 선택해주세요</option>
              <option value="상품 문의">상품 문의</option>
              <option value="배송 문의">배송 문의</option>
              <option value="입금/환불/결제오류">입금/환불/결제오류</option>
              <option value="기타 문의">기타 문의</option>
            </select>
          </div>
          <div class="editor-container">
            <textarea v-model="writeForm.content" class="editor-textarea" placeholder="내용을 입력해주세요."></textarea>
          </div>
          <div class="form-group row-line">
            <label>비밀번호</label>
            <input type="password" v-model="writeForm.password" class="input-underlined" placeholder="비밀번호 4자리">
          </div>
        </div>
        <div class="action-btns">
          <button class="btn-submit" @click="handleCreateQna">등록</button>
          <button class="btn-cancel" @click="cancelWrite">취소</button>
        </div>
      </div>
    </template>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';

const props = defineProps({ isNoticeMode: { type: Boolean, default: false } });
const route = useRoute();
const router = useRouter();

const viewMode = ref('list');
const qnaList = ref([]);
const noticeList = ref([]);
const selectedPost = ref(null);
const currentPage = ref(1);
const itemsPerPage = 10;
const inputPassword = ref('');

// 검색/필터 상태
const searchPeriod = ref('all');
const searchType = ref('subject');
const searchKeyword = ref('');
const appliedKeyword = ref('');
const selectedFilter = ref('all');

// [연동 핵심] 작성 폼 및 상품 정보
const displayProdInfo = ref({ name: '', price: '', img: '' });
const writeForm = ref({
  productId: null,  // 상세페이지에서 넘어온 ID가 여기 담김
  category: '',
  content: '',
  password: '',
  isPrivate: true
});

// 초기 데이터 로드 및 쿼리 파라미터 체크
const checkProductQuery = () => {
  if (route.query.mode === 'write') {
    viewMode.value = 'write';
    writeForm.value.productId = route.query.prodId; // 👈 쿼리에서 ID 추출하여 form에 삽입
    displayProdInfo.value = {
      name: route.query.prodName,
      price: route.query.prodPrice,
      img: route.query.prodImg
    };
  } else {
    viewMode.value = 'list';
    resetWriteForm();
  }
};

const resetWriteForm = () => {
  writeForm.value = { productId: null, category: '', content: '', password: '', isPrivate: true };
  displayProdInfo.value = { name: '', price: '', img: '' };
};

const fetchAllData = async () => {
  try {
    const res = await axios.get('/api/v1/qnas');
    const data = res.data.data;
    noticeList.value = data.filter(i => i.isNotice);
    qnaList.value = data.filter(i => !i.isNotice);
  } catch (e) { console.error(e); }
};

onMounted(() => {
  fetchAllData();
  checkProductQuery();
});

watch(() => route.query, () => checkProductQuery(), { deep: true });

// [연동 핵심] 실제 서버 전송 함수
const handleCreateQna = async () => {
  if (!writeForm.value.category) return alert("유형을 선택하세요.");
  if (!writeForm.value.content) return alert("내용을 입력하세요.");
  if (!writeForm.value.password) return alert("비밀번호를 입력하세요.");

  try {
    // 백엔드 Create API에 productId를 포함하여 전송
    const payload = {
      ...writeForm.value,
      subject: `[${writeForm.value.category}] 문의합니다.`
    };

    await axios.post('/api/v1/qnas', payload);

    alert("등록되었습니다.");
    router.push('/qna'); // 쿼리 스트링 제거를 위해 라우팅 이동
    viewMode.value = 'list';
    fetchAllData();
  } catch (e) {
    alert("등록에 실패했습니다.");
    console.error(e);
  }
};

const cancelWrite = () => {
  router.push('/qna');
  viewMode.value = 'list';
};

const goToWrite = () => { viewMode.value = 'write'; };
const handleSearch = () => { appliedKeyword.value = searchKeyword.value; currentPage.value = 1; };
const changePage = (p) => { if (p > 0 && p <= totalPages.value) currentPage.value = p; };

const filteredList = computed(() => {
  let list = props.isNoticeMode ? noticeList.value : qnaList.value;
  return list.filter(item => {
    const matchStatus = props.isNoticeMode || selectedFilter.value === 'all' || item.status === selectedFilter.value;
    const matchKeyword = item[searchType.value]?.toLowerCase().includes(appliedKeyword.value.toLowerCase());
    return matchStatus && matchKeyword;
  });
});

const paginatedList = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  return filteredList.value.slice(start, start + itemsPerPage);
});

const totalPages = computed(() => Math.ceil(filteredList.value.length / itemsPerPage));

const handlePostClick = (post) => {
  selectedPost.value = post;
  if (post.isNotice || !post.isPrivate) loadDetail(post.id);
  else { inputPassword.value = ''; viewMode.value = 'passwordCheck'; }
};

const loadDetail = async (id) => {
  try {
    const res = await axios.get(`/api/v1/qnas/${id}`);
    selectedPost.value = res.data.data;
    viewMode.value = 'detail';
  } catch (e) { alert("비밀번호가 틀렸습니다."); }
};

const validatePassword = () => loadDetail(selectedPost.value.id);
</script>

<style scoped>
/* 이전 스타일과 동일 (생략 없이 사용하세요) */
.qna-container { max-width: 800px; margin: 0 auto; padding: 60px 20px; color: #333; font-family: 'Noto Sans KR', sans-serif; }
.qna-header h2 { text-align: center; font-size: 1.25rem; letter-spacing: 5px; margin-bottom: 50px; font-weight: 700; }
.qna-list { list-style: none; padding: 0; margin: 0; border-top: 1px solid #000; }
.qna-item { border-bottom: 1px solid #eee; padding: 20px 0; cursor: pointer; }
.qna-content-wrapper { display: flex; align-items: center; gap: 20px; }
.thumbnail img { width: 50px; height: 65px; object-fit: cover; }
.subject { font-size: 0.85rem; margin-bottom: 5px; display: flex; align-items: center; gap: 6px; }
.re-tag { border: 1px solid #ddd; font-size: 10px; padding: 1px 4px; color: #999; }
.info { font-size: 0.75rem; color: #bbb; }
.pagination { display: flex; justify-content: center; gap: 20px; margin: 40px 0; font-size: 0.75rem; color: #999; }
.page-num.active { color: #000; font-weight: bold; text-decoration: underline; text-underline-offset: 4px; }
.search-section { border-top: 1px solid #eee; padding-top: 30px; margin-top: 20px; }
.search-row { display: flex; justify-content: center; gap: 15px; max-width: 600px; margin: 0 auto; }
.search-select { border: none; border-bottom: 1px solid #000; padding: 8px 0; font-size: 0.75rem; outline: none; }
.search-input-wrapper { display: flex; flex: 1; border-bottom: 1px solid #000; }
.search-input { border: none; padding: 8px 0; font-size: 0.75rem; outline: none; flex: 1; }
.search-btn { background: #000; color: #fff; border: none; padding: 0 15px; font-size: 0.7rem; cursor: pointer; }
.product-info-card { display: flex; gap: 20px; padding: 20px; border: 1px solid #eee; margin-bottom: 30px; background: #fafafa; }
.prod-img img { width: 70px; height: 90px; object-fit: cover; }
.prod-name { font-size: 0.85rem; font-weight: bold; }
.row-line { display: flex; align-items: center; border-bottom: 1px solid #eee; padding: 15px 0; }
.row-line label { width: 100px; font-size: 0.8rem; font-weight: bold; }
.select-underlined, .input-underlined { flex: 1; border: none; border-bottom: 1px solid #ddd; padding: 8px; outline: none; }
.editor-textarea { width: 100%; min-height: 300px; padding: 20px; border: 1px solid #eee; margin: 20px 0; resize: none; outline: none; }
.floating-write-btn { position: fixed; bottom: 50px; right: 50px; width: 54px; height: 54px; background: #fff; border: 1px solid #000; border-radius: 50%; display: flex; align-items: center; justify-content: center; cursor: pointer; box-shadow: 0 4px 10px rgba(0,0,0,0.1); }
.action-btns { display: flex; gap: 10px; margin-top: 30px; }
.btn-submit { flex: 1; background: #000; color: #fff; padding: 18px; border: none; cursor: pointer; font-weight: bold; }
.btn-cancel { flex: 1; background: #fff; border: 1px solid #000; padding: 18px; cursor: pointer; }
.qna-detail { border-top: 1px solid #000; margin-top: 20px; }
.detail-header { padding: 30px 0; border-bottom: 1px solid #eee; }
.detail-body { padding: 40px 0; min-height: 200px; font-size: 0.9rem; line-height: 1.6; white-space: pre-wrap; }
.answer-section { background: #f9f9f9; padding: 30px; margin-bottom: 30px; }
.btn-list { background: #fff; border: 1px solid #000; padding: 12px 40px; cursor: pointer; }
.password-check-container { padding: 100px 0; text-align: center; }
.pw-input { border: none; border-bottom: 1px solid #000; width: 200px; padding: 10px; text-align: center; margin: 20px 0; outline: none; }
</style>