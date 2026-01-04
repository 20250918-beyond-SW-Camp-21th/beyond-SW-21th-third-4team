<template>
  <div class="main-section">
    <!-- Swiper Container -->
    <swiper
        :modules="modules"
        :slides-per-view="1"
        :loop="true"
        :autoplay="{ delay: 5000, disableOnInteraction: false }"
        :pagination="{ el: '.custom-dots', clickable: true, renderBullet: customPagination }"
        class="main-banner-swiper"
    >
      <!-- Slides -->
      <swiper-slide v-for="(slide, index) in slides" :key="index">
        <div class="main-banner">
          <a :href="slide.link" class="link"></a>
          <!-- Background Image -->
          <div
              class="bg full"
              :style="{ backgroundImage: `url('${slide.image}')` }"
          ></div>

          <!-- Text Overlay -->
          <div class="text_wrap">
            <div class="text">
              <h2>{{ slide.title }}</h2>
              <a :href="slide.buttonLink || slide.link">
                {{ slide.buttonText || 'VIEW MORE' }}
              </a>
            </div>
          </div>
        </div>
      </swiper-slide>

      <!-- Custom Pagination Container -->
      <div class="custom-dots"></div>
    </swiper>
  </div>
</template>
<script setup>
import { ref } from 'vue';
import { Swiper, SwiperSlide } from 'swiper/vue';
import { Autoplay, Pagination } from 'swiper/modules';

// Import Swiper styles
import 'swiper/css';
import 'swiper/css/pagination';

import bannerImg1 from '../../assets/images/banner2.png';
import bannerImg2 from '../../assets/images/banner2.png';
import bannerImg3 from '../../assets/images/banner2.png';

const modules = [Autoplay, Pagination];

// Data derived from your HTML snippet
const slides = ref([
  {
    // title: 'MEN — CLEARANCE SALE',
    image: bannerImg1, // new URL('@/assets/banner1.png', import.meta.url).href,
    // link: 'https://www.insilence.co.kr/product/outlet_list.html?cate_no=572',
    buttonText: 'SHOP NOW',
    // buttonLink: 'https://www.insilence.co.kr/product/outlet_list.html?cate_no=572'
  },
  {
    title: 'WOMEN — CLEARANCE SALE',
    image: 'https://ambient.diskn.com/image/home/main/25FW/PC_2.png',
    // link: 'https://www.insilence.co.kr/product/outlet_list.html?cate_no=556',
    buttonText: 'SHOP NOW',
    // buttonLink: '/magazine/sample.html'
  },
  {
    title: 'FW25 EDITORIAL',
    image: 'https://ambient.diskn.com/image/home/main/25FW/PC_3.png',
    // link: 'https://www.insilence.co.kr/product/lookbook_detail.html?product_no=6808&cate_no=330&display_group=1',
    buttonText: 'VIEW MORE',
    // buttonLink: '/magazine/sample.html'
  }
]);

const customPagination = (index, className) => {
  return `<li class="${className}"><button type="button">${index + 1}</button></li>`;
};
</script>

<style scoped>
/* Main Structure */
.main-section {
  position: relative;
  width: 100%;
  overflow: hidden;
}

.main-banner {
  position: relative;
  width: 100%;
  height: 90vh;
}

/* Background Image */
.bg.full {
  width: 100%;
  height: 100%;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
}


/* Text Overlay Styling */
.text_wrap {
  position: absolute;
  bottom: 100px; /* Adjust based on design */
  left: 50%;
  transform: translateX(-50%);
  text-align: center;
  z-index: 2;
  color: white;
}

.text h2 {
  font-size: 2rem;
  margin-bottom: 20px;
  font-weight: bold;
}

.text a {
  display: inline-block;
  padding: 10px 20px;
  border: 1px solid white;
  color: white;
  text-decoration: none;
  font-size: 0.9rem;
  transition: background 0.3s;
}

.text a:hover {
  background: white;
  color: black;
}

/* Custom Dots Styling */
.custom-dots {
  position: absolute;
  bottom: 30px;
  left: 0;
  width: 100%;
  display: flex;
  justify-content: center;
  z-index: 10;
  list-style: none;
  padding: 0;
  margin: 0;
}

/* Swiper specifically targets bullets with class name swiper-pagination-bullet */
:deep(.swiper-pagination-bullet) {
  width: auto;
  height: auto;
  background: transparent;
  opacity: 1;
  margin: 0 5px;
  display: inline-block;
}

:deep(.swiper-pagination-bullet button) {
  width: 30px;
  height: 30px;
  background: rgba(255, 255, 255, 0.5);
  border: none;
  border-radius: 50%;
  cursor: pointer;
  color: transparent; /* Hide numbers if strictly dots */
}

:deep(.swiper-pagination-bullet-active button) {
  background: white;
}
</style>
