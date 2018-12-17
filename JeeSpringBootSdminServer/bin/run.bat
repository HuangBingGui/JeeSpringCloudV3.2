@echo off
rem /**
rem  */
echo.
echo [信息] 运行工程文件。
echo.

cd /d %~dp0
cd ../target

java -jar springboot-admin-server-0.0.1-SNAPSHOT.jar

cd bin
pause