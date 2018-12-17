@echo off
rem /**
rem  */
echo.
echo [info] del project info and target?
echo.

cd ..\

if exist .\jeespring-web\target (  
	rmdir /s/q .\jeespring-web\target
	echo [info] del .\jeespring-web\target
)
if exist .\jeespring-web\.settings (  
	rmdir /s/q .\jeespring-web\.settings
	echo [info] del .\jeespring-web\.settings
)
del .\jeespring-web\.classpath
del .\jeespring-web\.project

if exist .\jeespring-webDubboProvider\target (  
	rmdir /s/q .\jeespring-webDubboProvider\target
	echo [info] del .\jeespring-webDubboProvider\target
)
if exist .\jeespring-webDubboProvider\.settings (  
	rmdir /s/q .\jeespring-webDubboProvider\.settings
	echo [info] del .\jeespring-webDubboProvider\.settings
)
del .\jeespring-webDubboProvider\.classpath
del .\jeespring-webDubboProvider\.project

if exist .\jeespring-mq\target (  
	rmdir /s/q .\jeespring-mq\target
	echo [info] del .\jeespring-mq\target
)
if exist .\jeespring-mq\.settings (  
	rmdir /s/q .\jeespring-mq\.settings
	echo [info] del .\jeespring-mq\.settings
)
del .\jeespring-mq\.classpath
del .\jeespring-mq\.project

if exist .\jeespring-framework\target (
	rmdir /s/q .\jeespring-framework\target
	echo [info] del .\jeespring-framework\target
)
if exist .\jeespring-framework\.settings (  
	rmdir /s/q .\jeespring-framework\.settings
	echo [info] del .\jeespring-framework\.settings
)
del .\jeespring-framework\.classpath
del .\jeespring-framework\.project

if exist .\jeespring-company\target (
	rmdir /s/q .\jeespring-company\target
	echo [info] del .\jeespring-company\target
)
if exist .\jeespring-company\.settings (  
	rmdir /s/q .\jeespring-company\.settings
	echo [info] del .\jeespring-company\.settings
)
del .\jeespring-company\.classpath
del .\jeespring-company\.project

if exist .\jeespring-cms\target (
	rmdir /s/q .\jeespring-cms\target
	echo [info] del .\jeespring-cms\target
)
if exist .\jeespring-cms\.settings (  
	rmdir /s/q .\jeespring-cms\.settings
	echo [info] del .\jeespring-cms\.settings
)
del .\jeespring-cms\.classpath
del .\jeespring-cms\.project

if exist .\jeespring-act\target (
	rmdir /s/q .\jeespring-act\target
	echo [info] del .\jeespring-act\target
)
if exist .\jeespring-act\.settings (  
	rmdir /s/q .\jeespring-act\.settings
	echo [info] del .\jeespring-act\.settings
)
del .\jeespring-act\.classpath
del .\jeespring-act\.project

if exist .\jeespring-gencode\target (
	rmdir /s/q .\jeespring-gencode\target
	echo [info] del .\jeespring-gencode\target
)
if exist .\jeespring-gencode\.settings (  
	rmdir /s/q .\jeespring-gencode\.settings
	echo [info] del .\jeespring-gencode\.settings
)
del .\jeespring-gencode\.classpath
del .\jeespring-gencode\.project

if exist .\.settings (
	rmdir /s/q .\.settings
	echo [info] del .\.settings
)

del .project

echo [info] deal end
pause