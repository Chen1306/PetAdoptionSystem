import Vue from 'vue'
import Router from 'vue-router'
import { getRole, isLoggedIn } from '../utils/auth'
import LoginView from '../views/auth/LoginView.vue'
import RegisterView from '../views/auth/RegisterView.vue'
import PetListView from '../views/pet/PetListView.vue'
import PetDetailView from '../views/pet/PetDetailView.vue'
import MyAdoptionsView from '../views/profile/MyAdoptionsView.vue'
import ProfileView from '../views/profile/ProfileView.vue'
import AdminLayout from '../components/layout/AdminLayout.vue'
import AdminDashboardView from '../views/admin/AdminDashboardView.vue'
import AdminUsersView from '../views/admin/AdminUsersView.vue'
import AdminPetsView from '../views/admin/AdminPetsView.vue'
import AdminAdoptionsView from '../views/admin/AdminAdoptionsView.vue'

Vue.use(Router)

const router = new Router({
  mode: 'hash',
  routes: [
    { path: '/', redirect: '/pets' },
    { path: '/login', component: LoginView, meta: { guestOnly: true } },
    { path: '/register', component: RegisterView, meta: { guestOnly: true } },
    { path: '/pets', component: PetListView },
    { path: '/pets/:id', component: PetDetailView },
    { path: '/my-adoptions', component: MyAdoptionsView, meta: { requiresAuth: true, role: 'user' } },
    { path: '/profile', component: ProfileView, meta: { requiresAuth: true, role: 'user' } },
    {
      path: '/admin',
      component: AdminLayout,
      meta: { requiresAuth: true, role: 'admin' },
      children: [
        { path: '', component: AdminDashboardView },
        { path: 'users', component: AdminUsersView },
        { path: 'pets', component: AdminPetsView },
        { path: 'adoptions', component: AdminAdoptionsView }
      ]
    }
  ]
})

router.beforeEach((to, from, next) => {
  const loggedIn = isLoggedIn()
  const role = getRole()

  if (to.meta.guestOnly && loggedIn) {
    next(role === 'admin' ? '/admin' : '/pets')
    return
  }

  if (to.meta.requiresAuth && !loggedIn) {
    next('/login')
    return
  }

  if (to.meta.role && to.meta.role !== role) {
    next(role === 'admin' ? '/admin' : '/pets')
    return
  }

  next()
})

export default router
