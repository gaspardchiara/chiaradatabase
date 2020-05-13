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
import com.gaspard.data.filexlist;
import com.gaspard.data.indexlist;
import com.gaspard.data.templatefiles;
import com.gaspard.you.ChiaraConfiguration;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author gaspard
 */
public class indexoperation {
 
    public void createfirstindex () throws IOException {
    
    ChiaraConfiguration config = new ChiaraConfiguration();
File theindex = new File(config.getDatastorage()+"/INDEX");
    if (!theindex.exists()) {
indexlist firstindex = new indexlist();
     File createindex = new File(config.getDatastorage()+"/INDEX");
     createindex.createNewFile();
     
         ObjectOutputStream object = new   ObjectOutputStream(new FileOutputStream(config.getDatastorage()+"/INDEX"));
  object.writeObject(firstindex);
  object.close();


        
    }
    
    }
    public void deleteindex () {
    
    ChiaraConfiguration config = new ChiaraConfiguration();
File theindex = new File(config.getDatastorage()+"/INDEX");
    theindex.delete();
    return;
    
    }
    
    public indexlist getindexlistfromdisk ( ) throws IOException, ClassNotFoundException {
ChiaraConfiguration config = new ChiaraConfiguration();
File theindex = new File(config.getDatastorage()+"/INDEX");

if (theindex.exists()) {
    InputStream thedata = new FileInputStream(theindex);
   ObjectInputStream object = new   ObjectInputStream(thedata);

   return (indexlist) object.readObject();



} 

return null;


}
void indexflush (List<fileindexstruct> index) throws FileNotFoundException, IOException {
indexlist indexcontainant = new indexlist();
indexcontainant.setIndex(index);

ChiaraConfiguration config = new ChiaraConfiguration ();

ObjectOutputStream object = new   ObjectOutputStream(new FileOutputStream(config.getDatastorage()+"/INDEX"));
  object.writeObject(indexcontainant);
  object.close();

}

public templatefiles addfilesindisk (add filenew) throws FileNotFoundException, IOException {
ChiaraConfiguration config = new ChiaraConfiguration();
    UUID clustername =  UUID.randomUUID();
    
templatefiles template = new templatefiles();

template.clustername = clustername.toString();
template.file = filenew.file;
template.whereinlist = 0;
filexlist clusternew = new filexlist();
 List<templatefiles> index = clusternew.getIndex();  
index.add(template);

clusternew.setIndex(index);
File createfile = new File(config.getDatastorage()+"/"+clustername.toString()+"");
createfile.createNewFile();
ObjectOutputStream object = new   ObjectOutputStream(new FileOutputStream(config.getDatastorage()+"/"+clustername.toString()+""));
  object.writeObject(clusternew);
  object.close();
return template;
}
public templatefiles removefileinclusterandecrement (fileindexstruct filedescriptor) throws FileNotFoundException, IOException, ClassNotFoundException {
ChiaraConfiguration config =new  ChiaraConfiguration();
       File clusterfiles = new File(config.getDatastorage()+"/"+filedescriptor.dataclustername+"");
 
        InputStream thedata = new FileInputStream(clusterfiles);
   ObjectInputStream object = new   ObjectInputStream(thedata);
   filexlist clusterlist = (filexlist) object.readObject();
   List<templatefiles> index = clusterlist.getIndex();
   
 
   templatefiles deletedfromindex = index.get(filedescriptor.indexinfileslist-1 );
   
   
   index.remove(filedescriptor.indexinfileslist);
   clusterlist.setIndex(index);
   
   if (index.isEmpty()) {
   
   clusterfiles.delete();
   
   return deletedfromindex;
   
   } else {
   
   // decrement where in list all entries
   for (int g =0;g<index.size();g++) {
   
       
   templatefiles tmp = index.get(g);
   if(tmp.whereinlist == 0) {
   // if zero do nothing beause an entries can't be negative 
   } else {
   tmp.whereinlist--;
   }
   
   }
      clusterlist.setIndex(index);
ObjectOutputStream writedata = new   ObjectOutputStream(new FileOutputStream(config.getDatastorage()+"/"+filedescriptor.dataclustername+""));
  writedata.writeObject(clusterlist);
  writedata.close();
      
   
   }
   
   
   return  deletedfromindex;
    
}
public templatefiles addfilestoexistantcluster(fileindexstruct filedescriptor,add filenew,String name) throws IOException, ClassNotFoundException {

    templatefiles files = new templatefiles();
ChiaraConfiguration config = new ChiaraConfiguration();
    UUID clustername =  UUID.randomUUID();
    File clusterfiles = new File(config.getDatastorage()+"/"+name+"");

 
if(filenew.getFile().length + clusterfiles.length() >=2000000000) {

return addfilesindisk(filenew);
}

     InputStream thedata = new FileInputStream(clusterfiles);
   ObjectInputStream object = new   ObjectInputStream(thedata);
   filexlist clusterlist = (filexlist) object.readObject();
   List<templatefiles> index = clusterlist.getIndex();
   
   templatefiles fichieafterlist = new templatefiles ();
   fichieafterlist.file = filenew.file;
   fichieafterlist.clustername = filenew.name;
   fichieafterlist.whereinlist = index.size()+1;
   index.add(fichieafterlist);
   clusterlist.setIndex(index);
   ObjectOutputStream writedata = new   ObjectOutputStream(new FileOutputStream(config.getDatastorage()+"/"+name+""));
  writedata.writeObject(clusterlist);
  writedata.close();
   
  files.clustername = name;
   files.whereinlist = fichieafterlist.whereinlist;
   return files;

}

public templatefiles searchforavailablecluster (List<fileindexstruct> index,add filenew,fileindexstruct struct) throws IOException, ClassNotFoundException {

    // first check if non null 
    if(index.isEmpty()) {
    
        return addfilesindisk(filenew);
   }
    
// IF NOT SEARCH if files with the sema elength exist and yes go write it on the same cluster 

for (int x = 0;x<index.size();x++) {
fileindexstruct filedescriptor = index.get(x);

if (filedescriptor.size >= struct.size) {

return addfilestoexistantcluster(struct,filenew,filedescriptor.dataclustername) ;

}


}
        return addfilesindisk(filenew);



}





// CORRECT BUG, A IMPORTANT SECURITY BREACH ARE HERE !!!!!!!!!!!!!!!!!!!
public void writenewindewdata ( fileindexstruct struct,add filenew) throws IOException, ClassNotFoundException{

    if(filenew.file.length>=2000000000) {
    
    System.out.println("Chiaradatabase: Chiaradatabase is in beta so , we don't support 2GB> and more, you can change it on indexoperation line 185 ");
    return;
    }
    
indexlist theindex = getindexlistfromdisk();

if(theindex == null) {

createfirstindex();


writenewindewdata(struct,filenew); 
// at this tilme it will be ok

} else {
    
    
    
//templatefiles infofiles =     addfilesindisk(filenew);

List<fileindexstruct> index =theindex.getIndex();
templatefiles infofiles =     searchforavailablecluster(index,filenew,struct);

fileindexstruct filenetryfinal  = new fileindexstruct();

filenetryfinal.setName(struct.name);

filenetryfinal.setId(struct.id);
filenetryfinal.dataclustername = infofiles.clustername;
filenetryfinal.indexinfileslist = infofiles.whereinlist;
filenetryfinal.size = struct.size;
Date entrydata = new Date();
filenetryfinal.setAdddate(entrydata);

index.add(filenetryfinal);
indexflush(index);




}
    


}

    
    
    
}
