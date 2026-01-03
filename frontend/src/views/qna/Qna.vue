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
        <span v-for="page in totalPages" :key="page" class="page-num" :class="{ active: currentPage === page }" @click="changePage(page)">
          {{ page }}
        </span>
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

      <div v-if="!isNoticeMode" class="floating-write-btn" @click="viewMode = 'write'">
        <i class="xi-pen"></i>
      </div>
    </template>

    <template v-else-if="viewMode === 'passwordCheck'"></template>
    <template v-else-if="viewMode === 'detail'"></template>
    <template v-else-if="viewMode === 'write'"></template>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue';
import axios from 'axios';

const props = defineProps({
  isNoticeMode: { type: Boolean, default: false }
});

const viewMode = ref('list');
const qnaList = ref([]);
const noticeList = ref([]);
const selectedPost = ref(null);
const currentPage = ref(1);
const itemsPerPage = 10;

// [복구] 검색 및 기간 상태
const searchPeriod = ref('all');
const searchType = ref('subject');
const searchKeyword = ref('');
const appliedKeyword = ref(''); // 검색 버튼을 눌렀을 때만 반영되도록
const selectedFilter = ref('all');

// [복구] 기간 + 검색 + 상태 필터링 로직
const filteredList = computed(() => {
  let list = props.isNoticeMode ? noticeList.value : qnaList.value;

  return list.filter(item => {
    // 1. 답변 상태 필터 (Q&A 모드일 때만)
    const matchStatus = props.isNoticeMode || selectedFilter.value === 'all' || item.status === selectedFilter.value;

    // 2. 기간 필터 로직
    let matchPeriod = true;
    if (searchPeriod.value !== 'all') {
      const today = new Date();
      const postDate = new Date(item.createdAt);
      const diffTime = Math.abs(today - postDate);
      const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
      matchPeriod = diffDays <= parseInt(searchPeriod.value);
    }

    // 3. 키워드 검색
    const matchKeyword = item[searchType.value]?.includes(appliedKeyword.value);

    return matchStatus && matchPeriod && matchKeyword;
  });
});

const paginatedList = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  return filteredList.value.slice(start, start + itemsPerPage);
});

const totalPages = computed(() => Math.ceil(filteredList.value.length / itemsPerPage));

const handleSearch = () => {
  appliedKeyword.value = searchKeyword.value;
  currentPage.value = 1;
};

// ... (나머지 로드 및 작성 함수들은 이전과 동일) ...
const fetchAllData = async () => {
  try {
    const res = await axios.get('/api/v1/qnas');
    const data = res.data.data;
    noticeList.value = data.filter(i => i.isNotice);
    qnaList.value = data.filter(i => !i.isNotice);
  } catch (e) { console.error(e); }
};
onMounted(fetchAllData);
const changePage = (p) => { if (p > 0 && p <= totalPages.value) currentPage.value = p; };
</script>

<style scoped>
/* 이전 스타일 유지 */
.qna-container { max-width: 800px; margin: 0 auto; padding: 40px 20px; color: #333; font-family: 'Noto Sans KR', sans-serif; }
.qna-header h2 { text-align: center; font-size: 1.2rem; letter-spacing: 4px; margin-bottom: 50px; font-weight: 700; }

/* 검색바 스타일 (기간 필터 포함) */
.search-section { border-top: 1px solid #eee; padding-top: 30px; margin-top: 20px; }
.search-row { display: flex; justify-content: center; gap: 15px; max-width: 600px; margin: 0 auto; }
.search-select { border: none; border-bottom: 1px solid #000; padding: 8px 0; font-size: 0.75rem; outline: none; background: none; }
.period-select { width: 90px; } /* 기간 선택용 너비 */
.search-input-wrapper { display: flex; flex: 1; border-bottom: 1px solid #000; }
.search-input { border: none; padding: 8px 0; font-size: 0.75rem; outline: none; flex: 1; }
.search-btn { background: #000; color: #fff; border: none; padding: 0 15px; font-size: 0.7rem; cursor: pointer; letter-spacing: 1px; }

/* 나머지 스타일 생략 (이전과 동일) */
.qna-list { list-style: none; padding: 0; margin: 0; border-top: 1px solid #333; }
.qna-item { border-bottom: 1px solid #f2f2f2; padding: 18px 0; cursor: pointer; }
.qna-content-wrapper { display: flex; align-items: center; gap: 20px; }
.subject { font-size: 0.85rem; display: flex; align-items: center; gap: 8px; }
.info { font-size: 0.75rem; color: #aaa; margin-top: 6px; }
.pagination { display: flex; justify-content: center; align-items: center; gap: 20px; margin: 40px 0; font-size: 0.75rem; color: #888; }
.page-num.active { color: #000; font-weight: bold; text-decoration: underline; text-underline-offset: 4px; }
</style>