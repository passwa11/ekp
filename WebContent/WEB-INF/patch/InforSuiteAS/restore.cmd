@ECHO OFF
echo .
echo .
echo                  EKP �� InforSuiteAS ������ԭ����
echo ===================================================================
echo   ��ִ�г���ǰ������ϸ�Ķ�ϵͳ��װά���ֲ���InforSuiteAS�������������ݡ�
echo   �ó����ȡ��InforSuiteAS patch.cmd���������������޸ġ�
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

copy /Y %RESTORE%\*.jar  %EKPLIB%
copy /Y %RESTORE%\*.sign %EKPLIB%

ENDLOCAL

echo .
echo .
echo ����������ϡ�����㻹���������޸ģ��������ֶ���ԭ��

:END

pause

