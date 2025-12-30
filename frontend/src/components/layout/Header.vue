<template>
  <header id="header_wrap_outer">
    <div id="header_wrap">

      <nav class="nav">
        <ul>
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

            <div v-if="menu.subItems" class="sub_cate_wrap" v-show="menu.showSub">
              <ul class="middle_cate">
                <li v-for="(sub, subIndex) in menu.subItems" :key="subIndex">
                  <router-link :to="sub.link">{{ sub.name }}</router-link>
                </li>
              </ul>
            </div>
          </li>

          <li class="menu_item search_item">
            <a href="#" @click.prevent="openSearchModal">검색</a>
          </li>
        </ul>
      </nav>

      <div class="logo">
        <router-link to="/">
          <img src="https://insilence.co.kr/img/logo_black.png" alt="INSILENCE">
        </router-link>
      </div>

      <div class="header_right">
        <ul>
          <li><a href="#"><i class="xi-globus"></i></a></li>

          <li><router-link to="/help">고객센터</router-link></li>
          <li><router-link to="/login">계정</router-link></li>
          <li>
            <router-link to="/cart">
              쇼핑백
            </router-link>
          </li>
          <li><router-link to="/membership">멤버쉽</router-link></li>
        </ul>
      </div>

    </div>
  </header>
</template>

<script setup>
import { ref } from 'vue';

const mainMenus = ref([
  {
    name: '남성복',
    link: '/men',
    showSub: false,
    subItems: [
      { name: 'ALL', link: '/men/all' },
      { name: 'BEST', link: '/men/best' },
      { name: 'OUTER', link: '/men/outer' },
    ]
  },
  {
    name: '여성복',
    link: '/women',
    showSub: false,
    subItems: [
      { name: 'ALL', link: '/women/all' },
      { name: 'BEST', link: '/women/best' },
    ]
  },
  { name: 'CLEARANCE', link: '/clearance', subItems: null },
  { name: '컬렉션', link: '/collection', subItems: null },
  { name: '컨텐츠', link: '/contents', subItems: null },
  { name: '매장안내', link: '/store', subItems: null },
]);

const openSearchModal = () => {
  console.log("검색창 열기");
};
</script>

<style scoped>
/* 기본 설정 */
* {
  box-sizing: border-box;
}

ul, li {
  list-style: none;
  padding: 0;
  margin: 0;
}

#header_wrap_outer {
  width: 100%;
  background-color: #fff;
  /* 하단 선이 필요 없다면 제거하세요 */
  /* border-bottom: 1px solid #eee; */
  position: fixed; /* 헤더 상단 고정 (선택 사항) */
  top: 0;
  left: 0;
  z-index: 1000;
}

#header_wrap {
  display: flex;
  justify-content: space-between; /* 양쪽 끝으로 밀어내기 */
  align-items: center;
  height: 70px; /* 높이 적절히 조정 */
  width: 100%; /* 화면 꽉 차게 */
  padding: 0 50px; /* 좌우 여백을 충분히 줌 */
  position: relative;
}

/* --- 왼쪽 메뉴 & 오른쪽 메뉴 공통 --- */
.nav ul, .header_right ul {
  display: flex;
  gap: 35px; /* 메뉴 사이 간격 넓힘 */
  align-items: center;
}

/* 폰트 스타일 (원본과 비슷하게) */
a {
  text-decoration: none;
  color: #000;
  font-size: 13px;
  font-weight: 700; /* 조금 굵게 */
  font-family: 'Pretendard', sans-serif; /* 웹폰트가 있다면 적용 */
  letter-spacing: -0.5px;
}

/* --- 중앙 로고 --- */
.logo {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%); /* 정확히 정중앙 배치 */
  z-index: 10;
}

.logo img {
  height: 22px; /* 로고 크기 조정 (원본 비율 유지) */
  display: block; /* 이미지 하단 여백 제거 */
}

/* --- 강조 (CLEARANCE) --- */
.highlight a {
  color: #ff0000;
}

/* --- 지구본 아이콘 --- */
.xi-globus {
  font-size: 20px;
  vertical-align: sub; /* 위치 미세 조정 */
}

/* --- 드롭다운 (숨김/표시) --- */
.sub_cate_wrap {
  position: absolute;
  top: 70px; /* 헤더 높이만큼 */
  left: 0;
  width: 100vw; /* 화면 전체 너비 */
  background: #fff;
  border-top: 1px solid #eee;
  border-bottom: 1px solid #eee;
  padding: 20px 0;
  z-index: 90;
}

.middle_cate {
  display: flex;
  justify-content: center;
  gap: 30px;
}
</style>