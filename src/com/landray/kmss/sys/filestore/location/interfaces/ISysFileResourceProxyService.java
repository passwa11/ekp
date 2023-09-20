package com.landray.kmss.sys.filestore.location.interfaces;

import java.io.InputStream;

public interface ISysFileResourceProxyService {
    void write(String filePath, boolean isRelative, InputStream in) throws Exception;

    InputStream read(String filePath,boolean isRelative) throws Exception;

    void delete(String filePath,boolean isRelative) throws Exception;

}
