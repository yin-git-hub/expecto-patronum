const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
    configureWebpack: {},
    devServer: { // 环境配置
        // host: '0.0.0.0',
        // public: '192.168.0.107:8331', // 此处是自己电脑IP地址！
        // port: '8331',
        // https: false,
        // // hotOnly: false,
        // // disableHostCheck: true,
        // open: false // 配置自动启动浏览器
    }
})
