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

import com.gaspard.data.handcheck;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gaspard
 */
 class parserequest extends Thread {
    Socket receive;
    InputStream data;
    parserequest(Socket x,InputStream data) {
    this.receive = x;
    this.data = data;
    }
    
    
    public void run() {
    
        try {
            ObjectInputStream object = new   ObjectInputStream(receive.getInputStream());
            
            
            handcheck tmp = (handcheck) object.readObject();
            new parse().analysereq(receive, tmp);
        } catch (IOException ex) {
            Logger.getLogger(parserequest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(parserequest.class.getName()).log(Level.SEVERE, null, ex);
        }


                System.out.println("recois");

   return;
    
    
    }
    
    
}
