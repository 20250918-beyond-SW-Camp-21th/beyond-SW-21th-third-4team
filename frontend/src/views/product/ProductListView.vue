<template>
  <div class="category-page">
    <div class="container">
      <!-- 왼쪽 사이드바 -->
      <aside class="sidebar">
        <h2 class="category-title">남성 카테고리</h2>
        <ul class="category-list">
          <li :class="{ active: currentCategory === 'ALL' }">
            <a href="#" @click.prevent="changeCategory('ALL')">ALL</a>
          </li>
          <li :class="{ active: currentCategory === 'OUTER' }">
            <a href="#" @click.prevent="changeCategory('OUTER')">OUTER (코트/패딩)</a>
          </li>
          <li :class="{ active: currentCategory === 'TOP' }">
            <a href="#" @click.prevent="changeCategory('TOP')">TOP (니트/셔츠)</a>
          </li>
          <li :class="{ active: currentCategory === 'BOTTOM' }">
            <a href="#" @click.prevent="changeCategory('BOTTOM')">BOTTOM (바지)</a>
          </li>
          <li :class="{ active: currentCategory === 'ACC' }">
            <a href="#" @click.prevent="changeCategory('ACC')">ACC (악세서리)</a>
          </li>
        </ul>
      </aside>

      <!-- 오른쪽 상품 목록 -->
      <main class="product-content">
        <div class="sort-header">
          <h3 style="margin-right: auto; font-size: 14px; font-weight: bold;">
            {{ currentCategory }} ({{ filteredProducts.length }})
          </h3>
          <ul class="sort-menu">
            <li><a href="#" @click.prevent="changeSort('name')">이름순</a></li>
            <li><a href="#" @click.prevent="changeSort('lowPrice')">낮은가격</a></li>
            <li><a href="#" @click.prevent="changeSort('highPrice')">높은가격</a></li>
          </ul>
        </div>

        <div class="product-grid">
          <!-- filteredProducts: 현재 선택된 카테고리 상품만 보여줌 -->
          <div v-for="(product, index) in filteredProducts" :key="index" class="product-item">
            <div class="thumb">
              <a :href="product.link">
                <img :src="product.image" alt="상품 이미지">
              </a>
            </div>
            <div class="info">
              <a :href="product.link" class="name">{{ product.name }}</a>
              <div class="price-wrap">
                <span class="sale-price">{{ product.salePrice }}</span>
              </div>
            </div>
          </div>

          <!-- 상품이 없을 경우 -->
          <div v-if="filteredProducts.length === 0" style="grid-column: 1/-1; text-align: center; padding: 100px;">
            등록된 상품이 없습니다.
          </div>
        </div>
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';

const currentCategory = ref('ALL');
const currentSort = ref('default');

// 가격 변환 헬퍼 함수
const parsePrice = (priceStr) => {
  // 'KRW 248,000' -> 숫자 248000 으로 변환
  if (!priceStr) return 0;
  return parseInt(priceStr.replace(/[^0-9]/g, ''), 10);
};

// 정렬 변경 함수
const changeSort = (sortType) => {
  currentSort.value = sortType;
};

// 카테고리 변경 함수
const changeCategory = (categoryName) => {
  currentCategory.value = categoryName;
};

// 전체 상품 데이터
const allProducts = [
  // [OUTER]
  {
    category: 'OUTER',
    name: '칼라 플라이트 다운 푸퍼 KHAKI',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/jumper/IM2E4JP03/khaki/INSILENCE_2508135761.jpg',
    salePrice: 'KRW 248,000'
  },
  {
    category: 'OUTER',
    name: '칼라 플라이트 다운 푸퍼 BLACK',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/jumper/IM2E4JP03/black/INSILENCE_2508136960.jpg',
    salePrice: 'KRW 248,000'
  },
  {
    category: 'OUTER',
    name: '더블 레이어드 다운 자켓 BLACK',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/jumper/IM2E4JP02/black/INSILENCE_2508136908.jpg',
    salePrice: 'KRW 207,200'
  },
  {
    category: 'OUTER',
    name: '에센셜 다운 자켓 BLACK',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/jumper/IM2E4JP01/black/INSILENCE_2508136607.jpg',
    salePrice: 'KRW 141,600'
  },
  {
    category: 'OUTER',
    name: '마펠 캐시미어 발마칸 코트 MELANGE BROWN',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/coat/IM2E4CT03/melangebrown/INSILENCE_2508137747.jpg',
    salePrice: 'KRW 319,200'
  },
  {
    category: 'OUTER',
    name: '캐시미어 더블 롱 코트 BLACK',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/coat/IM2D4CT02/black/INSILENCE_2508137637.jpg',
    salePrice: 'KRW 248,000'
  },
  {
    category: 'OUTER',
    name: '울 캐시미어 카 코트 BLACK',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/coat/IM2E4CT04/black/INSILENCE_2508137489.jpg',
    salePrice: 'KRW 284,000'
  },
  {
    category: 'OUTER',
    name: '솔리스트 캐시미어 코트 BLACK',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/coat/IM2D4CT01/black/INSILENCE_2508136325.jpg',
    salePrice: 'KRW 212,800'
  },
  {
    category: 'OUTER',
    name: '퍼플렉 플라이트 푸퍼 BLACK',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/jumper/IM2E4JP16/black/INSILENCE_2508136549.jpg',
    salePrice: 'KRW 221,600'
  },
  {
    category: 'OUTER',
    name: '[UNISEX] N-2B 파일럿 파카 KHAKI BROWN',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/jumper/IM2E4JP19/brown/INSILENCE_2508137218.jpg',
    salePrice: 'KRW 266,400'
  },

  // [TOP]
  {
    category: 'TOP',
    name: '리브 헨리넥 롱슬리브 MELANGE GREY',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/top/IM2E4TO03/melangegrey/1030.jpg',
    salePrice: 'KRW 53,000'
  },
  {
    category: 'TOP',
    name: '라이트웨이트 집업 후디 BLACK',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/top/IM2E4TO07/black/23329.jpg',
    salePrice: 'KRW 79,200'
  },
  {
    category: 'TOP',
    name: '스프라이프 럭비 폴로 BROWN',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/top/IM2E4TO11/brown/25003.jpg',
    salePrice: 'KRW 88,000'
  },
  {
    category: 'TOP',
    name: '버튼 다운 저지 셔츠 CHARCOAL',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/top/IM2E4TO09/charcoal/22749.jpg',
    salePrice: 'KRW 89,000'
  },
  {
    category: 'TOP',
    name: '버튼 다운 저지 셔츠 BLACK',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/top/IM2E4TO09/black/24384.jpg',
    salePrice: 'KRW 87,000'
  },
  {
    category: 'TOP',
    name: '컷오프 롱슬리브 CHARCOAL',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/top/IM2E4TO08/charcoal/24424.jpg',
    salePrice: 'KRW 61,600'
  },
  {
    category: 'TOP',
    name: '리브 헨리넥 롱 슬리브 WHITE',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/top/IM2E4TO03/white/22856.jpg',
    salePrice: 'KRW 53,000'
  },
  {
    category: 'TOP',
    name: '보더 스트라이프 롱슬리브 CHARCOAL',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/top/IM2E4TO02/charcoal/INSILENCE_2508135851.jpg',
    salePrice: 'KRW 61,600'
  },
  {
    category: 'TOP',
    name: '오가닉 코튼 헨리넥 롱슬리브 DARK GREY',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/top/IM2E4TO10/darkgrey/24265.jpg',
    salePrice: 'KRW 79,200'
  },
  {
    category: 'TOP',
    name: '루나 다잉 롱슬리브 MUD BEIGE',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/top/IM2E4TO06/mudbeige/0153.jpg',
    salePrice: 'KRW 61,600'
  },

  // [BOTTOM]
  {
    category: 'BOTTOM',
    name: '스트라이브 카고 팬츠 MELANGE GREY',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/pants/IM2E4PT03/melangegrey/23753.jpg',
    salePrice: 'KRW 115,200'
  },
  {
    category: 'BOTTOM',
    name: '워시드 코튼 카고 팬츠 KHAKI BROWN',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/pants/IM2E4PT02/khakibrown/22816.jpg',
    salePrice: 'KRW 132,800'
  },
  {
    category: 'BOTTOM',
    name: '플라이트 카고 팬츠 BROWN',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/pants/IM2E4PT05/brown/2252.jpg',
    salePrice: 'KRW 130,400'
  },
  {
    category: 'BOTTOM',
    name: '울 테이퍼드 팬츠 GREY',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/pants/IM2E4PT01/grey/24687.jpg',
    salePrice: 'KRW 132,800'
  },
  {
    category: 'BOTTOM',
    name: '인시전 커브드 팬츠 BLACK',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/pants/IM2E4PT04/black/24226%E2%98%85.jpg',
    salePrice: 'KRW 115,200'
  },
  {
    category: 'BOTTOM',
    name: '카펜터 데님 팬츠 RINSED',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/pants/IM2E4PT35/rinsed/INSILENCE_2508137801%20%E2%98%85.jpg',
    salePrice: 'KRW 124,400'
  },
  {
    category: 'BOTTOM',
    name: '코드 데님 워크 팬츠 BROWN',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/pants/IM2E4PT42/brown/24345.jpg',
    salePrice: 'KRW 143,600'
  },
  {
    category: 'BOTTOM',
    name: '커브드 와이드 진 BLUE',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/pants/IM2E4PT41/blue/1846.jpg',
    salePrice: 'KRW 155,000'
  },
  {
    category: 'BOTTOM',
    name: '데미지 워시드 데님 팬츠 GREY',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/pants/IM2E4PT39/grey/1705%E2%98%85.jpg',
    salePrice: 'KRW 132,800'
  },
  {
    category: 'BOTTOM',
    name: '빈티지 컴뱃 카고 팬츠2 OLIVE',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/pants/IM2E4PT31/olive/1756.jpg',
    salePrice: 'KRW 186,200'
  },

  // [ACC]
  {
    category: 'ACC',
    name: '실버 체인 디스트레스드 레더 벨트 DARK BROWN',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/acc/IM2E2AC02/2054.jpg',
    salePrice: 'KRW 49,000'
  },
  {
    category: 'ACC',
    name: '스페이드 키체인 네크리스 BLACK',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/24SS/MEN/acc/IM2D2AC09/m0118_0192%20%2825%29.jpg',
    salePrice: 'KRW 46,200'
  },
  {
    category: 'ACC',
    name: '이펙트 씬 벨트 BLACK',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/24SS/MEN/acc/IM2D2AC04/m0119_0136%20%28142%29.jpg',
    salePrice: 'KRW 65,000'
  },
  {
    category: 'ACC',
    name: '청키 크레이프솔 더비 BLACK',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/22FW/MEN/Acc/IM2B4AC05/000318.jpg',
    salePrice: 'KRW 249,000'
  },
  {
    category: 'ACC',
    name: '라운드토 첼시 부츠 OIL BROWN',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/24SS/MEN/acc/IM2B4AC06/oilbrown/m0119_0795%20%28101%29.jpg',
    salePrice: 'KRW 298,000'
  },
  {
    category: 'ACC',
    name: '볼드 스티치 스프리트 부츠 TAN BROWN',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/24SS/MEN/acc/IM2B4AC04/m0119_1391%20%2814%29.jpg',
    salePrice: 'KRW 269,000'
  },
  {
    category: 'ACC',
    name: '리벳 포인트 크레페솔 샌들 BLACK',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/22SS/MEN/Acc/IM2B2AC01/200554.jpg',
    salePrice: 'KRW 260,000'
  },
  {
    category: 'ACC',
    name: '위빙 피셔맨 샌들 WHITE',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/22SS/MEN/Acc/IM2B2AC02/cream/300053.jpg',
    salePrice: 'KRW 256,000'
  },
  {
    category: 'ACC',
    name: '워시드 코튼 메신저 백 KHAKI',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25FW/MEN/acc/IM2E4AC01/khaki/22802.jpg',
    salePrice: 'KRW 135,200'
  },
  {
    category: 'ACC',
    name: '워시드 캔버스 크로스백 BEIGE',
    link: '#',
    image: 'https://ambient.diskn.com/detailimg/25SS/MEN/acc/IM2E2AC01/beige/250114_INSILENCE_DAY%20_1951%20%2898%29.jpg',
    salePrice: 'KRW 127,200'
  }
];

const filteredProducts = computed(() => {
  // 카테고리 필터링
  let result = [];
  if (currentCategory.value === 'ALL') {
    result = [...allProducts];
  } else {
    result = allProducts.filter(item => item.category === currentCategory.value);
  }

  //  정렬타입
  if (currentSort.value === 'name') {
    result.sort((a, b) => a.name.localeCompare(b.name));
  } else if (currentSort.value === 'lowPrice') {
    result.sort((a, b) => parsePrice(a.salePrice) - parsePrice(b.salePrice));
  } else if (currentSort.value === 'highPrice') {
    result.sort((a, b) => parsePrice(b.salePrice) - parsePrice(a.salePrice));
  }

  return result;
});
</script>


<style scoped>
.category-page {
  padding-top: 50px;
  max-width: 1600px;
  margin: 0 auto;
  font-family: 'Pretendard', sans-serif;
}

.container {
  display: flex;
  gap: 50px;
  padding: 0 40px;
}

/* 1. 왼쪽 사이드바 (카테고리 메뉴) */
.sidebar {
  width: 200px;
  flex-shrink: 0;
}

.category-title {
  font-size: 16px;
  font-weight: 800;
  margin-bottom: 30px;
  border-bottom: 2px solid #000;
  padding-bottom: 10px;
}

.category-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.category-list li {
  margin-bottom: 12px;
}

.category-list a {
  text-decoration: none;
  color: #888;
  font-size: 13px;
  transition: all 0.2s;
  display: block;
  padding: 2px 0;
}

.category-list li.active a {
  color: #000;
  font-weight: 700;
}

.category-list a:hover {
  color: #000;
}

/* 오른쪽 상품 목록 영역 */
.product-content {
  flex-grow: 1;
}

/* 상단 정렬 메뉴 */
.sort-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  border-bottom: 1px solid #eee;
  padding-bottom: 15px;
}

.sort-menu {
  display: flex;
  list-style: none;
  gap: 20px;
  padding: 0;
  margin: 0;
}

.sort-menu a {
  text-decoration: none;
  color: #888;
  font-size: 12px;
}

/* 상품 그리드 (바둑판 배열) */
.product-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  row-gap: 50px;
}

.product-item {
  width: 100%;
}

/* 이미지 크기 제어 (가장 중요!) */
.thumb {
  width: 100%;
  overflow: hidden;
  margin-bottom: 15px;
  background-color: #f5f5f5;
}

.thumb img {
  width: 100%;
  height: auto;
  display: block;
}

/* 상품 정보 텍스트 */
.info {
  margin-top: 10px;
}

.name {
  display: block;
  font-size: 13px;
  color: #000;
  text-decoration: none;
  margin-bottom: 5px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.price-wrap {
  font-size: 13px;
  font-weight: 600;
}

.sale-price {
  color: #000;
}

/* 반응형 (화면이 작아지면) */
@media (max-width: 1200px) {
  .product-grid {
    grid-template-columns: repeat(3, 1fr); /* 3개씩 */
  }
}

@media (max-width: 768px) {
  .container {
    flex-direction: column; /* 세로 배치 */
    padding: 0 20px;
  }
  .sidebar {
    width: 100%;
    margin-bottom: 20px;
  }
  .product-grid {
    grid-template-columns: repeat(2, 1fr); /* 2개씩 */
  }
}
</style>
