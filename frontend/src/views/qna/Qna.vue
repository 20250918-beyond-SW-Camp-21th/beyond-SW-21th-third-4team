<template>
  <div class="qna-container">
    <div class="qna-header">
      <h2>{{ isNoticeMode ? 'NOTICE' : 'Q&A' }}</h2>
    </div>

    <template v-if="viewMode === 'list'">
      <div v-if="!isNoticeMode" class="qna-filter-bar">
        <select v-model="selectedFilter" class="filter-select" @change="currentPage = 1">
          <option value="all">전체글보기</option>
          <option value="WAITING">답변전 글보기</option>
          <option value="ANSWERED">답변완료 글보기</option>
        </select>
      </div>

      <ul class="qna-list">
        <li v-if="paginatedList.length === 0" class="no-data">등록된 게시글이 없습니다.</li>
        <li v-for="item in paginatedList" :key="item.id" class="qna-item" @click="handlePostClick(item)">
          <div class="qna-content-wrapper">
            <div class="post-info">
              <div class="subject">
                {{ item.title }}
              </div>
              <div class="info">
                <span class="author">ID: {{ item.userId }}</span>
                <span class="date">{{ formatDate(item.createdAt) }}</span>
              </div>
            </div>
          </div>
        </li>
      </ul>

      <div class="pagination" v-if="totalPages > 0">
        <span class="page-btn" @click="changePage(currentPage - 1)" :class="{ disabled: currentPage === 1 }">PREV</span>
        <span v-for="page in totalPages" :key="page" class="page-num" :class="{ active: currentPage === page }" @click="changePage(page)">{{ page }}</span>
        <span class="page-btn" @click="changePage(currentPage + 1)" :class="{ disabled: currentPage === totalPages }">NEXT</span>
      </div>

      <div class="search-section">
        <div class="search-row">
          <select v-model="searchType" class="search-select">
            <option value="title">제목</option>
            <option value="userId">작성자</option>
          </select>
          <div class="search-input-wrapper">
            <input v-model="searchKeyword" type="text" class="search-input" @keyup.enter="handleSearch">
            <button class="search-btn" @click="handleSearch">SEARCH</button>
          </div>
        </div>
      </div>

      <div v-if="!isNoticeMode" class="floating-write-btn" @click="goToWrite"><i class="xi-pen"></i></div>
    </template>

    <template v-else-if="viewMode === 'write'">
      <div class="qna-write-container-inner">
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

          <div class="form-group row-line">
            <label>제목</label>
            <input type="text" v-model="writeForm.title" class="input-underlined" placeholder="제목을 입력해주세요.">
          </div>

          <div class="editor-container">
            <textarea v-model="writeForm.content" class="editor-textarea" placeholder="내용을 입력해주세요."></textarea>
          </div>

          <div class="file-section">
            <div v-for="n in 3" :key="n" class="form-group row-line">
              <label>첨부파일{{ n }}</label>
              <input type="file" class="real-file-input">
            </div>
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
import { useRoute } from 'vue-router';
import axios from 'axios';

const props = defineProps({ isNoticeMode: { type: Boolean, default: false } });
const route = useRoute();

const viewMode = ref('list');
const qnaList = ref([]);
const noticeList = ref([]);
const currentPage = ref(1);
const itemsPerPage = 10;
const searchType = ref('title');
const searchKeyword = ref('');
const appliedKeyword = ref('');
const selectedFilter = ref('all');

const displayProdInfo = ref({ name: '', price: '', img: '' });
const writeForm = ref({ category: '', title: '', content: '', password: '', productId: null });

const formatDate = (dateStr) => dateStr ? dateStr.split('T')[0] : '';

// 상품 문의하기 클릭 시 쿼리 파라미터를 감지하여 작성 폼을 띄우는 함수
const checkProductQuery = () => {
  if (route.query.mode === 'write') {
    viewMode.value = 'write';
    writeForm.value.productId = route.query.prodId;
    displayProdInfo.value = {
      name: route.query.prodName,
      price: route.query.prodPrice,
      img: route.query.prodImg
    };
  } else {
    viewMode.value = 'list';
  }
};

// <script setup> 내의 fetchAllData 함수를 찾아 아래 내용으로 교체하세요.
const fetchAllData = async () => {
  try {
    const token = localStorage.getItem('accessToken');

    // 네트워크 에러 방지를 위해 주소를 한 번 더 확인하세요.
    const res = await axios.get('http://localhost:8080/api/v1/qnas', {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });

    const rawData = res.data.data || res.data;
    if (Array.isArray(rawData)) {
      qnaList.value = rawData.filter(i => i.type === 'QUESTION');
      noticeList.value = rawData.filter(i => i.type === 'NOTICE');
      console.log("연동 성공! 목록을 불러왔습니다.");
    }
  } catch (e) {
    if (e.code === 'ERR_NETWORK') {
      console.error("서버 연결 실패: 백엔드 서버가 켜져 있는지 확인하세요.");
      alert("백엔드 서버와 연결할 수 없습니다. 서버를 실행해 주세요.");
    } else {
      console.error("기타 에러:", e.response?.status);
    }
  }
};

const handleCreateQna = async () => {
  try {
    const token = localStorage.getItem('accessToken');
    const payload = {
      type: "QUESTION",
      title: writeForm.value.category ? `[${writeForm.value.category}] ${writeForm.value.title}` : writeForm.value.title,
      content: writeForm.value.content,
      productId: writeForm.value.productId ? Number(writeForm.value.productId) : null
    };
    await axios.post('http://localhost:8080/api/v1/qnas', payload, {
      headers: { 'Authorization': `Bearer ${token}` }
    });
    alert("등록되었습니다.");
    viewMode.value = 'list';
    fetchAllData();
  } catch (e) { alert("등록 실패"); }
};

const filteredList = computed(() => {
  let list = qnaList.value;
  if (selectedFilter.value !== 'all') list = list.filter(i => i.status === selectedFilter.value);
  if (appliedKeyword.value) list = list.filter(i => i[searchType.value]?.includes(appliedKeyword.value));
  return list;
});

const paginatedList = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  return filteredList.value.slice(start, start + itemsPerPage);
});

const totalPages = computed(() => Math.ceil(filteredList.value.length / itemsPerPage));
const handleSearch = () => { appliedKeyword.value = searchKeyword.value; currentPage.value = 1; };
const changePage = (p) => { if (p > 0 && p <= totalPages.value) currentPage.value = p; };
const goToWrite = () => { viewMode.value = 'write'; };
const cancelWrite = () => { viewMode.value = 'list'; };

onMounted(() => {
  fetchAllData();
  checkProductQuery();
});

// URL 쿼리가 바뀔 때마다 감시 (상품 상세에서 문의하기 클릭 대응)
watch(() => route.query, () => checkProductQuery());
</script>

<style scoped>
.qna-container { max-width: 800px; margin: 0 auto; padding: 60px 20px; font-family: 'Noto Sans KR', sans-serif; }
.qna-header h2 { text-align: center; letter-spacing: 5px; margin-bottom: 40px; }

/* 리스트 스타일 */
.qna-list { list-style: none; padding: 0; border-top: 1px solid #000; }
.qna-item { border-bottom: 1px solid #eee; padding: 20px 0; cursor: pointer; }
.qna-content-wrapper { display: flex; align-items: center; gap: 20px; }
.thumbnail img { width: 50px; height: 65px; object-fit: cover; }
.subject { font-size: 14px; margin-bottom: 5px; }
.info { font-size: 12px; color: #bbb; display: flex; gap: 10px; }

/* 상품 정보 카드 */
.product-info-card { display: flex; gap: 20px; padding: 20px; border: 1px solid #eee; margin-bottom: 30px; background: #fafafa; }
.prod-img img { width: 70px; height: 90px; object-fit: cover; }
.prod-name { font-size: 14px; font-weight: bold; margin-bottom: 5px; }
.prod-price { font-size: 13px; color: #888; }

/* 검색 및 필터 */
.qna-filter-bar { display: flex; justify-content: flex-end; margin-bottom: 10px; }
.search-section { margin-top: 40px; display: flex; justify-content: center; }
.search-row { display: flex; gap: 10px; }
.search-btn { background: #eee; border: none; padding: 0 15px; cursor: pointer; font-size: 12px; }

/* 작성 폼 */
.row-line { display: flex; align-items: center; border-bottom: 1px solid #eee; padding: 15px 0; }
.row-line label { width: 100px; font-size: 13px; font-weight: bold; }
.input-underlined, .select-underlined { flex: 1; border: none; border-bottom: 1px solid #ddd; outline: none; padding: 5px; }
.editor-textarea { width: 100%; min-height: 250px; padding: 20px; border: 1px solid #eee; margin: 20px 0; resize: none; outline: none; }
.btn-submit { flex: 1; background: #000; color: #fff; padding: 18px; border: none; cursor: pointer; }
.btn-cancel { flex: 1; border: 1px solid #000; background: #fff; padding: 18px; cursor: pointer; }
.action-btns { display: flex; gap: 10px; }

.floating-write-btn { position: fixed; bottom: 50px; right: 50px; width: 54px; height: 54px; background: #fff; border: 1px solid #000; border-radius: 50%; display: flex; align-items: center; justify-content: center; cursor: pointer; }
.no-data { text-align: center; padding: 50px; color: #999; font-size: 14px; }
</style>