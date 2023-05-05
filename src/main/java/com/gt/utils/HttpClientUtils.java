package com.gt.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class HttpClientUtils {
    private PoolingHttpClientConnectionManager cm;
    private String UTF_8 = "UTF-8";
    private RequestConfig requestConfig = null;

    public HttpClientUtils() {
        this.requestConfig = RequestConfig.custom().setConnectionRequestTimeout(60000).setConnectTimeout(60000).setSocketTimeout(60000).build();
        this.init();
    }

    public HttpClientUtils(int connectTimeOut, int connectRequestTimeOut, int socketTimeOut) {
        this.requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeOut).setConnectionRequestTimeout(connectRequestTimeOut).setSocketTimeout(socketTimeOut).build();
        this.init();
    }

    private void init() {
        if (null == this.cm) {
            this.cm = new PoolingHttpClientConnectionManager();
            this.cm.setMaxTotal(50);
            this.cm.setDefaultMaxPerRoute(5);
        }
    }

    private CloseableHttpClient getHttpClient() {
        return HttpClients.custom().setConnectionManager(this.cm).build();
    }

    private HttpClientResultBean getResult(HttpRequestBase request, String sEncode) throws Exception {
        HttpClientResultBean httpClientResultBean = new HttpClientResultBean();
        CloseableHttpResponse response = null;
        try {
            //创建closeablehttpclient
            CloseableHttpClient httpClient = this.getHttpClient();
            //将request通过closeablehttpclient请求返回响应流
            response = httpClient.execute(request);
            //获取响应的code
            int iStatusCode = response.getStatusLine().getStatusCode();
            switch (iStatusCode) {
                case 200:
                    if (response != null) {
                        //实体数据解析,听过httpentity
                        HttpEntity httpEntity = response.getEntity();
                        httpClientResultBean.setCode(iStatusCode);
                        httpClientResultBean.setSuccess(true);
                        if (null != httpEntity) {
                            httpClientResultBean.setResultInfo(EntityUtils.toString(httpEntity,sEncode));
                        }
                    }
                    break;
                case 400:
                    httpClientResultBean.setCode(400);
                    httpClientResultBean.setSuccess(false);
                    httpClientResultBean.setErrorMsg("请求无效");
                    break;
                case 401:
                    httpClientResultBean.setCode(401);
                    httpClientResultBean.setSuccess(false);
                    httpClientResultBean.setErrorMsg("未经授权的请求");
                    break;
                case 403:
                    httpClientResultBean.setCode(403);
                    httpClientResultBean.setSuccess(false);
                    httpClientResultBean.setErrorMsg("禁止访问");
                    break;
                case 410:
                    httpClientResultBean.setCode(410);
                    httpClientResultBean.setSuccess(false);
                    httpClientResultBean.setErrorMsg("无法找到文件");
                    break;
                case 500:
                    httpClientResultBean.setCode(500);
                    httpClientResultBean.setSuccess(false);
                    httpClientResultBean.setErrorMsg("内部服务器错误");
                    break;
                case 502:
                    httpClientResultBean.setCode(502);
                    httpClientResultBean.setSuccess(false);
                    httpClientResultBean.setErrorMsg("网关错误");
                    break;
                case 504:
                    httpClientResultBean.setCode(504);
                    httpClientResultBean.setSuccess(false);
                    httpClientResultBean.setErrorMsg("连接超时");
                    break;
                default:
                    httpClientResultBean.setCode(iStatusCode);
                    httpClientResultBean.setSuccess(false);
                    httpClientResultBean.setErrorMsg("未知错误");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (response != null) {
                response.close();
            }
            request.abort();
        }
        return httpClientResultBean;
    }

    public HttpClientResultBean httpGetRequest(String url, Map<String, Object> params, String sEncode) throws Exception {
        //创建uribuilder
        URIBuilder ub = new URIBuilder();
        //设置url
        ub.setPath(url);
        //参数整理成为请求格式
        ArrayList<NameValuePair> pairs = this.covertParams2NVPS(params);
        ub.setParameters(pairs);
        //初始化httpget
        HttpGet httpGet = new HttpGet(ub.build());
        //请求配置设定
        httpGet.setConfig(this.requestConfig);
        //返回结果
        return this.getResult(httpGet,sEncode);
    }
    public HttpClientResultBean httpGetRequest(String url, Map<String, Object> params) throws Exception {
        return this.httpGetRequest(url, params, this.UTF_8);
    }
    public HttpClientResultBean httpGetRequest(String url,String sEncode) throws Exception{
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(this.requestConfig);
        return this.getResult(httpGet,this.UTF_8);
    }
    public HttpClientResultBean httpGetRequest(String url) throws Exception {
        return this.httpGetRequest(url, this.UTF_8);
    }
    public HttpClientResultBean httpGetRequest(String url, Map<String, Object> headers, Map<String, Object> params, String sEncode) throws Exception {
        URIBuilder ub = new URIBuilder();
        ub.setPath(url);
        ArrayList<NameValuePair> pairs = this.covertParams2NVPS(params);
        ub.setParameters(pairs);
        HttpGet httpGet = new HttpGet(ub.build());
        httpGet.setConfig(this.requestConfig);
        for (Map.Entry<String, Object> header:headers.entrySet() ){
            httpGet.addHeader((String)header.getKey(), String.valueOf(header.getValue()));
        }

        return this.getResult(httpGet, sEncode);
    }

    public HttpClientResultBean httpGetRequest(String url, Map<String, Object> headers, Map<String, Object> params) throws Exception {
        return this.httpGetRequest(url, headers, params, this.UTF_8);
    }

    public HttpClientResultBean httpPostRequest(String url, String sEncode) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(this.requestConfig);
        return this.getResult(httpPost, sEncode);
    }

    public HttpClientResultBean httpPostRequest(String url) throws Exception {
        return this.httpPostRequest(url, this.UTF_8);
    }

    public HttpClientResultBean httpPostRequest(String url, Map<String, Object> params, String sEncode) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(this.requestConfig);
        ArrayList<NameValuePair> pairs = this.covertParams2NVPS(params);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, this.UTF_8));
        return this.getResult(httpPost, sEncode);
    }

    public HttpClientResultBean sendPostRequest(String url, String params, String sEncode) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(this.requestConfig);
        httpPost.setEntity(new StringEntity(params, this.UTF_8));
        return this.getResult(httpPost, sEncode);
    }

    public HttpClientResultBean sendPostRequest(String url, String params) throws Exception {
        return this.sendPostRequest(url, params, this.UTF_8);
    }

    public HttpClientResultBean httpPostRequest(String url, Map<String, Object> params) throws Exception {
        return this.httpPostRequest(url, params, this.UTF_8);
    }

    public HttpClientResultBean httpPostRequest(String url, Map<String, Object> headers, Map<String, Object> params, String sEncode) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(this.requestConfig);

        for (Map.Entry<String, Object> header:headers.entrySet() ){
            httpPost.addHeader((String)header.getKey(), String.valueOf(header.getValue()));
        }

        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, Object> param:params.entrySet() ){
            httpPost.addHeader((String)param.getKey(), String.valueOf(param.getValue()));
        }

        StringEntity entity = new StringEntity(jsonObject.toString(), this.UTF_8);
        entity.setContentEncoding(this.UTF_8);
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        return this.getResult(httpPost, sEncode);
    }

    public HttpClientResultBean httpPostRequest(String url, Map<String, Object> headers, Map<String, Object> params) throws Exception {
        return this.httpPostRequest(url, headers, params, this.UTF_8);
    }


    private ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params) {
        ArrayList<NameValuePair> pairs = new ArrayList();
        for (Map.Entry<String,Object> param:params.entrySet()){
            pairs.add(new BasicNameValuePair(param.getKey(),String.valueOf(param.getValue())));
        }
        return pairs;
    }
}
