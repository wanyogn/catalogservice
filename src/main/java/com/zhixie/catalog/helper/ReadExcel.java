package com.zhixie.catalog.helper;

import com.zhixie.catalog.model.Catalog;
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
    public List<Catalog> getExcelInfo(MultipartFile mFile) {
        String fileName = mFile.getOriginalFilename();//获取文件名
        List<Catalog> catalogList = null;
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
    public List<Catalog> createExcel(InputStream is, boolean isExcel2003) {
        List<Catalog> catalogList = null;
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
    private List<Catalog> readExcelValue(Workbook wb) {
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数

        this.totalRows = sheet.getPhysicalNumberOfRows();
        // 得到Excel的列数(前提是有行数)
        if (totalRows > 1 && sheet.getRow(0) != null) {
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }
        List<Catalog> catalogList = new ArrayList<Catalog>();
        // 循环Excel行数
        for (int r = 1; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null){
                continue;
            }
            Catalog catalog = new Catalog();
            // 循环Excel的列
            for (int c = 0; c < this.totalCells; c++) {
                Cell cell = row.getCell(c);
                if (null != cell) {
                    if (c == 0) {
                        catalog.setFlow_number(String.valueOf(cell.getStringCellValue()));
                    }else if (c == 1) {
                        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                        catalog.setDirectory_number(String.valueOf(cell.getStringCellValue()));
                    }else if (c == 2){
                        catalog.setDirectory_name(String.valueOf(cell.getStringCellValue()));
                    }else if(c == 3){
                        row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                        catalog.setFirst_product_number(String.valueOf(cell.getStringCellValue()));
                    }else if(c == 4){
                        catalog.setFirst_product_name(String.valueOf(cell.getStringCellValue()));
                    }else if(c == 5){
                        row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                        catalog.setSecond_product_number(String.valueOf(cell.getStringCellValue()));
                    }else if(c == 6){
                        catalog.setSecond_product_name(String.valueOf(cell.getStringCellValue()));
                    }else if(c == 7){
                        catalog.setProduct_description(String.valueOf(cell.getStringCellValue()));
                    }else if(c == 8){
                        catalog.setExpected_use(String.valueOf(cell.getStringCellValue()));
                    }else if(c == 9){
                        catalog.setProduct_example(String.valueOf(cell.getStringCellValue()));
                    }else if(c == 10){
                        catalog.setManagement_category(String.valueOf(cell.getStringCellValue()));
                    }else if(c == 11){
                        catalog.setComposite_product(String.valueOf(cell.getStringCellValue()));
                    }else if(c == 12){
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
