@ECHO OFF
echo .
echo .
echo                  这是ekp的ojdbc5的补丁
echo               执行该脚本将替换EKP中的ojdbc7.jar
echo ===================================================================
echo .

SET Choice=
SET /P Choice=选择 Y:执行  N:不执行
IF /I "%Choice%"=="N" GOTO :END
IF /I "%Choice%"=="n" GOTO :END

:PATCH

set EKPLIB=..\..\..\lib
set WEBINF=%EKPLIB%\..
set RESTORE=..\restore

rem echo WEBINF : %WEBINF%
rem echo EKPLIB : %EKPLIB%
rem echo RESTORE: %RESTORE%
		
del /Q %EKPLIB%\ojdbc7.jar
copy /Y ojdbc5.jar 	%EKPLIB%\
copy /Y *.sign	%EKPLIB%\

ENDLOCAL

echo 已完成补丁操作

:END

pause

