@echo off
rem /**
rem  */
echo.
echo [info] del project info and target.
echo.

cd ..\

if exist .\jeespring-web\target (  
	rmdir /s/q .\jeespring-web\target
	echo [info] del .\jeespring-web\target
)
del .\jeespring-web\*.iml

if exist .\jeespring-webDubboProvider\target (  
	rmdir /s/q .\jeespring-webDubboProvider\target
	echo [info] del .\jeespring-webDubboProvider\target
)
del .\jeespring-webDubboProvider\*.iml

if exist .\jeespring-mq\target (  
	rmdir /s/q .\jeespring-mq\target
	echo [info] del .\jeespring-mq\target
)
del .\jeespring-mq\*.iml

if exist .\jeespring-framework\target (
	rmdir /s/q .\jeespring-framework\target
	echo [info] del .\jeespring-mq\target
)
del .\jeespring-framework\*.iml

if exist .\jeespring-company\target (
	rmdir /s/q .\jeespring-company\target
	echo [info] del .\jeespring-company\target
)
del .\jeespring-company\*.iml

if exist .\jeespring-cms\target (
	rmdir /s/q .\jeespring-cms\target
	echo [info] del .\jeespring-cms\target
)
del .\jeespring-cms\*.iml

if exist .\jeespring-act\target (
	rmdir /s/q .\jeespring-act\target
	echo [info] del .\jeespring-act\target
)
del .\jeespring-act\*.iml

if exist .\jeespring-gencode\target (
	rmdir /s/q .\jeespring-gencode\target
	echo [info] del .\jeespring-gencode\target
)
del .\jeespring-gencode\*.iml

if exist .\projectFilesBackup (
	rmdir /s/q .\projectFilesBackup
	echo [info] del .\projectFilesBackup
)

del jeespring.iml
del jeespring.ipr
del jeespring.iws

echo [info] deal end
pause