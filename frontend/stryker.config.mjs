export default {
  testRunner: "jest",
  jest: {
    configFile: "jest.config.js"
  },
  mutate: [
    "src/helper/getHref.js",
    "src/helper/search.js"
  ],
  reporters: ["html", "clear-text", "progress"],
  htmlReporter: {
    fileName: "reports/mutation/mutation.html"
  },
  coverageAnalysis: "perTest"
};