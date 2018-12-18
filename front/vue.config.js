module.exports = {
    runtimeCompiler: true,
    productionSourceMap: false,

    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:8082',
                changeOrigin: true
            }
        }
    }
}