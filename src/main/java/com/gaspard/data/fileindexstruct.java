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
package com.gaspard.data;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author gaspard
 */
public class fileindexstruct implements Serializable {
    /*
    here is the index struct of all the files is save on the database, 
    
    
    */
  public  Date adddate;
   public UUID id;
   public int size;
    public String name;
    public String dataclustername; //-> here is where is stored the file in where data cluster is ?
public int indexinfileslist; // where is where the files is put on the lsit ?
   static final long serialVersionUID = 42L;

    public Date getAdddate() {
        return adddate;
    }

    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataclustername() {
        return dataclustername;
    }

    public void setDataclustername(String dataclustername) {
        this.dataclustername = dataclustername;
    }
    
    
}
