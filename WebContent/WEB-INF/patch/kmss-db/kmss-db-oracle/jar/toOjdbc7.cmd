@ECHO OFF
echo .
echo .
echo                  ����ekp��ojdbc7�Ĳ�����
echo               ִ�иýű����滻EKP�е�ojdbc5.jar
echo ===================================================================
echo .

SET Choice=
SET /P Choice=ѡ�� Y:ִ��  N:��ִ��
IF /I "%Choice%"=="N" GOTO :END
IF /I "%Choice%"=="n" GOTO :END

:PATCH

set EKPLIB=..\..\..\lib
set WEBINF=%EKPLIB%\..
set RESTORE=..\restore

rem echo WEBINF : %WEBINF%
rem echo EKPLIB : %EKPLIB%
rem echo RESTORE: %RESTORE%
		
del /Q %EKPLIB%\ojdbc5.jar
copy /Y ojdbc7.jar 	%EKPLIB%\
copy /Y *.sign	%EKPLIB%\

ENDLOCAL

echo ����ɲ�������

:END

pause

