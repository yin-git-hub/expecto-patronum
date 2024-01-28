import {createRouter, createWebHistory} from 'vue-router'
import {notification} from "ant-design-vue";
import store from "@/store";


const routes = [

    {
        path: '/',
        component: () => import( '../views/main.vue'),
        meta: {
            loginRequire: true
        },
        children: [{
            path: 'welcome',
            component: () => import('../views/main/welcome.vue'),
        }, {
            path: '/login',
            component: () => import( '../views/login.vue')
        }, {
            path: 'create',
            component: () => import('../views/main/create.vue'),
        }, {
            path: '/search/:select',
            component: () => import( '../views/main/search.vue')
        }, {
            path: "/player",
            component:()=>import('../views/main/videoPlayer.vue')
        }, {
            path: "/myPage",
            component:()=>import('../views/main/MessagePage.vue'),
            children: [{
                path: '/personal',
                component: () => import('../components/messagePage/personPage.vue'),

            },{
                path: '/getSelfVideo',
                component:()=>import('../components/list/VideoInfoListSelf.vue')
            },{
                path:'/personal-edit',
                component:()=>import('../components/messagePage/personPageEdit.vue')
            },{
                path: '/personal-following',
                component:()=>import('../components/messagePage/followingPage.vue')
            },{
                path: '/personal-following-group',
                component:()=>import('../components/messagePage/followingPageByGroup.vue')
            },{
                path: '/personal-collection',
                component:()=>import('../components/messagePage/collectionPage.vue')
            },{
                path: '/personal-collection-group',
                component:()=>import('../components/messagePage/collectionPageByGroup.vue')
            }

            ]
        }

        ],
    }, {
        path: "/dplayer",
        component:()=>import('../components/videoPlayer/vueDplayer.vue')

    }, {
        path: "/comment",
        component:()=>import('../components/comments.vue')

    }, {
        path: "/test",
        component:()=>import('../components/test/theTest.vue')

    }, {
        path: '/login',
        name: 'login',
        component: () => import( '../views/login.vue')
    }


]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})
// 路由登录拦截
router.beforeEach((to, from, next) => {
    // 要不要对meta.loginRequire属性做监控拦截
    if (to.matched.some(function (item) {
        console.log(item, "是否需要登录校验：", item.meta.loginRequire || false);
        return item.meta.loginRequire
    })) {
        const _userInfo = store.state.userInfo;
        console.log("页面登录校验开始：", _userInfo);
        if (!_userInfo.token) {
            console.log("用户未登录或登录超时！");
            notification.error({description: "未登录或登录超时rout"});
            if (to.path === '/login') { // 防止死循环
                next()
            } else {
                next('/login')
            }
        } else {
            next();
        }

    } else {
        next();
    }
});
export default router
