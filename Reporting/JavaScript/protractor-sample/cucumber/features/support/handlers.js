// const Reporting = require('perfecto-reporting');
//
// var myHandlers = function () {
//
//     this.registerHandler('BeforeScenario', function (scenario, callback) {
//         browser.reportiumClient = function setupReportingClient() {
//             // Setup Perfecto reporting client
//             const perfectoExecutionContext = new Reporting.Perfecto.PerfectoExecutionContext({
//                 webdriver: browser.driver,
//                 tags: ['Cucumber', 'nodejs', 'daniela@perfectomobile.com'] // optional
//             });
//             return new Reporting.Perfecto.PerfectoReportingClient(perfectoExecutionContext);
//         }
//         browser.reportiumClient.testStart(scenario.getName());
//         callback();
//     });
//
//     this.registerHandler('BeforeStep', function (step, callback) {
//         browser.reportiumClient.testStep(step.getName());
//         callback();
//     });
// }
//
// module.exports = myHandlers;