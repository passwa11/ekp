@ECHO OFF
echo .
echo .
echo                         EKP �� InforSuiteAS ����
echo ===================================================================
echo ��ִ�г���ǰ������ϸ�Ķ�ϵͳ��װά���ֲ���InforSuiteAS�������������ݡ�
echo ��������������InforSuiteAS��ص�jar�ļ����������軹�谴���ĵ�Ҫ��������
echo .
echo ȷ��Ҫִ�б�����
echo .

SET Choice=
SET /P Choice=ѡ�� Y:ִ��  N:��ִ��
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
echo ����������ϡ�����ظ����ĵ����InforSuiteAS������������������

:END

pause

