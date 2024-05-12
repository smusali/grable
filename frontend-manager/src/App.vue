<template>
  <nav class="navbar">
    <div class="navbar-menu">
      <router-link to="/allrestaurants" class="navbar-item" active-class="active-link">Restaurants</router-link>
      <router-link to="/addrestaurant" class="navbar-item" active-class="active-link">Add a Restaurant</router-link>
    </div>
  </nav>
  <router-view/>
</template>

<style>
/* Global styles */
body {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  margin: 0;
  padding: 0;
  text-align: center;
}

/* Navbar styles */
.navbar {
  background-color: #2980b9; /* Change color as per preference */
  color: #fff;
  padding: 10px 0;
}

.navbar-menu {
  display: flex;
  justify-content: center;
}

.navbar-item {
  text-decoration: none;
  color: #fff;
  font-weight: bold;
  margin: 0 10px;
  padding: 10px 20px;
  border-radius: 5px;
  transition: background-color 0.3s;
}

.navbar-item:hover,
.active-link {
  background-color: #1e639b; /* Darker shade on hover and when active */
}
</style>
