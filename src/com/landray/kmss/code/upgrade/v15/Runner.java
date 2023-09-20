package com.landray.kmss.code.upgrade.v15;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class Runner {

	public static void main(String[] args) {

		CommandLineParser parser = new DefaultParser();
		Options options = new Options();
		options.addOption("h", "help", false, "输出帮助信息");
		options.addOption("c", "compile", true, "执行v15版本的jsp 编译");
		options.addOption("m", "mvc", true, "执行v15版本的 struts 转换 spring mvc");
		options.addOption("s", "server_path", true, "访问 EKP 服务器地址");
		
		HelpFormatter formatter = new HelpFormatter();
		
		try {

			CommandLine commandLine = parser.parse(options, args);

			if (commandLine.hasOption('h')) {
				formatter.printHelp("command-line", options);
				System.exit(0);
			}
			if (commandLine.hasOption('c')) {
				//检查现有JSP的使用情况
				JspCompile.main(args);
			}

			if (commandLine.hasOption('m')) {
				//SrpingMvc转换的执行程序(执行代码迁移)
				com.landray.kmss.code.upgrade.v15.springmvc.trans.Runner.main(args);
			}

		} catch (Exception e) {
			//e.printStackTrace();
			formatter.printHelp("command-line", options);
			System.exit(1);
		}

	}

}
