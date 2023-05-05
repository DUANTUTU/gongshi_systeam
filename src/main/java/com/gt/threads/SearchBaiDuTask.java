//package com.gt.threads;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.gt.pageModel.DatagridForLayUI;
//import com.gt.utils.Contans;
//import com.gt.utils.HttpClientResultBean;
//import com.gt.utils.HttpClientUtils;
//import com.gt.utils.PbUtils;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * 所有任务接口
// * 其他任务必须继承访类
// *
// * @author 刘涛
// */
//public class SearchBaiDuTask extends Task {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    private ITCofSearchService tCofSearchService;
//    @Autowired
//    private ITAppPressSearchCusService tAppPressSearchCusService;
//
//
//    @Override
//    protected boolean useDb() {
//        return false;
//    }
//
//    @Override
//    protected boolean needExecuteImmediate() {
//        return true;
//    }
//
//    @Override
//    public String info() {
//        return null;
//    }
//
//    public void run() {
//        searchBaidu();
//    }
//
//    public void searchBaidu() {
//        Map<String, Object> map = new HashMap<>();
//        Map<String, Object> headers = new HashMap<>();
//        List<TAppPressSearchCus> customerList = new ArrayList<>();
//        Date date = new Date();
//        TCofSearch tCofSearch = new TCofSearch();
//        tCofSearch.setSneturls(Contans.S_NET_URLS_BAIDU);
//        tCofSearch.setSstatus(Contans.S_STATUS[1]);
//        try {
//            DatagridForLayUI datagrid = tCofSearchService.datagrid(tCofSearch);
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
//                if (null == max || null == kw || null == url) {
//                    map.put("errormsg", "参数错误");
////            return map;
//                }
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
//                            String mob = getMobile(cInfo);
//                            if (!mob.equals("")) {
//                                companyMoblie = mob;
//                            }
//                        }
//                        if (!"".equals(companyMoblie) && !"".equals(companyName)) {
//                            TAppPressSearchCus entity = new TAppPressSearchCus();
//                            entity.setId(PbUtils.getUUID());
//                            entity.setPnetname(jsonArr.getJSONObject(i).get("snetname").toString());
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
//                    tAppPressSearchCusService.add(entity);
//                }
//            }
//        } catch (Exception e) {
//            logger.error("异常信息为:", e);
//        }
//    }
//
//    public String getMobile(String str) {
//        String mobile = "";
//        //手机:((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\d{8}
//        //固定电话:(0\d{2}-\d{8}(-\d{1,4})?)|(0\d{3}-\d{7,8}(-\d{1,4})?)
//        // 将给定的正则表达式编译到模式中
//        Pattern pattern = Pattern.compile("((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}");
//        // 创建匹配给定输入与此模式的匹配器。
//        Matcher matcher = pattern.matcher(str);
//        //查找字符串中是否有符合的子字符串
//        while (matcher.find()) {
//            //查找到符合的即输出
////            if (mobile.equals("")) {
//            mobile = matcher.group();
//            break;
////            } else {
////                mobile += "," + matcher.group();
////            }
//        }
//        return mobile;
//    }
//}
//
