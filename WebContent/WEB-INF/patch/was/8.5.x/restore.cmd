@ECHO OFF
echo .
echo .
echo                  EKP �� WAS 7 ������ԭ����
echo ===================================================================
echo   ��ִ�г���ǰ������ϸ�Ķ�ϵͳ��װά���ֲ���WebSphere�������������ݡ�
echo   �ó����ȡ��WAS 7.x patch.cmd���������������޸ġ�
echo .
echo   ȷ��Ҫִ�б�����

SET Choice=
SET /P Choice=ѡ�� Y:ִ��  N:��ִ��
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

del /Q %EKPLIB%\jsr173-api-1.0.jar
copy /Y %RESTORE%\*.jar  %EKPLIB%
copy /Y %RESTORE%\*.sign %EKPLIB%

ENDLOCAL

echo .
echo .
echo ����������ϡ�����㻹���������޸ģ��������ֶ���ԭ��

:END

pause

