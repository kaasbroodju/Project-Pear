
const teacherRoutes = [
            {
                path: 'group/:groupName',
                meta: {
                    title: "inspect group"
                },
                component: () => import('@/modules/teacher/views/InspectGroup.vue')
            },
            {
                path: 'student/:email/details',
                meta: {
                    title: "inspect student"
                },
                component: () => import('@/modules/teacher/views/InspectStudent.vue')
            },
            {
                path: 'group/:groupname/:formtemplate/details',
                meta: {
                    title: "inspect group on form"
                },
                component: () => import('@/modules/teacher/views/FormSummary.vue')
            }
]

export default teacherRoutes;