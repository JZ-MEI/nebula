package com.nebula.common.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;

public class DownloadUtil {
    public static void downloadFile(InputStream inputStream, HttpServletResponse response, String fileName) {
        ServletOutputStream out = null;
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        int buffer = 1024 * 10;
        byte[] data = new byte[buffer];
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            int read;
            out = response.getOutputStream();
            while ((read = bis.read(data)) != -1) {
                out.write(data, 0, read);
            }
            out.flush();
        } catch (Exception ex) {
            IoUtil.close(out);
        } finally {
            IoUtil.close(inputStream);
        }
    }

    public static void downloadFile(File file, HttpServletResponse response, String fileName) {
        downloadFile(FileUtil.getInputStream(file),response,fileName);
    }

    public static void downloadFile(String filePath, HttpServletResponse response, String fileName){
        File file = new File(filePath);
        downloadFile(file,response,fileName);
    }
}
