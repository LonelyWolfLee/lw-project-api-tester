import Vue from 'vue'
import VueRouter from 'vue-router'
import Api from '../views/Api.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    redirect: { name: 'Api' }
  },
  {
    path: '/api',
    name: 'Api',
    component: Api
  },
  {
    path: '/history',
    name: 'History',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/History.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router
