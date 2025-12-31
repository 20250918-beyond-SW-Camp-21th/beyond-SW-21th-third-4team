<template>
  <main>
    <!-- 1. 메인 배너 -->
    <MainBanner />

    <!-- 2. 베스트 상품 목록  -->
    <BestProductList />
  </main>
</template>

<script setup>
// 메인페이지 중앙부 사진
import MainBanner from '../../components/main/MainBanner.vue';

// 메인페이지 중앙부 베스트 컬렉션
import BestProductList from '../../components/main/BestProductList.vue';
</script>

<style scoped>
/* 메인 페이지 전체에 필요한 스타일이 있다면 여기에 작성 */
</style>
