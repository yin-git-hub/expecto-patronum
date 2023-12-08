import {createRouter, createWebHistory} from 'vue-router'


const routes = [

    {
        path: '/',
        component: () => import( '../views/main.vue'),
        children: [{
            path: 'welcome',
            component: () => import('../views/main/welcome.vue'),
        }, {
            path: '/login',
            name: 'login',
            component: () => import( '../views/login.vue')
        }, {
            path: '/create',
            component:()=>import('../views/main/create.vue')
        }, {
            path: '/search/:select',
            name: 'search',
            component: () => import( '../views/main/search.vue')
        },

        ],
    },


]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
