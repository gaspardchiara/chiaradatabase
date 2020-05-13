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
package com.gaspard.you;

import java.net.SocketImpl;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author gaspard
 */
public class ChiaraConfiguration {

    public static String ip = "127.0.0.1"; // you ip here don't support now 
    public static int port = 7895; // your server port here
    public static String user = "chiara"; // the main user
    public static String pass = "changeit"; // will we encrypted in sha 256
    public static String datastorage = "/"; // where the files will be put

    public  String getIp() {
        return ip;
    }

    public  int getPort() {
        return port;
    }

    public  String getUser() {
        return user;
    }

    public  String getPass() {
        return pass;
    }

    public  String getDatastorage() {
        return datastorage;
    }
    
    
public  String getsha256pass () {
String sha256hex = DigestUtils.sha256Hex(pass);
return sha256hex;

}
    
  

}
