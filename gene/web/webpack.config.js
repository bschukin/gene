var path = require("path");

module.exports = {
    entry: {
        "gene-web": path.resolve(__dirname, "build/kotlin-js-min/main/gene-web.js")
    },
    output: {
        path: path.resolve(__dirname, "build/bundle"),
        filename: "[name].bundle.js"
    },
    resolve: {
        modules: [
            path.resolve(__dirname, "build/kotlin-js-min/main"),
            path.resolve(__dirname, "node_modules")
        ]
    },
    optimization: {
        splitChunks: {
            cacheGroups: {
                core: {
                    test: /ice\-core\-web/,
                    name: "ice-core-web",
                    chunks: "all"
                },
                agent: {
                    test: /gene\-web/,
                    name: "gene-web",
                    chunks: "all"
                },
                vendors: {
                    test: /[\\/]node_modules[\\/]|main[\\/]kotlin/,
                    name: "vendors",
                    chunks: "all"
                }
            }
        }
    }
};