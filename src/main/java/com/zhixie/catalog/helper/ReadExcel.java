package com.zhixie.catalog.helper;

import com.zhixie.catalog.model.Catalog;
import com.zhixie.catalog.model.ExemptionDir;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//Excel导入
public class ReadExcel {
    //总行数
    private int totalRows = 0;
    //总条数
    private int totalCells = 0;
    //错误信息接收器
    private String errorMsg;
    //构造方法
    public ReadExcel(){}
    //获取总行数
    public int getTotalRows()  { return totalRows;}
    //获取总列数
    public int getTotalCells() {  return totalCells;}
    //获取错误信息
    public String getErrorInfo() { return errorMsg; }

    /**
     * 读EXCEL文件，获取信息集合
     * @param mFile
     * @return
     */
    public List<ExemptionDir> getExcelInfo(MultipartFile mFile) {
        String fileName = mFile.getOriginalFilename();//获取文件名
        List<ExemptionDir> catalogList = null;
        try {
            if (!validateExcel(fileName)) {// 验证文件名是否合格
                return null;
            }
            boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
            if (isExcel2007(fileName)) {
                isExcel2003 = false;
            }
            catalogList = createExcel(mFile.getInputStream(), isExcel2003);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return catalogList;
    }
    /**
     * 根据excel里面的内容读取信息
     * @param is 输入流
     * @param isExcel2003 excel是2003还是2007版本
     * @return
     * @throws IOException
     */
    public List<ExemptionDir> createExcel(InputStream is, boolean isExcel2003) {
        List<ExemptionDir> catalogList = null;
        try{
            Workbook wb = null;
            if (isExcel2003) {// 当excel是2003时,创建excel2003
                wb = new HSSFWorkbook(is);
            } else {// 当excel是2007时,创建excel2007
                wb = new XSSFWorkbook(is);
            }
           catalogList = readExcelValue(wb);// 读取Excel里面信息
        } catch (IOException e) {
            e.printStackTrace();
        }
        return catalogList;
    }
    /**
     * 读取Excel里面的信息
     * @param wb
     * @return
     */
    private List<ExemptionDir> readExcelValue(Workbook wb) {
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数

        this.totalRows = sheet.getPhysicalNumberOfRows();
        // 得到Excel的列数(前提是有行数)
        if (totalRows > 1 && sheet.getRow(0) != null) {
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }
        List<ExemptionDir> catalogList = new ArrayList<ExemptionDir>();
        // 循环Excel行数
        for (int r = 1; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null){
                continue;
            }
            ExemptionDir catalog = new ExemptionDir();
            // 循环Excel的列
            for (int c = 0; c < this.totalCells; c++) {
                Cell cell = row.getCell(c);
                if (null != cell) {
                    if (c == 0) {
                        catalog.setCategory(String.valueOf(cell.getStringCellValue()));
                    }else if (c == 1) {
                        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                        catalog.setClassify_code(String.valueOf(cell.getStringCellValue()));
                    }else if (c == 2){
                        catalog.setProduct_name(String.valueOf(cell.getStringCellValue()));
                    }else if(c == 3){
                        row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                        catalog.setProduct_description(String.valueOf(cell.getStringCellValue()));
                    }else if(c == 4){
                        catalog.setManagement_category(String.valueOf(cell.getStringCellValue()));
                    }else if(c == 5){
                        row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                        catalog.setRemark(String.valueOf(cell.getStringCellValue()));
                    }
                }
            }
            // 添加到list
            catalogList.add(catalog);
        }
        return catalogList;
    }

    /**
     * 验证EXCEL文件
     *
     * @param filePath
     * @return
     */
    public boolean validateExcel(String filePath) {
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
            errorMsg = "文件名不是excel格式";
            return false;
        }
        return true;
    }

    // @描述：是否是2003的excel，返回true是2003
    public static boolean isExcel2003(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    //@描述：是否是2007的excel，返回true是2007
    public static boolean isExcel2007(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
}
