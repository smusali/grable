import { createRouter, createWebHistory } from 'vue-router'

import AllRestaurants from "../views/AllRestaurants.vue";
import ARestaurants from "../views/ARestaurant.vue";
import AddRestaurants from "../views/AddRestaurant.vue";
import SignUp from "../views/SignUp.vue";
import LogIn from "../views/LogIn.vue";
import auth from "../auth";



const routes = [{
  path: '/',
  name: 'AllRestaurants',
  component: AllRestaurants
},
  {
    path: "/signup",
    name: "SignUp",
    component: SignUp,
  },
  {
    path: "/login",
    name: "LogIn",
    component: LogIn,
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
  }
]


const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach(async (to, from, next) => {
  // Define routes that do not require authentication
  const publicPages = ['/auth/login', '/auth/signup', '/login', '/signup'];
  const authRequired = !publicPages.includes(to.path);

  let authResult = await auth.authenticated();

  // Redirect to login page if not authenticated and trying to access a restricted page
  if (authRequired && !authResult) {
    return next('/login');
  }

  next();
});


export default router