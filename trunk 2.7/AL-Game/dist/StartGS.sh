#!/bin/bash

case $1 in
noloop)
  [ -d log/ ] || mkdir log/
  [ -f log/console.log ] && mv log/console.log "log/backup/`date +%Y-%m-%d_%H-%M-%S`_console.log"
  java -Xms128m -Xmx8192m -ea -javaagent:./libs/al-commons-5716.jar -cp ./libs/*:AL-Game.jar com.light.gameserver.GameServer > log/console.log 2>&1
  echo $! > gameserver.pid
  echo "Server started!"
  ;;
*)
  ./StartGS_loop.sh &
  ;;
esac