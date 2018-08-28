var path = require("path");

module.exports = {
    entry: {
        "gene-web": path.resolve(__dirname, "build/classes/kotlin/main/gene-web.js")
    },
    output: {
        path: path.resolve(__dirname, "build/bundle"),
        filename: "[name].bundle.js"

    },
    resolve: {
        modules: [
            path.resolve(__dirname, "build/classes/kotlin/main"),
            path.resolve(__dirname, "../../ice-core/web/build/classes/kotlin/main"),
            path.resolve(__dirname, "build/node_modules"),
            path.resolve(__dirname, "node_modules")
        ]
    },
    optimization: {
        splitChunks: {
            cacheGroups: {
                core: {
                    test: /ice-core-web/,
                    name: "ice-core-web",
                    chunks: "all"
                },
                vendors: {
                    test: /[\\/]node_modules[\\/]/,
                    name: "vendors",
                    chunks: "all"
                }
            }
        }
    },
    devServer: {
        contentBase: path.resolve(__dirname, "src/main/web"),
        port: 8088,
        proxy: [
            {
                context: ["/app"],
                target: "http://localhost:8080",
                ws: true
            }
        ]
    }
}