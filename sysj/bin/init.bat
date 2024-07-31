@echo off
@rem ##########################################################################
@rem
@rem  SystemJ compiler script based on Gradle 2.7 startup script 
@rem
@rem ##########################################################################

if not defined NEED_JDK set NEED_JDK=false
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto testjavac

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:testjavac

if %NEED_JDK%==false goto init 
set JAVAC_EXE=javac.exe
%JAVAC_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'javac' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your JDK installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe
set JAVAC_EXE=%JAVA_HOME%/bin/javac.exe

if exist "%JAVA_EXE%" goto findJavacFromJavaHome

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavacFromJavaHome

if %NEED_JDK%==true (
		if exist "%JAVAC_EXE%" goto init
		echo.
		echo ERROR: Could not find the java compiler: 'javac'
		echo.
		echo Please set the JAVA_HOME variable in your environment to match the
		echo location of your JDK installation

		goto fail
) else goto init

rem if "%@eval[2+2]" == "4" goto 4NT_args
rem set CMD_LINE_ARGS=%*
rem
rem :4NT_args
rem set CMD_LINE_ARGS=%$

:fail
exit /b 1


:init 

rem #########################################
rem Parsing class path and other arguments
rem #########################################

set LIBCLASSPATH=%APP_HOME%\lib\*
set CLASSPATH=%LIBCLASSPATH%;.

:loop
if not "%1"=="" (
	if "%1"=="-cp" (
		set CLASSPATH=%LIBCLASSPATH%;%2
		shift
	) else if "%1"=="-classpath" (
		set CLASSPATH=%LIBCLASSPATH%;%2
		shift
	) else (
		set CMD_LINE_ARGS=%CMD_LINE_ARGS% "%1"
		shift
	)
	shift
	goto loop
)





