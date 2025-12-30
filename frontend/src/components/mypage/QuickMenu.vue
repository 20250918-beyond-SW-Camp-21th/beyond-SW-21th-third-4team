<template>
  <div class="quick-menu">
    <div v-for="item in menuItems" :key="item.path" class="menu-item">
      <router-link :to="item.path" class="menu-link">
        <div class="menu-icon">
          <component :is="item.icon" />
        </div>
        <span class="menu-label">
          {{ item.label }}
          <span v-if="item.count !== undefined" class="count">({{ item.count }})</span>
        </span>
        <div class="menu-arrow">›</div>
      </router-link>
    </div>
  </div>
</template>

<script setup>
import { computed, h } from 'vue'

const props = defineProps({
  couponCount: {
    type: Number,
    default: 0
  }
})

// Icon components
const OrderIcon = () => h('svg', { width: 20, height: 20, viewBox: '0 0 24 24', fill: 'none', stroke: 'currentColor', 'stroke-width': 2 }, [
  h('path', { d: 'M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z' }),
  h('polyline', { points: '14 2 14 8 20 8' })
])

const UserIcon = () => h('svg', { width: 20, height: 20, viewBox: '0 0 24 24', fill: 'none', stroke: 'currentColor', 'stroke-width': 2 }, [
  h('path', { d: 'M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2' }),
  h('circle', { cx: 12, cy: 7, r: 4 })
])

const MileageIcon = () => h('svg', { width: 20, height: 20, viewBox: '0 0 24 24', fill: 'none', stroke: 'currentColor', 'stroke-width': 2 }, [
  h('path', { d: 'M3 3h7l5 7 5-7h7' }),
  h('path', { d: 'M12 10v11' })
])

const WalletIcon = () => h('svg', { width: 20, height: 20, viewBox: '0 0 24 24', fill: 'none', stroke: 'currentColor', 'stroke-width': 2 }, [
  h('rect', { x: 3, y: 7, width: 18, height: 13, rx: 2 }),
  h('path', { d: 'M21 11h-3a2 2 0 0 0 0 4h3' })
])

const CouponIcon = () => h('svg', { width: 20, height: 20, viewBox: '0 0 24 24', fill: 'none', stroke: 'currentColor', 'stroke-width': 2 }, [
  h('rect', { x: 2, y: 7, width: 20, height: 10, rx: 2 }),
  h('circle', { cx: 12, cy: 12, r: 2 })
])

const BoardIcon = () => h('svg', { width: 20, height: 20, viewBox: '0 0 24 24', fill: 'none', stroke: 'currentColor', 'stroke-width': 2 }, [
  h('path', { d: 'M12 2v4' }),
  h('path', { d: 'M12 18v4' }),
  h('path', { d: 'M4.93 4.93l2.83 2.83' }),
  h('path', { d: 'M16.24 16.24l2.83 2.83' }),
  h('path', { d: 'M2 12h4' }),
  h('path', { d: 'M18 12h4' })
])

const AddressIcon = () => h('svg', { width: 20, height: 20, viewBox: '0 0 24 24', fill: 'none', stroke: 'currentColor', 'stroke-width': 2 }, [
  h('path', { d: 'M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z' }),
  h('circle', { cx: 12, cy: 10, r: 3 })
])

const menuItems = computed(() => [
  { path: '/mypage/orders', label: '주문조회', icon: OrderIcon },
  { path: '/mypage/profile', label: '회원정보', icon: UserIcon },
  { path: '/mypage/mileage', label: '마일리지', icon: MileageIcon },
  { path: '/mypage/deposits', label: '예치금', icon: WalletIcon },
  { path: '/mypage/coupons', label: '쿠폰', icon: CouponIcon, count: props.couponCount },
  { path: '/mypage/boards', label: '게시물관리', icon: BoardIcon },
  { path: '/mypage/addresses', label: '배송 주소록 관리', icon: AddressIcon }
])
</script>

<style scoped>
.quick-menu {
  background: #fff;
  border: 1px solid #e0e0e0;
}

.menu-item {
  border-bottom: 1px solid #e0e0e0;
}

.menu-item:last-child {
  border-bottom: none;
}

.menu-link {
  display: flex;
  align-items: center;
  padding: 18px 25px;
  text-decoration: none;
  color: #000;
  transition: background 0.2s;
}

.menu-link:hover {
  background: #f9f9f9;
}

.menu-icon {
  width: 24px;
  height: 24px;
  margin-right: 15px;
  color: #757575;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.menu-label {
  flex: 1;
  font-size: 12px;
  font-weight: 300;
  color: #000;
}

.count {
  font-size: 11px;
  color: #757575;
  margin-left: 4px;
}

.menu-arrow {
  font-size: 20px;
  color: #757575;
  font-weight: 300;
}
</style>