<template>
  <div class="product-detail-container" v-if="productData">
    <div class="image-section" v-if="productData && productData.detailImages">
      <div class="image-wrapper">
        <img :src="formatImageUrl(productData.imageUrl)" />
      </div>
      <div v-for="(img, index) in productData.detailImages" :key="index" class="image-wrapper">
        <img :src="formatImageUrl(img)" />
      </div>
    </div>

    <div class="info-section">
      <div class="sticky-content">
        <h1 class="product-title">{{ productData.name }}</h1>

        <div class="price-info">
          <!-- 할인 전 가격이 있다면 표시 (API에 없으므로 생략 가능하거나 임의 표시) -->
          <!-- <span class="original-price">KRW {{ (productData.price * 1.2).toLocaleString() }}</span> -->
          <div class="current-price">
            <span class="bold">KRW {{ productData.price.toLocaleString() }}</span>
            <!-- <span class="discount">20%</span> -->
          </div>
        </div>

        <ul class="product-summary">
          <!-- 설명 텍스트를 줄바꿈 기준으로 나누거나 그대로 표시 -->
          <li v-for="(line, index) in descriptionLines" :key="index">{{ line }}</li>
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
            <!-- 색상 선택 시 라우팅 이동 (임시 하드코딩) -->
            <div
              class="color-dot black"
              :class="{ active: currentProductId == 1 }"
              @click="changeColor(1)"
            ></div>
            <div
              class="color-dot gray"
              :class="{ active: currentProductId == 2 }"
              @click="changeColor(2)"
            ></div>
            <span class="color-name">{{ currentColorName }}</span>
          </div>
        </div>

        <div class="action-buttons">
          <button class="btn-black" @click="handleBuy">구매하기</button>
          <!-- 네이버 페이 버튼 제거 -->
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
            <div v-if="item.isOpen" class="accordion-content" style="white-space: pre-line;">
              {{ item.content }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div v-else class="loading">
    Loading...
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { fetchProductDetail } from '../../api/product.js';
import { addCartItem } from '../../api/cart.js';
import { formatImageUrl } from '../../utils/imageUtils.js';

const route = useRoute();
const router = useRouter();

const productData = ref(null);
const currentProductId = ref(null);

// 이미지 로드 에러 처리
const handleImageError = (e) => {
  e.target.src = '/images/no-image.png';
};

// 상품 설명 파싱
const descriptionLines = computed(() => {
  if (!productData.value?.description) return [];
  return productData.value.description.split('\n');
});

// 현재 색상 이름
const currentColorName = computed(() => {
  if (currentProductId.value == 1) return 'BLACK';
  if (currentProductId.value == 2) return 'GRAY';
  return 'BLACK';
});

const accordions = ref([
  { title: '제품 소재 정보', isOpen: false, content: '겉감 : Wool 87%, Nylon 9%, Cashmere 4%\n안감 : Polyester 100%' },
  { title: '사이즈', isOpen: false, content: 'FREE: 총장 63 / 어깨 54 / 가슴 60 / 소매 61' },
  { title: '교환 및 반품', isOpen: false, content: '상품 수령일로부터 7일 이내에 가능합니다.' }
]);

// 데이터 로드 함수
const loadProductData = async (id) => {
  try {
    const response = await fetchProductDetail(id);
    productData.value = response.data.data;
    currentProductId.value = id;
  } catch (error) {
    console.error('상품 정보를 불러오는데 실패했습니다.', error);
    alert('상품 정보를 불러올 수 없습니다.');
  }
};

onMounted(() => {
  const id = route.params.productId || 1;
  loadProductData(id);
});

watch(() => route.params.productId, (newId) => {
  if (newId) loadProductData(newId);
});

const changeColor = (id) => {
  if (currentProductId.value === id) return;
  router.push(`/product/${id}`);
};

// ProductDetail.vue 내부의 handleBuy 함수
const handleBuy = () => {
  if (!productData.value) return;

  router.push({
    path: '/order', // 👈 '/order/checkout' 대신 '/order'로 변경
    query: {
      productId: productData.value.productId,
      quantity: 1
    }
  });
};

// 장바구니 담기 핸들러
const handleCart = async () => {
  if (!productData.value) return;

  try {
    // 1. 장바구니에 아이템을 추가하는 API 호출
    await addCartItem(productData.value.productId, 1);

    // 2. 메시지나 확인창 없이 바로 장바구니 페이지로 이동
    router.push('/cart');

  } catch (error) {
    console.error('장바구니 담기 실패', error);
    alert('장바구니 담기에 실패했습니다.');
  }
};
</script>

<style scoped>
.product-detail-container {
  display: flex;
  max-width: 1400px;
  margin: 0 auto;
  /* 상단 여백 수정: 40px -> 120px (헤더 높이 고려) */
  padding: 120px 20px 40px;
  gap: 80px;
}

.loading {
  text-align: center;
  padding: 100px;
  font-size: 20px;
}

/* 좌측 이미지 */
.image-section { flex: 1.5; }
.image-wrapper { width: 100%; margin-bottom: 20px; }
.image-wrapper img { width: 100%; display: block; }

/* 우측 정보 고정 */
.info-section { flex: 1; position: relative; }
.sticky-content { position: sticky; top: 120px; /* 스크롤 시 고정 위치도 조정 */ }

/* 텍스트 디테일 */
.product-title { font-size: 15px; font-weight: 700; margin-bottom: 8px; }
.original-price { font-size: 13px; text-decoration: line-through; color: #999; }
.current-price { font-size: 15px; margin-bottom: 25px; }
.bold { font-weight: 700; margin-right: 8px; }
.discount { color: #ff0000; font-weight: 700; }

.product-summary {
  list-style: none;
  padding: 0;
  margin-bottom: 40px;
  font-size: 13px;
  line-height: 1.6;
  color: #333;
}

.option-section {
  margin-bottom: 30px;
}
.option-label {
  display: block;
  font-size: 12px;
  font-weight: 700;
  margin-bottom: 10px;
}

.size-selector button {
  border: 1px solid #ddd;
  background: #fff;
  padding: 8px 20px;
  font-size: 12px;
  cursor: pointer;
}
.size-selector button.active {
  border-color: #000;
  font-weight: 700;
}

.color-selector {
  display: flex;
  align-items: center;
  gap: 10px;
}
.color-dot {
  width: 20px;
  height: 20px;
  border-radius: 50%;
  cursor: pointer;
  border: 1px solid #ddd;
}
.color-dot.active {
  border: 2px solid #000; /* 선택된 색상 강조 */
}
.color-dot.black { background-color: #000; }
.color-dot.gray { background-color: #888; }
.color-name { font-size: 12px; margin-left: 5px; }

.action-buttons {
  margin-top: 40px;
  margin-bottom: 40px;
}
.action-buttons button {
  width: 100%;
  height: 50px;
  margin-bottom: 10px;
  cursor: pointer;
  font-size: 14px;
  font-weight: 700;
}
.btn-black { background: #000; color: #fff; border: none; }
.btn-naver { background: #2DB400; color: #fff; border: none; }
.btn-split { display: flex; gap: 10px; }
.btn-white { background: #fff; border: 1px solid #ddd; color: #000; }

.accordion-list { border-top: 1px solid #eee; }
.accordion-item {
  padding: 15px 0;
  border-bottom: 1px solid #eee;
  cursor: pointer;
  display: flex;
  justify-content: space-between;
  font-size: 13px;
  font-weight: 700;
}
.accordion-content {
  padding: 10px 0 20px;
  font-size: 12px;
  line-height: 1.6;
  color: #666;
  border-bottom: 1px solid #eee;
}
</style>
