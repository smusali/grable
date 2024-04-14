<template>
  <div class="AllRestaurants">
    <div id="restaurants-list">
    <h1>All Restaurants</h1>
      <ul>
        <div class="item" v-for="restaurant in restaurants" :key="restaurant.id">
          <!--  We are putting an anchor for each restaurant, when we click on it, we will be directed to the specific restaurant view (/arestaurant/) /  -->
          <a class="singlerestaurant" :href="'/arestaurant/' + restaurant.id">
            <span class="id"> <b>Id:</b> {{ restaurant.id }} </span><br />
            <span class="name"> <b>Name:</b> {{ restaurant.name }} </span> <br />
            <span class="location"> <b>Location:</b> {{ restaurant.location }} </span> <br />
            <span class="contactInfo"> <b>Contact Info:</b> {{ restaurant.contactInfo }} </span> <br />
          </a>
        </div>
      </ul>
    </div>
  </div>
</template>


<script>
export default {
  name: "AllRestaurants",
  data() {
    return {
      restaurants: [],
    };
  },
  methods: {
    fetchRestaurants() {
      // fetch is a GET request by default unless stated otherwise. Therefore, it will fetch all restaurants from the database
      fetch(`http://localhost:8081/restaurants`)
        .then((response) => response.json())
        .then((data) => (this.restaurants = data))
        .catch((err) => console.log(err.message));
    },
  },
  mounted() {
    // call fetchRestaurants() when this element (AllRestaurants()) mounts
    this.fetchRestaurants();
    console.log("mounted");
  },
};
</script>

<style scoped>
h1 {
  font-size: 20px;
}
a {
  text-decoration: none;
}
a:hover {
  text-decoration: underline;
}
.item {
  background: rgb(189, 212, 199);
  margin-bottom: 5px;
  padding: 3px 5px;
  border-radius: 10px;
}
#post-list {
  background: #6e8b97;
  box-shadow: 1px 2px 3px rgba(0, 0, 0, 0.2);
  margin-bottom: 30px;
  padding: 10px 20px;
  margin: auto;
  width: 50%;
  border-radius: 20px;
}
#post-list ul {
  padding: 0;
}
#post-list li {
  display: inline-block;
  margin-right: 10px;
  margin-top: 10px;
  padding: 20px;
  background: rgba(255, 255, 255, 0.7);
}
</style>