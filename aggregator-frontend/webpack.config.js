const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');


module.exports = {
    devtool: 'source-map',
    entry: "./src/index.js",

    output: {
        path: path.join(__dirname, "/dist"),
        filename: "index-bundle.js"
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /node_modules/,
                use: [
                    {
                        loader: 'babel-loader',
                        options:
                            {
                                presets: ["@babel/preset-env", "@babel/preset-react"]
                            }
                    }
                    ],
            },
            {
                test: /\.css$/,
                use: ["style-loader", "css-loader"]
            },
            {
                test: /\.svg/,
                use: {
                    loader: 'svg-url-loader',
                    options: {}
                }
            },

        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: "./src/index.html"
        })
    ]
};