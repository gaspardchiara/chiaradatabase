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

/**
 *
 * @author gaspard
 */
public class handcheck implements Serializable {
    public String user;
    public String password; // in shasha 256
    
   public long handcheck; // 0 to check password // 1 to send data // 2 to read data and resend it /:3to delete
   public add filepayload;
   public getfile searchfile;
   public delete delfile;
   static final long serialVersionUID = 42L;

    public delete getDelfile() {
        return delfile;
    }

    public void setDelfile(delete delfile) {
        this.delfile = delfile;
    }

    public getfile getSearchfile() {
        return searchfile;
    }

    public void setSearchfile(getfile searchfile) {
        this.searchfile = searchfile;
    }

    public add getFilepayload() {
        return filepayload;
    }

    public void setFilepayload(add filepayload) {
        this.filepayload = filepayload;
    }

   

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getHandcheck() {
        return handcheck;
    }

    public void setHandcheck(long handcheck) {
        this.handcheck = handcheck;
    }
   
}
