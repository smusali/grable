<template>
  <div class="form">
    <h3>Add a Restaurants</h3>

    <label for="name">Name: </label>
    <input name="name" type="text" id="name" required v-model="restaurants.name" />

    <label for="location">Location: </label>
    <input name="location"  type="text" id="location" required v-model="restaurants.location"/>

    <label for="contactInfo">Contact Info: </label>
    <input name="contactInfo"  type="text" id="contactInfo" required v-model="restaurants.contactInfo"/>

    <button @click="addRestaurants" class="addPost">Add Restaurants</button>
  </div>
</template>

<script>
export default {
  name: "AddRestaurants",
  data() {
    return {
      restaurants: {
        name: "",
        location: "",
        contactInfo: "",
      },
    };
  },
  methods: {
    addRestaurants() {
      var data = {
        name: this.restaurants.name,
        location: this.restaurants.location,
        contactInfo: this.restaurants.contactInfo,
      };
      // using Fetch - post method - send an HTTP post request to the specified URI with the defined body
      fetch("http://localhost:8081/restaurants", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      })
      .then((response) => {
        console.log(response.data);
        // redirect to /allposts view
        this.$router.push("/allrestaurants");
      })
      .catch((e) => {
        console.log(e);
        console.log("error");
      });
    },
  },
};
</script>

<style scoped>
.form {
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
  align-items: center;
  text-align: center;
}
</style>