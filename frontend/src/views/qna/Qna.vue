<template>
  <div class="qna-container">
    <div class="qna-header">
      <h2>Q&A</h2>
    </div>

    <template v-if="viewMode === 'list'">
      <div class="qna-filter">
        <select v-model="selectedFilter" class="filter-select" @change="currentPage = 1">
          <option value="all">전체글보기</option>
          <option value="N">답변전 글보기</option>
          <option value="Y">답변완료 글보기</option>
        </select>
      </div>

      <ul class="qna-list">
        <li
            v-for="item in noticeList"
            :key="'notice-'+item.id"
            class="qna-item notice"
            @click="handlePostClick(item)"
        >
          <div class="qna-content-wrapper">
            <div class="subject">
              <strong>■ {{ item.subject }}</strong>
            </div>
            <div class="info">
              <span class="author">{{ item.author }}</span>
              <span class="date">{{ item.date }}</span>
            </div>
          </div>
        </li>

        <li
            v-for="item in paginatedList"
            :key="item.id"
            class="qna-item"
            @click="handlePostClick(item)"
        >
          <div class="qna-content-wrapper">
            <div class="thumbnail">
              <img :src="item.thumbUrl || 'https://via.placeholder.com/50x65'" alt="product">
            </div>

            <div class="post-info">
              <div class="subject">
                <span v-if="item.status === 'Y'" class="re-tag">+ RE</span>
                <i v-if="item.isPrivate" class="xi-lock-o"></i>
                {{ item.subject }}
              </div>
              <div class="info">
                <span class="author">{{ item.author }}</span>
                <span class="date">{{ item.date }}</span>
              </div>
            </div>
          </div>
        </li>
      </ul>

      <div class="pagination" v-if="totalPages > 0">
        <span class="prev" @click="changePage(currentPage - 1)" :class="{ disabled: currentPage === 1 }">이전</span>

        <span
            v-for="page in totalPages"
            :key="page"
            class="page-num"
            :class="{ active: currentPage === page }"
            @click="changePage(page)"
        >
          {{ page }}
        </span>

        <span class="next" @click="changePage(currentPage + 1)" :class="{ disabled: currentPage === totalPages }">다음</span>
      </div>

      <div class="search-section">
        <div class="search-row">
          <select v-model="searchPeriod" class="search-select">
            <option value="7">일주일</option>
            <option value="30">한 달</option>
            <option value="90">세 달</option>
            <option value="all">전체</option>
          </select>
          <select v-model="searchType" class="search-select">
            <option value="subject">제목</option>
            <option value="author">작성자</option>
          </select>
        </div>
        <div class="search-input-wrapper">
          <input
              v-model="searchKeyword"
              type="text"
              class="search-input"
              @keyup.enter="handleSearch"
          >
          <button class="search-btn" @click="handleSearch">검색</button>
        </div>
      </div>

      <div class="floating-write-btn" @click="viewMode = 'write'">
        <i class="xi-message"></i>
      </div>
    </template>

    <template v-else-if="viewMode === 'passwordCheck'">
      <div class="password-check-container">
        <div class="password-box">
          <h3>🔒 비밀글 보기</h3>
          <p>작성 시 설정한 비밀번호를 입력해 주세요.</p>
          <input
              v-model="inputPassword"
              type="password"
              class="pw-input"
              placeholder="PASSWORD"
              @keyup.enter="validatePassword"
          >
          <div class="pw-btns">
            <button @click="viewMode = 'list'" class="btn-pw-cancel">목록</button>
            <button @click="validatePassword" class="btn-pw-confirm">확인</button>
          </div>
        </div>
      </div>
    </template>

    <template v-else-if="viewMode === 'detail'">
      <div class="qna-detail">
        <div class="detail-header">
          <h3>{{ selectedPost.subject }}</h3>
          <div class="detail-info">
            <span>{{ selectedPost.author }}</span>
            <span>{{ selectedPost.date }}</span>
          </div>
        </div>
        <div class="detail-body">
          {{ selectedPost.content || '내용이 표시됩니다.' }}
        </div>
        <button @click="viewMode = 'list'" class="btn-list">목록으로</button>
      </div>
    </template>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';

const viewMode = ref('list');
const selectedFilter = ref('all');
const selectedPost = ref(null);

// 페이징 관련 상태
const currentPage = ref(1);
const itemsPerPage = 3; // 한 페이지에 보여줄 게시글 수

// 검색 및 기간 필터 상태
const searchPeriod = ref('all');
const searchType = ref('subject');
const searchKeyword = ref('');
const appliedKeyword = ref('');
const inputPassword = ref('');

// 데이터 (페이징 테스트를 위해 7개로 늘림)
const qnaList = ref([
  { id: 1, subject: '1번 문의 (비공개)', author: 'user01', date: '2025-12-31', isPrivate: true, status: 'Y', password: '1', content: '1번 내용' },
  { id: 2, subject: '2번 문의 (공개)', author: 'user02', date: '2025-12-30', isPrivate: false, status: 'N', password: '', content: '2번 내용' },
  { id: 3, subject: '3번 문의 (비공개)', author: 'user03', date: '2025-01-02', isPrivate: true, status: 'N', password: '1', content: '3번 내용' },
  { id: 4, subject: '4번 문의 (공개)', author: 'user04', date: '2025-01-03', isPrivate: false, status: 'Y', password: '', content: '4번 내용' },
  { id: 5, subject: '5번 문의 (공개)', author: 'user05', date: '2025-11-20', isPrivate: false, status: 'N', password: '', content: '5번 내용' },
  { id: 6, subject: '6번 문의 (비공개)', author: 'user06', date: '2025-10-10', isPrivate: true, status: 'Y', password: '1', content: '6번 내용' },
  { id: 7, subject: '7번 문의 (공개)', author: 'user07', date: '2025-09-05', isPrivate: false, status: 'N', password: '', content: '7번 내용' },
]);

const noticeList = ref([
  { id: 99, subject: '교환/반품 접수 안내', author: 'ADMIN', date: '2024-09-13', isNotice: true, content: '공지 내용입니다.' }
]);

// 1. 전체 필터링된 리스트 (상태 + 기간 + 검색)
const filteredList = computed(() => {
  return qnaList.value.filter(item => {
    const matchStatus = selectedFilter.value === 'all' || item.status === selectedFilter.value;
    let matchPeriod = true;
    if (searchPeriod.value !== 'all') {
      const today = new Date();
      const postDate = new Date(item.date);
      const diffDays = Math.ceil(Math.abs(today - postDate) / (1000 * 60 * 60 * 24));
      matchPeriod = diffDays <= parseInt(searchPeriod.value);
    }
    const matchKeyword = item[searchType.value].includes(appliedKeyword.value);
    return matchStatus && matchPeriod && matchKeyword;
  });
});

// 2. [추가] 현재 페이지에 보여줄 리스트 계산 (Slice 사용)
const paginatedList = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  const end = start + itemsPerPage;
  return filteredList.value.slice(start, end);
});

// 3. [추가] 전체 페이지 수 계산
const totalPages = computed(() => {
  return Math.ceil(filteredList.value.length / itemsPerPage);
});

// 페이지 변경 함수
const changePage = (page) => {
  if (page >= 1 && page <= totalPages.value) {
    currentPage.value = page;
  }
};

const handlePostClick = (post) => {
  selectedPost.value = post;
  inputPassword.value = '';
  if (post.isNotice || !post.isPrivate) {
    viewMode.value = 'detail';
  } else {
    viewMode.value = 'passwordCheck';
  }
};

const validatePassword = () => {
  if (inputPassword.value === selectedPost.value.password) {
    viewMode.value = 'detail';
  } else {
    alert('비밀번호가 틀렸습니다.');
  }
};

const handleSearch = () => {
  appliedKeyword.value = searchKeyword.value;
  currentPage.value = 1; // 검색 시 1페이지로 리셋
};
</script>

<style scoped>
/* 기존 스타일 유지 */
.qna-container { max-width: 800px; margin: 0 auto; padding: 20px; color: #333; font-family: 'Noto Sans KR', sans-serif; }
.qna-header h2 { text-align: center; font-size: 1.2rem; letter-spacing: 2px; margin-bottom: 40px; font-weight: 700; }
.qna-filter { border-bottom: 1px solid #e5e5e5; padding-bottom: 10px; }
.filter-select { border: none; font-size: 0.8rem; outline: none; cursor: pointer; color: #666; background: none; }
.qna-list { list-style: none; padding: 0; margin: 0; }
.qna-item { border-bottom: 1px solid #f2f2f2; padding: 15px 0; cursor: pointer; }
.qna-content-wrapper { display: flex; align-items: center; gap: 15px; }
.thumbnail img { width: 45px; height: 55px; object-fit: cover; background: #eee; }
.post-info { flex: 1; }
.subject { font-size: 0.85rem; margin-bottom: 5px; display: flex; align-items: center; gap: 5px; }
.re-tag { border: 1px solid #ddd; font-size: 0.65rem; padding: 1px 3px; color: #666; }
.info { font-size: 0.75rem; color: #a0a0a0; }
.info span { margin-right: 10px; }

/* 페이징 스타일 개선 */
.pagination { display: flex; justify-content: center; align-items: center; gap: 15px; margin: 30px 0; font-size: 0.8rem; color: #888; }
.page-num { cursor: pointer; padding: 2px 5px; }
.page-num.active { color: #000; text-decoration: underline; text-underline-offset: 4px; font-weight: bold; }
.prev, .next { cursor: pointer; color: #333; font-weight: bold; }
.prev.disabled, .next.disabled { color: #ccc; cursor: default; }

.search-section { border-top: 1px solid #eee; padding-top: 30px; max-width: 450px; margin: 0 auto; }
.search-row { display: flex; gap: 10px; }
.search-select { flex: 1; border: none; border-bottom: 1px solid #000; padding: 8px 0; font-size: 0.8rem; outline: none; }
.search-input-wrapper { display: flex; align-items: flex-end; margin-top: 10px; }
.search-input { flex: 1; border: none; border-bottom: 1px solid #000; padding: 10px 0; font-size: 0.8rem; outline: none; }
.search-btn { background: #000; color: #fff; border: none; padding: 11px 25px; font-size: 0.8rem; cursor: pointer; }
.password-check-container { padding: 80px 0; display: flex; justify-content: center; }
.password-box { text-align: center; width: 300px; }
.pw-input { width: 100%; border: none; border-bottom: 1px solid #000; padding: 10px 0; text-align: center; outline: none; margin-bottom: 20px; }
.pw-btns { display: flex; gap: 10px; }
.pw-btns button { flex: 1; padding: 12px; border: 1px solid #000; font-size: 0.8rem; cursor: pointer; }
.btn-pw-confirm { background: #000; color: #fff; }
.btn-pw-cancel { background: #fff; color: #000; }
.qna-detail { padding: 20px 0; }
.detail-header { border-bottom: 1px solid #000; padding-bottom: 15px; margin-bottom: 20px; }
.detail-info { font-size: 0.8rem; color: #888; margin-top: 10px; }
.detail-body { min-height: 150px; font-size: 0.9rem; line-height: 1.6; }
.btn-list { background: #000; color: #fff; border: none; padding: 10px 30px; cursor: pointer; margin-top: 30px; }
.floating-write-btn { position: fixed; bottom: 40px; right: 30px; width: 50px; height: 50px; background: #fff; border: 1px solid #000; border-radius: 50%; display: flex; align-items: center; justify-content: center; cursor: pointer; }
</style>