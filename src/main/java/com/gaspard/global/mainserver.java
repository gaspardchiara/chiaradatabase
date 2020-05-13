/*
 * Copyright (C) 2020 gaspard
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.gaspard.global;

import com.gaspard.you.ChiaraConfiguration;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gaspard
 */
public class mainserver extends Thread{
    
    public void run () {
        
        try {
            ChiaraConfiguration config = new ChiaraConfiguration ();
            
            ServerSocket server = new ServerSocket(config.getPort());
            
        //   
            
            while(true) {
            Socket thesocket = server.accept();
 if (thesocket.getReceiveBufferSize() >=1) {
 
  InputStream data = thesocket.getInputStream();
parserequest parse = new parserequest(thesocket,data);
                    parse.start();
                
 
 }        
 
 
                
            }   } catch (IOException ex) {
            Logger.getLogger(mainserver.class.getName()).log(Level.SEVERE, null, ex);
        }

    
    
    
    
    }
    
    
    
    
}
