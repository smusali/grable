import { createRouter, createWebHistory } from 'vue-router'

import AllRestaurants from "../views/AllRestaurants.vue";
import ARestaurants from "../views/ARestaurant.vue";

const routes = [{
  path: '/',
  name: 'AllRestaurants',
  component: AllRestaurants,
},
  {
    path: "/allrestaurants",
    name: "AllRestaurants",
    component: AllRestaurants,
  },
  {
    path: "/arestaurant/:id",
    name: "ARestaurants",
    component: ARestaurants,
  },
  { //will route to AllPosts view if none of the previous routes apply
    path: "/:catchAll(.*)",
    name: "AllRestaurants",
    component: AllRestaurants,
  }
]


const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router