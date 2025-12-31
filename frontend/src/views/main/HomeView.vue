<template>
  <main>
    <!-- 3. 여기에 배너 부품을 갖다 씁니다 -->
    <MainBanner />

    <!-- 그 외 메인 페이지의 다른 내용들이 아래에 들어갑니다 -->
    <div style="text-align: center; padding: 50px;">
      <h2>여기는 메인 페이지의 나머지 내용입니다.</h2>
    </div>
  </main>
</template>

<script setup>
// 1. 아까 만든 부품 파일을 가져옵니다.
// (경로 설명: @는 src 폴더를 의미:ㅈㅂ합니다. 혹은 ../../components/MainBanner.vue 로 해도 됩니다)
import MainBanner from '../../components/main/MainBanner.vue';
</script>

<style scoped>
/* 메인 페이지 전체에 필요한 스타일이 있다면 여기에 작성 */
</style>
