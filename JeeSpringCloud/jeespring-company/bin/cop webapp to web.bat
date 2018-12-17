@echo off
rem /**
rem  */
echo.
echo [信息] 生成package文件。
echo.

cd /d %~dp0
cd..

xcopy src\main\webapp\*.* ..\jeespring-web\src\main\webapp /s /e /y

cd bin
pause