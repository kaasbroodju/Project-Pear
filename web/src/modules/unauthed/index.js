import Login from "@/modules/unauthed/views/Login.vue";

export const unauthedRoutes = [
    {
        path: '/logout',
        name: 'logout',

        component: () => import('@/modules/unauthed/views/Logout.vue')
    },
    {
        path: '/login',
        component: Login
    },
    {
        path: '/signup',
        component: () => import("@/modules/unauthed/views/Signup.vue")
    },
    {
        path: '/register/:id',
        component: () => import("@/modules/unauthed/views/Register.vue")
    },

]