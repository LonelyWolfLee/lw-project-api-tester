import Vue from 'vue'
import VueRouter from 'vue-router'
import Call from '../views/Call.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    redirect: { name: 'Call' }
  },
  {
    path: '/call',
    name: 'Call',
    component: Call
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
