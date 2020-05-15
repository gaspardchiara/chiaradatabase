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

import com.gaspard.data.add;
import com.gaspard.data.delete;
import com.gaspard.data.fileindexstruct;
import com.gaspard.data.filexlist;
import com.gaspard.data.getfile;
import com.gaspard.data.getfilerespond;
import com.gaspard.data.handcheck;
import com.gaspard.data.indexlist;
import com.gaspard.data.respond;
import com.gaspard.data.templatefiles;
import com.gaspard.database.databasewrite;
import com.gaspard.database.indexoperation;
import com.gaspard.you.ChiaraConfiguration;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author gaspard
 */
public class parse {
    
    public void respondtoerror (Socket request,Object o) throws IOException {
    
    ObjectOutputStream in = new ObjectOutputStream(request.getOutputStream());
    in.writeObject(o);
    in.flush();
    in.close();

    request.close();
    return;
    
    
    }
    
    public void goodpassword (Socket request) throws IOException {
    
    respond gorespond = new respond();
    
    gorespond.setMessage("Chiaradatabase: password OK ! ");
    gorespond.setStatus(0);
    respondtoerror(request,gorespond);
    
    }
    
    public void waitdatadelete(Socket request,handcheck data) throws IOException, ClassNotFoundException {
    delete delfile = data.getDelfile();

ChiaraConfiguration config = new ChiaraConfiguration();
if (checkstringifequal( delfile.password,config.getsha256pass()) && checkstringifequal( delfile.user,config.getUser()) ) {

indexoperation operationcluster = new indexoperation();
fileindexstruct filestruct =     searchfileindexandeleteit(delfile.id);

if(filestruct.id == null) {
respond filerespond = new respond();
filerespond.setMessage("The file doesn't exist in database index");
filerespond.setStatus(1);
respondtoerror(request,filerespond);
return ;
} else {
respond filerespond = new respond();
filerespond.setMessage("Chiaradatabase: Ok, file will be deleted soon");
filerespond.setStatus(1);
respondtoerror(request,filerespond);
templatefiles deletedfilefromcluster  = operationcluster.removefileinclusterandecrement(filestruct);




}




} else {

respond filerespond = new respond();
filerespond.setMessage("Bad password or user id");
filerespond.setStatus(1);
respondtoerror(request,filerespond);
}
     
    
    }
   public boolean checkpass (String password,String name) {
      ChiaraConfiguration check = new ChiaraConfiguration();
    
    if ( password.equals(check.getsha256pass()) && name.equals(check.getUser())  ) {
    
       return true;

    
    }
       
   
   return false;
   
   }
    
    
    
    public void waitdataup (Socket socket,handcheck data) throws IOException, ClassNotFoundException {
  Calendar rightNow = Calendar.getInstance();
int hour = rightNow.get(Calendar.MINUTE);
int ticktock = hour+2;
add filestruct = (add)data.getFilepayload();
System.out.println("In wait data"+filestruct.name);

ChiaraConfiguration config = new ChiaraConfiguration();
if (checkstringifequal( filestruct.password,config.getsha256pass()) && checkstringifequal( filestruct.user,config.getUser()) ) {
    
 
    sendok(socket);
 
        socket.close();
databasewrite p = new databasewrite();

p.writedata(filestruct);
} else {
respond error = new respond();

error.setMessage("Chiaradatabase : The password or user is not valid pleasy retry or modify chiaraconfig");
 error.setStatus(1);
        respondtoerror(socket,error);
        return;



}
        
    }
    
    
    public void parsetyperequest(Socket request,handcheck datarequested) throws IOException, ClassNotFoundException {
    System.out.println(datarequested.handcheck);
    switch ((int)datarequested.handcheck) {
    
        case 0 : goodpassword(request);      break; // the password is ok send ok request !
        case 1:  waitdataup(request, datarequested);     break;
        case 2 : getdata(request,datarequested);     break;
        case 3 :  waitdatadelete(request,datarequested);    break; // go delete
           }
        
        
    
    }
    
   public boolean checkstringifequal (String s1,String s2) {
   
   for (int x = 0;x<s1.length();x++)
   {
   char test = s1.charAt(x);
   char test2 = s2.charAt(x);

   if(test!=test2) {
   
   return false;
   }
   
   
   }
   
   return true;
   } 
    
    
    public void analysereq(Socket request,handcheck datarequested) throws IOException, ClassNotFoundException {
    
    // first you need to check the password and username
    
    ChiaraConfiguration check = new ChiaraConfiguration();
    System.out.println(    checkstringifequal(datarequested.password,check.getsha256pass()));
    if ( checkstringifequal(datarequested.password,check.getsha256pass()) && checkstringifequal(datarequested.user,check.getUser())  ) {
    
    
    
    System.out.println("ok mdp ");
    parsetyperequest(request,datarequested);
    
    } else {
        System.out.println("bas padd or username "+datarequested.password+" "+datarequested.user+"");
        System.out.println("chiara config noprmalement "+check.getUser()+" "+check.getsha256pass()+"");
respond error = new respond();

error.setMessage("Chiaradatabase : The password or user is not valid pleasy retry or modify chiaraconfig");
 error.setStatus(1);
        respondtoerror(request,error);
        
   return ;
    }
    
    
    
    }

    private void sendok(Socket socket) throws IOException {

    respond gorespond = new respond();
    
    gorespond.setMessage("Chiaradatabase: password OK ! ");
    gorespond.setStatus(0);
     ObjectOutputStream in = new ObjectOutputStream(socket.getOutputStream());
    in.writeObject(gorespond);
    in.flush();
    }

 public fileindexstruct searchfileindexandeleteit (UUID fileid) throws IOException, ClassNotFoundException {
  ChiaraConfiguration config = new ChiaraConfiguration();
          indexoperation x  = new indexoperation();
        indexlist theindex = x.getindexlistfromdisk();
List<fileindexstruct> index = theindex.getIndex();
    fileindexstruct tmp = null;

if(index.isEmpty()) {

return new fileindexstruct();
}
int valid = 0;
    for (int g = 0;g<index.size();g++) {
        tmp = index.get(g);
                System.out.println("dans boucle est "+tmp.id+"");

    if (tmp.id.equals(fileid)) {
valid =1;
    index.remove(g);
    
    
    }
    
    
    
    }
if(valid !=1) {


return new fileindexstruct();
}
    theindex.setIndex(index);

if(index.isEmpty()) {
x.deleteindex();
return tmp;
}
// go turn around to decrement the member of the same cluster 
 for (int g = 0;g<index.size();g++) {
        fileindexstruct testmember = index.get(g);
 if(testmember.dataclustername.equals(tmp.dataclustername)){
        
     if(testmember.indexinfileslist == 0) {
     // do nothing 
     
     } else {
  testmember.indexinfileslist--;
     }
     
     index.set(g, testmember);
     
  
     
        
 }
 }

  
 ObjectOutputStream object = new   ObjectOutputStream(new FileOutputStream(config.getDatastorage()+"/INDEX"));
  object.writeObject(theindex);
  object.close();

    return  tmp;
    }
    
    
    
    public fileindexstruct searchfileindex (UUID fileid) throws IOException, ClassNotFoundException {
   indexoperation x  = new indexoperation();
        indexlist theindex = x.getindexlistfromdisk();
List<fileindexstruct> index = theindex.getIndex();
if(index.isEmpty()) {

return new fileindexstruct();
}

    for (int g = 0;g<index.size();g++) {
    fileindexstruct tmp = index.get(g);
System.out.println("dans boucle est "+tmp.id+"");
    if (tmp.id.equals(fileid)) {
    
    return tmp;
    
    }
    
    
    
    }





  


    return new fileindexstruct();
    }
    
    private void getdata(Socket request, handcheck datarequested) throws IOException, ClassNotFoundException {

getfile researchfile = datarequested.getSearchfile();

ChiaraConfiguration config = new ChiaraConfiguration();
if (checkstringifequal( researchfile.password,config.getsha256pass()) && checkstringifequal( researchfile.user,config.getUser()) ) {


fileindexstruct filestruct =     searchfileindex(researchfile.id);
if(filestruct.id == null) {
getfilerespond filerespond = new getfilerespond();
filerespond.setMessage("The file doesn't exist in database index");
filerespond.setStatus(1);
respondtoerror(request,filerespond);
return ;
} else {

getfilerespond filerespond = getdatafromcluster(filestruct);
respondtoerror(request,filerespond);



}




} else {

getfilerespond filerespond = new getfilerespond();
filerespond.setMessage("Bad password or user id");
filerespond.setStatus(1);
respondtoerror(request,filerespond);
}

    }

    private getfilerespond getdatafromcluster(fileindexstruct filestruct) throws FileNotFoundException, IOException, ClassNotFoundException {
        getfilerespond thefile = new getfilerespond();
        ChiaraConfiguration config = new ChiaraConfiguration();
        File clusterfiles = new File(config.getDatastorage()+"/"+filestruct.dataclustername+"");
System.out.println("data cluster name "+filestruct.dataclustername+"");
     InputStream thedata = new FileInputStream(clusterfiles);
   ObjectInputStream object = new   ObjectInputStream(thedata);
   filexlist clusterlist = (filexlist) object.readObject();
   List<templatefiles> index = clusterlist.getIndex();
   
   templatefiles fichieafterlist =index.get(filestruct.indexinfileslist);
thefile.data = fichieafterlist.file;
thefile.message = "The file is here !";   
thefile.status = 0;
return thefile;

    }
    
}
