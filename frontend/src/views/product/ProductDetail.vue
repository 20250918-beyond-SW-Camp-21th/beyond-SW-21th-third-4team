<template>
  <div class="product-detail-container">
    <div class="image-section">
      <div v-for="(img, index) in productImages" :key="index" class="image-wrapper">
        <img :src="img" :alt="`상품 상세 이미지 ${index + 1}`" />
      </div>
    </div>

    <div class="info-section">
      <div class="sticky-content">
        <h1 class="product-title">{{ productData.name }}</h1>

        <div class="price-info">
          <span class="original-price">KRW 333,000</span>
          <div class="current-price">
            <span class="bold">KRW {{ productData.price.toLocaleString() }}</span>
            <span class="discount">20%</span>
          </div>
        </div>

        <ul class="product-summary">
          <li v-for="line in descriptions" :key="line">{{ line }}</li>
        </ul>

        <div class="option-section">
          <span class="option-label">SIZE</span>
          <div class="size-selector">
            <button class="size-item active">FREE</button>
          </div>
        </div>

        <div class="option-section">
          <span class="option-label">Color</span>
          <div class="color-selector">
            <div class="color-dot black active"></div>
            <div class="color-dot gray"></div>
            <span class="color-name">BLACK</span>
          </div>
        </div>

        <div class="action-buttons">
          <button class="btn-black" @click="handleBuy">구매하기</button>
          <button class="btn-naver">네이버 페이</button>
          <div class="btn-split">
            <button class="btn-white">상품 문의하기</button>
            <button class="btn-white" @click="handleCart">장바구니 담기</button>
          </div>
        </div>

        <div class="accordion-list">
          <div v-for="(item, index) in accordions" :key="index" class="accordion-group">
            <div class="accordion-item" @click="item.isOpen = !item.isOpen">
              {{ item.title }} <span>{{ item.isOpen ? '－' : '＋' }}</span>
            </div>
            <div v-if="item.isOpen" class="accordion-content">
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
};

const productImages = ref([
  'https://images.unsplash.com/photo-1591047139829-d91aecb6caea?auto=format&fit=crop&q=80&w=800',
  'https://images.unsplash.com/photo-1551488831-00ddcb6c6bd3?auto=format&fit=crop&q=80&w=800',
  'https://images.unsplash.com/photo-1544022613-e87ca75a784a?auto=format&fit=crop&q=80&w=800'
]);

const descriptions = [
  '― 캐시미어 혼방 울 소재 사용',
  '― 탈부착이 가능한 시어링 칼라',
  '― 세미오버핏 실루엣',
  '― 컴포템프 충전재 사용'
];

const accordions = ref([
  { title: '제품 소재 정보', isOpen: false, content: '겉감 : Wool 87%, Nylon 9%, Cashmere 4%\n안감 : Polyester 100%' },
  { title: '사이즈', isOpen: false, content: 'FREE: 총장 63 / 어깨 54 / 가슴 60 / 소매 61' },
  { title: '교환 및 반품', isOpen: false, content: '상품 수령일로부터 7일 이내에 가능합니다.' }
]);

const handleBuy = () => alert('FREE 사이즈 구매 페이지로 이동합니다.');
const handleCart = () => alert('장바구니에 담겼습니다.');
</script>

<style scoped>
.product-detail-container {
  display: flex;
  max-width: 1400px;
  margin: 0 auto;
  padding: 40px 20px;
  gap: 80px;
}

/* 좌측 이미지 */
.image-section { flex: 1.5; }
.image-wrapper { width: 100%; margin-bottom: 20px; }
.image-wrapper img { width: 100%; display: block; }

/* 우측 정보 고정 */
.info-section { flex: 1; position: relative; }
.sticky-content { position: sticky; top: 40px; }

/* 텍스트 디테일 */
.product-title { font-size: 15px; font-weight: 700; margin-bottom: 8px; }
.original-price { font-size: 13px; text-decoration: line-through; color: #999; }
.current-price { font-size: 15px; margin-bottom: 25px; }