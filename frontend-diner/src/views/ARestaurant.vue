<template>
  <div class="DinerManager">
    <div class="menu-container">

      <router-link to="/restaurants" class="back-button">Back to All Restaurants</router-link>
      <!-- Restaurant Information -->
      <div class="restaurant-info">
        <div class="info-header">
          <h2>{{ restaurant.name }}</h2>
          <p>{{ restaurant.location }}</p>
          <p>{{ restaurant.contactInfo }}</p>
        </div>
      </div>

      <!-- Menu -->
      <div class="menu-card" v-if="menu">
        <h3>Menu</h3>
        <p class="last-updated">Last updated: <strong>{{ menu.lastUpdated.substr(0, 10) }}</strong></p>
        <div v-if="menu.items.length === 0" class="no-items">No items in the menu</div>
        <div class="menu-items" v-else>
          <div v-for="item in menu.items" :key="item.id" class="menu-item">
            <div class="item-details">
              <h4>{{ item.name }}</h4>
              <p class="description">{{ item.description }}</p>
              <p class="category">{{ item.category }}</p>
              <div class="item-info">
                <p class="price">$ {{ item.price }}</p>
              </div>
            </div>
            <div class="item-actions">
              <input type="number" v-model="item.quantity" class="quantity-input" placeholder="Quantity">
              <button @click="addToCart(item)" class="order-button">Order</button>
            </div>
          </div>
        </div>
      </div>
      <div v-else>
        <p>No menu available</p>
      </div>
    </div>

    <!-- Order Confirmation Modal -->
    <div v-if="cart.length > 0" class="modal">
      <div class="modal-content">
        <h2>Order Confirmation</h2>
        <div v-for="(item, index) in cart" :key="index" class="order-item">
          <h3>{{ item.name }}</h3>
          <p class="description">Description: {{ item.description }}</p>
          <p class="price">Price: ${{ item.price }}</p>
        </div>
        <div class="input-group">
          <label for="cardNumber">Card Number:</label>
          <input type="text" id="cardNumber" v-model="cardInfo.cardNumber" required>
          <span class="error-message">{{ errors.cardNumber }}</span>
        </div>
        <div class="input-group">
          <label for="expiryDate">Expiry Date:</label>
          <input type="date" id="expiryDate" v-model="cardInfo.expiryDate" required>
          <span class="error-message">{{ errors.expiryDate }}</span>
        </div>
        <div class="input-group">
          <label for="cvv">CVV:</label>
          <input type="text" id="cvv" v-model="cardInfo.cvv" required>
          <span class="error-message">{{ errors.cvv }}</span>
        </div>
        <div class="modal-buttons" v-for="(item, index) in cart" :key="index">
        
          <button @click="confirmOrder(item)" class="confirm-button">Confirm Order</button>
          <button @click="cancelOrder" class="cancel-button">Cancel</button>
   
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "DinerManager",
  data() {
    return {
      restaurant: {
        name: "",
        location: "",
        contactInfo: ""
      },
      menu: null,
      cart: [],
      cardInfo: {
        cardNumber: "",
        expiryDate: "",
        cvv: ""
      },
      errors: {
        cardNumber: "",
        expiryDate: "",
        cvv: ""
      }
    };
  },
  methods: {
    fetchRestaurant(id) {
      fetch(`http://localhost:8080/restaurants/${id}`)
        .then((response) => response.json())
        .then((data) => {
          this.restaurant = data;
        })
        .catch((err) => console.log(err.message));
    },
    fetchMenu(id) {
      fetch(`http://localhost:8080/restaurants/${id}/menu`)
        .then((response) => response.json())
        .then((data) => {
          this.menu = data;
          // Initialize quantity property for each item
          this.menu.items.forEach(item => {
            item.quantity = 0;
          });
        })
        .catch((err) => console.log(err.message));
    },
    addToCart(item) {
      // Check if quantity is set
      if (item.quantity > 0) {
        // Add item to cart with specified quantity
        this.cart.push({
          ...item,
          quantity: item.quantity
        });
        // Reset quantity for this item
        item.quantity = 0;
      } else {
        alert("Please set the quantity before ordering.");
      }
    },
    removeFromCart(index) {
      this.cart.splice(index, 1);
    },
    confirmOrder(item) {
      if (this.validateCardInfo()) {
        fetch("http://localhost:8083/orders/1/orderDetails", {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
            orderid: 1, 
            menuItemId: item.id, 
            quantity: item.quantity
          })
        }).then((response) => response.json())
          .then((data) => {
            console.log(data);
          })
          .catch((err) => console.log(err.message));

        this.cart = [];
        this.cardInfo = {
          cardNumber: "",
          expiryDate: "",
          cvv: ""
        };
        alert("Order successfully placed!");
      }
    },
    cancelOrder() {
      this.cart = [];
      this.cardInfo = {
        cardNumber: "",
        expiryDate: "",
        cvv: ""
      };
      this.errors = {
        cardNumber: "",
        expiryDate: "",
        cvv: ""
      };
    },
    validateCardInfo() {
      // Reset errors
      this.errors = {
        cardNumber: "",
        expiryDate: "",
        cvv: ""
      };

      let isValid = true;

      // Check card number
      if (!/^\d{16}$/.test(this.cardInfo.cardNumber)) {
        this.errors.cardNumber = "Card number must be a 16-digit number.";
        isValid = false;
      }

      // Check expiry date
      const currentDate = new Date();
      const expiryDate = new Date(this.cardInfo.expiryDate);
      if (!(expiryDate instanceof Date && !isNaN(expiryDate) && expiryDate > currentDate)) {
        this.errors.expiryDate = "Expiry date must be at least one day after the current date.";
        isValid = false;
      }

      // Check CVV
      if (!/^\d{3}$/.test(this.cardInfo.cvv)) {
        this.errors.cvv = "CVV must be a 3-digit number.";
        isValid = false;
      }

      return isValid;
    }
  },
  mounted() {
    this.fetchRestaurant(this.$route.params.id);
    this.fetchMenu(this.$route.params.id);
  }
};
</script>

<style scoped>
/* Styles for the DinerManager component */

.back-button {
  margin: 20px;
  padding: 10px 20px;
  background-color: #5C6BC0;
  color: white;
  border: none;
  border-radius: 5px;
  text-decoration: none;
  text-align: center;
  transition: background-color 0.3s ease;
}

.back-button:hover {
  background-color: #3949AB;
}
.DinerManager {
  font-family: 'Arial', sans-serif;
  color: #333;
}

.menu-container {
  max-width: 1000px;
  margin: 0 auto;
}

.restaurant-info {
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  margin-top: 30px;
}

.info-header h2 {
  font-size: 24px;
  margin-bottom: 5px;
}

.info-header p {
  font-size: 16px;
  margin-bottom: 10px;
}

.menu-card {
  margin-top: 30px;
  background-color: #ffffff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
}

.menu-card h3 {
  font-size: 20px;
  color: #5C6BC0;
  margin-bottom: 10px;
}

.last-updated {
  text-align: center;
  margin-bottom: 20px;
}

.no-items {
  text-align: center;
}

.menu-items {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  grid-gap: 20px;
}

.menu-item {
  background-color: #f9f9f9;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.menu-item:hover {
  transform: translateY(-5px);
}

.item-image img {
  width: 100%;
  height: 150px;
  object-fit: cover;
}

.item-details {
  padding: 20px;
}

.item-details h4 {
  font-size: 18px;
  margin-bottom: 5px;
}

.description {
  margin-bottom: 10px;
}

.item-info {
  text-align: right;
}

.price {
  font-weight: bold;
  color: #5C6BC0;
  margin-bottom: 10px;
  font-size: large;
}

.category,
.availability,
.dietary-info {
  font-size: 14px;
  font-style: italic;
}

.item-actions {
  padding: 20px;
}

.quantity-input {
  width: 70px;
  padding: 5px;
  margin-right: 10px;
}

.order-button {
  background-color: #5C6BC0;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 8px 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.order-button:hover {
  background-color: #3949AB;
}

.modal {
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
  background-color: white;
  padding: 50px;
  border-radius: 10px;
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.2);
  max-width: 500px;
  width: 100%;
}

.order-item {
  margin-bottom: 20px;
}

.input-group {
  margin-bottom: 20px;
}

.input-group label {
  display: block;
  margin-bottom: 5px;
}

.input-group input {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.error-message {
  color: #f44336;
  font-size: 14px;
  margin-top: 5px;
}

.modal-buttons {
  display: flex;
  justify-content: space-between;
}

.confirm-button,
.cancel-button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.confirm-button {
  background-color: #5C6BC0;
  color: white;
}

.cancel-button {
  background-color: #f44336;
  color: white;
}

.confirm-button:hover,
.cancel-button:hover {
  background-color: #3949AB;
}
</style>
