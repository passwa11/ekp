@ECHO OFF
echo .
echo .
echo                  ����ekp��weblogic 10.3.x�����Ļ�ԭ����
echo ===================================================================
echo   �ó����ȡ��weblogic 10.3.x patch.cmd���������������޸ġ�
echo   ����㻹���������޸ģ������޸���classpath�����������ļ��������ֶ���ԭ��
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

del /Q %WEBINF%\weblogic.xml

copy /Y %RESTORE%\*.jar %EKPLIB%
copy /Y %RESTORE%\*.sign %EKPLIB%

ENDLOCAL

echo �ָ���������Ҫ�ֶ�ִ�����й�����
echo ����㻹���������޸ģ������ֶ���ԭ��

:END

pause

