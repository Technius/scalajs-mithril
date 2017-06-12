// From the scalajs-bundler docs

var config = require("./scalajs.webpack.config");
var globalModules = {
    "react": "React",
    "react-dom": "ReactDOM",
    "chart.js": "Chart"
};
Object.keys(config.entry).forEach(function(key) {
    config.entry[key] = Object.keys(globalModules).concat(config.entry[key]);
});
config.module.loaders = Object.keys(globalModules).map(function (pkg) {
    return {
        test: require.resolve(pkg),
        loader: "expose-loader?" + globalModules[pkg]
    }
});
module.exports = config;
