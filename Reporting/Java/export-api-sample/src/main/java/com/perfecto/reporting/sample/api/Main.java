package com.perfecto.reporting.sample.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.TimeZone;

public class Main {
    public static final String CQL_NAME = "demo";

    // Use this for US
    public static final String REPORTING_SERVER_URL = "https://" + CQL_NAME + ".reporting-01.perfectomobile.com";

    // Use this for Europe
    // public static final String REPORTING_SERVER_URL = "https://" + CQL_NAME + ".reporting-12.perfectomobile.com";

    public static final String OFFLINE_TOKEN = "MY_CQ_LAB_OFFLINE_TOKEN";

    public static final String CQL_SERVER_URL = "https://" + CQL_NAME + ".perfectomobile.com";

    public static void main(String[] args) throws Exception {
        HttpClient httpClient = HttpClientBuilder.create().build();
        Gson gson = new Gson();
        String accessToken;

        // Use your personal offline token to retrieve an access token
        HttpPost tokenPost = new HttpPost(new URI(CQL_SERVER_URL + "/services/v2.0/auth/access-token"));
        tokenPost.setEntity(new UrlEncodedFormEntity(Collections.singletonList(new BasicNameValuePair("offline_token", OFFLINE_TOKEN))));
        System.out.println(tokenPost);
        HttpResponse tokenResponse = httpClient.execute(tokenPost);
        JsonObject tokenEntity = gson.fromJson(new InputStreamReader(tokenResponse.getEntity().getContent()), JsonObject.class);
        accessToken = tokenEntity.getAsJsonObject("data").get("access_token").getAsString();
        System.out.println("Got access token:\n" + accessToken);

        // Retrieves a list of the test executions in your lab for the past month
        URIBuilder uriBuilder = new URIBuilder(REPORTING_SERVER_URL + "/export/api/v1/test-executions");
        uriBuilder.addParameter("startExecutionTime[0]", Long.toString(System.currentTimeMillis() - 30 * 24 * 60 * 60 * 1000L));
        uriBuilder.addParameter("endExecutionTime[0]", Long.toString(System.currentTimeMillis()));
        HttpGet getExecutions = new HttpGet(uriBuilder.build());
        addDefaultRequestHeaders(getExecutions, accessToken);
        System.out.println(getExecutions);
        HttpResponse getExecutionsResponse = httpClient.execute(getExecutions);
        JsonObject executionsEntity = gson.fromJson(new InputStreamReader(getExecutionsResponse.getEntity().getContent()), JsonObject.class);
        System.out.println("List of test executions response:\n" + executionsEntity.toString());

        String testId = executionsEntity.getAsJsonArray("resources").get(0).getAsJsonObject().get("id").getAsString();
        String driverExecutionId = executionsEntity.getAsJsonArray("resources").get(0).getAsJsonObject().get("externalId").getAsString();
        int timeZoneOffsetMinutes = TimeZone.getDefault().getOffset(System.currentTimeMillis()) / (60 * 1000);

        // Retrieves a list of commands of a test
        HttpGet getCommands = new HttpGet(new URI(REPORTING_SERVER_URL + "/export/api/v1/test-executions/" + testId + "/commands"));
        addDefaultRequestHeaders(getCommands, accessToken);
        System.out.println(getCommands);
        HttpResponse getCommandsResponse = httpClient.execute(getCommands);
        JsonObject commandsEntity = gson.fromJson(new InputStreamReader(getCommandsResponse.getEntity().getContent()), JsonObject.class);
        System.out.println("List of commands response:\n" + commandsEntity.toString());

        // Retrieves summary PDF report of an execution (can contain several tests)
        uriBuilder = new URIBuilder(REPORTING_SERVER_URL + "/export/api/v1/test-executions/pdf");
        uriBuilder.addParameter("externalId[0]", driverExecutionId);
        uriBuilder.addParameter("timeZoneOffsetMinutes", Integer.toString(timeZoneOffsetMinutes));
        HttpGet getSummaryPdf = new HttpGet(uriBuilder.build());
        addDefaultRequestHeaders(getSummaryPdf, accessToken);
        System.out.println(getSummaryPdf);
        HttpResponse getSummaryPdfResponse = httpClient.execute(getSummaryPdf);
        if (HttpStatus.SC_OK == getSummaryPdfResponse.getStatusLine().getStatusCode()) {
            Path summaryPdfFile = Files.createTempFile(driverExecutionId, ".pdf");
            IOUtils.copy(getSummaryPdfResponse.getEntity().getContent(), new FileOutputStream(summaryPdfFile.toFile()));
            System.out.println("Saved execution summary PDF report to: " + summaryPdfFile.toFile().getAbsolutePath());
        } else {
            EntityUtils.consumeQuietly(getSummaryPdfResponse.getEntity());
        }

        // Retrieves PDF report of a test
        uriBuilder = new URIBuilder(REPORTING_SERVER_URL + "/export/api/v1/test-executions/pdf/" + testId);
        uriBuilder.addParameter("timeZoneOffsetMinutes", Integer.toString(timeZoneOffsetMinutes));
        HttpGet getPdf = new HttpGet(uriBuilder.build());
        addDefaultRequestHeaders(getPdf, accessToken);
        System.out.println(getPdf);
        HttpResponse getPdfResponse = httpClient.execute(getPdf);
        if (HttpStatus.SC_OK == getPdfResponse.getStatusLine().getStatusCode()) {
            Path pdfFile = Files.createTempFile(testId, ".pdf");
            IOUtils.copy(getPdfResponse.getEntity().getContent(), new FileOutputStream(pdfFile.toFile()));
            System.out.println("Saved test PDF report to: " + pdfFile.toFile().getAbsolutePath());
        } else {
            EntityUtils.consumeQuietly(getPdfResponse.getEntity());
        }
    }

    public static void addDefaultRequestHeaders(HttpRequestBase request, String accessToken) {
        request.addHeader("TENANTID", CQL_NAME);
        request.addHeader("Authorization", "Bearer " + accessToken);
    }
}
