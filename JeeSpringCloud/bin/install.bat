@echo off
rem /**
rem  */
echo.
echo [–≈œ¢] install°£
echo.

cd /d %~dp0
cd..

call mvn install -f pom-install.xml

cd bin
pause