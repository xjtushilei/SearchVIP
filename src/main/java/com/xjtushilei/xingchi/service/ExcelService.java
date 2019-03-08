package com.xjtushilei.xingchi.service;

import com.xjtushilei.xingchi.entity.Vipcard;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class ExcelService {
    public static void main(String file_path[]) {

    }

    public static ArrayList<Vipcard> readExcel(InputStream excelInputStream) {
        ArrayList<Vipcard> list = new ArrayList<>();
        Workbook wb = null;
        try {
            wb = Workbook.getWorkbook(excelInputStream);
            Sheet sheet = wb.getSheet(0); //get sheet(0)
            //遍历
            for (int i = 0; i < sheet.getRows(); i++) {
                Vipcard temp = new Vipcard(
                        sheet.getCell(1, i).getContents().trim(),
                        sheet.getCell(4, i).getContents().trim(),
                        sheet.getCell(8, i).getContents().trim(),
                        sheet.getCell(11, i).getContents().trim(),
                        sheet.getCell(12, i).getContents().trim(),
                        sheet.getCell(14, i).getContents().trim().replace(".", "").trim());

                list.add(temp);
            }
        } catch (BiffException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
    