@echo off
rem /**
rem  */
echo.
echo [信息] 生成compile文件。
echo.

cd /d %~dp0
cd..

call mvn compile

cd bin
pause