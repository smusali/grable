<template>
  <div class="QR">
    <qrcode-vue v-if="QrValue" :value="QrValue" :size="size" level="H" />
    <button class="Scan" @click="$router.push('/chooseMenu')">Scan Me!</button>
  </div>
</template>
<script>
import QrcodeVue from 'qrcode.vue'

export default {
  data() {
    return {
      QrValue: "https://localhost:8080/chooseMenu/",
      size: 300,
    }
  },
  components: {
    QrcodeVue,
  },
}
</script>


<style scoped>
.QR {
  max-width: 300px;
  margin: 30px auto;
  background: #f4f4f4;
  text-align: left;
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.2);
  transition: box-shadow 0.3s ease;
}
.Scan {
  margin: 10px auto;
  background: #f4f4f4;
  justify-content: center;
  align-items: center;
  padding: 10px;
  border-radius: 10px;
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.2);
}
</style>