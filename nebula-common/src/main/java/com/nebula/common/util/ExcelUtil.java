package com.nebula.common.util;

import com.alibaba.excel.EasyExcel;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public class ExcelUtil<T> {

    public static <T> List<T> readExcel(File file, Class<T> headRow,Integer sheetIndex) {
        ExcelReader<T> excelReader = new ExcelReader<>();
        EasyExcel.read(file,headRow,excelReader).sheet(sheetIndex).doRead();
        return excelReader.getList();
    }

    public static <T> List<T> readExcel(InputStream inputStream, Class<T> headRow, Integer sheetIndex) {
        ExcelReader<T> excelReader = new ExcelReader<>();
        EasyExcel.read(inputStream,headRow,excelReader).sheet(sheetIndex).doRead();
        return excelReader.getList();
    }

    public static <T> List<T> readExcel(String path, Class<T> headRow, Integer sheetIndex) {
        ExcelReader<T> excelReader = new ExcelReader<>();
        EasyExcel.read(path,headRow,excelReader).sheet(sheetIndex).doRead();
        return excelReader.getList();
    }
}
