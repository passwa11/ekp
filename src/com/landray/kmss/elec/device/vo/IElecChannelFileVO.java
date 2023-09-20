package com.landray.kmss.elec.device.vo;

import java.io.InputStream;
import java.util.Base64;
import java.util.Map;


/**
 * 签署相关文件信息接口
 * @author lr-linyuchao
 * @date 2021年4月25日
 */
public interface IElecChannelFileVO{

	/**
	 * ekp附件id<br/>
	 * 建议使用SysAttFile对象的fdId
	 */
	public String getEkpFileId();

	/**
	 * ekp附件id<br/>
	 * 建议使用SysAttFile对象的fdId
	 * @param ekpFileId
	 * @return
	 */
	public IElecChannelFileVO setEkpFileId(String ekpFileId);

	/**
	 * 文件存储位置<br/>
	 * 此参数对应ekp附件存储位置参数 如；server/aliyun
	 * @return
	 */
	public  String getFileLocation();
	/**
	 * 文件存储位置<br/>
	 * 此参数对应ekp附件存储位置参数 如；server/aliyun
	 * @return
	 */
	public  IElecChannelFileVO setFileLocation(String fileLocation);
	/**
	 * 文件路径
	 * @return
	 */
	public String getFilePath();
	/**
	 * 文件路径
	 * @param fileBase64
	 */
	public IElecChannelFileVO setFilePath(String filePath);
	/**
	 * 文件名称
	 * @return
	 */
	public String getFileName();
	/**
	 * 文件名称
	 * @param fileName
	 */
	public IElecChannelFileVO setFileName(String fileName);
	/**
	 * 获取文件下载链接
	 * @return
	 */
	public String getDownLoadUrl();
	/**
	 * 获取文件下载链接
	 * @param url
	 * @return
	 */
	public IElecChannelFileVO setDownLoadUrl(String url);
	/**
	 * 文件base64格式
	 * @return
	 */
	public Base64 getBase64();
	/**
	 * 文件base64格式
	 * @param base64
	 * @return
	 */
	public IElecChannelFileVO setBase64(Base64 base64);	
	/**
	 * 二进制文件
	 * @return
	 */
	public byte[] getBytes();
	/**
	 * 二进制文件
	 * @param bytes
	 * @return
	 */
	public IElecChannelFileVO setBytes(byte[] bytes);	
	/**
	 * 获取文件流，具体方法由渠道服务实现
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public InputStream getFileStream() throws Exception;
	/**
     * 附加信息，可为空
     * @return
     */
    Map<String,Object> getFdAdditionalInfo();
    /**
     * 第三方平台实体的id，可选
     * @return
     */
    public String getFdThirdNum();

    /**
     * 第三方平台实体的id，可选
     * @param fdThirdNum
     */
    public IElecChannelFileVO setFdThirdNum(String fdThirdNum) ;
}
