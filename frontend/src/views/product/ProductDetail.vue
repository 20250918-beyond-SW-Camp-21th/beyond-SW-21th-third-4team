<template>
  <div class="product-detail-container">
    <div class="image-section">
      <div v-for="(img, index) in productImages" :key="index" class="image-wrapper">
        <img :src="img" :alt="`상품 이미지 ${index + 1}`" />
      </div>
    </div>

    <div class="info-section">
      <div class="sticky-content">
        <h1 class="product-title">{{ productData.name }}</h1>

        <div class="price-box">
          <span class="price-text">KRW {{ productData.price.toLocaleString() }}</span>
        </div>

        <ul class="product-summary">
          <li v-for="desc in productData.descriptions" :key="desc">{{ desc }}</li>
        </ul>

        <div class="option-group">
          <span class="label">SIZE</span>
          <div class="size-selector">
            <button class="size-btn active">FREE</button>
          </div>
        </div>

        <div class="option-group">
          <span class="label">COLOR</span>
          <div class="color-selector">
            <button
                v-for="color in productData.colors"
                :key="color.id"
                class="color-btn-wrapper"
                :class="{ active: color.name === 'BLACK' }"
                @click="goToColorProduct(color.id)"
            >
              <span class="color-dot" :style="{ backgroundColor: color.hex }"></span>
            </button>
            <span class="selected-color-name">BLACK</span>
          </div>
        </div>

        <div class="action-group">
          <button class="btn-black" @click="handleBuy">구매하기</button>
          <button class="btn-naver">네이버 페이</button>
          <div class="btn-half-group">
            <button class="btn-white">상품 문의하기</button>
            <button class="btn-white" @click="handleCart">장바구니 담기</button>
          </div>
        </div>

        <div class="accordion-container">
          <div v-for="(item, index) in accordions" :key="index" class="accordion-item">
            <div class="accordion-header" @click="item.isOpen = !item.isOpen">
              {{ item.title }}
              <span class="icon">{{ item.isOpen ? '－' : '＋' }}</span>
            </div>
            <div v-if="item.isOpen" class="accordion-body">
              {{ item.content }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const productData = {
  name: '디테처블 칼라 캐시미어 블루종 BLACK',
  price: 266400,
  descriptions: [
    '― 캐시미어 혼방 울 소재 사용',
    '― 탈부착이 가능한 시어링 칼라',
    '― 세미오버핏 실루엣',
    '― 컴포템프 충전재 사용'
  ],
  colors: [
    { id: 'black-01', name: 'BLACK', hex: '#000000' },
    { id: 'gray-01', name: 'GRAY', hex: '#666666' }
  ]
};

const productImages = ref([
  'https://images.unsplash.com/photo-1591047139829-d91aecb6caea?auto=format&fit=crop&q=80&w=800',
  'https://images.unsplash.com/photo-1551488831-00ddcb6c6bd3?auto=format&fit=crop&q=80&w=800'
]);

const accordions = ref([
  { title: '제품 소재 정보', content: '겉감 : Wool 87%, Nylon 9%, Cashmere 4%', isOpen: false },
  { title: '사이즈 정보', content: 'FREE: 총장 63 / 어깨 54 / 가슴 60', isOpen: false }
]);

// 컬러 변경 시 실행될 함수 (나중에 DB 연동 후 router 활용)
const goToColorProduct = (id) => {
  console.log(`선택된 상품 ID: ${id} - 해당 상품 상세로 이동합니다.`);
  // window.location.href = `/product/${id}`; // 또는 router.push
};

const handleBuy = () => alert('구매 페이지로 이동합니다.');
const handleCart = () => alert('장바구니에 담겼습니다.');
</script>

<style scoped>
.product-detail-container {
  display: flex;
  max-width: 1200px;
  margin: 0 auto;
  padding: 60px 20px;
  gap: 70px;
}

.image-section { flex: 1.4; }
.image-wrapper { width: 100%; margin-bottom: 15px; }
.image-wrapper img { width: 100%; display: block; }

.info-section { flex: 1; position: relative; }
.sticky-content { position: sticky; top: 100px; }

.product-title { font-size: 16px; font-weight: 700; margin-bottom: 10px; }
.price-box { margin-bottom: 30px; border-bottom: 1px solid #eee; padding-bottom: 20px; }
.price-text { font-size: 15px; font-weight: 700; }

.product-summary { list-style: none; padding: 0; margin-bottom: 40px; }
.product-summary li { font-size: 13px; line-height: 1.8; color: #444; }

.option-group { margin-bottom: 25px; }
.label { display: block; font-size: 12px; font-weight: 800; margin-bottom: 12px; }

/* 사이즈 버튼 */
.size-btn {
  width: 100px; height: 42px; border: 1px solid #000; background: #000; color: #fff;
  font-size: 12px; cursor: pointer;
}

/* 컬러 버튼 (디자인 유지 + 클릭 가능 구조) */
.color-selector { display: flex; align-items: center; gap: 12px; }

.color-btn-wrapper {
  background: none; border: none; padding: 0; cursor: pointer; display: flex;
  align-items: center; justify-content: center;
}

.color-dot {
  width: 20px; height: 20px; border-radius: 50%; border: 1px solid #ddd;
  transition: outline 0.1s ease;
}

.color-btn-wrapper.active .color-dot {
  outline: 1px solid #000; outline-offset: 3px;
}

.selected-color-name { font-size: 12px; margin-left: auto; font-weight: 600; }

/* 액션 버튼 그룹 */
.action-group { display: flex; flex-direction: column; gap: 8px; margin-top: 40px; }
.btn-black { height: 55px; background: #000; color: #fff; border: none; font-weight: 700; cursor: pointer; }
.btn-naver { height: 55px; background: #fff; color: #00c73c; border: 1px solid #00c73c; font-weight: 700; cursor: pointer; }
.btn-half-group { display: flex; gap: 6px; }
.btn-white { flex: 1; height: 48px; background: #fff; border: 1px solid #000; font-size: 12px; cursor: pointer; }

/* 아코디언 */
.accordion-container { margin-top: 50px; border-top: 1px solid #000; }
.accordion-header {
  padding: 18px 0; border-bottom: 1px solid #eee; display: flex;
  justify-content: space-between; font-size: 13px; font-weight: 700; cursor: pointer;
}
.accordion-body { padding: 15px 5px; font-size: 12px; line-height: 1.6; color: #666; white-space: pre-line; }

@media (max-width: 768px) {
  .product-detail-container { flex-direction: column; }
  .sticky-content { position: static; }
}
</style>