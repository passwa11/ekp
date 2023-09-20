package com.landray.kmss.common.forms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.landray.kmss.web.upload.FormFile;

/**
 * EKP用于接收上传文件的类，使用Spring的上传模型，同时兼具struts的FormFile接口<br/>
 * 该类用于自定义的MultipartResolver
 * @author Kilery.Chen
 */
public class KmssFormFile extends CommonsMultipartFile implements FormFile{

    private static final long serialVersionUID = 67912386911234441L;

    public KmssFormFile(FileItem fileItem) {
        super(fileItem);
    }
    
    private File transferFile = null;
//--------------------------------------------------------------------------------
    
    @Override
    public void setContentType(String contentType) {
      //父类构造函数中完成 do nothing
    }
    @Override
    public void setFileSize(long fileSize) {
      //父类构造函数中完成 do nothing
    }
    @Override
    public void setFileName(String fileName) {
      //父类构造函数中完成 do nothing
    }
    
    @Override
    public String getContentType() {
        return getFileItem().getContentType();
    }
    
    @Override
    public long getFileSize() {
        return getSize();
    }

    
    @Override
    public String getFileName() {
        String name = getFileItem().getName();
        return name;
    }

    @Override
    public byte[] getFileData() throws FileNotFoundException, IOException {
        return getBytes();
    }

    /**
     * 覆盖原struts上传逻辑，删除磁盘文件（如果存在）
     */
    @Override
    public void destroy() {
        if(transferFile!=null){
            boolean delete = transferFile.delete();
            onDestroy(transferFile,delete);
        }
    }
    
    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        super.transferTo(dest);
        transferFile = dest;
    }
    
    /*
     * destroy 结果处理，默认do nothing
     */
    protected void onDestroy(File transferFile, boolean deleted){
        // do nothing
    }
}
