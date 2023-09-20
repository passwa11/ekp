package com.landray.kmss.sys.portal.util;

import com.landray.kmss.util.StringUtil;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;

import java.io.*;
import java.util.Enumeration;

/**
 * @description: zip操作类
 * @version: 1.0
 */

public class ZipUtil {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(com.landray.kmss.sys.portal.util.ZipUtil.class);
    private static final int BUFFEREDSIZE = 1024;

    /**
     * 压缩文件
     *
     * @param filePath 需要压缩的文件夹或者文件路径
     * @param isDelete 是否删除源文件
     * @throws Exception
     */
    public static void zip(String filePath, boolean isDelete) throws Exception {
        String zipFileName = filePath + ".zip";
        zip(zipFileName, filePath, isDelete);
    }

    /**
     * 压缩文件
     *
     * @param zipFileName 保存的压缩包文件路径
     * @param filePath    需要压缩的文件夹或者文件路径
     * @param isDelete    是否删除源文件
     * @throws Exception
     */
    public static void zip(String zipFileName, String filePath, boolean isDelete) throws Exception {
        zip(zipFileName, new File(filePath), isDelete);
    }

    /**
     * 压缩文件
     *
     * @param zipFileName 保存的压缩包文件路径
     * @param inputFile   需要压缩的文件夹或者文件
     * @param isDelete    是否删除源文件
     * @throws Exception
     */
    public static void zip(String zipFileName, File inputFile, boolean isDelete) throws Exception {
        if (StringUtil.isNotNull(zipFileName) && !inputFile.exists()) {
            throw new FileNotFoundException("在指定路径未找到需要压缩的文件！");
        }
        FileOutputStream fileOutputStream = new FileOutputStream(zipFileName);
        ZipOutputStream out = new ZipOutputStream(fileOutputStream);
        zip(out, inputFile, "", isDelete);
        out.close();
        fileOutputStream.close();
    }

    /**
     * 递归压缩方法
     *
     * @param out       压缩包输出流
     * @param inputFile 需要压缩的文件
     * @param base      压缩的路径
     * @param isDelete  是否删除源文件
     * @throws Exception
     */
    private static void zip(ZipOutputStream out, File inputFile, String base, boolean isDelete) throws Exception {
        if (inputFile.isDirectory()) { // 如果是目录
            File[] inputFiles = inputFile.listFiles();
            out.putNextEntry(new ZipEntry(base + "/"));
            base = base.length() == 0 ? "" : base + "/";
            for (int i = 0; i < inputFiles.length; i++) {
                zip(out, inputFiles[i], base + inputFiles[i].getName(), isDelete);
            }
        } else { // 如果是文件
            if (base.length() > 0) {
                out.putNextEntry(new ZipEntry(base));
            } else {
                out.putNextEntry(new ZipEntry(inputFile.getName()));
            }
            FileInputStream in = new FileInputStream(inputFile);
            try {
                int len;
                byte[] buff = new byte[BUFFEREDSIZE];
                while ((len = in.read(buff)) != -1) {
                    out.write(buff, 0, len);
                }
            } catch (IOException e) {
                throw e;
            } finally {
                in.close();
            }
        }
        if (isDelete) {
            inputFile.delete();
        }
    }

    /**
     * 解压缩
     *
     * @param zipFilePath 压缩包路径
     * @param isDelete    是否删除源文件
     * @throws Exception
     */
    public static void unZip(String zipFilePath, boolean isDelete) throws Exception {
        String filePath = zipFilePath.substring(0, zipFilePath.length() - 4);
        unZip(zipFilePath, filePath, isDelete);
    }

    /**
     * 解压缩
     *
     * @param zipFilePath  压缩包路径
     * @param fileSavePath 解压路径
     * @param isDelete     是否删除源文件
     * @throws Exception
     */
    public static void unZip(String zipFilePath, String fileSavePath, boolean isDelete) throws Exception {
        ZipFile zipFile = null;
        try {
            (new File(fileSavePath)).mkdirs();
            File f = new File(zipFilePath);
            if ((!f.exists()) && (f.length() <= 0)) {
                throw new Exception("要解压的文件不存在!");
            }
            zipFile = new ZipFile(f);
            String strPath, gbkPath, strtemp;
            File tempFile = new File(fileSavePath);// 从当前目录开始
            strPath = tempFile.getAbsolutePath();// 输出的绝对位置
            Enumeration<ZipEntry> e = zipFile.getEntries();
            while (e.hasMoreElements()) {
                org.apache.tools.zip.ZipEntry zipEnt = e.nextElement();
                gbkPath = zipEnt.getName();
                if (zipEnt.isDirectory()) {
                    strtemp = strPath + File.separator + gbkPath;
                    File dir = new File(strtemp);
                    dir.mkdirs();
                    continue;
                } else {
                    // 读写文件
                    InputStream is = zipFile.getInputStream(zipEnt);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    gbkPath = zipEnt.getName();
                    strtemp = strPath + File.separator + gbkPath;
                    // 建目录
                    String strsubdir = gbkPath;
                    for (int i = 0; i < strsubdir.length(); i++) {
                        if ("/".equalsIgnoreCase(strsubdir.substring(i, i + 1))) {
                            String temp = strPath + File.separator + strsubdir.substring(0, i);
                            File subDir = new File(temp);
                            if (!subDir.exists()) {
                                subDir.mkdir();
                            }
                        }
                    }
                    FileOutputStream fos = new FileOutputStream(strtemp);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    int len;
                    byte[] buff = new byte[BUFFEREDSIZE];
                    while ((len = bis.read(buff)) != -1) {
                        bos.write(buff, 0, len);
                    }
                    bos.close();
                    fos.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (zipFile != null) {
                zipFile.close();
            }
        }
        if (isDelete) {
            new File(zipFilePath).delete();
        }
    }

}