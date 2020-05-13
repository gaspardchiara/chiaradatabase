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
package com.gaspard.database;

import com.gaspard.data.add;
import com.gaspard.data.fileindexstruct;
import com.gaspard.you.ChiaraConfiguration;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author gaspard
 */
public class databasewrite {
  
   

    public void writedata(add filenew) throws IOException, ClassNotFoundException {
  
        fileindexstruct structindex = new fileindexstruct();
        
            structindex.id = filenew.id;
            structindex.name = filenew.name;
            structindex.size = filenew.file.length;
            structindex.adddate = new Date();
         indexoperation fileindexstruct = new indexoperation();
         fileindexstruct.writenewindewdata(structindex,filenew);
        /*
        Ici, il faudra écrire le fichier sur le nom du cluster et dans le disquer dur 
        et après remplir la structure avec tout les idnentifnats et l'écrire
        
        
        */
        
        
    
    
    
    
    }
    
    
    
    
    
}
