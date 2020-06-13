package com.example.demohttp;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ApacheHttpClientTest
 *
 * @author sjp
 * @date 2020/3/22
 */
public class ApacheHttpClientTest {

    private Logger logger = LoggerFactory.getLogger(ApacheHttpClientTest.class);

    @Test void test1() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://www.shijianpeng.top");
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        try {
            logger.info("GET------------:{}", response1.getStatusLine());
            HttpEntity entity1 = response1.getEntity();
            logger.info("GET------------:{}", entity1.getContent().read());
            EntityUtils.consume(entity1);
        } finally {
            response1.close();
        }

        HttpPost httpPost = new HttpPost("https://www.shijianpeng.top");
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("loginName", "atsjp"));
        nvps.add(new BasicNameValuePair("password", "sjp520cwjava"));
        nvps.add(new BasicNameValuePair("sid", "API_ALL"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        CloseableHttpResponse response2 = httpclient.execute(httpPost);

        try {
            logger.info("POSY------------:{}", response2.getStatusLine());
            HttpEntity entity2 = response2.getEntity();
            EntityUtils.consume(entity2);
            logger.info("POST------------:{}", entity2);
        } finally {
            response2.close();
        }
    }

    @Test void test2() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet("https://www.shijianpeng.top");
            System.out.println("Executing request " + httpget.getRequestLine());
            // Create a custom response handler
            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            String responseBody = httpclient.execute(httpget, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        } finally {
            httpclient.close();
        }
    }
}
