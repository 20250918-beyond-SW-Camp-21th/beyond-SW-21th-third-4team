<template>
  <div class="order-sheet-page">
    <div class="page-container">
      <ShippingForm ref="shippingFormRef" />
      
      <!-- 임시: 하단 마진 -->
      <div style="height: 100px;"></div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import ShippingForm from '../../components/order/ShippingForm.vue';

const shippingFormRef = ref(null);
</script>

<style scoped>
.order-sheet-page {
  padding-top: 80px; /* 헤더 높이만큼 여백 */
  background-color: #fff;
  min-height: 100vh;
}

.page-container {
  max-width: 1200px;
  margin: 0 auto;
}
</style>
