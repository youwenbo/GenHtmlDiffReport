使用本软件前请安装好Beyond Compare、JRE（如果没有，可到此处取用安装：\\10.184.47.184\share）；

首次启动可能需要对 GenReport.jar点右键，依次：打开方式->选择默认程序->Java(TM) Platform SE binary；（xxx\jrexxx\bin\javaw.exe）

以后启动只需直接双击 GenReport.jar 即可运行；

首次使用由于配置错误可能会失败，建议关闭重启应用；

生成的报告在应用（GenReport.jar）所在目录的change文件夹中；

软件有很多bug，只保证正常使用，有强迫症者慎用！



故障处理指南：
	a.如果提示未找到对应问题单，请确认仓库地址是否配置正确，修改的代码是否在仓库地址对应的层级里
	
	b.如果提示成功了，但未看到html比较报告，请确认beyond compare工具路径是否配置正确
	

双击不能打开，修改注册表：
regedit
在注册表编辑器中，找到“HKEY_CLASSES_ROOT\Applications\javaw.exe\shell\open\command”，在其中文件打开命令中加入参数“-jar”（无引号），修改后的数值类似：“"C:\Program Files\Java\jre7\bin\javaw.exe" -jar "%1"”（只需要添加-jar参数，无需修改其他信息），保存并退出注册表编辑器。
