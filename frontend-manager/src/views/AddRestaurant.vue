<template>
  <div class="form">
    <h3>Add a Restaurant</h3>

    <div class="input-group">
      <label for="name">Name: </label>
      <input name="name" type="text" id="name" required v-model="restaurants.name" :class="{ 'error-border': errors.name }" @input="clearError('name')" />
      <p class="error-message" v-if="errors.name">{{ errors.name }}</p>
    </div>

    <div class="input-group">
      <label for="location">Location: </label>
      <input name="location" type="text" id="location" required v-model="restaurants.location" :class="{ 'error-border': errors.location }" @input="clearError('location')" />
      <p class="error-message" v-if="errors.location">{{ errors.location }}</p>
    </div>

    <div class="input-group">
      <label for="contactInfo">Contact Info: </label>
      <input name="contactInfo" type="text" id="contactInfo" required v-model="restaurants.contactInfo" :class="{ 'error-border': errors.contactInfo }" @input="clearError('contactInfo')" />
      <p class="error-message" v-if="errors.contactInfo">{{ errors.contactInfo }}</p>
    </div>

    <button @click="addRestaurant" class="addPost">Add Restaurant</button>
  </div>
</template>

<script>
export default {
  name: "AddRestaurant",
  data() {
    return {
      restaurants: {
        name: "",
        location: "",
        contactInfo: "",
      },
      errors: {
        name: "",
        location: "",
        contactInfo: "",
      },
    };
  },
  methods: {
    clearError(field) {
      this.errors[field] = "";
    },
    addRestaurant() {
      this.errors = {
        name: "",
        location: "",
        contactInfo: "",
      };

      if (this.restaurants.name.length < 2 || this.restaurants.name.length > 20) {
        this.errors.name = "Name should be between 2 and 20 characters.";
      }

      if (this.restaurants.location.length < 3 || this.restaurants.location.length > 30) {
        this.errors.location = "Location should be between 3 and 30 characters.";
      }

      if (this.restaurants.contactInfo.length < 3 || this.restaurants.contactInfo.length > 12 || !/^\d+$/.test(this.restaurants.contactInfo)) {
        this.errors.contactInfo = "Contact Info should be between 3 and 12 numbers.";
      }

      if (Object.values(this.errors).every((error) => error === "")) {
        var data = {
          name: this.restaurants.name,
          location: this.restaurants.location,
          contactInfo: this.restaurants.contactInfo,
        };

        fetch("http://localhost:8081/restaurants", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(data),
        })
          .then((response) => {
            console.log(response.data);
            this.$router.push("/allrestaurants");
          })
          .catch((e) => {
            console.log(e);
            console.log("error");
          });
      }
    },
  },
};
</script>


<style scoped>
.form {
  max-width: 420px;
  margin: 30px auto;
  background: #f4f4f4;
  text-align: left;
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
}
h3 {
  text-align: center;
  color: #333;
  margin-bottom: 30px;
}
.input-group {
  margin-bottom: 20px;
}
label {
  color: #333;
  display: block;
  margin-bottom: 10px;
  font-size: 0.9em;
  text-transform: uppercase;
  letter-spacing: 1px;
  font-weight: bold;
}
input {
  display: block;
  padding: 10px;
  width: 100%;
  box-sizing: border-box;
  border: 1px solid #ddd;
  border-radius: 5px;
  transition: border 0.3s ease;
}
input:focus {
  border: 1px solid #5C6BC0;
}
button {
  background: #5C6BC0;
  border: 0;
  padding: 10px 20px;
  margin-top: 20px;
  color: white;
  border-radius: 20px;
  cursor: pointer;
  transition: background 0.3s ease;
}
button:hover {
  background: #3949AB;
}
.error-border {
  border: 1px solid red;
}
.error-message {
  color: red;
  margin-top: 5px;
  font-size: 0.8em;
}
</style>