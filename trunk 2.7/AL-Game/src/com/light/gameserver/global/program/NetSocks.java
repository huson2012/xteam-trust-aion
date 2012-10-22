/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.light.gameserver.global.program;

import com.light.gameserver.global.additions.MessagerAddition;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Stan
 */
public class NetSocks {
    static final int port = 8036;
    private static final Logger log = LoggerFactory.getLogger(NetSocks.class);
 
        public static void init() {
            
          {
           try
              {
         InetAddress thisIp =InetAddress.getLocalHost();
         ServerSocket ss=new ServerSocket(1800);
         while(true)
         {
         Socket s=ss.accept();
         log.info("[Announce]From:"+thisIp.getHostAddress());
         BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
         //System.out.println(br.readLine());
         
         MessagerAddition.announceAll("[Global]:"+br.readLine(), 0);
         PrintWriter wr=new PrintWriter(new OutputStreamWriter(s.getOutputStream()),true);
         wr.println("Welcome to Socket Programming");
         }
}
     catch(Exception e){
    System.out.println(e);
     }
 }
    }
 
}
