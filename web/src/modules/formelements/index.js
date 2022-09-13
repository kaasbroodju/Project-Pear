import {selfPickableStore} from "@/modules/formelements/store";
import {mapActions} from "pinia/dist/pinia";

export const formRoutes = [
    {
        path: '/form/create/:template',
        name: 'create a form from template',
        meta: {
            title: "create form"
        },

        component: () => import('@/modules/formelements/views/CreateFrom.vue')
    },
    {
        path: '/form/:id/fill',
        name: 'Fill a form in',
        meta: {
            title: "create form"
        },
        beforeEnter: () => {
            mapActions(selfPickableStore, ['clear']).clear();
        },

        component: () => import('@/modules/formelements/views/DynamicFillForm.vue')
    },
    {
        path: '/form/:id/review',
        name: 'Review a form',
        meta: {
            title: "review form"
        },

        component: () => import('@/modules/formelements/views/DynamicReviewForm.vue')
    },
    {
        path: '/form/:id/done',
        name: 'Show finished form',
        meta: {
            title: "view form"
        },

        component: () => import('@/modules/formelements/views/DynamicDoneForm.vue')
    },
    {
        path: '/form/:id/dynamic',
        name: 'Show dynamic form',
        meta: {
            title: "view form"
        },

        component: () => import('@/modules/formelements/views/DynamicFillForm.vue')
    },
    {
        path: '/form/reviewer/:id/:reviewPolicy',
        name: 'add reviewer',
        meta: {
            title: "add reviewer"
        },

        component: () => import('@/modules/formelements/views/AddReviewer.vue')
    },
    {
        path: '/form/reviewer/:id/board',
        name: 'add form to board',
        meta: {
            title: "add to board"
        },

        component: () => import('@/modules/formelements/views/AddToBoard.vue')
    },
]