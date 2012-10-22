@ECHO off
TITLE Aion Light - Game Server Console
:START
CLS
IF "%MODE%" == "" (
CALL PanelGS.bat
)
ECHO Starting Aion Lightning Game Server in %MODE% mode.
JAVA %JAVA_OPTS% -ea -javaagent:./libs/al-commons-5716.jar -cp ./libs/*;AL-Game.jar com.light.gameserver.GameServer
SET CLASSPATH=%OLDCLASSPATH%
IF ERRORLEVEL 2 GOTO START
IF ERRORLEVEL 1 GOTO ERROR
IF ERRORLEVEL 0 GOTO END
:ERROR
ECHO.
ECHO Game Server has terminated abnormaly!
ECHO.
PAUSE
EXIT
:END
ECHO.
ECHO Game Server is terminated!
ECHO.
PAUSE
EXIT