package com.landray.kmss.code.dict;

public class Runner {
	public static void main(String[] args) throws Exception {
		// 日志文件
		DataDictTool.LOG_FILE = "/DictError.log";
		DataDictTool.LOG_WARN = true;
		// 创建模块的数据字典
		// new DataDictCreateTool("sys/news").create();

		// 检查所有数据字典
		// new DataDictFixTool().check();
		// 修复所有数据字典
		// new DataDictFixTool().fix();

		// 检查指定模块的数据字典
		// new DataDictFixTool("sys/news").check();
		// 修复指定模块的数据字典
		// new DataDictFixTool("sys/news").fix();

		new DataDictFixTool().check();
	}
}
