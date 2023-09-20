@ECHO OFF
echo .
echo .
echo                         EKP 的 InforSuiteAS 补丁
echo ===================================================================
echo 在执行程序前，请仔细阅读系统安装维护手册中InforSuiteAS环境的配置内容。
echo 本程序仅负责处理和InforSuiteAS相关的jar文件，其它步骤还需按照文档要求来做。
echo .
echo 确定要执行本程序？
echo .

SET Choice=
SET /P Choice=选择 Y:执行  N:不执行
IF /I "%Choice%"=="N" GOTO :END
IF /I "%Choice%"=="n" GOTO :END

:PATCH

set EKPLIB=..\..\..\lib
set WEBINF=%EKPLIB%\..

rem echo WEBINF : %WEBINF%
rem echo EKPLIB : %EKPLIB%

del /Q %EKPLIB%\xercesImpl.jar
copy /Y *.sign %EKPLIB%

ENDLOCAL

echo .
echo .
echo 程序运行完毕。请务必根据文档完成InforSuiteAS环境的其它配置内容

:END

pause

