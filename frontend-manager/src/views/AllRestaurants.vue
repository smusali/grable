<template>
  <nav class="navbar">
    <div class="navbar-menu">
      <router-link to="/allrestaurants" class="navbar-item" active-class="active-link">Restaurants</router-link>
      <router-link to="/addrestaurant" class="navbar-item" active-class="active-link">Add a Restaurant</router-link>
    </div>
  </nav>
  <div class="AllRestaurants">
    <h1>All Restaurants</h1>
    <div class="restaurants-list">
      <div class="restaurant-card" v-for="restaurant in restaurants" :key="restaurant.id">
        <a :href="'/arestaurant/' + restaurant.id">
          <h2>{{ restaurant.name }}</h2>
          <p><b>Id:</b> {{ restaurant.id }}</p>
          <p><b>Location:</b> {{ restaurant.location }}</p>
          <p><b>Contact Info:</b> {{ restaurant.contactInfo }}</p>
        </a>
      </div>
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
      const token = localStorage.getItem('jwtToken');
      const headers = {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`
      };
      // fetch is a GET request by default unless stated otherwise. Therefore, it will fetch all restaurants from the database
      fetch(`http://localhost:8080/restaurants`, { headers })
        .then((response) => response.json())
        .then((data) => (this.restaurants = data))
        .catch((err) => console.log(err.message));
    },
  },
  mounted() {
    this.fetchRestaurants();
  },
};
</script>

<style scoped>
.AllRestaurants {
  display: flex;
  flex-direction: column;
  align-items: center;
  background: #f4f4f4;
  padding: 20px;
}
h1 {
  color: #333;
  margin-bottom: 30px;
}
.restaurants-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  width: 100%;
}
.restaurant-card {
  background: white;
  border-radius: 10px;
  box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
  margin: 20px;
  padding: 20px;
  width: calc(25% - 40px);
  transition: box-shadow 0.3s ease;
}
.restaurant-card:hover {
  box-shadow: 0px 0px 20px rgba(0,0,0,0.2);
}
.restaurant-card a {
  text-decoration: none;
  color: #333;
}
</style>