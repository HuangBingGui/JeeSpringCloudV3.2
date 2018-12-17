@echo off
rem /**
rem  */
echo.
echo [信息] 生成Eclipse工程文件。
echo.

cd /d %~dp0
cd..

call mvn idea:idea

cd bin
pause