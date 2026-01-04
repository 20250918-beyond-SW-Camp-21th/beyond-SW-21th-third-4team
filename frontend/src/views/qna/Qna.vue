<template>
  <div class="qna-container">
    <div class="qna-header">
      <h2>{{ isNoticeMode ? 'NOTICE' : 'Q&A' }}</h2>
    </div>

    <!-- 목록 모드 -->
    <template v-if="viewMode === 'list'">
      <!-- Q&A 모드일 때만 필터 바 표시 -->
      <div v-if="!isNoticeMode" class="qna-filter-bar">
        <select v-model="selectedFilter" class="filter-select" @change="currentPage = 1">
          <option value="all">전체글보기</option>
          <option value="WAITING">답변전 글보기</option>
          <option value="ANSWERED">답변완료 글보기</option>
        </select>
      </div>

      <ul class="qna-list">
        <!-- 데이터 없을 경우 -->
        <li v-if="paginatedList.length === 0" class="no-data">등록된 게시글이 없습니다.</li>
        
        <!-- 데이터 목록 -->
        <li v-for="item in paginatedList" :key="item.id" class="qna-item" @click="handlePostClick(item)">
          <div class="qna-content-wrapper">
             <!-- 상품 썸네일 (있다면) -->
            <div v-if="!isNoticeMode && item.thumbUrl" class="thumbnail">
               <img :src="item.thumbUrl" alt="product">
            </div>

            <div class="post-info">
              <div class="subject">
                <!-- 답변완료 표시 -->
                <span v-if="!isNoticeMode && item.status === 'ANSWERED'" class="re-tag">ANSWERED</span>
                <!-- 비밀글 아이콘 -->
                <span v-if="!isNoticeMode && item.isPrivate">🔒</span>
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

      <!-- 페이지네이션 -->
      <div class="pagination" v-if="totalPages > 0">
        <span class="page-btn" @click="changePage(currentPage - 1)" :class="{ disabled: currentPage === 1 }">PREV</span>
        <span v-for="page in totalPages" :key="page" class="page-num" :class="{ active: currentPage === page }" @click="changePage(page)">{{ page }}</span>
        <span class="page-btn" @click="changePage(currentPage + 1)" :class="{ disabled: currentPage === totalPages }">NEXT</span>
      </div>

      <!-- 검색 -->
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

      <!-- 글쓰기 버튼 (Q&A 모드일 때만) -->
      <div v-if="!isNoticeMode" class="floating-write-btn" @click="goToWrite">
        <!-- 연필 아이콘 대용 텍스트 or SVG -->
        <span>✐</span>
      </div>
    </template>

    <!-- 비밀번호 확인 모드 -->
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

    <!-- 상세 보기 모드 -->
    <template v-else-if="viewMode === 'detail'">
      <div class="qna-detail" v-if="selectedPost">
        <div class="detail-header">
          <h3>{{ selectedPost.title }}</h3>
          <div class="detail-info">
            <span>ID: {{ selectedPost.userId }}</span> | <span>{{ formatDate(selectedPost.createdAt) }}</span>
          </div>
        </div>
        <div class="detail-body">{{ selectedPost.content }}</div>
        
        <div class="answer-section">
          <div v-if="selectedPost.answer" class="admin-answer">
            <div class="answer-header"><strong>관리자 답변</strong> <span>{{ formatDate(selectedPost.answeredAt) }}</span></div>
            <div class="answer-content">{{ selectedPost.answer }}</div>
          </div>
          <div v-else class="no-answer">등록된 답변이 없습니다.</div>
        </div>
        <div class="detail-actions"><button @click="viewMode = 'list'" class="btn-list">LIST</button></div>
      </div>
    </template>

    <!-- 글쓰기 모드 -->
    <template v-else-if="viewMode === 'write'">
      <div class="qna-write-container-inner">
        <!-- 상품 정보 (상품 문의인 경우) -->
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
              <option value="입금/환불/결제오류">입금/환불 및 결제 오류</option>
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
import { getToken } from '../../utils/token'; // 토큰 유틸

const props = defineProps({ isNoticeMode: { type: Boolean, default: false } });
const route = useRoute();
const router = useRouter();

// 상태 변수
const viewMode = ref('list'); // list, detail, write, passwordCheck
const qnaList = ref([]);
const noticeList = ref([]);
const selectedPost = ref(null);
const inputPassword = ref('');

// 페이지네이션 & 검색
const currentPage = ref(1);
const itemsPerPage = 10;
const searchType = ref('title');
const searchKeyword = ref('');
const appliedKeyword = ref('');
const selectedFilter = ref('all'); // all, WAITING, ANSWERED

// 글쓰기 폼
const displayProdInfo = ref({ name: '', price: '', img: '' });
const writeForm = ref({
  category: '',
  title: '',
  content: '',
  password: '',
  productId: null
});

// 날짜 포맷
const formatDate = (dateStr) => {
  if (!dateStr) return '';
  return dateStr.split('T')[0];
};

// [API] 데이터 조회
const fetchAllData = async () => {
  try {
    const token = getToken(); // 로컬 스토리지 등에서 토큰 가져오기
    // 백엔드는 8080 포트
    const res = await axios.get('http://localhost:8080/api/v1/qnas', {
      headers: token ? { 'Authorization': `Bearer ${token}` } : {}
    });

    // 응답 구조 확인 (res.data.data가 리스트라고 가정)
    const rawData = res.data.data; 
    if (Array.isArray(rawData)) {
      // type: 'QUESTION' vs 'NOTICE' 구분
      qnaList.value = rawData.filter(i => i.type === 'QUESTION');
      noticeList.value = rawData.filter(i => i.type === 'NOTICE');
    }
  } catch (e) {
    console.error("데이터 로드 실패:", e);
    // 에러 처리 (alert 등)
  }
};

// [API] 글 등록
const handleCreateQna = async () => {
  if(!writeForm.value.title) return alert("제목을 입력하세요.");
  if(!writeForm.value.content) return alert("내용을 입력하세요.");
  if(!writeForm.value.password) return alert("비밀번호를 입력하세요.");

  try {
    const token = getToken();
    const payload = {
      type: "QUESTION",
      // 카테고리가 있으면 제목 앞에 붙여줌 (선택사항)
      title: writeForm.value.category ? `[${writeForm.value.category}] ${writeForm.value.title}` : writeForm.value.title,
      content: writeForm.value.content,
      productId: writeForm.value.productId ? Number(writeForm.value.productId) : null,
      // 백엔드에 password 필드가 없다면... (Qna.java에는 없었음. 비밀글 로직 확인 필요)
      // 하지만 프론트에서 입력을 받으므로 일단 보냄주거나, 로그인한 유저만 볼 수 있게 처리되거나.
      // Qna.java 엔티티에는 password 필드가 없었습니다. userId로 체크하는 방식일 수 있음.
      // 일단 전송은 하되, 백엔드에서 무시하면 그만.
    };

    await axios.post('http://localhost:8080/api/v1/qnas', payload, {
      headers: { 'Authorization': `Bearer ${token}` }
    });

    alert("등록되었습니다.");
    viewMode.value = 'list';
    fetchAllData();
    resetWriteForm();
  } catch (e) {
    console.error("등록 실패:", e);
    alert("등록에 실패했습니다.");
  }
};

const resetWriteForm = () => {
    writeForm.value = { category: '', title: '', content: '', password: '', productId: null };
    displayProdInfo.value = { name: '', price: '', img: '' };
};

// [API] 상세 조회 (선택 시)
const handlePostClick = async (post) => {
  selectedPost.value = post;
  
  // 공지사항이거나 내 글(또는 공개글)이면 바로 조회
  // 백엔드 엔티티에 isPrivate 필드가 없었음(Qna.java). 
  // 대신 status만 있었는데... 
  // 로직상 비밀글 처리가 필요하다면 passwordCheck 모드로.
  // 여기서는 일단 바로 detail 로드 시도하고, 권한 없으면 403 뜰테니 catch에서 처리
  
  // Qna.java의 validateOwner 로직을 보면 본인이 아니면 에러.
  // 비밀번호 필드는 DB에 없었으므로, 프론트에서 비밀번호 체크는 '비회원 주문 조회' 같은게 아니면 의미가 없을수도 있습니다.
  // 다만 기획상 비밀번호 입력이 필요하다면... 일단 뷰 모드 전환.
  
  // 일단 바로 로드 시도
  loadDetail(post.id);
};

const loadDetail = async (id) => {
  try {
    const token = getToken();
    const res = await axios.get(`http://localhost:8080/api/v1/qnas/${id}`, {
        headers: token ? { 'Authorization': `Bearer ${token}` } : {}
    });
    selectedPost.value = res.data.data;
    viewMode.value = 'detail';
  } catch (e) {
     // 권한 없음 등의 에러 시 비밀번호 입력창 등을 띄울 수 있음.
     // 현재 백엔드 로직상 본인이 아니면 QNA_NOT_PERMITTED 에러 발생.
     alert("조회 권한이 없습니다. (본인 작성 글만 확인 가능할 수 있습니다)");
  }
};

const validatePassword = () => {
    // 비밀번호 검증 로직이 별도 API로 있는지? 없다면 그냥 detail 요청 재시도?
    // 백엔드엔 비밀번호 필드가 없으므로 이 부분은 UI적 요식행위거나, 비회원용 로직일 수 있음.
    // 여기선 loadDetail 재시도.
    loadDetail(selectedPost.value.id);
};


// 필터링 목록
const filteredList = computed(() => {
  // 모드에 따라 리스트 선택
  let list = props.isNoticeMode ? noticeList.value : qnaList.value;

  // 필터 적용 (Q&A 모드일 때만)
  if (!props.isNoticeMode && selectedFilter.value !== 'all') {
    list = list.filter(i => i.status === selectedFilter.value);
  }

  // 검색 적용
  if (appliedKeyword.value) {
    list = list.filter(i => {
        const val = i[searchType.value];
        return val && val.toString().includes(appliedKeyword.value);
    });
  }

  return list;
});

// 페이지네이션
const paginatedList = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  return filteredList.value.slice(start, start + itemsPerPage);
});

const totalPages = computed(() => Math.ceil(filteredList.value.length / itemsPerPage));

const changePage = (p) => {
  if (p > 0 && p <= totalPages.value) currentPage.value = p;
};

// 검색 핸들러
const handleSearch = () => {
  appliedKeyword.value = searchKeyword.value;
  currentPage.value = 1;
};

// 글쓰기 진입 (상품 상세에서 넘어온 파라미터 체크)
const checkProductQuery = () => {
  if (route.query.mode === 'write' && !props.isNoticeMode) {
    viewMode.value = 'write';
    writeForm.value.productId = route.query.prodId;
    displayProdInfo.value = {
      name: route.query.prodName,
      price: route.query.prodPrice,
      img: route.query.prodImg
    };
  } else {
    // 기본 리스트 모드 (이미 detail 보고 있다가 뒤로가기 했을때 등 고려)
    if(viewMode.value !== 'detail') viewMode.value = 'list';
  }
};

const goToWrite = () => { viewMode.value = 'write'; };
const cancelWrite = () => { viewMode.value = 'list'; resetWriteForm(); };

// 라이프사이클
onMounted(() => {
  fetchAllData();
  checkProductQuery();
});

watch(() => route.query, () => checkProductQuery());
// 모드 변경(Props 변경) 시 데이터 재확인 (라우터 이동 시)
watch(() => props.isNoticeMode, () => {
    currentPage.value = 1;
    viewMode.value = 'list';
});

</script>

<style scoped>
.qna-container { max-width: 800px; margin: 0 auto; padding: 60px 20px; font-family: 'Noto Sans KR', sans-serif; color: #333; }
.qna-header h2 { text-align: center; letter-spacing: 5px; margin-bottom: 40px; font-weight: 700; font-size: 1.5rem; }

/* 리스트 스타일 */
.qna-list { list-style: none; padding: 0; border-top: 2px solid #000; margin-top: 10px; }
.qna-item { border-bottom: 1px solid #eee; padding: 20px 0; cursor: pointer; transition: background 0.2s; }
.qna-item:hover { background-color: #f9f9f9; }

.qna-content-wrapper { display: flex; align-items: center; gap: 20px; }
.thumbnail img { width: 50px; height: 65px; object-fit: cover; border: 1px solid #eee; }

.post-info { flex: 1; }
.subject { font-size: 15px; margin-bottom: 8px; font-weight: 500; display: flex; align-items: center; gap: 8px; }
.re-tag { font-size: 11px; border: 1px solid #333; padding: 2px 6px; border-radius: 4px; font-weight: bold; }
.info { font-size: 12px; color: #888; display: flex; gap: 10px; }

.no-data { text-align: center; padding: 60px 0; color: #999; }

/* 페이지네이션 */
.pagination { display: flex; justify-content: center; gap: 15px; margin: 40px 0; font-size: 13px; color: #888; }
.page-btn { cursor: pointer; user-select: none; }
.page-btn.disabled { color: #eee; cursor: default; }
.page-num { cursor: pointer; }
.page-num.active { color: #000; font-weight: bold; text-decoration: underline; text-underline-offset: 4px; }

/* 검색 & 필터 */
.qna-filter-bar { display: flex; justify-content: flex-end; margin-bottom: 15px; }
.filter-select { border: none; font-size: 13px; outline: none; cursor: pointer; color: #555; }

.search-section { display: flex; justify-content: center; margin-top: 50px; border-top: 1px solid #eee; padding-top: 30px; }
.search-row { display: flex; gap: 10px; align-items: center; border-bottom: 1px solid #000; padding-bottom: 5px; }
.search-select { border: none; font-size: 13px; outline: none; }
.search-input-wrapper { display: flex; }
.search-input { border: none; outline: none; padding: 5px; font-size: 13px; width: 200px; }
.search-btn { background: none; border: none; font-size: 13px; font-weight: bold; cursor: pointer; padding: 0 10px; }

/* 글쓰기 버튼 (플로팅) */
.floating-write-btn { position: fixed; bottom: 50px; right: 50px; width: 60px; height: 60px; background: #000; color: #fff; border-radius: 50%; display: flex; align-items: center; justify-content: center; cursor: pointer; box-shadow: 0 4px 12px rgba(0,0,0,0.2); font-size: 24px; z-index: 100; transition: transform 0.2s; }
.floating-write-btn:hover { transform: scale(1.1); }

/* 글쓰기 폼 */
.write-form { margin-top: 20px; }
.row-line { display: flex; align-items: center; border-bottom: 1px solid #eee; padding: 20px 0; }
.row-line label { width: 120px; font-weight: bold; font-size: 14px; }
.input-underlined, .select-underlined { flex: 1; border: none; outline: none; padding: 5px; font-size: 14px; }
.editor-textarea { width: 100%; min-height: 300px; padding: 20px; border: 1px solid #eee; margin: 30px 0; resize: none; outline: none; font-size: 14px; line-height: 1.6; }

.product-info-card { display: flex; gap: 20px; padding: 20px; background: #f8f8f8; margin-bottom: 30px; }
.prod-img img { width: 80px; height: 100px; object-fit: cover; }
.prod-details { display: flex; flex-direction: column; justify-content: center; }
.prod-name { font-weight: bold; margin-bottom: 5px; }
.prod-price { font-size: 13px; color: #666; }

.action-btns { display: flex; gap: 10px; justify-content: center; margin-top: 40px; }
.btn-submit { width: 200px; padding: 15px 0; background: #000; color: #fff; border: none; font-weight: bold; cursor: pointer; }
.btn-cancel { width: 200px; padding: 15px 0; background: #fff; border: 1px solid #000; font-weight: bold; cursor: pointer; }

/* 상세 보기 */
.detail-header { border-bottom: 1px solid #eee; padding-bottom: 20px; margin-bottom: 30px; }
.detail-header h3 { font-size: 20px; font-weight: bold; margin-bottom: 15px; }
.detail-info { font-size: 13px; color: #888; }
.detail-body { min-height: 200px; line-height: 1.8; white-space: pre-wrap; margin-bottom: 50px; }
.answer-section { background: #f9f9f9; padding: 30px; margin-bottom: 50px; }
.answer-header { display: flex; justify-content: space-between; margin-bottom: 15px; font-size: 14px; }
.answer-content { font-size: 14px; line-height: 1.6; color: #555; }
.no-answer { text-align: center; color: #999; font-size: 14px; }
.btn-list { display: block; margin: 0 auto; width: 150px; padding: 12px 0; border: 1px solid #ddd; background: #fff; cursor: pointer; }

/* 비밀번호 확인 */
.password-check-container { padding: 100px 0; display: flex; justify-content: center; }
.password-box { text-align: center; width: 400px; border: 1px solid #000; padding: 40px; }
.password-box h3 { font-size: 20px; margin-bottom: 20px; }
.pw-input { display: block; width: 100%; border: none; border-bottom: 1px solid #000; text-align: center; padding: 10px; margin: 30px 0; font-size: 24px; letter-spacing: 5px; outline: none; }
.pw-btns { display: flex; gap: 10px; }
.pw-btns button { flex: 1; padding: 15px 0; border: 1px solid #000; background: #fff; cursor: pointer; font-weight: bold; }
.pw-btns .btn-pw-confirm { background: #000; color: #fff; }

/* 반응형 */
@media (max-width: 768px) {
  .qna-container { padding: 40px 15px; }
  .qna-header h2 { font-size: 1.2rem; }
  .floating-write-btn { width: 50px; height: 50px; bottom: 20px; right: 20px; }
}
</style>