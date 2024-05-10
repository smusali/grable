<template>
  <div class="ARestaurant">
    <div id="form">
      <h3>A Restaurant</h3>
      <div class="input-group">
        <label for="name">Name: </label>
        <input name="name" type="text" id="name" required v-model="restaurant.name"
          :class="{ 'error-border': errors.name }" @input="clearError('name')" />
        <p class="error-message" v-if="errors.name">{{ errors.name }}</p>
      </div>
      <div class="input-group">
        <label for="location">Location: </label>
        <input name="location" type="text" id="location" required v-model="restaurant.location"
          :class="{ 'error-border': errors.location }" @input="clearError('location')" />
        <p class="error-message" v-if="errors.location">{{ errors.location }}</p>
      </div>
      <div class="input-group">
        <label for="contactInfo">Contact Info: </label>
        <input name="contactInfo" type="text" id="contactInfo" required v-model="restaurant.contactInfo"
          :class="{ 'error-border': errors.contactInfo }" @input="clearError('contactInfo')" />
        <p class="error-message" v-if="errors.contactInfo">{{ errors.contactInfo }}</p>
      </div>
    </div>
    <div class="buttons_container">
      <button @click="updateRestaurant" class="updateRestaurant" :disabled="!dirty"
        :class="{ 'disabled-button': !dirty }">Update Restaurant</button>
      <button @click="deleteRestaurantModalVisible = true" class="deleteRestaurant">Delete Restaurant</button>
    </div>
    <div v-if="deleteModalVisible" class="modal">
      <div class="modal-content">
        <h2>Are you sure you want to delete this item?</h2>
        <button @click="deleteItem(itemToDeleteId)" class="confirmDelete">Yes, delete it</button>
        <button @click="hideDeleteModal" class="cancelDelete">Cancel</button>
      </div>
    </div>

    <!-- Add menu card -->
    <div class="menu-container">
      <div class="menu-card" v-if="menu">
        <h3>Menu</h3>
        <p style="text-align: center; margin-bottom: 50px;">Last updated: <strong>{{ menu.lastUpdated.substr(0,10)}}</strong></p>
        <div v-if="menu.items.length === 0" style="text-align: center;">No items in the menu</div>
        <ul class="menu-items" v-if="menu && menu.items && menu.items.length > 0">
          <li v-for="item in menu.items" :key="item.id" class="menu-item">
            <div class="item-details">
              <h3 class="item-name" style="text-align: start; color: black;">{{ item.name }}</h3>
              <p class="item-description">{{ item.description }}</p>
              <p class="item-price">Price: <strong>${{ item.price }}</strong></p>
              <p class="item-category">Category: <strong>{{ item.category }}</strong></p>
              <p class="item-availability">Availability: <strong>{{ item.availability ? 'Yes' : 'No' }}</strong></p>
              <p class="item-dietary-info">Dietary Info: <strong>{{ item.dietaryInfo }}</strong></p>
            </div>
            <div class="item-actions">
              <button class="edit-button" @click="showEditModal(item)">Edit</button>
              <button class="delete-button" @click="showDeleteModal(item.id)">Delete</button>
            </div>
          </li>
        </ul>
        <button @click=" errors={}; addItemModalVisible = true" class="add-item-button">Add Item</button>
      </div>
      <div v-else>
        <button @click="createMenu" class="create-menu-button">Create Menu</button>
      </div>
    </div>

    <div v-if="deleteItemModalVisible" class="modal">
      <div class="modal-content">
        <h2>Are you sure you want to delete this item?</h2>
        <button @click="deleteItem(itemToDeleteId)" class="confirmDelete">Yes, delete it</button>
        <button @click="hideDeleteItemModal" class="cancelDelete">Cancel</button>
      </div>
    </div>

    <div v-if="deleteRestaurantModalVisible" class="modal">
      <div class="modal-content">
        <h2>Are you sure you want to delete this restaurant?</h2>
        <button @click="deleteRestaurant" class="confirmDelete">Yes, delete it</button>
        <button @click="hideDeleteRestaurantModal" class="cancelDelete">Cancel</button>
      </div>
    </div>

    <div v-if="editItemModalVisible" class="modal">
      <div class="modal-content">
        <h2>Edit Item</h2>
        <div class="input-group">
          <label for="editItemName">Name:</label>
          <input type="text" id="editItemName" v-model="editedItem.name" required :class="{ 'error-border': editErrors.name }">
          <p class="error-message" v-if="editErrors.name">{{ editErrors.name }}</p>
        </div>
        <div class="input-group">
          <label for="editItemDescription">Description:</label>
          <input type="text" id="editItemDescription" v-model="editedItem.description" required :class="{ 'error-border': editErrors.description }">
          <p class="error-message" v-if="editErrors.description">{{ editErrors.description }}</p>
        </div>
        <div class="input-group">
          <label for="editItemPrice">Price:</label>
          <input type="number" id="editItemPrice" v-model="editedItem.price" required :class="{ 'error-border': editErrors.price }">
          <p class="error-message" v-if="editErrors.price">{{ editErrors.price }}</p>
        </div>
        <div class="input-group">
          <label for="editItemCategory">Category:</label>
          <select id="editItemCategory" v-model="editedItem.category">
            <option value="Main Course">Main Course</option>
            <option value="Appetizer">Appetizer</option>
            <option value="Dessert">Dessert</option>
            <option value="Beverage">Beverage</option>
          </select>
        </div>
        <div class="input-group">
          <label for="editItemAvailability">Availability:</label>
          <select id="editItemAvailability" v-model="editedItem.availability">
            <option :value="true" :selected="editedItem.availability === true">Yes</option>
            <option :value="false" :selected="editedItem.availability === false">No</option>
          </select>
        </div>
        <div class="input-group">
          <label for="editItemDietaryInfo">Dietary Info:</label>
          <select id="editItemDietaryInfo" v-model="editedItem.dietaryInfo">
            <option value="Vegan">Vegan</option>
            <option value="Vegetarian">Vegetarian</option>
            <option value="Gluten-Free">Gluten-Free</option>
            <option value="Nut-Free">Nut-Free</option>
          </select>
        </div>
        <div class="modal-buttons">
          <button @click="updateItem" class="add-button">Update Item</button>
          <button @click="cancelEdit" class="cancel-button">Cancel</button>
        </div>
      </div>
    </div>

    <div class="modal" v-if="addItemModalVisible">
      <div class="modal-content">
        <h2>Add New Item</h2>
        <div class="input-group">
          <label for="itemName">Name:</label>
          <input type="text" id="itemName" v-model="newItem.name" required :class="{ 'error-border': errors.name }">
          <p class="error-message" v-if="errors.name">{{ errors.name }}</p>
        </div>
        <div class="input-group">
          <label for="itemDescription">Description:</label>
          <input type="text" id="itemDescription" v-model="newItem.description" required :class="{ 'error-border': errors.description }">
          <p class="error-message" v-if="errors.description">{{ errors.description }}</p>
        </div>
        <div class="input-group">
          <label for="itemPrice">Price:</label>
          <input type="number" id="itemPrice" v-model="newItem.price" required :class="{ 'error-border': errors.price }">
          <p class="error-message" v-if="errors.price">{{ errors.price }}</p>
        </div>
        <div class="input-group">
          <label for="itemCategory">Category:</label>
          <select id="itemCategory" v-model="newItem.category">
            <option value="Main Course">Main Course</option>
            <option value="Appetizer">Appetizer</option>
            <option value="Dessert">Dessert</option>
            <option value="Beverage">Beverage</option>
          </select>
        </div>
        <div class="input-group">
          <label for="itemAvailability">Availability:</label>
          <select id="itemAvailability" v-model="newItem.availability">
            <option value="yes">Yes</option>
            <option value="no">No</option>
          </select>
        </div>
        <div class="input-group">
          <label for="itemDietaryInfo">Dietary Info:</label>
          <select id="itemDietaryInfo" v-model="newItem.dietaryInfo">
            <option value="Vegan">Vegan</option>
            <option value="Vegetarian">Vegetarian</option>
            <option value="Gluten-Free">Gluten-Free</option>
            <option value="Nut-Free">Nut-Free</option>
          </select>
        </div>    
        <div class="modal-buttons">
          <button @click="addItem" class="add-button">Add Item</button>
          <button @click=" errors={}; addItemModalVisible = false;" class="cancel-button">Cancel</button>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
export default {
  name: "ARestaurant",
  data() {
    
  return {
    editItemModalVisible: false,
    addItemModalVisible: false, 
    deleteItemModalVisible: false,
    deleteRestaurantModalVisible: false,
    editedItem: {
      id: null,
      name: '',
      description: '',
      price: '',
      category: '',
      availability: '',
      dietaryInfo: ''
    },
    editErrors: {},
    newItem: {
      name: '',
      description: '',
      price: '',
      category: 'Main Course',
      availability: 'yes',
      dietaryInfo: 'Vegan'
    },
    restaurant: {
      name: "",
      location: "",
      contactInfo: "",
    },
    originalRestaurant: {}, // Store the original restaurant data
    errors: {},
    deleteModalVisible: false,
    dirty: false, 
    menu: null,// Track whether any fields have been changed
  };
},
  methods: {
    showEditModal(item) {
    this.editedItem = { ...item }; // Copy item data to editedItem
    this.editItemModalVisible = true;
  },
  cancelEdit() {
    this.editItemModalVisible = false;
    this.editErrors = {}; // Clear edit errors
  },

  updateItem() {
    this.editErrors = {};
    if (!this.editedItem.name) {
      this.editErrors.name = "Name is required.";
    } else if (this.editedItem.name.length < 2 || this.editedItem.name.length > 50) {
      this.editErrors.name = "Name should be between 2 and 50 characters.";
    }
    if (!this.editedItem.description) {
      this.editErrors.description = "Description is required.";
    } else if (this.editedItem.description.length < 2 || this.editedItem.description.length > 100) {
      this.editErrors.description = "Description should be between 2 and 100 characters.";
    }
    if (!this.editedItem.price) {
      this.editErrors.price = "Price is required.";
    } else if (this.editedItem.price <= 0) {
      this.editErrors.price = "Price should be greater than 0.";
    }
    if (Object.keys(this.editErrors).length > 0) {
      return;
    }

    fetch(`http://localhost:8082/restaurants/${this.restaurant.id}/menu/${this.menu.id}/items/${this.editedItem.id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(this.editedItem),
    })
      .then((response) => {
        if (response.ok) {
          return response.json();
        }
        throw new Error("Failed to update item");
      })
      .then((data) => {
        // Update the item in the menu
        const index = this.menu.items.findIndex((item) => item.id === data.id);
        this.menu.items[index] = data;
        this.editItemModalVisible = false;
      })
      .catch((error) => {
        console.error("Error updating item:", error);
      });
  },







    showDeleteItemModal(itemId) {
      this.itemToDeleteId = itemId;
      this.deleteItemModalVisible = true;
    },
    hideDeleteItemModal() {
      this.deleteItemModalVisible = false;
    },

    showDeleteRestaurantModal() {
      this.deleteRestaurantModalVisible = true;
    },
    hideDeleteRestaurantModal() {
      this.deleteRestaurantModalVisible = false;
    },
    addItem() {
  // Clear previous errors
  this.errors = {};

  // Validate new item fields
  if (!this.newItem.name) {
    this.errors.name = "Name is required.";
  } else if (this.newItem.name.length < 2 || this.newItem.name.length > 50) {
    this.errors.name = "Name should be between 2 and 50 characters.";
  }

  if (!this.newItem.description) {
    this.errors.description = "Description is required.";
  } else if (this.newItem.description.length < 2 || this.newItem.description.length > 100) {
    this.errors.description = "Description should be between 2 and 100 characters.";
  }

  if (!this.newItem.price) {
    this.errors.price = "Price is required.";
  } else if (this.newItem.price <= 0) {
    this.errors.price = "Price should be greater than 0.";
  }

  // If there are errors, stop execution
  if (Object.keys(this.errors).length > 0) {
    return;
  }

  // Create a new item object
  const newItem = {
    name: this.newItem.name,
    description: this.newItem.description,
    price: this.newItem.price,
    category: this.newItem.category || "Main Course", // Default category
    availability: this.newItem.availability === 'yes', // Convert string to boolean
    dietaryInfo: this.newItem.dietaryInfo || "Vegan", // Default dietary info
  };

  // Send a POST request to add the new item
  fetch(`http://localhost:8082/restaurants/${this.restaurant.id}/menu/${this.menu.id}/items`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(newItem),
  })
    .then((response) => {
      if (response.ok) {
        return response.json(); // Parse response JSON
      }
      throw new Error("Failed to add item"); // Throw error if response is not okay
    })
    .then((data) => {
      // Update the menu items with the new item
      this.menu.items.push(data); 
      // Reset newItem fields
      this.newItem = {
        name: "",
        description: "",
        price: "",
        category: "Main Course",
        availability: "yes",
        dietaryInfo: "Vegan",
      };
      // Hide modal after successful addition
      this.addItemModalVisible = false;
    })
    .catch((error) => {
      // Handle any errors that occur during the fetch operation
      console.error("Error adding item:", error);
    });
},

    clearError(field) {
      this.errors[field] = "";
    },
    fetchRestaurant(id) {
      console.log("fetching restaurant");
      fetch(`http://localhost:8081/restaurants/${id}`)
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
        fetch(`http://localhost:8081/restaurants/${this.restaurant.id}`, {
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
  fetch(`http://localhost:8081/restaurants/${this.restaurant.id}`, {
    method: "DELETE",
  })
    .then((response) => {
      console.log(response.data);
      this.$router.push("/allrestaurants");
    })
    .catch((e) => {
      console.log(e);
    });

  this.deleteModalVisible = false;
},
    createMenu() {
      fetch(`http://localhost:8082/restaurants/${this.restaurant.id}/menu`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
      })
        .then((response) => {
          if (response.ok) {
            return response.json();
          }
          throw new Error("Failed to create menu");
        })
        .then((data) => {
          // Update menu data property
          this.menu = data;
        })
        .catch((error) => {
          console.error("Error creating menu:", error);
        });
    },
    fetchMenu() {
      console.log("fetching menu");
      fetch(`http://localhost:8082/restaurants/${this.restaurant.id}/menu`)
        .then((response) => response.json())
        .then((data) => {
          this.menu = data;
        })
        .catch((err) => {
          console.error("Error fetching menu:", err);
        });
    },
    checkDirty() {
      this.dirty = Object.keys(this.restaurant).some(
        (key) => this.restaurant[key] !== this.originalRestaurant[key]
      );
    },
    deleteItem(itemId) {
    // Assuming you have an API endpoint to delete an item by its ID
    fetch(`http://localhost:8082/restaurants/${this.restaurant.id}/menu/${this.menu.id}/items/${itemId}`, {
      method: "DELETE",
    })
    .then((response) => {
      if (response.ok) {
        // If the deletion is successful, remove the item from the menu
        this.menu.items = this.menu.items.filter(item => item.id !== itemId);
        // Optionally, you can display a success message or perform any other action
      } else {
        throw new Error("Failed to delete item");
      }
    })
    .catch((error) => {
      console.error("Error deleting item:", error);
      // Optionally, you can display an error message to the user
    });
    this.hideDeleteModal(); // Hide the delete modal after deletion
  },
  showDeleteModal(itemId) {
    this.itemToDeleteId = itemId; // Store the ID of the item to be deleted
    this.deleteModalVisible = true; // Show the delete modal
  },
  hideDeleteModal() {
    this.deleteModalVisible = false; // Hide the delete modal
  },
  },
  mounted() {
  this.fetchRestaurant(this.$route.params.id);
  

  // Check if a menu exists for the restaurant
  fetch(`http://localhost:8082/restaurants/${this.$route.params.id}/menu`)
    .then((response) => {
      if (response.ok) {
        return response.json();
      }
      throw new Error("Menu not found");
    })
    .then((data) => {
      // If a menu exists, store it in the menu data property
      this.menu = data;
    })
    .catch((error) => {
      // If a menu does not exist, handle the error (e.g., display a message)
      console.error("Error fetching menu:", error);
    });
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

.input-group {
  margin-bottom: 20px;
  position: relative; /* Required for positioning error message */
}

.error-border input {
  border: 1px solid red;
}

.error-message {
  position: absolute;
  bottom: -30px;
  left: 0;
  color: red;
  font-size: 0.8em;
}

.modal-content input,
.modal-content select {
  /* Modify styles for inputs and selects inside the modal */
  width: calc(100% - 20px);
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 3px;
  margin-bottom: 10px;
}


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
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.2);
  transition: box-shadow 0.3s ease;
}

.form {
  max-width: 420px;
  margin: 30px auto;
  background: #f4f4f4;
  text-align: left;
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.2);
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
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
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
  background-color: rgba(0, 0, 0, 0.4);
}

.modal-content {
  background-color: #fefefe;
  margin: 15% auto;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
  text-align: center;
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.2);
}

.confirmDelete,
.cancelDelete {
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

.buttons_container {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.menu-container {
  margin-top: 30px;
}

.menu-card {
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
}

.menu-card h3 {
  color: #5C6BC0;
  margin-bottom: 10px;
}

.menu-items {
  list-style: none;
  padding: 0;
}

.menu-item {
  display: flex;
  justify-content: space-between;
  align-items: end;
  border-bottom: 1px solid #ddd;
  padding: 20px 0;
}

.item-details {
  flex-grow: 1;
}

.item-name {
  margin-top: 0;
  margin-bottom: 5px;
  color: #333;
}

.item-description {
  margin-bottom: 10px;
}

.item-actions button {
  background-color: #5C6BC0;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 8px 16px;
  margin-left: 10px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.item-actions button:hover {
  background-color: #3949AB;
}

.add-item-button {
  background-color: #5C6BC0;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  margin-top: 20px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.add-item-button:hover {
  background-color: #3949AB;
}

.create-menu-button {
  background-color: #5C6BC0;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  margin-top: 20px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.create-menu-button:hover {
  background-color: #3949AB;
}

.modal {
  /* Modal styles */
  position: fixed;
  z-index: 9999;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  /* Modal content styles */
  background-color: white;
  padding: 50px;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
  max-width: 600px;
}

.input-group {
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 5px;
}

input, select {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 3px;
}

.modal-buttons button {
  margin-right: 10px;
  padding: 8px 16px;
  background-color: #5C6BC0;
  color: white;
  border: none;
  border-radius: 3px;
  cursor: pointer;
}

.modal-buttons button:hover {
  background-color: #3949AB;
}

</style>