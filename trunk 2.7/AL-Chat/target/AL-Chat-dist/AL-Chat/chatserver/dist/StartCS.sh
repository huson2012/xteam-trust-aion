#!/bin/sh
java -Xms128m -Xmx128m -ea -Xbootclasspath/p:./libs/jsr166.jar -cp ./libs/*:AL-Chat.jar com.aionemu.chatserver.ChatServer
