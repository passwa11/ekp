@ECHO OFF
echo .
echo .
echo                  这是ekp的weblogic 10.3.x补丁的还原程序。
echo ===================================================================
echo   该程序会取消weblogic 10.3.x patch.cmd补丁程序所作的修改。
echo   如果你还做了其它修改，比如修改了classpath或其他配置文件请自行手动还原。
echo .
echo   确定要执行本程序？

SET Choice=
SET /P Choice=选择 Y:执行  N:不执行
IF /I "%Choice%"=="N" GOTO :END
IF /I "%Choice%"=="n" GOTO :END

:RESTORE

SETLOCAL

set EKPLIB=..\..\..\lib
set WEBINF=%EKPLIB%\..
set RESTORE=restore

rem echo WEBINF : %WEBINF%
rem echo EKPLIB : %EKPLIB%
rem echo RESTORE: %RESTORE%

del /Q %WEBINF%\weblogic.xml

copy /Y %RESTORE%\*.jar %EKPLIB%
copy /Y %RESTORE%\*.sign %EKPLIB%

ENDLOCAL

echo 恢复操作还需要手动执行下列工作：
echo 如果你还做了其它修改，自行手动还原。

:END

pause

