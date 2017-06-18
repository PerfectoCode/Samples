package com.perfecto.reporting.sample.api;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class PdfDownloadDemo {

    // The Perfecto Continuous Quality Lab you work with
    public static final String CQL_NAME = "demo";

    public static final String REPORTING_SERVER_URL = "https://" + CQL_NAME + ".reporting.perfectomobile.com";

    // See http://developers.perfectomobile.com/display/PD/Using+the+Reporting+Public+API on how to obtain a Security Token
    public static final String SECURITY_TOKEN = "MY_CONTINUOUS_QUALITY_LAB_SECURITY_TOKEN";

    public static final int PDF_DOWNLOAD_ATTEMPTS = 5;

    public static void main(String[] args) throws Exception {

        // TODO put your driver execution ID here
        String driverExecutionId = "MY_DRIVER_EXECUTION_ID";

        // TODO put your reportium test execution ID here
        String testId = "MY_TEST_ID";

        // Download an execution summary PDF report of an execution (may contain several tests)
        downloadExecutionSummaryReport(driverExecutionId);

        // Download a PDF report of a single test
        downloadTestReport(testId);
    }

    private static void downloadExecutionSummaryReport(String driverExecutionId) throws URISyntaxException, IOException {
        System.out.println("Downloading PDF for driver execution ID: " + driverExecutionId);
        URIBuilder uriBuilder = new URIBuilder(REPORTING_SERVER_URL + "/export/api/v1/test-executions/pdf");
        uriBuilder.addParameter("externalId[0]", driverExecutionId);
        downloadPdfFile(driverExecutionId, uriBuilder.build(), ".pdf", "execution summary PDF report");
    }

    private static void downloadTestReport(String testId) throws URISyntaxException, IOException {
        System.out.println("Downloading PDF for test ID: " + testId);
        URIBuilder uriBuilder = new URIBuilder(REPORTING_SERVER_URL + "/export/api/v1/test-executions/pdf/" + testId);
        downloadPdfFile(testId, uriBuilder.build(), ".pdf", "test PDF report");
    }

    private static void downloadPdfFile(String fileName, URI uri, String suffix, String description) throws IOException {
        HttpGet httpGet = new HttpGet(uri);
        addDefaultRequestHeaders(httpGet, SECURITY_TOKEN);
        downloadPdfFileToFS(httpGet, fileName, suffix, description);
    }

    private static void addDefaultRequestHeaders(HttpRequestBase request, String offlineToken) {
        request.addHeader("PERFECTO_AUTHORIZATION", offlineToken);
    }

    private static void downloadPdfFileToFS(HttpGet httpGet, String fileName, String suffix, String description) throws IOException {
        boolean downloadComplete = false;
        for (int attempt = 1; attempt <= PDF_DOWNLOAD_ATTEMPTS && !downloadComplete; attempt++) {

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse response = httpClient.execute(httpGet);
            FileOutputStream fileOutputStream = null;

            try {
                int statusCode = response.getStatusLine().getStatusCode();
                if (HttpStatus.SC_OK == statusCode) {
                    Path file = Files.createTempFile(fileName, suffix);
                    fileOutputStream = new FileOutputStream(file.toFile());
                    IOUtils.copy(response.getEntity().getContent(), fileOutputStream);
                    System.out.println("\nSaved " + description + " to: " + file.toFile().getAbsolutePath());
                    downloadComplete = true;
                } else if (HttpStatus.SC_NO_CONTENT == statusCode) {

                    // if the execution is being processed, the server will respond with empty response and status code 204
                    System.out.println("\nThe server responded with 204 (no content). " +
                            "The execution is still being processed. Attempting again in 5 sec (" + attempt + "/" + PDF_DOWNLOAD_ATTEMPTS + ")");
                    Thread.sleep(5000);
                } else {
                    String errorMsg = IOUtils.toString(response.getEntity().getContent(), Charset.defaultCharset());
                    System.err.println("Error downloading file. Status: " + response.getStatusLine() + ".\nInfo: " + errorMsg);
                    downloadComplete = true;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                EntityUtils.consumeQuietly(response.getEntity());
                IOUtils.closeQuietly(fileOutputStream);
            }
        }
        if (!downloadComplete) {
            System.err.println("The execution is still being processed. No more download attempts");
        }
    }
}
