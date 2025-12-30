<script setup>
import { ref } from 'vue'
import UserGreeting from './components/mypage/UserGreeting.vue'
import ReferralSection from './components/mypage/ReferralSection.vue'
import OrderStatusDashboard from './components/mypage/OrderStatusDashboard.vue'
import QuickMenu from './components/mypage/QuickMenu.vue'

// Mock data for testing
const mockUser = ref({
  name: '홍홍사',
  username: 'user123',
  email: 'user@example.com',
  grade: 'BRONZE',
  profileImage: null
})

const mockMileage = ref(5000)
const mockDeposits = ref(0)
const mockCouponCount = ref(1)

const mockStatusCounts = ref({
  pending: 0,
  preparing: 0,
  shipped: 0,
  delivered: 0,
  cancelled: 0,
  exchanged: 0,
  returned: 0
})
</script>

<template>
  <div class="app">
    <!-- Header -->
    <header class="header">
      <div class="header-content">
        <nav class="nav-left">
          <a href="#">남성복</a>
          <a href="#">여성복</a>
          <a href="#" class="clearance">CLEARANCE</a>
          <a href="#">컬렉션</a>
          <a href="#">컨텐츠</a>
          <a href="#">매장안내</a>
        </nav>

        <div class="logo">INSILENCE</div>

        <nav class="nav-right">
          <a href="#">🌐</a>
          <a href="#">고객센터</a>
          <a href="#">계정</a>
          <a href="#">소팅링</a>
          <a href="#">멤버십</a>
        </nav>
      </div>
    </header>

    <!-- Main Content -->
    <main class="main-content">
      <div class="container">
        <UserGreeting
          :user="mockUser"
          :mileage="mockMileage"
          :deposits="mockDeposits"
          :couponCount="mockCouponCount"
        />

        <ReferralSection />

        <OrderStatusDashboard
          :statusCounts="mockStatusCounts"
        />

        <QuickMenu
          :couponCount="mockCouponCount"
        />
      </div>
    </main>
  </div>
</template>

<style>
.app {
  min-height: 100vh;
  background: #fff;
}

.header {
  background: #fff;
  border-bottom: 1px solid #e0e0e0;
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-content {
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 40px;
  height: 70px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.nav-left,
.nav-right {
  display: flex;
  gap: 30px;
  align-items: center;
}

.nav-left a,
.nav-right a {
  font-size: 11px;
  color: #000;
  text-decoration: none;
  font-weight: 300;
}

.nav-left a:hover,
.nav-right a:hover {
  font-weight: 400;
}

.clearance {
  color: #dc3545 !important;
  font-weight: 400 !important;
}

.logo {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  font-size: 20px;
  font-weight: 400;
  letter-spacing: 3px;
  color: #000;
}

.main-content {
  padding: 40px 0 80px;
}

.container {
  max-width: 600px;
  margin: 0 auto;
  padding: 0 20px;
}

@media (max-width: 1024px) {
  .nav-left {
    display: none;
  }

  .logo {
    position: static;
    transform: none;
  }

  .container {
    max-width: 100%;
  }
}
</style>