<template>
  <div id="app-layout">

    <Header />

    <main class="content-body">
      <router-view />
    </main>

  </div>
</template>

<script setup>
// Header.vue 파일 위치에 맞게 경로를 적어주세요.
// 보통 src/components 안에 넣습니다.
import Header from './components/layout/Header.vue';
</script>

<style scoped>
/* 전체 레이아웃 관련 스타일 */
.content-body {
  width: 100%;
  /* 헤더 높이(80px)를 뺀 만큼 최소 높이 잡기 */
  min-height: calc(100vh - 80px);
}
</style>