import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import Antd, {notification} from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
// 全局使用图标
import * as Icons from '@ant-design/icons-vue';
import store from "./store";
import axios from "axios";

import vue3videoPlay from "vue3-video-play"; // 引入组件
import "vue3-video-play/dist/style.css"; // 引入css
import VueRouter from 'vue-router';

const app = createApp(App);
app.use(VueRouter).use(vue3videoPlay).use(Antd).use(store).use(router).mount('#app');

// 全局使用图标
const icons = Icons;
for (const i in icons) {
    app.component(i, icons[i]);
}


/**
 * axios拦截器
 */
axios.interceptors.request.use(function (config) {

    const _token = store.state.userInfo.token;
    const _refreshToken = store.state.userInfo.refreshToken;
    if (_token) {
        config.headers.token = _token;
        config.headers.refreshToken = _refreshToken;
    }
    return config;
}, error => {
    return Promise.reject(error);
});
axios.interceptors.response.use(function (response) {
    const data = response.data
    return data;
}, error => {
    const response = error.response;
    const status = response.status;
    if (status === 401) {
        // 判断状态码是401 跳转到登录页
        store.commit("setMember", {});
        notification.error({ description: "未登录或登录超时main" });
        router.push('/login');
    }
    return Promise.reject(error);
});

// 多环境配置
axios.defaults.baseURL = process.env.VUE_APP_SERVER;

