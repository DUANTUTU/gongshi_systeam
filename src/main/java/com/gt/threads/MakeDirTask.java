//package com.gt.threads;
//
//import java.io.File;
//import java.util.Calendar;
//import java.util.Date;
//
//import org.apache.log4j.Logger;
//
//import com.project.jdbc.IntoDB;
//import com.project.util.ConfigUtil;
//import com.project.util.JDateToolkit;
//import com.project.util.thread.Task;
//
//public class MakeDirTask extends Task {
//    private static Logger logger = Logger.getLogger(MakeDirTask.class);
//
//
//    public boolean useDb() {
//        return false;
//
//    }
//
//
//    public String info() {
//        return "自动创建系统附件路径";
//    }
//
//
//    public boolean needExecuteImmediate() {
//        return true;
//    }
//
//
//    public void run() {
//        try {
//            JDateToolkit jdt = new JDateToolkit();
//            // String SumRkInfoByOrgCodeTask_times =
//            // ConfigUtil.getFileIO("SumRkInfoByOrgCodeTask_times",
//            // "/com/project/autojob/task.properties");
//            while (true) {
//                String SumRkInfoByOrgCodeTask_clock = ConfigUtil.getFileIO("MakeDirTask_clock", "/com/project/autojob/task.properties");
//                if (SumRkInfoByOrgCodeTask_clock != null && !SumRkInfoByOrgCodeTask_clock.trim().equals("")) {
//                    Calendar rightNow = Calendar.getInstance();
//                    int hour = rightNow.get(Calendar.HOUR_OF_DAY);
//                    int SumRkInfoByOrgCodeTask_startclock = Integer.parseInt(SumRkInfoByOrgCodeTask_clock);
//                    if (hour == SumRkInfoByOrgCodeTask_startclock) {
//                        System.out.println(this.info() + "-线程启动......");
//                        long millis = 1000 * 60 * 60 * 1; // 系统默认10分钟间隔休眠
//
//                        String windows = ConfigUtil.getFileIO("windowspath", "/com/project/autojob/systemfile.properties");// os种类
//                        String linux = ConfigUtil.getFileIO("linuxpath", "/com/project/autojob/systemfile.properties");// os种类
//                        String rootfilepath = "";
//                        String notepath = "";
//                        String notetemppath = "";
//                        String applypath = "";
//                        String applytemppath = "";
//                        String rphotopath = "";
//                        String rphototemppath = "";
//                        String hphotopath = "";
//                        String hphototemppath = "";
//                        boolean iswindows = false;
//                        boolean islinux = false;
//                        if (windows.trim().equals("on")) // 辨别os种类，并赋予判断
//                        {
//                            iswindows = true;
//                        }
//                        if (linux.trim().equals("on"))// 辨别os种类，并赋予判断
//                        {
//                            islinux = true;
//                        }
//                        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//                        String realpath = path.substring(0, path.indexOf("WEB-INF") - 1);
//                        if (iswindows) {
//                            rootfilepath = realpath + File.separatorChar + ConfigUtil.getFileIO("rootpath", "/com/project/autojob/systemfile.properties");// 附件总目录
//                            rootfilepath = java.net.URLDecoder.decode(rootfilepath, "utf-8");
//                            notepath = rootfilepath + File.separatorChar + ConfigUtil.getFileIO("notepath", "/com/project/autojob/systemfile.properties");// 公告附件
//                            notetemppath = rootfilepath + File.separatorChar + ConfigUtil.getFileIO("notetemppath", "/com/project/autojob/systemfile.properties");// 公告附件TMP
//                            applypath = rootfilepath + File.separatorChar + ConfigUtil.getFileIO("applypath", "/com/project/autojob/systemfile.properties");// 补办附件
//                            applytemppath = rootfilepath + File.separatorChar + ConfigUtil.getFileIO("applytemppath", "/com/project/autojob/systemfile.properties");// 补办附件TMP
//                            rphotopath = rootfilepath + File.separatorChar + ConfigUtil.getFileIO("rphotopath", "/com/project/autojob/systemfile.properties");// 人口信息
//                            rphototemppath = rootfilepath + File.separatorChar + ConfigUtil.getFileIO("rphototemppath", "/com/project/autojob/systemfile.properties");// 人口信息TMP
//                            hphotopath = rootfilepath + File.separatorChar + ConfigUtil.getFileIO("hphotopath", "/com/project/autojob/systemfile.properties");// 房屋信息
//                            hphototemppath = rootfilepath + File.separatorChar + ConfigUtil.getFileIO("hphototemppath", "/com/project/autojob/systemfile.properties");// 房屋信息TMP
//                        } else if (islinux) {
//                            rootfilepath = realpath + File.separatorChar + ConfigUtil.getFileIO("rootpath", "/com/project/autojob/systemfile.properties");// 附件总目录
//                            rootfilepath = java.net.URLDecoder.decode(rootfilepath, "utf-8");
//                            notepath = rootfilepath + File.separatorChar + ConfigUtil.getFileIO("notepath", "/com/project/autojob/systemfile.properties");// 公告附件
//                            notetemppath = rootfilepath + File.separatorChar + ConfigUtil.getFileIO("notetemppath", "/com/project/autojob/systemfile.properties");// 公告附件TMP
//                            applypath = rootfilepath + File.separatorChar + ConfigUtil.getFileIO("applypath", "/com/project/autojob/systemfile.properties");// 补办附件
//                            applytemppath = rootfilepath + File.separatorChar + ConfigUtil.getFileIO("applytemppath", "/com/project/autojob/systemfile.properties");// 补办附件TMP
//                            rphotopath = rootfilepath + File.separatorChar + ConfigUtil.getFileIO("rphotopath", "/com/project/autojob/systemfile.properties");// 人口信息
//                            rphototemppath = rootfilepath + File.separatorChar + ConfigUtil.getFileIO("rphototemppath", "/com/project/autojob/systemfile.properties");// 人口信息TMP
//                            hphotopath = rootfilepath + File.separatorChar + ConfigUtil.getFileIO("hphotopath", "/com/project/autojob/systemfile.properties");// 房屋信息
//                            hphototemppath = rootfilepath + File.separatorChar + ConfigUtil.getFileIO("hphototemppath", "/com/project/autojob/systemfile.properties");// 房屋信息TMP
//                        }
//
//                        File rootfile = new File(rootfilepath);
//                        File notepathfile = new File(notepath);
//                        File notetemppathfile = new File(notetemppath);
//                        File applypathfile = new File(applypath);
//                        File applytemppathfile = new File(applytemppath);
//                        File rphotopathfile = new File(rphotopath);
//                        File rphototemppathfile = new File(rphototemppath);
//                        File hphotopathfile = new File(hphotopath);
//                        File hphototemppathfile = new File(hphototemppath);
//                        // 如果文件夹不存在则创建
//                        if (!rootfile.exists() && !rootfile.isDirectory()) {
//                            System.out.println("根不存在");
//                            System.out.println("创建目录root路径为：：：：" + rootfilepath);
//                            rootfile.mkdir();
//                        } else {
//                            System.out.println("根目录存在");
//                        }
//                        if (!notepathfile.exists() && !notepathfile.isDirectory()) {
//
//                            System.out.println("公告附件目录不存在");
//                            notepathfile.mkdir();
//                            if (iswindows) {
//                                String datepath = notepath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            } else if (islinux) {
//                                String datepath = notepath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            }
//
//                        } else {
//                            if (iswindows) {
//                                String datepath = notepath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            } else if (islinux) {
//                                String datepath = notepath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            }
//                        }
//                        if (!notetemppathfile.exists() && !notetemppathfile.isDirectory()) {
//                            System.out.println("公告附件tmp目录不存在");
//                            notetemppathfile.mkdir();
//                            if (iswindows) {
//                                String datepath = notetemppath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            } else if (islinux) {
//                                String datepath = notetemppath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            }
//
//                        } else {
//                            if (iswindows) {
//                                String datepath = notetemppath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            } else if (islinux) {
//                                String datepath = notetemppath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            }
//                        }
//                        if (!applypathfile.exists() && !applypathfile.isDirectory()) {
//                            System.out.println("补办附件目录不存在");
//                            applypathfile.mkdir();
//                            if (iswindows) {
//                                String datepath = applypath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            } else if (islinux) {
//                                String datepath = applypath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            }
//
//                        } else {
//                            if (iswindows) {
//                                String datepath = applypath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            } else if (islinux) {
//                                String datepath = applypath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            }
//
//                        }
//                        if (!applytemppathfile.exists() && !applytemppathfile.isDirectory()) {
//                            System.out.println("补办附件tmp目录不存在");
//                            applytemppathfile.mkdir();
//                            if (iswindows) {
//                                String datepath = applytemppath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            } else if (islinux) {
//                                String datepath = applytemppath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            }
//
//                        } else {
//                            if (iswindows) {
//                                String datepath = applytemppath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            } else if (islinux) {
//                                String datepath = applytemppath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            }
//                        }
//                        if (!rphotopathfile.exists() && !rphotopathfile.isDirectory()) {
//                            System.out.println("人口附件目录不存在");
//                            rphotopathfile.mkdir();
//                            if (iswindows) {
//                                String datepath = rphotopath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            } else if (islinux) {
//                                String datepath = rphotopath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            }
//
//                        } else {
//                            if (iswindows) {
//                                String datepath = rphotopath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            } else if (islinux) {
//                                String datepath = rphotopath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            }
//                        }
//                        if (!rphototemppathfile.exists() && !rphototemppathfile.isDirectory()) {
//                            System.out.println("人口附件tmp目录不存在");
//                            rphototemppathfile.mkdir();
//                            if (iswindows) {
//                                String datepath = rphototemppath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            } else if (islinux) {
//                                String datepath = rphototemppath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            }
//                        } else {
//                            if (iswindows) {
//                                String datepath = rphototemppath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            } else if (islinux) {
//                                String datepath = rphototemppath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            }
//                        }
//                        if (!hphotopathfile.exists() && !hphotopathfile.isDirectory()) {
//                            System.out.println("房屋附件目录不存在");
//                            hphotopathfile.mkdir();
//                            if (iswindows) {
//                                String datepath = hphotopath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            } else if (islinux) {
//                                String datepath = hphotopath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            }
//                        } else {
//                            if (iswindows) {
//                                String datepath = hphotopath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            } else if (islinux) {
//                                String datepath = hphotopath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            }
//                        }
//                        if (!hphototemppathfile.exists() && !hphototemppathfile.isDirectory()) {
//                            System.out.println("房屋附件tmp目录不存在");
//                            hphototemppathfile.mkdir();
//                            if (iswindows) {
//                                String datepath = hphototemppath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            } else if (islinux) {
//                                String datepath = hphototemppath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            }
//                        } else {
//                            if (iswindows) {
//                                String datepath = hphototemppath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            } else if (islinux) {
//                                String datepath = hphototemppath + File.separatorChar + jdt.getNowYear() + jdt.getNowMonth() + jdt.getNowDay();
//                                File datepathfile = new File(datepath);
//                                datepathfile.mkdir();
//                            }
//                        }
//
//                        try {
//
//                            Thread.currentThread().sleep(millis);
//
//                        } catch (InterruptedException e) {
//                            logger.error(e.getMessage());
//                        }
//                    } else {
//                        try {
//                            System.out.println(this.info() + "-线程未到时间,休眠......");
//                            Thread.currentThread().sleep(1000 * 60 * 60 * 1);
//                        } catch (InterruptedException e) {
//                            logger.error(e.getMessage());
//                        }
//                    }
//                } else {
//                    try {
//                        System.out.println(this.info() + "-线程未到时间,休眠......");
//                        Thread.currentThread().sleep(1000 * 60 * 60 * 1);
//                    } catch (InterruptedException e) {
//                        logger.error(e.getMessage());
//                    }
//                }
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//}
