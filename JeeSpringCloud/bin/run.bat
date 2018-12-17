@echo off
rem /**
rem  */
echo.
echo [信息] 运行工程文件。
echo.

cd /d %~dp0
cd ../jeespring-web/target

java -jar jeespring-web-3.0.0.war

cd bin
pause