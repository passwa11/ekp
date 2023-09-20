package com.landray.kmss.sys.filestore.location.service;

import com.landray.kmss.sys.filestore.location.interfaces.ISysFileResourceProxyService;

import java.io.InputStream;

public abstract class AbstractSysFileResourceProxyService implements ISysFileResourceProxyService {
    public void write(String fileRelativePath,InputStream in) throws Exception{
        write(fileRelativePath, true, in);
    };

    public InputStream read(String fileRelativePath) throws Exception{
        return read(fileRelativePath, true);
    }

    public void delete(String fileRelativePath) throws Exception {
        delete(fileRelativePath,true);
    }
}
