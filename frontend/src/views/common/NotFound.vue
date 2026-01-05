<template>
  <main class="nf">
    <section class="card">
      <p class="eyebrow">PAGE NOT FOUND</p>

      <h1 class="title">
        404
        <span class="dot">.</span>
      </h1>

      <p class="desc">
        요청하신 페이지를 찾을 수 없어요.<br />
        주소가 변경되었거나, 존재하지 않는 경로일 수 있습니다.
      </p>

      <div class="actions">
        <button class="btn primary" @click="goHome">메인으로</button>
        <button class="btn ghost" @click="goBack">뒤로가기</button>
      </div>

      <p class="hint">
        입력한 URL: <span class="path">{{ route.fullPath }}</span>
      </p>
    </section>
  </main>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

const goHome = () => router.push('/')
const goBack = () => router.back()
</script>

<style scoped>
/* Insilence-ish: monochrome, minimal, sharp */
.nf {
  min-height: calc(100vh - 60px);
  display: grid;
  place-items: center;
  padding: 48px 20px;
  background:
      radial-gradient(1200px 600px at 20% 10%, rgba(255,255,255,0.06), transparent 60%),
      radial-gradient(900px 500px at 80% 90%, rgba(255,255,255,0.05), transparent 60%),
      #0b0b0c;
  color: #f5f5f5;
  font-family: ui-sans-serif, system-ui, -apple-system, Segoe UI, Roboto, Helvetica, Arial;
}

.card {
  width: min(560px, 100%);
  border: 1px solid rgba(255,255,255,0.10);
  background: rgba(255,255,255,0.04);
  backdrop-filter: blur(10px);
  border-radius: 18px;
  padding: 28px 26px;
  box-shadow: 0 18px 60px rgba(0,0,0,0.55);
}

.eyebrow {
  letter-spacing: 0.24em;
  font-size: 12px;
  color: rgba(255,255,255,0.65);
  margin: 0 0 14px;
}

.title {
  font-size: 72px;
  line-height: 1;
  margin: 0 0 10px;
  font-weight: 800;
}

.dot {
  color: rgba(255,255,255,0.35);
}

.desc {
  margin: 0 0 22px;
  color: rgba(255,255,255,0.75);
  font-size: 15px;
  line-height: 1.6;
}

.actions {
  display: flex;
  gap: 10px;
  margin-bottom: 16px;
}

.btn {
  border-radius: 12px;
  padding: 10px 14px;
  font-size: 14px;
  cursor: pointer;
  border: 1px solid rgba(255,255,255,0.16);
  background: transparent;
  color: #f5f5f5;
  transition: transform 0.12s ease, background 0.12s ease, border-color 0.12s ease;
}

.btn:hover {
  transform: translateY(-1px);
  border-color: rgba(255,255,255,0.28);
}

.btn.primary {
  background: rgba(255,255,255,0.12);
}

.btn.primary:hover {
  background: rgba(255,255,255,0.18);
}

.btn.ghost {
  background: transparent;
}

.hint {
  margin: 0;
  font-size: 12px;
  color: rgba(255,255,255,0.55);
}

.path {
  font-family: ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, "Liberation Mono", monospace;
  color: rgba(255,255,255,0.8);
  word-break: break-all;
}
</style>
