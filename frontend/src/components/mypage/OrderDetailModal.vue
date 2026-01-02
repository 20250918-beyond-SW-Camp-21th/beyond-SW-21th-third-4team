<template>
  <div class="modal-overlay" @click.self="close">
    <div class="modal-content">
      <div class="modal-header">
        <h2>주문 상세</h2>
        <button @click="close" class="close-btn">&times;</button>
      </div>

      <div class="modal-body">
        <p class="not-implemented">주문 상세 기능은 아직 구현되지 않았습니다.</p>
        <p class="order-id">Order ID: {{ orderId }}</p>
      </div>

      <div class="modal-footer">
        <button @click="close" class="btn-close">닫기</button>
      </div>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  orderId: {
    type: [Number, String],
    required: true
  }
})

const emit = defineEmits(['close'])

function close() {
  emit('close')
}
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  background: #fff;
  max-width: 600px;
  width: 90%;
  max-height: 80vh;
  overflow-y: auto;
  border-radius: 4px;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.5rem;
  border-bottom: 1px solid var(--color-border);
}

.modal-header h2 {
  font-size: 18px;
  font-weight: var(--font-weight-bold);
}

.close-btn {
  background: none;
  border: none;
  font-size: 28px;
  cursor: pointer;
  color: #666;
  padding: 0;
  width: 30px;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.close-btn:hover {
  color: #000;
}

.modal-body {
  padding: 2rem;
  text-align: center;
}

.not-implemented {
  font-size: 14px;
  color: #666;
  margin-bottom: 1rem;
}

.order-id {
  font-size: var(--font-size-medium);
  color: #999;
}

.modal-footer {
  padding: 1.5rem;
  border-top: 1px solid var(--color-border);
  display: flex;
  justify-content: flex-end;
}

.btn-close {
  padding: 0.75rem 2rem;
  background: #000;
  color: #fff;
  border: none;
  font-size: var(--font-size-medium);
  cursor: pointer;
  transition: opacity 0.2s;
}

.btn-close:hover {
  opacity: 0.8;
}
</style>