export const adminRoutes = [
    {
        path: '/admin',
        component: () => import("@/modules/admin/views/AdminViewWrapper.vue"),
        children: [
            {
                path: 'user',
                name: 'admin home',

                component: () => import('@/modules/admin/views/AdminUsers.vue')
            },
            {
                path: 'user/:email/profile',
                name: 'Admin user profile',

                component: () => import('@/modules/admin/views/AdminUserProfile.vue')
            },
            {
                path: 'group',

                component: () => import('@/modules/admin/views/AdminGroups.vue')
            },
            {
                path: 'group/:name/profile',
                name: 'Admin group profile',

                component: () => import('@/modules/admin/views/AdminGroupProfile.vue')
            },
        ]
    },

]