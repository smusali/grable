<template>
  <div class="ARestaurant">
    <div id="form">
      <h3>A Restaurant</h3>
      
      <div class="input-group">
        <label for="name">Name: </label>
        <input name="name" type="text" id="name" required v-model="restaurant.name" :class="{ 'error-border': errors.name }" @input="clearError('name')" />
        <p class="error-message" v-if="errors.name">{{ errors.name }}</p>
      </div>
  
      <div class="input-group">
        <label for="location">Location: </label>
        <input name="location" type="text" id="location" required v-model="restaurant.location" :class="{ 'error-border': errors.location }" @input="clearError('location')" />
        <p class="error-message" v-if="errors.location">{{ errors.location }}</p>
      </div>
  
      <div class="input-group">
        <label for="contactInfo">Contact Info: </label>
        <input name="contactInfo" type="text" id="contactInfo" required v-model="restaurant.contactInfo" :class="{ 'error-border': errors.contactInfo }" @input="clearError('contactInfo')" />
        <p class="error-message" v-if="errors.contactInfo">{{ errors.contactInfo }}</p>
      </div>

    </div>
    <div class="buttons_container">
      <button @click="updateRestaurant" class="updateRestaurant" :disabled="!dirty" :class="{ 'disabled-button': !dirty }">Update Restaurant</button>
      <button @click="showModal = true" class="deleteRestaurant">Delete Restaurant</button>
    </div>

    <div v-if="showModal" class="modal">
      <div class="modal-content">
        <h2>Are you sure you want to delete this restaurant?</h2>
        <button @click="deleteRestaurant" class="confirmDelete">Yes, delete it</button>
        <button @click="showModal = false" class="cancelDelete">No, keep it</button>
      </div>
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
      originalRestaurant: {}, // Store the original restaurant data
      errors: {},
      showModal: false,
      dirty: false, // Track whether any fields have been changed
    };
  },
  methods: {
    clearError(field) {
      this.errors[field] = "";
    },
    fetchRestaurant(id) {
      fetch(`http://localhost:8080/restaurants/${id}`)
        .then((response) => response.json())
        .then((data) => {
          this.restaurant = data;
          this.originalRestaurant = { ...data }; // Save the original data
        })
        .catch((err) => console.log(err.message));
    },
    updateRestaurant() {
      this.errors = {};
      if (this.restaurant.name.length < 2 || this.restaurant.name.length > 20) {
        this.errors.name = "Name should be between 2 and 20 characters.";
      }

      if (this.restaurant.location.length < 3 || this.restaurant.location.length > 50) {
        this.errors.location = "Location should be between 3 and 50 characters.";
      }

      if (
        this.restaurant.contactInfo.length < 3 ||
        this.restaurant.contactInfo.length > 12 ||
        !/^\d+$/.test(this.restaurant.contactInfo)
      ) {
        this.errors.contactInfo = "Contact Info should be between 3 and 12 numbers.";
      }

      if (Object.keys(this.errors).length === 0 && this.dirty) { // Check if any fields are dirty
        fetch(`http://localhost:8080/restaurants/${this.restaurant.id}`, {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(this.restaurant),
        })
          .then((response) => {
            console.log(response.data);
            this.$router.push("/allrestaurants");
          })
          .catch((e) => {
            console.log(e);
          });
      }
    },
    deleteRestaurant() {
      fetch(`http://localhost:8080/restaurants/${this.restaurant.id}`, {
        method: "DELETE",
      })
        .then((response) => {
          console.log(response.data);
          this.$router.push("/allrestaurants");
        })
        .catch((e) => {
          console.log(e);
        });

      this.showModal = false;
    },
    // Method to check if any field has been modified
    checkDirty() {
      this.dirty = Object.keys(this.restaurant).some(
        (key) => this.restaurant[key] !== this.originalRestaurant[key]
      );
    },
  },
  mounted() {
    this.fetchRestaurant(this.$route.params.id);
  },
  watch: {
    // Watch for changes in the restaurant object and update the dirty state accordingly
    restaurant: {
      handler: function () {
        this.checkDirty();
      },
      deep: true,
    },
  },
};
</script>

<style scoped>


.disabled-button {
  background: #ccc !important; 
  font-weight: bold;
  cursor: not-allowed;
}

.ARestaurant {
  max-width: 420px;
  margin: 30px auto;
  background: #f4f4f4;
  text-align: left;
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0px 0px 20px rgba(0,0,0,0.2);
  transition: box-shadow 0.3s ease;
}
.form {
  max-width: 420px;
  margin: 30px auto;
  background: #f4f4f4;
  text-align: left;
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0px 0px 20px rgba(0,0,0,0.2);
  transition: box-shadow 0.3s ease;
}
h3 {
  text-align: center;
  color: #5C6BC0;
  margin-bottom: 30px;
  font-size: 1.5em;
}
.input-group {
  margin-bottom: 20px;
}
label {
  color: #333;
  display: block;
  margin-bottom: 10px;
  font-size: 1em;
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
  transition: border 0.3s ease, box-shadow 0.3s ease;
}
input:focus {
  border: 1px solid #5C6BC0;
  box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
}
button {
  background: #5C6BC0;
  border: 0;
  padding: 10px 20px;
  margin-top: 20px;
  color: white;
  border-radius: 20px;
  cursor: pointer;
  transition: background 0.3s ease, transform 0.3s ease;
}
button:hover {
  background: #3949AB;
  transform: scale(1.05);
}
.error-border {
  border: 1px solid red;
}
.error-message {
  color: red;
  margin-top: 5px;
  font-size: 0.8em;
}

.modal {
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0,0,0,0.4);
}

.modal-content {
  background-color: #fefefe;
  margin: 15% auto;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
  text-align: center;
  box-shadow: 0px 0px 20px rgba(0,0,0,0.2);
}
.confirmDelete, .cancelDelete {
  background: #5C6BC0;
  border: 0;
  padding: 10px 20px;
  margin: 20px;
  color: white;
  border-radius: 20px;
  cursor: pointer;
  transition: background 0.3s ease, transform 0.3s ease;
}

.confirmDelete {
  background: red;
}

.confirmDelete:hover {
  background: darkred;
  transform: scale(1.05);
}

.cancelDelete {
  background: green;
}

.cancelDelete:hover {
  background: darkgreen;
  transform: scale(1.05);
}

.deleteRestaurant {
  background: red;
}

.deleteRestaurant:hover {
  background: darkred;
  transform: scale(1.05);
}

.updateRestaurant {
  background: green;
}

.updateRestaurant:hover {
  background: darkgreen;
  transform: scale(1.05);
}

.buttons_container{
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}
</style>