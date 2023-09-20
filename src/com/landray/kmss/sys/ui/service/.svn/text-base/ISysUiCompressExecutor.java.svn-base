package com.landray.kmss.sys.ui.service;

import java.util.Map;

/**
 * 合并压缩js执行接口
 * @author lr-linyuchao
 * @desc Create with IntelliJ IDEA
 * @date 2021-10-18
 */
public interface ISysUiCompressExecutor {
    /**
     * 对应定义的扩展点id
     */
    public static final String  EXTENSION_POINT_ID = "com.landray.kmss.sys.ui.compressExecutor";
    /**
     * 压缩合并js存储目录
     */
    public static final String COM_COMPRESS_SOURCE_PATH = "/resource/dynamic_combination";

    public static final String FIX_JS = ".js";

    /**
     * 合并压缩js执行入口
     */
    void execute();

    /**
     * 根据key值合并压缩指定文件
     * @param fileKey 文件唯一标志
     * @return js文件的src
     */
    String execute(String fileKey);

    /**
     * 相对目录 <br/>
     * 由实现类实现。<br/>
     * 不指定则压缩文件直接存储在/resource/dynamic_combination<br/>
     * 指定则在/resource/dynamic_combination下追加目录<br/>
     * @return
     */
     String getRelatePath();

    /**
     * 获取文件列表映射<br/>
     * key值为压缩文件名称<br/>
     * value为压缩来源文件集合或数组对象<br/>
     * @return
     */
    Map<String, Object> getFileListMapping();

    /**
     * 该方法暂未有实际效果</br>
     *  设置为禁用
     * @return
     */
    boolean disabled();

    /**
     * 该方法暂未有实际效果</br>
     * 设置为启用
     * @return
     */
    boolean enabled();

    /**
     * 该方法暂未有实际效果</br>
     * 是否启用
     * @return
     */
    boolean isEnabled();
}
