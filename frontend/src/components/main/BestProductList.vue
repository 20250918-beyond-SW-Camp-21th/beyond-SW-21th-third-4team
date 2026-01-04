<template>
  <div class="main-section">
    <h2>MEN BEST</h2>
    <div class="product-list-container">
      <ul class="prdList column4">
        <!-- v-for로 수동으로 만든 products 배열을 돌립니다 -->
        <li v-for="product in products" :key="product.id" class="item">
          <div class="box_wrap">
            <div class="box">
              <router-link :to="`/product/${product.id}`" class="link"></router-link>
              <div class="thumb">
                <!-- 이미지는 import한 변수를 바로 사용합니다 -->
                <img :src="product.image" class="big" alt="Product Image">
                <img :src="product.image" class="medium" alt="Hover Image">
              </div>
              <div class="contents">
                <div class="name_wrap">
                  <div class="name_contents flex_wrap space_between">
                    <router-link :to="`/product/${product.id}`" class="name">
                      <span style="font-size:12px;color:#000000;">{{ product.name }}</span>
                    </router-link>
                  </div>
                </div>
                <div class="custom_price_wrap">
                  <div class="flex_wrap">
                    <div class="original_price custom"><span>{{ product.price }}</span></div>
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
import { ref } from 'vue';

// [1] 사용할 이미지들을 import 합니다.
// (경로는 현재 파일 위치에 맞게 수정해주세요. 보통 @/assets/images/... 사용)
import babyImg from '../../assets/images/baby.png';
import chaesangImg from '../../assets/images/chaesang.png';
import reongImg from '../../assets/images/reong.png';
import sekongImg from '../../assets/images/sekong.png';

// [2] 수동으로 상품 목록 데이터 생성 (순서대로 출력됩니다)
const products = ref([
  {
    id: 1, // 임의의 ID
    name: 'Baby Coat', // 원하는 상품명
    price: 'KRW 289,000', // 가격
    image: babyImg // 위에서 import한 이미지 변수
  },
  {
    id: 2,
    name: 'Chaesang Jacket',
    price: 'KRW 319,000',
    image: chaesangImg
  },
  {
    id: 3,
    name: 'Reong Blazer',
    price: 'KRW 159,000',
    image: reongImg
  },
  {
    id: 4,
    name: 'Sekong Outer',
    price: 'KRW 429,000',
    image: sekongImg
  }
]);

// 더 이상 API 호출이나 이미지 변환 함수는 필요 없습니다.
</script>

<style scoped>
/* 스타일은 기존과 동일하게 유지 */
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
  width: calc(25% - 15px);
  margin-bottom: 30px;
  position: relative;
}
@media (max-width: 768px) {
  .prdList .item {
    width: calc(50% - 10px);
  }
}
.box_wrap { position: relative; }
.thumb {
  position: relative;
  overflow: hidden;
  margin-bottom: 15px;
}
.thumb {
  position: relative;
  overflow: hidden;
  margin-bottom: 15px;
}

.thumb img {
  width: 100%;
  aspect-ratio: 3 / 4;
  object-fit: cover;

  object-position: top center;

  display: block;
  transition: opacity 0.3s ease;
}
.thumb .medium {
  position: absolute;
  top: 0;
  left: 0;
  opacity: 0;
}
.thumb:hover .medium { opacity: 1; }
.link {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 10;
}
.contents { text-align: left; }
.name {
  text-decoration: none;
  display: block;
  margin-bottom: 5px;
}
.original_price span {
  font-weight: bold;
  font-size: 14px;
  color: #000;
}
</style>


<!--<template>-->
<!--  <div class="main-section">-->
<!--    <h2>MEN BEST</h2>-->
<!--    <div class="product-list-container">-->
<!--      <ul class="prdList column4">-->
<!--        <li v-for="product in products" :key="product.id" class="item">-->
<!--          <div class="box_wrap">-->
<!--            <div class="box">-->
<!--              &lt;!&ndash; router-link로 수정 &ndash;&gt;-->
<!--              <router-link :to="`/product/${product.id}`" class="link"></router-link>-->
<!--              <div class="thumb">-->
<!--                &lt;!&ndash; formatImageUrl 적용 &ndash;&gt;-->
<!--                <img :src="formatImageUrl(product.imageUrl)" class="big" alt="Product Image" @error="handleImageError">-->
<!--                &lt;!&ndash; hoverImage는 API에 없으므로 일단 동일 이미지 사용 &ndash;&gt;-->
<!--                <img :src="formatImageUrl(product.imageUrl)" class="medium" alt="Hover Image" @error="handleImageError">-->
<!--              </div>-->
<!--              <div class="contents">-->
<!--                <div class="name_wrap">-->
<!--                  <div class="name_contents flex_wrap space_between">-->
<!--                    &lt;!&ndash; router-link로 수정 &ndash;&gt;-->
<!--                    <router-link :to="`/product/${product.id}`" class="name">-->
<!--                      <span style="font-size:12px;color:#000000;">{{ product.name }}</span>-->
<!--                    </router-link>-->
<!--                  </div>-->
<!--                </div>-->
<!--                <div class="custom_price_wrap">-->
<!--                  &lt;!&ndash; API에 할인 정보가 없으므로 가격만 표시 &ndash;&gt;-->
<!--                  <div class="flex_wrap">-->
<!--                    <div class="original_price custom"><span>KRW {{ product.price.toLocaleString() }}</span></div>-->
<!--                  </div>-->
<!--                </div>-->
<!--              </div>-->
<!--            </div>-->
<!--          </div>-->
<!--        </li>-->
<!--      </ul>-->
<!--    </div>-->
<!--  </div>-->
<!--</template>-->

<!--<script setup>-->
<!--import { ref, onMounted } from 'vue';-->
<!--import { fetchProducts } from '../../api/product.js';-->
<!--import { formatImageUrl } from '../../utils/imageUtils.js';-->

<!--const products = ref([]);-->

<!--// 이미지 로드 에러 처리-->
<!--const handleImageError = (e) => {-->
<!--  e.target.src = '/images/no-image.png'; // 기본 이미지 경로 (public/images/no-image.png 필요)-->
<!--};-->

<!--onMounted(async () => {-->
<!--  try {-->
<!--    // 'OUTER' 카테고리 상품을 가져오거나, 전체 상품을 가져오려면 null 전달-->
<!--    const response = await fetchProducts('OUTER');-->
<!--    // 수정: response.data는 { success: true, data: [...] } 형태이므로 .data를 한 번 더 접근해야 함-->
<!--    products.value = response.data.data;-->
<!--  } catch (error) {-->
<!--    console.error('상품 목록을 불러오는데 실패했습니다.', error);-->
<!--  }-->
<!--});-->
<!--</script>-->

<!--<style scoped>-->
<!--.main-section {-->
<!--  padding: 50px 20px;-->
<!--  text-align: center;-->
<!--}-->

<!--h2 {-->
<!--  font-size: 24px;-->
<!--  font-weight: bold;-->
<!--  margin-bottom: 30px;-->
<!--}-->

<!--.prdList {-->
<!--  display: flex;-->
<!--  flex-wrap: wrap;-->
<!--  list-style: none;-->
<!--  padding: 0;-->
<!--  margin: 0;-->
<!--  gap: 20px;-->
<!--}-->

<!--.prdList .item {-->
<!--  width: calc(25% - 15px); /* 4 columns */-->
<!--  margin-bottom: 30px;-->
<!--  position: relative;-->
<!--}-->

<!--/* Mobile responsive */-->
<!--@media (max-width: 768px) {-->
<!--  .prdList .item {-->
<!--    width: calc(50% - 10px); /* 2 columns on mobile */-->
<!--  }-->
<!--}-->

<!--.box_wrap {-->
<!--  position: relative;-->
<!--}-->

<!--.thumb {-->
<!--  position: relative;-->
<!--  overflow: hidden;-->
<!--  margin-bottom: 15px;-->
<!--}-->

<!--.thumb img {-->
<!--  width: 100%;-->
<!--  height: auto;-->
<!--  display: block;-->
<!--  transition: opacity 0.3s ease;-->
<!--}-->

<!--/* Hover effect: Show second image */-->
<!--.thumb .medium {-->
<!--  position: absolute;-->
<!--  top: 0;-->
<!--  left: 0;-->
<!--  opacity: 0;-->
<!--}-->

<!--.thumb:hover .medium {-->
<!--  opacity: 1;-->
<!--}-->

<!--.link {-->
<!--  position: absolute;-->
<!--  top: 0;-->
<!--  left: 0;-->
<!--  width: 100%;-->
<!--  height: 100%;-->
<!--  z-index: 10;-->
<!--}-->

<!--.contents {-->
<!--  text-align: left;-->
<!--}-->

<!--.name {-->
<!--  text-decoration: none;-->
<!--  display: block;-->
<!--  margin-bottom: 5px;-->
<!--}-->

<!--.sale_price span {-->
<!--  text-decoration: line-through;-->
<!--  color: #888;-->
<!--  font-size: 12px;-->
<!--  margin-right: 5px;-->
<!--}-->

<!--.flex_wrap {-->
<!--  display: flex;-->
<!--  align-items: center;-->
<!--  gap: 5px;-->
<!--}-->

<!--.original_price span {-->
<!--  font-weight: bold;-->
<!--  font-size: 14px;-->
<!--  color: #000;-->
<!--}-->

<!--.custom_sale_percent {-->
<!--  color: #ff0000;-->
<!--  font-size: 12px;-->
<!--  font-weight: bold;-->
<!--}-->
<!--</style>-->
