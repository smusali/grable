import { createRouter, createWebHistory } from 'vue-router'

import AllRestaurants from "../views/AllRestaurants.vue";
import ARestaurants from "../views/ARestaurant.vue";
import AddRestaurants from "../views/AddRestaurant.vue";
import QrcodeVue from "../views/QrCode.vue";
import ChooseMenu from "../views/ChooseMenu.vue";



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
  {
    path: "/addrestaurant",
    name: "AddRestaurants",
    component: AddRestaurants,
  },
  { //will route to AllPosts view if none of the previous routes apply
    path: "/:catchAll(.*)",
    name: "AllRestaurants",
    component: AllRestaurants,

  },

  {
    path: "/getQrCode",
    name: "QrcodeVue",
    component: QrcodeVue,
  },
  {
    path: "/chooseMenu",
    name: "ChooseMenu",
    component: ChooseMenu,
  }
]


const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router