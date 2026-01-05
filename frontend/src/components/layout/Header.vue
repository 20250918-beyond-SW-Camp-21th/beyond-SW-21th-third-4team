<template>
  <header id="header_wrap_outer">
    <div id="header_wrap">
      
      <!-- [좌측] 카테고리 메뉴 -->
      <nav class="left_menu">
        <!-- 데스크탑 메뉴 -->
        <ul class="visible-desktop">
          <li
            v-for="(menu, index) in mainMenus"
            :key="index"
            class="menu_item"
            :class="{ 'highlight': menu.name === 'CLEARANCE' }"
            @mouseenter="menu.showSub = true"
            @mouseleave="menu.showSub = false"
          >
            <router-link :to="menu.link">
              {{ menu.name }}
            </router-link>

            <!-- 서브메뉴 드롭다운 -->
            <transition name="fade">
              <div v-if="menu.subItems" class="sub_cate_wrap" v-show="menu.showSub">
                <ul class="middle_cate">
                  <li v-for="(sub, subIndex) in menu.subItems" :key="subIndex">
                    <router-link :to="sub.link">{{ sub.name }}</router-link>
                  </li>
                </ul>
              </div>
            </transition>
          </li>
          
          <li class="menu_item search_item">
            <a href="#" @click.prevent="openSearchModal">검색</a>
          </li>
        </ul>

        <!-- 모바일 메뉴 (햄버거 + 검색 아이콘) -->
        <ul class="visible-mobile">
          <li class="icon_item hamburger">
            <a href="#" @click.prevent="toggleMobileMenu">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <line x1="3" y1="12" x2="21" y2="12"></line>
                <line x1="3" y1="6" x2="21" y2="6"></line>
                <line x1="3" y1="18" x2="21" y2="18"></line>
              </svg>
            </a>
          </li>
          <li class="icon_item search_icon">
            <a href="#" @click.prevent="openSearchModal">
              <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <circle cx="11" cy="11" r="8"></circle>
                <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
              </svg>
            </a>
          </li>
        </ul>
      </nav>

      <!-- [중앙] 로고 (절대 중앙 정렬) -->
      <div class="logo">
        <router-link to="/">
          <img src="https://insilence.co.kr/img/logo_black.png" alt="INSILENCE">
        </router-link>
      </div>

      <!-- [우측] 유틸리티 메뉴 -->
      <div class="right_menu">
        <!-- 데스크탑 메뉴 -->
        <ul class="visible-desktop">
          <li class="icon_item">
            <a href="#">
              <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <circle cx="12" cy="12" r="10"></circle>
                <line x1="2" y1="12" x2="22" y2="12"></line>
                <path d="M12 2a15.3 15.3 0 0 1 4 10 15.3 15.3 0 0 1-4 10 15.3 15.3 0 0 1-4-10 15.3 15.3 0 0 1 4-10z"></path>
              </svg>
            </a>
          </li>
          <li class="relative_item" @mouseenter="showCustomerSub = true" @mouseleave="showCustomerSub = false">
            <router-link to="/help">고객센터</router-link>
            <!-- 고객센터 드롭다운 -->
            <transition name="fade">
              <ul v-show="showCustomerSub" class="mini_dropdown">
                <li v-for="item in customerItems" :key="item.name">
                  <router-link :to="item.link">{{ item.name }}</router-link>
                </li>
              </ul>
            </transition>
          </li>
          <li class="relative_item" @mouseenter="showAccountSub = true" @mouseleave="showAccountSub = false">
            <router-link to="/login">계정</router-link>
            <!-- 계정 드롭다운 -->
            <transition name="fade">
              <ul v-show="showAccountSub" class="mini_dropdown">
                <li v-for="item in accountItems" :key="item.name">
                  <router-link v-if="item.link" :to="item.link">{{ item.name }}</router-link>
                  <a v-else href="#" @click.prevent="item.action">{{ item.name }}</a>
                </li>
              </ul>
            </transition>
          </li>
          <li>
            <router-link to="/cart">
              쇼핑백<span v-if="cartCount > 0">({{ cartCount }})</span>
            </router-link>
          </li>
          <li><router-link to="/membership">멤버쉽</router-link></li>
        </ul>

        <!-- 모바일 메뉴 (계정 + 장바구니 아이콘) -->
        <ul class="visible-mobile">
           <li class="icon_item">
             <router-link :to="isLoggedIn ? '/mypage' : '/login'">
               <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                 <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                 <circle cx="12" cy="7" r="4"></circle>
               </svg>
             </router-link>
           </li>
           <li class="icon_item">
             <router-link to="/cart">
               <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                 <circle cx="9" cy="21" r="1"></circle>
                 <circle cx="20" cy="21" r="1"></circle>
                 <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path>
               </svg>
             </router-link>
           </li>
        </ul>
      </div>

    </div>

    <!-- [모바일 메뉴 패널] -->
    <transition name="slide">
      <div v-if="mobileMenuOpen" class="mobile-menu-overlay">
        <div class="mobile-menu-panel">
          <!-- 닫기 버튼 -->
          <div class="mobile-menu-header">
            <button class="close-btn" @click="closeMobileMenu">닫기</button>
          </div>

          <!-- 메인 메뉴 -->
          <nav class="mobile-main-nav">
            <ul>
              <li v-for="(menu, index) in mainMenus" :key="index" :class="{ 'highlight': menu.name === 'CLEARANCE' }">
                <router-link :to="menu.link" @click="closeMobileMenu">
                  <span>{{ menu.name }}</span>
                  <span v-if="menu.subItems" class="arrow">›</span>
                </router-link>
              </li>
            </ul>
          </nav>

          <!-- 구분선 -->
          <div class="mobile-menu-divider"></div>

          <!-- 유틸리티 메뉴 -->
          <nav class="mobile-util-nav">
            <ul>
              <li v-for="(item, index) in mobileUtilMenus" :key="index">
                <router-link :to="item.link" @click="closeMobileMenu">
                  <span>{{ item.name }}</span>
                  <span v-if="item.hasArrow" class="arrow">›</span>
                </router-link>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </transition>

  </header>
</template>

<script setup>
import { ref, onMounted, computed, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import http from '../../api/http';
import { getToken, removeToken } from '../../utils/token';

const route = useRoute();
const router = useRouter();

const mainMenus = ref([
  { name: '남성복', link: '/product/list', subItems: null },
  { name: '여성복', link: '/product/list', subItems: null },
  { name: 'CLEARANCE', link: '/product/list', subItems: null },
  { name: '컬렉션', link: '/collection', subItems: null },
  { name: '컨텐츠', link: '/contents', subItems: null },
  { name: '매장안내', link: '/store', subItems: null },
]);

// [고객센터] 서브메뉴 데이터
const showCustomerSub = ref(false);
const customerItems = [
  { name: '공지사항', link: '/notice' },
  { name: '질문과답변', link: '/qna' },
  { name: '소재별 케어', link: '/care' },
];

// [계정] 서브메뉴 데이터 (로그인 여부에 따라 변경)
const showAccountSub = ref(false);
const isLoggedIn = ref(false);

const checkLoginStatus = () => {
  isLoggedIn.value = !!getToken();
};

const handleLogout = () => {
  removeToken();
  isLoggedIn.value = false;
  alert('로그아웃 되었습니다.');
  // 메인으로 이동 또는 새로고침
  if (route.path === '/') {
      window.location.reload();
  } else {
      router.push('/');
  }
};

const accountItems = computed(() => {
  if (isLoggedIn.value) {
    return [
      { name: '로그아웃', action: handleLogout },
      { name: '정보수정', link: '/mypage/profile' },
      { name: '마이 페이지', link: '/mypage' },
      { name: '주문조회', link: '/mypage/orders' },
    ];
  } else {
    return [
      { name: '로그인', link: '/login' },
      { name: '회원가입', link: '/signup/terms' },
      { name: '마이 페이지', link: '/mypage' },
      { name: '주문조회', link: '/mypage/orders' },
    ];
  }
});

watch(() => route.path, () => {
  checkLoginStatus();
});

// 장바구니 카운트 (초기값 0)
const cartCount = ref(0);

// 장바구니 개수 조회 Fetch 함수
const fetchCartCount = async () => {
  try {
    const response = await http.get('/cart/my');
    if (response.data && response.data.data) {
      cartCount.value = response.data.data.length;
    }
  } catch (error) {
    console.error('장바구니 조회 실패 (비로그인 상태일 수 있음):', error);
  }
};

onMounted(() => {
  checkLoginStatus();
  fetchCartCount();
});

const openSearchModal = () => {
  console.log("검색 모달 열기");
};

// 모바일 메뉴 상태
const mobileMenuOpen = ref(false);

const toggleMobileMenu = () => {
  mobileMenuOpen.value = !mobileMenuOpen.value;
  // body 스크롤 방지
  document.body.style.overflow = mobileMenuOpen.value ? 'hidden' : '';
};

const closeMobileMenu = () => {
  mobileMenuOpen.value = false;
  document.body.style.overflow = '';
};

// 모바일 메뉴용 유틸리티 메뉴 항목
const mobileUtilMenus = computed(() => [
  { name: '고객센터', link: '/notice', hasArrow: true },
  { name: '계정', link: isLoggedIn.value ? '/mypage' : '/login', hasArrow: true },
  { name: '쇼핑백', link: '/cart', hasArrow: false },
  { name: '멤버쉽', link: '/membership', hasArrow: false },
  { name: '언어(language)', link: '#', hasArrow: true },
]);

</script>

<style scoped>
/* [초기화 & 기본] */
* {
  box-sizing: border-box;
}

ul, li {
  list-style: none;
  padding: 0;
  margin: 0;
}

a {
  text-decoration: none;
  color: #000;
  transition: color 0.2s;
}

/* [레이아웃] 헤더 래퍼 */
#header_wrap_outer {
  width: 100%;
  background-color: #fff;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 1000;
}

#header_wrap {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 60px;
  padding: 0 40px;
  position: relative;
}

/* [공통] 메뉴 스타일 */
.left_menu ul, .right_menu ul {
  display: flex;
  gap: 25px;
  align-items: center;
}

.menu_item a, .right_menu a {
  font-size: 11px;
  font-weight: 700;
  letter-spacing: -0.2px;
  font-family: 'Pretendard', sans-serif;
  cursor: pointer;
}

/* [로고] 절대 중앙 정렬 */
.logo {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 20;
}

.logo img {
  height: 18px;
  display: block;
}

/* [강조] CLEARANCE */
.highlight a {
  color: #ff0000;
}

/* [강조] CLEARANCE */
.highlight a {
  color: #ff0000;
}

/* [드롭다운] */
/* [미니 드롭다운] - 고객센터, 계정 등 */
.relative_item {
  position: relative;
  height: 100%;
  display: flex;
  align-items: center;
}

/* 텍스트 수직 보정 (지구본 등 다른 아이콘과 높이 맞춤) */
.relative_item > a {
  padding-top: 3px;
  display: inline-block;
}

.mini_dropdown {
  position: absolute;
  top: 30px;
  left: 50%;
  transform: translateX(-50%);
  background: #fff;
  border: 1px solid #000;
  min-width: 110px; /* 너비 축소 */
  display: flex;
  flex-direction: column;
  gap: 0;
  z-index: 100;
  padding: 10px 0 !important; /* 상하 패딩 축소 */
  margin: 0 !important;
  list-style: none !important;
}

.mini_dropdown li {
  margin: 0 !important;
  padding: 0 !important;
  border: 0 !important;
  width: 100% !important;
  text-align: center !important; /* 중앙 정렬 */
  display: block !important;
  min-height: auto !important;
  height: auto !important;
  line-height: normal !important;
}

.mini_dropdown a {
  display: block !important;
  padding: 3px 0 !important; /* 항목 간격 더 배짝 붙임 */
  font-size: 11px !important;
  color: #000 !important;
  font-weight: 400 !important;
  text-decoration: none !important;
  white-space: nowrap !important;
  background-color: transparent !important;
  line-height: 1.2 !important; /* 줄 간격도 줄임 */
}

.mini_dropdown a:hover {
  text-decoration: underline !important;
  background-color: transparent !important;
}

.mini_dropdown a:hover {
  text-decoration: underline;
  background-color: transparent; /* 배경색 변경 없음 */
}

/* [대형 드롭다운 - 기존 유지] */
.sub_cate_wrap {
  position: absolute;
  top: 60px;
  left: 0;
  width: 100vw;
  background: #fff;
  padding: 15px 0;
  border-top: 1px solid #eee;
  border-bottom: 1px solid #eee;
  z-index: 10;
  display: flex;
  justify-content: center;
}

.middle_cate {
  display: flex;
  gap: 30px;
}

.middle_cate a {
  font-size: 11px;
  font-weight: 400;
  color: #555;
}

.middle_cate a:hover {
  text-decoration: underline;
  color: #000;
}

/* [트랜지션] */
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s ease;
}
.fade-enter-from, .fade-leave-to {
  opacity: 0;
}

/* [아이콘] */
.icon_item svg {
  vertical-align: middle;
  stroke: #000;
}

/* [반응형] 모바일/데스크탑 노출 여부 */
.visible-mobile {
  display: none !important;
}

/* [미디어 쿼리] 모바일 (< 1024px) */
@media (max-width: 1024px) {
  /* 노출 토글 */
  .visible-desktop {
    display: none !important;
  }
  .visible-mobile {
    display: flex !important;
    align-items: center;
    gap: 15px !important;
  }

  /* 레이아웃 조정 */
  #header_wrap {
    height: 50px; /* 모바일 헤더 높이 축소 */
    padding: 0 20px; /* 좌우 여백 축소 */
  }

  .logo img {
    height: 16px; /* 로고 크기 미세 축소 */
  }
  
  /* 좌/우 메뉴 간격 조정 */
  .left_menu ul, .right_menu ul {
    gap: 15px; 
  }
  
  /* 아이콘 크기 미세 조정 */
  .hamburger svg {
    width: 24px;
    height: 24px;
  }
  .search_icon svg {
    width: 20px;
    height: 20px;
  }
}

/* ========================================= */
/* [모바일 메뉴 패널 스타일] */
/* ========================================= */
.mobile-menu-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: #fff;
  z-index: 9999;
  overflow-y: auto;
}

.mobile-menu-panel {
  padding: 20px;
  min-height: 100vh;
}

.mobile-menu-header {
  display: flex;
  justify-content: flex-end;
  padding-bottom: 30px;
}

.close-btn {
  background: none;
  border: none;
  font-size: 14px;
  color: #000;
  cursor: pointer;
  font-family: inherit;
  padding: 5px 10px;
}

.close-btn:hover {
  text-decoration: underline;
}

/* 메인 메뉴 */
.mobile-main-nav ul,
.mobile-util-nav ul {
  list-style: none;
  margin: 0;
  padding: 0;
}

.mobile-main-nav li,
.mobile-util-nav li {
  border-bottom: none;
}

.mobile-main-nav a,
.mobile-util-nav a {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 0;
  font-size: 12px;
  color: #000;
  text-decoration: none;
  font-weight: 700;
  font-family: 'Pretendard', sans-serif;
  letter-spacing: -0.2px;
}

.mobile-main-nav a:hover,
.mobile-util-nav a:hover {
  text-decoration: underline;
}

/* CLEARANCE 빨간색 강조 */
.mobile-main-nav li.highlight a {
  color: #c00;
  font-weight: 500;
}

.mobile-main-nav li.highlight .arrow {
  color: #c00;
}

/* 화살표 */
.arrow {
  font-size: 18px;
  color: #999;
  font-weight: 300;
}

/* 구분선 */
.mobile-menu-divider {
  height: 1px;
  background: #eee;
  margin: 20px 0;
}

/* 슬라이드 트랜지션 */
.slide-enter-active,
.slide-leave-active {
  transition: transform 0.3s ease, opacity 0.3s ease;
}

.slide-enter-from {
  opacity: 0;
  transform: translateX(-100%);
}

.slide-leave-to {
  opacity: 0;
  transform: translateX(-100%);
}
</style>