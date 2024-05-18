<template>
  <div class="ChooseMenu">
    <h1>Please, choose menu!</h1>
    <div class= "Orders" v-for="item in items" :key="item.id">
      <h2>{{ item.name }}</h2>
      <p><b>Description:</b> {{ item.description }}</p>
      <p><b>Price:</b> {{ item.price }}</p>
      <p><b>Availability:</b> {{ item.availability }}</p>
      <button  class="Order" @click="orderMenu">Order</button>
    </div>
  </div>
</template>
<script>

export default {
  name: "ChooseMenu",
  data() {
    return {
      items: [],
    }
  },
  methods: {
    fetchMenu() {
      fetch("http://localhost:8082/restaurants/13/menu")
    .then((response) => response.json())
          .then((data) => {this.items = data.items})
          .catch((err) => console.log(err.message));
    },
    orderMenu(){
      const  newOrder={
            "status": "WAITING_TO_BE_PAID",
            "restaurantId":13,
            "tableId":3,
          };

      fetch("http://localhost:8083/orders", {
      method: "POST",
          headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(newOrder),
    }).then((response) => response.json()).then((data)  => console.log("order", data))
    .catch((err) => console.log(err.message));
}
},
  mounted() {
    // call fetchRestaurants() when this element (AllRestaurants()) mounts
    this.fetchMenu();
    console.log("mounted");
  },
}
</script>