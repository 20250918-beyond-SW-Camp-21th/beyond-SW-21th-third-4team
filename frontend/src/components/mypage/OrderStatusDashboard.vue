<template>
  <div class="order-status-dashboard">
    <div class="title">
      <h3>나의 주문처리 현황 <span class="desc">(최근 <em>3개월</em> 기준)</span></h3>
    </div>

    <div class="state">
      <ul class="order">
        <li v-for="status in orderStatuses" :key="status.key">
          <a :href="`/myshop/order/list.html?order_status=${status.key.toLowerCase()}`">
            <strong>{{ status.label }}</strong>
            <span class="count"><span>{{ status.count }}</span></span>
          </a>
        </li>
      </ul>

      <ul class="cs">
        <li v-for="cs in csStatuses" :key="cs.key">
          <a :href="`/myshop/order/list.html?order_status=${cs.key.toLowerCase()}`">
            <span class="icoDot"></span>
            <strong>{{ cs.label }} : </strong>
            <span class="count"><span>{{ cs.count }}</span></span>
          </a>
        </li>
      </ul>
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
  { key: 'ORDERED', label: '입금전', count: props.statusCounts.pending || 0 },
  { key: 'PREPARING', label: '배송준비중', count: props.statusCounts.preparing || 0 },
  { key: 'SHIPPING', label: '배송중', count: props.statusCounts.shipped || 0 },
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
  margin-bottom: 16px;
}

.title {
  margin-bottom: 12px;
}

.title h3 {
  font-size: 12px;
  font-weight: 400;
  color: #000;
  margin: 0;
  line-height: 18px;
}

.title .desc {
  font-size: 10px;
  font-weight: 300;
  color: #757575;
}

.title em {
  font-style: normal;
}

.state {
  background: #fff;
  border: 2px solid #000;
  padding: 24px;
}

.order {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  justify-content: space-between;
  padding-bottom: 25px;
  margin-bottom: 20px;
  border-bottom: 1px solid #e0e0e0;
}

.order li {
  text-align: center;
  flex: 1;
  position: relative;
}

.order li:not(:last-child)::after {
  content: '';
  position: absolute;
  right: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 1px;
  height: 40px;
  background: #e0e0e0;
}

.order a {
  text-decoration: none;
  color: #000;
  display: block;
}

.order strong {
  font-size: 11px;
  color: #757575;
  margin-bottom: 10px;
  font-weight: 300;
  display: block;
}

.order .count {
  font-size: 30px;
  color: #000;
  font-weight: 400;
  display: block;
  line-height: 1;
}

.cs {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  justify-content: center;
  gap: 40px;
}

.cs li {
  font-size: 10px;
  color: #757575;
}

.cs a {
  text-decoration: none;
  color: #757575;
  display: flex;
  align-items: center;
  gap: 4px;
}

.icoDot {
  width: 4px;
  height: 4px;
  border-radius: 50%;
  background: #757575;
  display: inline-block;
}

.cs strong {
  font-weight: 300;
  font-size: 10px;
}

.cs .count {
  font-weight: 400;
  color: #000;
  font-size: 10px;
}
</style>