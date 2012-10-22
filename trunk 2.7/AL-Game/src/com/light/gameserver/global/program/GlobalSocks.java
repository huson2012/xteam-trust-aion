package com.light.gameserver.global.program;

import com.light.gameserver.global.additions.MessagerAddition;
import java.lang.*;
import java.io.*;
import java.net.*;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalSocks {
   private static final Logger log = LoggerFactory.getLogger(GlobalSocks.class);
   
   public static void init() {
     // Объявляем переменные
     // Объявляем сервер сокет и клиент сокет
     // Объявляем входной и выходной потоки
       ServerSocket echoServer = null;
       String line;
       DataInputStream is;
       PrintStream os;
       Socket clientSocket = null;
       // Пытаемся открыть 8765 порт
       try {
           echoServer = new ServerSocket(8765);
       }
       catch (IOException e) {
           log.info("[Global] Error in Socks");
       }
       // Создаем объект сокета из ServerSocket, чтобы слушать и принимать соединения.
       // Открываем входные и выходные потоки
       try {
           clientSocket = echoServer.accept();
           is = new DataInputStream(clientSocket.getInputStream());
           os = new PrintStream(clientSocket.getOutputStream());
       
       // Принятые данные отправляем обратно клиенту
       while (true) {
           line = is.readLine();
           os.println(line);
           MessagerAddition.announceAll("[Global]:"+ line, 0);
           log.info("[Global]Entering String :" + line);
       }
       }
       catch (IOException e) {
    log.info("[Global]Socks: Error in Socket(Line)");
}
}
}
