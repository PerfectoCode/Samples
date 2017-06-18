const request = require('request')
const fs = require('fs');

// The Perfecto Continuous Quality Lab you work with
const CQL_NAME = 'MY_CQL_NAME';

/**
 * The reporting Server address depends on the location of the lab. Please refer to the documentation at
 * http://developers.perfectomobile.com/display/PD/Reporting#Reporting-ReportingserverAccessingthereports
 * to find your relevant address
 * For example the following is used for US:
 */
const REPORTING_SERVER_URL = 'https://' + CQL_NAME + '.reporting.perfectomobile.com';

// See http://developers.perfectomobile.com/display/PD/Using+the+Reporting+Public+API on how to obtain an Offline Token
// In this case the offline token is stored as a env variable
const OFFLINE_TOKEN = process.env.my_token;

const CQL_SERVER_URL = 'https://' + CQL_NAME + '.perfectomobile.com';

/**
 * Retrieve a list of test executions within the last month
 * @returns {Promise} a promise resolved into json array of executions
 */
function retrieveTestsExecutions() {
  let apiUrl = REPORTING_SERVER_URL + '/export/api/v1/test-executions';

  // Optional parameters to be passed with our http request, In this example:
  // retrieve test executions of the past month (result may contain tests of multiple driver executions)
  let payload = {
    'startExecutionTime[0]': (new Date().getTime()) - (30 * 24 * 60 * 60 * 1000),
    'endExecutionTime[0]': (new Date().getTime())
  }

  return new Promise(function (resolve, reject) {
    request.get({url: apiUrl, qs: payload, headers: {'PERFECTO_AUTHORIZATION': OFFLINE_TOKEN}}, (err, resp, body) => {
      if (err) {
        reject(err);
      }
      else {
        resolve(JSON.parse(body)['resources']);
      }
    });
  });
}

/**
 * retrieve commands of test with a given test id
 * @param testId
 * @returns {Promise} resolved into json array of single test commands
 */
function retrieveTestCommands(testId) {
  let apiUrl = REPORTING_SERVER_URL + "/export/api/v1/test-executions/" + testId + "/commands";

  return new Promise(function (resolve, reject) {
    request.get({url: apiUrl, headers: {'PERFECTO_AUTHORIZATION': OFFLINE_TOKEN}}, (err, resp, body) => {
      if (err) {
        reject(err);
      }
      else {
        resolve(JSON.parse(body)['resources']);
      }
    });
  });
}

/**
 * download execution report summary (pdf format)
 * will create a pdf file asynchronously
 * @param driverExecutionId
 * @param filename
 * @returns {Promise} a promise resolved into the binary
 */
function downloadExecutionSummaryReport(driverExecutionId, filename) {
  let apiUrl = REPORTING_SERVER_URL + '/export/api/v1/test-executions/pdf';
  let payload = {
    'externalId[0]': driverExecutionId
  }
  return new Promise(function (resolve, reject) {
    request.get({url: apiUrl, qs: payload, headers: {'PERFECTO_AUTHORIZATION': OFFLINE_TOKEN}}, (err, resp, body) => {
      if (err) {
        reject(err);
      }
      else {
        resolve(body);
      }
    }).pipe(fs.createWriteStream(filename + '.pdf'));
  });
}

/**
 * download test report summary (pdf format)
 * @param testId
 * @param filename
 * @returns {Promise} promise resolved into the pdf binary
 */
function downloadTestReport(testId, filename) {
  let apiUrl = REPORTING_SERVER_URL + "/export/api/v1/test-executions/pdf/" + testId;

  return new Promise(function (resolve, reject) {
    request.get({url: apiUrl, headers: {'PERFECTO_AUTHORIZATION': OFFLINE_TOKEN}}, (err, resp, body) => {
      if (err) {
        reject(err);
      }
      else {
        resolve(body);
      }
    }).pipe(fs.createWriteStream(filename + '.pdf'));
  });
}

/**
 * downloads a video for given test execution
 * will create a mp4 file (async)
 * @param testExecution
 * @returns {Promise} a promise resolved into a mp4 binary
 */
function downloadVideo(testExecution) {
  let videos = testExecution['videos'];
  return new Promise(function (resolve, reject) {
    if (videos.length > 0) {
      let video = videos[0];
      let downloadUrl = video['downloadUrl'];
      let videoFormat = '.' + video['format'];
      let testId = testExecution['id'];
      request.get({url: downloadUrl}, (err, resp, body) => {
        if (err) {
          reject(err);
        }
        else {
          resolve(body);
        }
      }).pipe(fs.createWriteStream(testId + videoFormat));
    }
    else {
      reject('No Videos were found!');
    }
  });
}

/**
 * downloads attachments for a given test execution
 * @param testExecution
 * @returns {Promise} a promise resolved into attachment binary
 */
function downloadAttachments(testExecution) {
  let artifacts = testExecution['artifacts'];

  return new Promise(function (resolve, reject) {
    if (artifacts.length > 0) {
      for (arti in artifacts) {
        let type = arti['type'];
        if (type == 'DEVICE_LOGS') {
          let testId = testExecution['id'];
          let path = arti['path'];
          request.get({url: path}, (err, resp, body) => {
            if (err) {
              reject(err);
            }
            else {
              resolve(body);
            }
          }).pipe(fs.createWriteStream(testId + '.zip'));
        }
      }
    }
    else {
      reject('No artifacts were found!');
    }
  });
}


/*****************
 * Usage example *
 *****************/

let testExecution,
  driverExecutionId,
  testId;

retrieveTestsExecutions()
  .then((resources) => {
    testExecution = resources[1]; // retrieve a test execution
    driverExecutionId = testExecution['externalId']; // retrieve the execution id
    testId = testExecution['id']; // retrieve a single test id

    // retrieve the test commands
    return retrieveTestCommands(testId);
  })
  .then((commands) => {
    let testCommands = commands;

    // Do something with the test commands ...

    // download execution report in pdf format (Async)
    return downloadExecutionSummaryReport(driverExecutionId, 'executionReport');
  })
  .then(() => {
    // downloads the test report in pdf format
    return downloadTestReport(testId, 'testReport');
  })
  .then(() => {
    return downloadVideo(testExecution);
  })
  .then(() => {
    return downloadAttachments(testExecution);
  });


