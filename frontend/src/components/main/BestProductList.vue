<template>
  <div class="main-section">
    <h2>MEN BEST</h2>
    <div class="product-list-container">
      <ul class="prdList column4">
        <li v-for="(product, index) in products" :key="index" class="item">
          <div class="box_wrap">
            <div class="box">
              <a :href="product.link" class="link"></a>
              <div class="thumb">
                <img :src="product.image" class="big" alt="Product Image">
                <img :src="product.hoverImage" class="medium" alt="Hover Image">
              </div>
              <div class="contents">
                <div class="name_wrap">
                  <div class="name_contents flex_wrap space_between">
                    <a :href="product.link" class="name">
                      <span style="font-size:12px;color:#000000;">{{ product.name }}</span>
                    </a>
                  </div>
                </div>
                <div class="custom_price_wrap">
                  <div class="sale_price custom"><span>{{ product.originalPrice }}</span></div>
                  <div class="flex_wrap">
                    <div class="original_price custom"><span>{{ product.salePrice }}</span></div>
                    <div class="custom_sale_percent">{{ product.discount }}%</div>
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

const products = ref([
  {
    name: '마펠 캐시미어 발마칸 코트 BLACK',
    link: '/product/detail.html?product_no=2791',
    image: '//www.insilence.co.kr/web/product/big/202509/d1e9cadb016f364283b23e587576e2b3.jpg',
    hoverImage: '//www.insilence.co.kr/web/product/extra/big/202509/ed8682cc61ca5581be16d3a7c1277648.jpg',
    originalPrice: 'KRW 399,000',
    salePrice: 'KRW 319,200',
    discount: 20
  },
  {
    name: '마펠 캐시미어 발마칸 코트 MELANGE BROWN',
    link: '/product/detail.html?product_no=6848',
    image: '//www.insilence.co.kr/web/product/big/202509/0d65dec0b39aca3832f3f90f41652269.jpg',
    hoverImage: '//www.insilence.co.kr/web/product/extra/big/202509/7c721c246556e802a5433fea374e1b3e.jpg',
    originalPrice: 'KRW 399,000',
    salePrice: 'KRW 319,200',
    discount: 20
  },
  {
    name: '칼라 플라이트 다운 푸퍼 BLACK',
    link: '/product/detail.html?product_no=6959',
    image: '//www.insilence.co.kr/web/product/big/202509/e2544dae09199485a27076fef68f0b96.jpg',
    hoverImage: '//www.insilence.co.kr/web/product/extra/big/202509/e2ce892a2c79eb866b9f211c740f88c4.jpg',
    originalPrice: 'KRW 310,000',
    salePrice: 'KRW 248,000',
    discount: 20
  },
  {
    name: '칼라 플라이트 다운 푸퍼 KHAKI',
    link: '/product/detail.html?product_no=6960',
    image: '//www.insilence.co.kr/web/product/big/202509/014bde499ee36c8c91deacb7851a6bc5.jpg',
    hoverImage: '//www.insilence.co.kr/web/product/extra/big/202509/24fb6deeba61ed9939f788b57a622ec7.jpg',
    originalPrice: 'KRW 310,000',
    salePrice: 'KRW 248,000',
    discount: 20
  }
]);
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
