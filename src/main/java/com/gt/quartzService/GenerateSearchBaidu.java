//package com.gt.quartzService;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.gt.pageModel.DatagridForLayUI;
//import com.gt.utils.*;
//import org.apache.log4j.Logger;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.*;
//
//@Component  // 声明为spring组件
//public class GenerateSearchBaidu {
//    private Logger logger = Logger.getLogger(GenerateSearchBaidu.class);
//    private ITCofSearchService tCofSearchService;
//
//    public ITCofSearchService getTCofSearchService() {
//        return tCofSearchService;
//    }
//
//    @Autowired
//    public void setTCofSearchService(ITCofSearchService tCofSearchService) {
//        this.tCofSearchService = tCofSearchService;
//    }
//
//    private ITAppPressSearchCusService tAppPressSearchCusService;
//
//    public ITAppPressSearchCusService getTAppPressSearchCusService() {
//        return tAppPressSearchCusService;
//    }
//
//    @Autowired
//    public void setITAppPressSearchCusService(ITAppPressSearchCusService tAppPressSearchCusService) {
//        this.tAppPressSearchCusService = tAppPressSearchCusService;
//    }
//
//    // 静态初使化当前类
//    @Autowired
//    private static GenerateSearchBaidu createChildTable;
//
//    // 通过@PostConstruct 和 @PreDestroy 方法 实现初始化和销毁bean之前进行的操作
//    @PostConstruct
//    public void init() {
//        createChildTable = this;
//        createChildTable.tAppPressSearchCusService = this.tAppPressSearchCusService;       // 初使化时将已静态化的Service实例化
//    }
//
//
//    public void searchBaidu() {
//        Map<String, Object> headers = new HashMap<>();
//        List<TAppPressSearchCus> customerList = new ArrayList<>();
//        Date date = new Date();
//        TCofSearch tCofSearch = new TCofSearch();
//        tCofSearch.setSneturls(Contans.S_NET_URLS_BAIDU);
//        tCofSearch.setSstatus(Contans.S_STATUS[1]);
//        try {
//            DatagridForLayUI datagrid = createChildTable.tCofSearchService.datagrid(tCofSearch);
//            JSONArray jsonArr = JSONArray.parseArray(JSONObject.toJSONString(datagrid.getData()));
//            String kw = null;//关键词
//            String max = "75";//最大页数
//            String url = null;//爬取地址
//            for (int a = 0; a < jsonArr.size(); a++) {
//                kw = jsonArr.getJSONObject(a).get("scontent").toString();
//                url = jsonArr.getJSONObject(a).get("sneturls").toString();
//
//
////        请求头一定要加不然会被拦截
//                headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36");
//                headers.put("Content-Type", "application/x-www-form-urlencoded;multipart/form-data;");
//
//                Integer maxPage = Integer.parseInt(max);
//
//                Map<String, Object> params = new HashMap<>();
//                params.put("ie", "utf-8");
//                params.put("wd", kw);
////            params.put("rn","50");
//                for (int page = 0; page < maxPage; page++) {
//                    params.put("pn", String.valueOf(page * 10));
//                    if (page > 0) {
//                        params.put("oq", kw);
//                        params.put("usm", "2");
//                    }
//                    HttpClientUtils httpUtils = new HttpClientUtils();
//                    HttpClientResultBean httpClientResultBean = httpUtils.httpGetRequest(url, headers, params);
//                    Document doc = Jsoup.parse(httpClientResultBean.getResultInfo());
//                    if (null == doc) {
//                        continue;
//                    }
//                    /*
//                     * 百度搜索每个结果层的id规则为 第一页id从1开始向后逐条递增
//                     * */
//                    for (int i = (page) * 10 + 1; i <= (page + 1) * 10; i++) {
//                        Element element = doc.getElementById(String.valueOf(i));
//                        if (null == element) {
//                            continue;
//                        }
//                        //公司名称
//                        String cNameInfo = element.getElementsByTag("A").html();
//                        String companyName = "";//检索出的公司名称
//                        if (cNameInfo.indexOf("公司") >= 0) {
//                            companyName = cNameInfo.substring(0, cNameInfo.indexOf("公司") + 2).replace("<em>", "").replace("</em>", "");
//                        }
//                        //联系电话
//                        String cInfo = element.getElementsByClass("c-abstract").html();
//                        String companyMoblie = "";//检索出的公司电话
//                        if (!cInfo.equals("") && null != cInfo) {
//                            String mob = MobileUtils.getMobile(cInfo);
//                            if (!mob.equals("")) {
//                                companyMoblie = mob;
//                            }
//                        }
//                        if (!"".equals(companyMoblie) && !"".equals(companyName)) {
//                            TAppPressSearchCus entity = new TAppPressSearchCus();
//                            entity.setId(PbUtils.getUUID());
//                            entity.setPnetname("百度");
//                            entity.setPneturl(url);
//                            entity.setPcontent(kw);
//                            entity.setPcompany(companyName);
//                            entity.setPphone(companyMoblie);
//                            entity.setPcreatedate(date);
////                            entity.setPsource();
//                            customerList.add(entity);
//                        }
//                    }
//                }
//            }
//            if (customerList.size() > 0) {
//                for (TAppPressSearchCus entity : customerList) {
//                    createChildTable.tAppPressSearchCusService.add(entity);
//                }
//            }
//        } catch (Exception e) {
//            logger.error("异常信息为:", e);
//            e.printStackTrace();
//        }
//    }
//
//
//}
