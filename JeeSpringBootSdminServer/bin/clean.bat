@echo off
rem /**
rem  */
echo.
echo [信息] 清理工程文件。
echo.
pause
echo.

cd /d %~dp0
cd..

call mvn clean

cd bin
pause