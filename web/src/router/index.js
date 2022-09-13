import {createRouter, createWebHistory} from 'vue-router'
import Home from '@/views/Home.vue'
import MainRoute from "@/components/MainRoute.vue";
import {formRoutes} from "@/modules/formelements";
import {unauthedRoutes} from "@/modules/unauthed";
import {adminRoutes} from "@/modules/admin";
import teacherRoutes from "@/modules/teacher";

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
    ...unauthedRoutes,
  {
    path: '/',
    component: MainRoute,
    children: [
      {
        path: '/home',
        name: 'Home',
        component: Home,
        meta: {
          title: "home"
        }
      },
      {
        path: '/old',
        name: 'Old',
        meta: {
          title: "old forms"
        },

        component: () => import('../views/Old.vue')
      },
      {
        path: '/board',
        name: 'open board',
        meta: {
          title: "board"
        },

        component: () => import('../views/OpenBoard.vue')
      },
      {
        path: '/reviews',
        name: 'Reviews',
        meta: {
          title: "reviews"
        },
        component: () => import('../views/Reviews.vue')
      },
      {
        path: '/progress',
        name: 'Progress',
        meta: {
          title: "progress"
        },
        component: () => import('../views/Progress.vue')
      },
      {
        path: '/drafts',
        name: 'Drafts',
        meta: {
          title: "drafts"
        },
        component: () => import('../views/Drafts.vue')
      },
      {
        path: '/settings',
        name: 'Settings',
        meta: {
          title: "settings"
        },
        component: () => import('../views/Settings.vue')
      },
        ...formRoutes,
      ...teacherRoutes,
    ]
  },
  ...adminRoutes,


]



const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

router.beforeEach((to, from) => {
  document.title = to.meta.title || "Pear";
  if (!(to.path === "/login" || to.path === "/signup" || to.path.startsWith("/register")) && !localStorage.getItem("accessToken")) {
    return { path: "/login"}
  } else if (localStorage.getItem("accessToken") && from.path === "/" && to.path === "/login") {
    return { path: "/home" }
  }
})

export default router
