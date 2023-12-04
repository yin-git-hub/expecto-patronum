import { createRouter, createWebHistory } from 'vue-router'
import AboutView from "@/views/AboutView.vue";

const routes = [
  {
    path: '/about',
    name: 'about',
    component: AboutView
  },
  {
    path: '/login',
    name: 'login',
    component: () => import( '../views/login.vue')
  },
  {
    path: '/search/',
    name: 'search',
    component: () => import( '../views/search.vue')
  },

  {
    path: '/search/:select',
    name: 'search',
    component: () => import( '../views/search.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
