package com.landray.kmss.sys.attachment.integrate.dianju;

import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import java.io.*;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.util.FileMimeTypeUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class DianjuFile {
    private DianjuSmartUpload m_parent;
    private int m_startData = 0;
    private int m_endData = 0;
    private int m_size = 0;
    private String m_fieldname = new String();
    private String m_filename = new String();
    private String m_fileExt = new String();
    private String m_filePathName = new String();
    private String m_contentType = new String();
    private String m_contentDisp = new String();
    private String m_typeMime = new String();
    private String m_subTypeMime = new String();
    private String m_contentString = new String();
    private boolean m_isMissing = true;
    public static final int SAVEAS_AUTO = 0;
    public static final int SAVEAS_VIRTUAL = 1;
    public static final int SAVEAS_PHYSICAL = 2;
    private ISysAttMainCoreInnerService sysAttMainService = null;

    protected ISysAttMainCoreInnerService getServiceImp() {
        if (sysAttMainService == null) {
            sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService");
        }
        return sysAttMainService;
    }

    private ISysAttUploadService sysAttUploadService = null;

    protected ISysAttUploadService getSysAttUploadService() {
        if (sysAttUploadService == null) {
            sysAttUploadService = (ISysAttUploadService) SpringBeanUtil.getBean("sysAttUploadService");
        }
        return sysAttUploadService;
    }
    DianjuFile() {
    }

    public void saveAs(String s) throws IOException, DianjuSmartUploadException {
        this.saveAs(s, 0);
    }
    public void saveAs(String s, int i) throws IOException, DianjuSmartUploadException {
        new String();
        String s1 = this.m_parent.getPhysicalPath(s, i);
        if (s1 == null) {
            throw new IllegalArgumentException("There is no specified destination file (1140).");
        } else {
            try {
                java.io.File file = new java.io.File(s1);
                FileOutputStream fileoutputstream = new FileOutputStream(file);
                fileoutputstream.write(this.m_parent.m_binArray, this.m_startData, this.m_size);
                fileoutputstream.close();
            } catch (IOException var6) {
                throw new DianjuSmartUploadException("File can't be saved (1120).");
            }
        }
    }

    /**
     * 将文件保存到附件中
     *
     * @throws IOException
     * @throws DianjuSmartUploadException
     */
    public void saveToAttmain(DianjuSaveRequestVO dianjuSaveRequestVO) throws IOException, DianjuSmartUploadException {
       if (DianjuConstant.DIANJC_SAVE_TYPE_ATTACHMENT.equals(dianjuSaveRequestVO.getType())) {
           saveSysAttMain(dianjuSaveRequestVO);
       } else {
           saveAttMaint(dianjuSaveRequestVO);
       }
    }

    /**
     * 附件签章更新文件
     * @throws IOException
     * @throws DianjuSmartUploadException
     */
    public void saveSysAttMain(DianjuSaveRequestVO dianjuSaveRequestVO) throws IOException, DianjuSmartUploadException {
        new String();
        String attMainId = dianjuSaveRequestVO.getAttMainId();
        InputStream in = null;
        try {
            in = new ByteArrayInputStream(this.m_parent.m_binArray, this.m_startData, this.m_size);

            SysAttMain sysAttMain = (SysAttMain) getServiceImp().findByPrimaryKey(attMainId);
            sysAttMain.setInputStream(in);
            getServiceImp().update(sysAttMain);
        }  catch (Exception e) {
            throw new DianjuSmartUploadException("File can't be saved (1121).");
        } finally {
            if(in != null) {
                in.close();
            }
        }
    }

    /**
     * 业务模块签章保存文件
     */
    public void saveAttMaint(DianjuSaveRequestVO dianjuSaveRequestVO) throws IOException, DianjuSmartUploadException {
        new String();
        String modelId = dianjuSaveRequestVO.getModelId();
        String modelName = dianjuSaveRequestVO.getModelName();
        String fileName = dianjuSaveRequestVO.getFileName();
        InputStream in = null;
        try {
            in = new ByteArrayInputStream(this.m_parent.m_binArray, this.m_startData, this.m_size);
            List<SysAttMain> attMainList = getServiceImp().findByModelKey(modelName,
                    modelId,DianjuConstant.DIANJU_SIGNATURE_KEY);

            // 转换OFD文件或PDF文件已经在附件表中，则直接修改信息
            if (attMainList != null && attMainList.size() > 0) {
                SysAttMain attMain = attMainList.get(0);
                String attMainfileName = attMain.getFdFileName();
                String extendName = DianjuUtil.getFileSuffix(attMainfileName);
                // OFD或PDF文件直接保存
                if(DianjuConstant.DIANJU_FILE_SUFFIX_OFD.equals(extendName)
                        || DianjuConstant.DIANJU_FILE_SUFFIX_PDF.equals(extendName)) {
                    attMain.setInputStream(in);
                    getServiceImp().update(attMain);
                    return;
                }
            }

            // 转换OFD文件或PDF文件未在附件表中，则添加信息
            String saveFileName = DianjuUtil.getFileName(fileName) + DianjuConstant.DIANJU_FILE_SUFFIX_OFD;
            String fileNameSuffix = DianjuUtil.getFileSuffix(fileName);
            // PDF文件
            if (DianjuConstant.DIANJU_FILE_SUFFIX_PDF.equals(fileNameSuffix)) {
                saveFileName = DianjuUtil.getFileName(fileName) + DianjuConstant.DIANJU_FILE_SUFFIX_PDF;
            }

            SysAttMain sysAttMain = new SysAttMain();
            sysAttMain.setDocCreateTime(new Date());
            sysAttMain.setFdFileName(saveFileName);
            sysAttMain.setFdSize(Double.valueOf(this.m_parent.m_binArray.length));
            sysAttMain.setDocCreateTime(new Date());
            String contentType = FileMimeTypeUtil.getContentType(saveFileName);
            sysAttMain.setFdContentType(contentType);
            sysAttMain.setInputStream(in);
            sysAttMain.setFdKey(DianjuConstant.DIANJU_SIGNATURE_KEY);
            sysAttMain.setFdModelId(modelId);
            sysAttMain.setFdModelName(modelName);
            sysAttMain.setFdAttType(DianjuConstant.DIANJU_ATTACHMENT_TYPE);
            getServiceImp().add(sysAttMain);


        }  catch (Exception e) {
            throw new DianjuSmartUploadException("File can't be saved (1121).");
        } finally {
            if(in != null) {
                in.close();
            }
        }

    }
    /**
     * EKP地址
     *
     * @return
     */
    public String getSystemUrl() {
        String basePath = ResourceUtil.getKmssConfigString("kmss.resource.path");
        if (basePath.endsWith("/")) {
            basePath = basePath.substring(0, basePath.lastIndexOf("/"));
        }

        return basePath;
    }
    public byte[] getBytes() throws Exception {
        byte[] src = this.m_parent.m_binArray;
        byte[] bts = new byte[this.m_size];

        for(int i = 0; i < this.m_size; ++i) {
            bts[i] = src[this.m_startData + i];
        }

        return bts;
    }

    public void fileToField(ResultSet resultset, String s) throws ServletException, IOException, DianjuSmartUploadException, SQLException {
        long l = 0L;
        int i = 65536;
      //  int j = false;
        int k = this.m_startData;
        if (resultset == null) {
            throw new IllegalArgumentException("The RecordSet cannot be null (1145).");
        } else if (s == null) {
            throw new IllegalArgumentException("The columnName cannot be null (1150).");
        } else if (s.length() == 0) {
            throw new IllegalArgumentException("The columnName cannot be empty (1155).");
        } else {
            l = BigInteger.valueOf((long)this.m_size).divide(BigInteger.valueOf((long)i)).longValue();
            int j = BigInteger.valueOf((long)this.m_size).mod(BigInteger.valueOf((long)i)).intValue();

            try {
                for(int i1 = 1; (long)i1 < l; ++i1) {
                    resultset.updateBinaryStream(s, new ByteArrayInputStream(this.m_parent.m_binArray, k, i), i);
                    k = k != 0 ? k : 1;
                    k = i1 * i + this.m_startData;
                }

                if (j > 0) {
                    resultset.updateBinaryStream(s, new ByteArrayInputStream(this.m_parent.m_binArray, k, j), j);
                }
            } catch (SQLException var10) {
                byte[] abyte0 = new byte[this.m_size];
                System.arraycopy(this.m_parent.m_binArray, this.m_startData, abyte0, 0, this.m_size);
                resultset.updateBytes(s, abyte0);
            } catch (Exception var11) {
                throw new DianjuSmartUploadException("Unable to save file in the DataBase (1130).");
            }

        }
    }

    public boolean isMissing() {
        return this.m_isMissing;
    }

    public String getFieldName() {
        return this.m_fieldname;
    }

    public String getFileName() {
        return this.m_filename;
    }

    public String getFilePathName() {
        return this.m_filePathName;
    }

    public String getFileExt() {
        return this.m_fileExt;
    }

    public String getContentType() {
        return this.m_contentType;
    }

    public String getContentDisp() {
        return this.m_contentDisp;
    }

    public String getContentString() {
        String s = new String(this.m_parent.m_binArray, this.m_startData, this.m_size);
        return s;
    }

    public String getTypeMIME() throws IOException {
        return this.m_typeMime;
    }

    public String getSubTypeMIME() {
        return this.m_subTypeMime;
    }

    public int getSize() {
        return this.m_size;
    }

    protected int getStartData() {
        return this.m_startData;
    }

    protected int getEndData() {
        return this.m_endData;
    }

    protected void setParent(DianjuSmartUpload smartupload) {
        this.m_parent = smartupload;
    }

    protected void setStartData(int i) {
        this.m_startData = i;
    }

    protected void setEndData(int i) {
        this.m_endData = i;
    }

    protected void setSize(int i) {
        this.m_size = i;
    }

    protected void setIsMissing(boolean flag) {
        this.m_isMissing = flag;
    }

    protected void setFieldName(String s) {
        this.m_fieldname = s;
    }

    protected void setFileName(String s) {
        this.m_filename = s;
    }

    protected void setFilePathName(String s) {
        this.m_filePathName = s;
    }

    protected void setFileExt(String s) {
        this.m_fileExt = s;
    }

    protected void setContentType(String s) {
        this.m_contentType = s;
    }

    protected void setContentDisp(String s) {
        this.m_contentDisp = s;
    }

    protected void setTypeMIME(String s) {
        this.m_typeMime = s;
    }

    protected void setSubTypeMIME(String s) {
        this.m_subTypeMime = s;
    }

    public byte getBinaryData(int i) {
        if (this.m_startData + i > this.m_endData) {
            throw new ArrayIndexOutOfBoundsException("Index Out of range (1115).");
        } else {
            return this.m_startData + i <= this.m_endData ? this.m_parent.m_binArray[this.m_startData + i] : 0;
        }
    }
}

