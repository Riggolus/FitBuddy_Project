import { createRouter as createRouter, createWebHistory } from 'vue-router'
import { useStore } from 'vuex'

// Import components
import HomeView from '../views/HomeView.vue';
import LoginView from '../views/LoginView.vue';
import LogoutView from '../views/LogoutView.vue';
import RegisterView from '../views/RegisterView.vue';
import EditAccountView from '../views/EditAccountView.vue';
import CreateAccountView from '../views/CreateAccountView.vue';
import ProfileView from '../views/ProfileView.vue';
import UserRoleView from '../views/UserRoleView.vue';
import WorkoutView from '../views/WorkoutView.vue';
import AnalyticsView from '../views/AnalyticsView.vue';

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */
const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/login",
    name: "login",
    component: LoginView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/logout",
    name: "logout",
    component: LogoutView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/register",
    name: "register",
    component: RegisterView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/edit-account",
    name: "editAccount",
    component: EditAccountView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/create-account",
    name: "createAccount",
    component: CreateAccountView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/account/:username", //Switch this to username
    name: "profile",
    component: ProfileView,
    meta: {
      requiresAuth: true
    }
  },
  {  
    path: "/user-role",
    name: "userRole",
    component: UserRoleView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/workout/:username",
    name: "workout",
    component: WorkoutView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/analytics/:username",
    name: "analytics",
    component: AnalyticsView,
    meta: {
      requiresAuth: true
    } 
  }
];

// Create the router
const router = createRouter({
  history: createWebHistory(),
  routes: routes
});

router.beforeEach((to) => {

  // Get the Vuex store
  const store = useStore();

  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    return {name: "login"};
  }
  // Otherwise, do nothing and they'll go to their next destination
});

export default router;
