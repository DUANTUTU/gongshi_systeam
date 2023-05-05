//package com.gt.quartz;
//
//import com.gt.quartzService.GenerateSearchBaidu;
//import org.quartz.Job;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SearchBaiduQuartz extends BaseQuartz implements Job {
//    @Override
//    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        try {
//            GenerateSearchBaidu generateSearchBaidu = generateSearchBaidu = new GenerateSearchBaidu();
//            generateSearchBaidu.searchBaidu();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
