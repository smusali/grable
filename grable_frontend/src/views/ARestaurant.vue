<template>
  <div class="A Post">
    <div id="form">
      <h3>A Restaurant</h3>
      
      <label for="name">Name: </label>
      <input name="name" type="text" id="name" required v-model="restaurant.name" />
      
      <label for="location">Location: </label>
      <input name="location" type="text" id="location" required v-model="restaurant.location" />

      <label for="contactInfo">Contact Info: </label>
      <input name="contactInfo" type="text" id="contactInfo" required v-model="restaurant.contactInfo" />

    </div>
    <div>
      <button @click="updateRestaurant" class="updateRestaurant">Update Restaurant</button>
      <button @click="deleteRestaurant" class="deleteRestaurant">Delete Restaurant</button>
    </div>
  </div>
</template>


<script>
export default {
  name: "ARestaurant",
  data() {
    return {
      restaurant: {
        name: "",
        location: "",
        contactInfo: "",
      },
    };
  },
  methods: {
    fetchRestaurant(id) {
      // fetch one restaurant with the specified  id (id)
      fetch(`http://localhost:8081/restaurants/${id}`)
        .then((response) => response.json())
        .then((data) => (this.restaurant = data))
        .catch((err) => console.log(err.message));
    },
    updateRestaurant() {
      // using Fetch - put method - updates a specific restaurant based on the passed id and the specified body
      fetch(`http://localhost:8081/restaurants/${this.restaurant.id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(this.restaurant),
      })
        .then((response) => {
          console.log(response.data);
          //this.$router.push("/apost/" + this.restaurant.id);
          // We are using the router instance of this element to navigate to a different URL location
          this.$router.push("/allrestaurants");
        })
        .catch((e) => {
          console.log(e);
        });
    },
    deleteRestaurant() {
      fetch(`http://localhost:8081/restaurants/${this.restaurant.id}`, {
        method: "DELETE"
      })
          .then((response) => {
            console.log(response.data);
            this.$router.push("/allrestaurants");
          })
          .catch((e) => {
            console.log(e);
          });
    },
  },
  mounted() {
    // call fetchAPost() when this element mounts, and pass to it a route parameter  (id)
    // Route parameters (this.$route.params.id) are named URL segments that are used to capture the values specified at their 
    // position in the URL. The captured values are populated in the req.params object, with the name 
    // of the route parameter specified in the path as their respective keys
    this.fetchRestaurant(this.$route.params.id);
  },
};
</script>

<style scoped>
#form {
  max-width: 420px;
  margin: 30px auto;
  background: rgb(167, 154, 154);
  text-align: left;
  padding: 40px;
  border-radius: 10px;
}
h3 {
  text-align: center;
  color: rgb(8, 110, 110);
}
label {
  color: rgb(8, 110, 110);
  display: inline-block;
  margin: 25px 0 15px;
  font-size: 0.8em;
  text-transform: uppercase;
  letter-spacing: 1px;
  font-weight: bold;
}
input {
  display: block;
  padding: 10px 6px;
  width: 100%;
  box-sizing: border-box;
  border: none;
  border-bottom: 1px solid white;
  color: blue;
}
button {
  background: rgb(8, 110, 110);
  border: 0;
  padding: 10px 20px;
  margin-top: 20px;
  color: white;
  border-radius: 20px;
}
</style>