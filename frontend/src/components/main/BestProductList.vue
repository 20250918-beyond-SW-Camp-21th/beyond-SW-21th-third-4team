<template>
  <div class="main-section">
    <h2>MEN BEST</h2>
    <div class="product-list-container">
      <ul class="prdList column4">
        <li v-for="product in products" :key="product.id" class="item">
          <div class="box_wrap">
            <div class="box">
              <!-- router-link로 수정 -->
              <router-link :to="`/product/${product.id}`" class="link"></router-link>
              <div class="thumb">
                <!-- formatImageUrl 적용 -->
                <img :src="formatImageUrl(product.imageUrl)" class="big" alt="Product Image" @error="handleImageError">
                <!-- hoverImage는 API에 없으므로 일단 동일 이미지 사용 -->
                <img :src="formatImageUrl(product.imageUrl)" class="medium" alt="Hover Image" @error="handleImageError">
              </div>
              <div class="contents">
                <div class="name_wrap">
                  <div class="name_contents flex_wrap space_between">
                    <!-- router-link로 수정 -->
                    <router-link :to="`/product/${product.id}`" class="name">
                      <span style="font-size:12px;color:#000000;">{{ product.name }}</span>
                    </router-link>
                  </div>
                </div>
                <div class="custom_price_wrap">
                  <!-- API에 할인 정보가 없으므로 가격만 표시 -->
                  <div class="flex_wrap">
                    <div class="original_price custom"><span>KRW {{ product.price.toLocaleString() }}</span></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { fetchProducts } from '../../api/product.js';
import { formatImageUrl } from '../../utils/imageUtils.js';

const products = ref([]);

// 이미지 로드 에러 처리
const handleImageError = (e) => {
  e.target.src = '/images/no-image.png'; // 기본 이미지 경로 (public/images/no-image.png 필요)
};

onMounted(async () => {
  try {
    // 'OUTER' 카테고리 상품을 가져오거나, 전체 상품을 가져오려면 null 전달
    const response = await fetchProducts('OUTER');
    // 수정: response.data는 { success: true, data: [...] } 형태이므로 .data를 한 번 더 접근해야 함
    products.value = response.data.data;
  } catch (error) {
    console.error('상품 목록을 불러오는데 실패했습니다.', error);
  }
});
</script>

<style scoped>
.main-section {
  padding: 50px 20px;
  text-align: center;
}

h2 {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 30px;
}

.prdList {
  display: flex;
  flex-wrap: wrap;
  list-style: none;
  padding: 0;
  margin: 0;
  gap: 20px;
}

.prdList .item {
  width: calc(25% - 15px); /* 4 columns */
  margin-bottom: 30px;
  position: relative;
}

/* Mobile responsive */
@media (max-width: 768px) {
  .prdList .item {
    width: calc(50% - 10px); /* 2 columns on mobile */
  }
}

.box_wrap {
  position: relative;
}

.thumb {
  position: relative;
  overflow: hidden;
  margin-bottom: 15px;
}

.thumb img {
  width: 100%;
  height: auto;
  display: block;
  transition: opacity 0.3s ease;
}

/* Hover effect: Show second image */
.thumb .medium {
  position: absolute;
  top: 0;
  left: 0;
  opacity: 0;
}

.thumb:hover .medium {
  opacity: 1;
}

.link {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 10;
}

.contents {
  text-align: left;
}

.name {
  text-decoration: none;
  display: block;
  margin-bottom: 5px;
}

.sale_price span {
  text-decoration: line-through;
  color: #888;
  font-size: 12px;
  margin-right: 5px;
}

.flex_wrap {
  display: flex;
  align-items: center;
  gap: 5px;
}

.original_price span {
  font-weight: bold;
  font-size: 14px;
  color: #000;
}

.custom_sale_percent {
  color: #ff0000;
  font-size: 12px;
  font-weight: bold;
}
</style>
