<template>
  <div class="order">
    <!-- Order Header -->
    <h3>
      <span class="date">{{ formatDate(order.orderDate) }}</span>
      <span class="number">
        <a href="#" @click.prevent="viewDetail">주문번호 {{ order.orderNumber }}</a>
      </span>
    </h3>

    <!-- Order Items -->
    <div class="ec-base-prdInfo">
      <div
        v-for="item in order.items"
        :key="item.id"
        class="prdBox"
      >
        <div class="thumbnail">
          <a href="#" @click.prevent="viewDetail">
            <img
              :src="item.productImage || '//img.echosting.cafe24.com/thumb/img_product_small.gif'"
              :alt="item.productName"
              width="71"
              height="71"
            />
          </a>
        </div>
        <div class="description">
          <strong class="prdName">{{ item.productName }}</strong>
          <ul class="info">
            <li>
              <span class="price">
                <strong>{{ formatPrice(item.price) }}원</strong>
              </span>
              <span class="ec-base-qty">
                <strong>{{ item.quantity }}</strong>개
              </span>
            </li>
            <li v-if="item.size" class="option">
              사이즈: {{ item.size }}
            </li>
          </ul>
        </div>
      </div>

      <!-- Order Footer -->
      <div class="prdFoot">
        <div class="gLeft">
          <span class="store">{{ getStatusText(order.status) }}</span>
        </div>
        <div class="gRight">
          <button
            v-if="canCancel(order.status)"
            @click="cancelOrder"
            class="btnNormal mini pc"
          >
            주문취소
          </button>
        </div>

        <!-- Payment Detail -->
        <div class="payDetail">
          <div class="title">
            <strong>총 결제금액</strong>
            <div class="gRight">
              <strong>{{ formatPrice(order.totalAmount) }}원</strong>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  order: {
    type: Object,
    required: true
  }
})

const emit = defineEmits(['view-detail', 'cancel-order'])

function formatDate(dateString) {
  const date = new Date(dateString)
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  return `${year}.${month}.${day}`
}

function formatPrice(price) {
  return price?.toLocaleString('ko-KR') || '0'
}

function getStatusText(status) {
  const statusMap = {
    'ORDERED': '입금전',
    'PAYMENT_COMLETED': '결제완료',
    'PREPARING': '배송준비중',
    'SHIPPING': '배송중',
    'DELIVERED': '배송완료',
    'CANCELLED': '취소됨',
    'EXCHANGED': '교환',
    'RETURNED': '반품'
  }
  return statusMap[status] || status
}

function canCancel(status) {
  return ['ORDERED', 'PAYMENT_COMLETED', 'PREPARING'].includes(status)
}

function viewDetail() {
  emit('view-detail', props.order.id)
}

function cancelOrder() {
  emit('cancel-order', props.order.id)
}
</script>

<style scoped>
/* Order Container */
.order {
  position: relative;
  border: 1px solid #d5d5d5;
  background-color: #fff;
  margin-bottom: 20px;
}

/* Order Header */
.order h3 {
  overflow: hidden;
  margin: 0;
  padding: 12px 14px;
  line-height: 20px;
  color: #fff;
  font-size: 13px;
  font-weight: normal;
  background-color: #737781;
}

.order h3:after {
  content: "";
  display: block;
  clear: both;
}

.order h3 .date {
  float: left;
  font-weight: bold;
}

.order h3 .number {
  float: right;
}

.order h3 a {
  color: #fff;
  text-decoration: none;
}

.order h3 a:hover {
  text-decoration: underline;
}

/* Product Info */
.ec-base-prdInfo {
  padding: 14px;
}

/* Product Box */
.prdBox {
  display: flex;
  gap: 12px;
  margin-bottom: 14px;
  padding-bottom: 14px;
  border-bottom: 1px solid #f0f0f0;
}

.prdBox:last-of-type {
  margin-bottom: 0;
  padding-bottom: 0;
  border-bottom: none;
}

.thumbnail {
  flex-shrink: 0;
}

.thumbnail img {
  display: block;
  width: 71px;
  height: 71px;
  object-fit: cover;
  border: 1px solid #d5d5d5;
}

.thumbnail a {
  display: block;
}

/* Product Description */
.description {
  flex: 1;
  min-width: 0;
}

.prdName {
  display: block;
  font-size: 13px;
  font-weight: normal;
  margin-bottom: 8px;
  line-height: 18px;
  color: #000;
}

.info {
  list-style: none;
  padding: 0;
  margin: 0;
}

.info li {
  font-size: 12px;
  color: #666;
  margin-bottom: 4px;
  line-height: 16px;
}

.info li:last-child {
  margin-bottom: 0;
}

.price {
  margin-right: 10px;
}

.price strong {
  color: #000;
  font-size: 13px;
}

.ec-base-qty strong {
  color: #000;
}

.option {
  color: #666;
  font-size: 11px;
}

/* Product Footer */
.prdFoot {
  margin-top: 14px;
  padding-top: 14px;
  border-top: 1px solid #f0f0f0;
}

.prdFoot .gLeft,
.prdFoot .gRight {
  display: inline-block;
}

.prdFoot .gLeft {
  float: left;
}

.prdFoot .gRight {
  float: right;
}

.prdFoot:after {
  content: "";
  display: block;
  clear: both;
}

.store {
  font-size: 13px;
  font-weight: bold;
  color: #4a5164;
}

/* Buttons */
.btnNormal.mini {
  padding: 6px 12px;
  border: 1px solid #d5d5d5;
  background: #fff;
  font-size: 11px;
  color: #333;
  cursor: pointer;
  transition: all 0.2s;
}

.btnNormal.mini:hover {
  background: #f8f8f8;
  border-color: #999;
}

/* Payment Detail */
.payDetail {
  clear: both;
  margin-top: 14px;
}

.payDetail .title {
  padding: 10px 14px;
  margin: 0 -14px;
  font-weight: normal;
  border-top: 1px solid #d5d5d5;
  border-bottom: 1px solid #d5d5d5;
  background: #f8f8f8;
}

.payDetail .title strong {
  font-size: 13px;
  color: #000;
}

.payDetail .title .gRight {
  float: right;
}

.payDetail .title:after {
  content: "";
  display: block;
  clear: both;
}

/* Mobile Responsive */
@media (max-width: 768px) {
  .prdBox {
    gap: 10px;
  }

  .thumbnail img {
    width: 60px;
    height: 60px;
  }

  .prdName {
    font-size: 12px;
  }

  .prdFoot .gLeft,
  .prdFoot .gRight {
    float: none;
    display: block;
    margin-bottom: 10px;
  }

  .btnNormal.mini {
    width: 100%;
    display: block;
  }
}
</style>