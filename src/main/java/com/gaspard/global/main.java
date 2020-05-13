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

/**
 *
 * @author gaspard
 */
public class main {
    public static void mainsocket () throws IOException, InterruptedException {
mainserver maintread = new mainserver();


maintread.start();

    }
    
    
    public static void main (String argvs[]) throws IOException, InterruptedException {
    
    System.out.println("Welcome to chiaralab");
    System.out.println("we will read the configuration class and open the main socket ");
    
    mainsocket();
    }
    
}
