@echo off
rem /**
rem  */
echo.
echo [信息] 生成package文件。
echo.

cd /d %~dp0
cd..

call mvn package

cd bin
pause