package com.landray.kmss.sys.filestore.location.server.service.spring;

import com.landray.kmss.sys.attachment.io.IOUtil;
import com.landray.kmss.sys.filestore.location.service.AbstractSysFileResourceProxyService;
import com.landray.kmss.util.ResourceUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ServerResourceProxyServiceImpl extends AbstractSysFileResourceProxyService {

    private String getFileFullPath(String path, boolean isRelative) {
        if (!isRelative) {
            return path;
        }
        String root = ResourceUtil.getKmssConfigString("kmss.resource.path").replaceAll("\\\\", "/");
        if (!root.endsWith("/")) {
            root += "/";
        }
        path = path.replaceAll("\\\\", "/");
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        return root + path;
    }

    @Override
    public void write(String filePath, boolean isRelative, InputStream in) throws Exception {
        String fileFullPath = getFileFullPath(filePath, isRelative);
        File file = new File(fileFullPath);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        IOUtil.write(in, new FileOutputStream(file));
    }

    @Override
    public InputStream read(String filePath, boolean isRelative) throws Exception {
        String fileFullPath = getFileFullPath(filePath, isRelative);
        return new FileInputStream(new File(fileFullPath));
    }

    @Override
    public void delete(String filePath, boolean isRelative) throws Exception {
        String fileFullPath = getFileFullPath(filePath, isRelative);
        FileUtils.deleteQuietly(new File(fileFullPath));
    }

}
