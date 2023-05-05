package com.gt.utils;


import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.OutputStream;
import java.util.ArrayList;


/**
 * @ClassName LoadExcelUtil
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/10/14
 * @Version V1.0
 **/
public class LoadDebugExcelUtil {

    private final int SPLIT_COUNT = 10000; //Excel每个工作簿的行数

    private ArrayList<String> fieldName = null; //excel标题数据集

    private ArrayList<ArrayList<String>> fieldData = null; //excel数据内容

    private HSSFWorkbook workBook = null;

    /**
     * 构造器
     * fieldName 结果集的字段名
     * data
     */
    public LoadDebugExcelUtil(ArrayList<ArrayList<String>> fieldData) {
        this.fieldData = fieldData;
    }

    /**
     * 创建HSSFWorkbook对象
     *
     * @return HSSFWorkbook
     */
    public HSSFWorkbook createWorkbookManhour() {

        workBook = new HSSFWorkbook();//创建一个工作薄对象
        int rows = fieldData.size();//总的记录数
        int sheetNum = 0;           //指定sheet的页数

        if (rows % SPLIT_COUNT == 0) {
            sheetNum = rows / SPLIT_COUNT;
        } else {
            sheetNum = rows / SPLIT_COUNT + 1;
        }

        for (int i = 1; i <= sheetNum; i++) {//循环2个sheet的值
            HSSFSheet sheet = workBook.createSheet("Page " + i);//使用workbook对象创建sheet对象
            /**************对标题添加样式begin********************/
            HSSFRow headRow = sheet.createRow((short) 0); //创建行，0表示第一行（本例是excel的标题）
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 0));//参数说明：1：开始行 2：结束行  3：开始列 4：结束列
            headRow.createCell(0).setCellValue("项目名称");

            sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 1));//参数说明：1：开始行 2：结束行  3：开始列 4：结束列
            //设置样式-颜色
            HSSFCellStyle style = workBook.createCellStyle();
            style.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            //水平居中
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //垂直居中
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            HSSFFont font = workBook.createFont();//创建字体对象
            //字体加粗
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            //字体颜色变红
            font.setColor(HSSFColor.RED.index);
            //如果font中存在设置后的字体，并放置到cellStyle对象中，此时该单元格中就具有了样式字体
            style.setFont(font);
            headRow.createCell(1).setCellValue("项目里程碑名称");
            //headRow.createCell(1).setCellStyle(style);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 2));//参数说明：1：开始行 2：结束行  3：开始列 4：结束列
            headRow.createCell(2).setCellValue("工作内容");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 3));//参数说明：1：开始行 2：结束行  3：开始列 4：结束列
            headRow.createCell(3).setCellValue("确认工时");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 4));//参数说明：1：开始行 2：结束行  3：开始列 4：结束列
            headRow.createCell(4).setCellValue("填报人");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 5, 5));//参数说明：1：开始行 2：结束行  3：开始列 4：结束列
            headRow.createCell(5).setCellValue("创建时间");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 6, 6));//参数说明：1：开始行 2：结束行  3：开始列 4：结束列
            headRow.createCell(6).setCellValue("debug时间");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 7));//参数说明：1：开始行 2：结束行  3：开始列 4：结束列
            headRow.createCell(7).setCellValue("");

            //headRow.createCell(2).setCellStyle(style);

            //创建绘图对象
            HSSFPatriarch p = sheet.createDrawingPatriarch();
		/*	//创建单元格对象,批注插入到4行,1列,B5单元格
			HSSFCell cell=sheet.createRow(4).createCell(1);
			//插入单元格内容
			cell.setCellValue(new HSSFRichTextString("批注"));
			//获取批注对象
			//(int dx1, int dy1, int dx2, int dy2, short col1, int row1, short col2, int row2)
			//前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
			HSSFComment comment=p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
			//输入批注信息
			comment.setString(new HSSFRichTextString("插件批注成功!插件批注成功!"));
			//添加作者,选中B5单元格,看状态栏
			comment.setAuthor("toad");
			//将批注添加到单元格对象中
			cell.setCellComment(comment);*/

            //分页处理excel的数据，遍历所有的结果
            for (int k = 0; k < (rows < SPLIT_COUNT ? rows : SPLIT_COUNT); k++) {
                if (((i - 1) * SPLIT_COUNT + k) >= rows)//如果数据超出总的记录数的时候，就退出循环
                    break;
                HSSFRow row = sheet.createRow((short) (k + 1));//创建1行
                //分页处理，获取每页的结果集，并将数据内容放入excel单元格
                ArrayList<String> rowList = (ArrayList<String>) fieldData.get((i - 1) * SPLIT_COUNT + k);
                for (int n = 0; n < rowList.size()-1; n++) {//遍历某一行的结果
                    HSSFCell cell2 = row.createCell(n);//使用行创建列对象
                    if (rowList.get(n) != null) {
                        cell2.setCellValue((String) rowList.get(n).toString());
                    } else {
                        cell2.setCellValue("");
                    }
                }
            }
        }
        return workBook;
    }

    public void expordExcelManhour(OutputStream os) throws Exception {
        workBook = createWorkbookManhour();
        workBook.write(os);//将excel中的数据写到输出流中，用于文件的输出
        os.close();
    }
}
