#!/bin/sh
java -Xms8m -Xmx32m -ea -Xbootclasspath/p:./libs/jsr166.jar -cp ./libs/*:AL-Login.jar com.aionemu.loginserver.LoginServer
lspid=$!
echo ${lspid} > loginserver.pid
echo "LoginServer started!"