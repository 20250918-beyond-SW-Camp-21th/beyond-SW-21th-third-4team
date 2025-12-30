<template>
  <div class="order-status-dashboard">
    <div class="dashboard-header">
      <h3>나의 주문처리 현황 <span class="period">(최근 3개월 기준)</span></h3>
    </div>

    <div class="status-container">
      <div class="main-statuses">
        <div v-for="status in orderStatuses" :key="status.key" class="status-item">
          <div class="status-label">{{ status.label }}</div>
          <div class="status-count">{{ status.count }}</div>
        </div>
      </div>

      <div class="cs-statuses">
        <div v-for="cs in csStatuses" :key="cs.key" class="cs-item">
          <span class="bullet">·</span>
          <span class="cs-label">{{ cs.label }} :</span>
          <span class="cs-count">{{ cs.count }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  statusCounts: {
    type: Object,
    default: () => ({})
  }
})

const orderStatuses = computed(() => [
  { key: 'PENDING', label: '입금전', count: props.statusCounts.pending || 0 },
  { key: 'PREPARING', label: '배송준비중', count: props.statusCounts.preparing || 0 },
  { key: 'SHIPPED', label: '배송중', count: props.statusCounts.shipped || 0 },
  { key: 'DELIVERED', label: '배송완료', count: props.statusCounts.delivered || 0 }
])

const csStatuses = computed(() => [
  { key: 'CANCELLED', label: '취소', count: props.statusCounts.cancelled || 0 },
  { key: 'EXCHANGED', label: '교환', count: props.statusCounts.exchanged || 0 },
  { key: 'RETURNED', label: '반품', count: props.statusCounts.returned || 0 }
])
</script>

<style scoped>
.order-status-dashboard {
  background: #fff;
  border: 2px solid #000;
  padding: 30px;
  margin-bottom: 20px;
}

.dashboard-header h3 {
  font-size: 13px;
  font-weight: 400;
  color: #000;
  margin-bottom: 20px;
}

.dashboard-header .period {
  font-size: 11px;
  font-weight: 300;
  color: #757575;
}

.status-container {
  border: 1px solid #e0e0e0;
  padding: 25px 20px;
}

.main-statuses {
  display: flex;
  justify-content: space-around;
  padding-bottom: 20px;
  margin-bottom: 15px;
  border-bottom: 1px solid #f0f0f0;
}

.status-item {
  text-align: center;
  flex: 1;
}

.status-label {
  font-size: 11px;
  color: #000;
  margin-bottom: 12px;
  font-weight: 300;
}

.status-count {
  font-size: 28px;
  color: #000;
  font-weight: 300;
}

.cs-statuses {
  display: flex;
  justify-content: center;
  gap: 40px;
}

.cs-item {
  font-size: 10px;
  color: #757575;
  display: flex;
  align-items: center;
  gap: 4px;
}

.bullet {
  font-size: 10px;
}

.cs-label {
  font-weight: 300;
}

.cs-count {
  font-weight: 400;
  color: #000;
}
</style>