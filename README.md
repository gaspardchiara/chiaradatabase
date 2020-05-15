# Chiaradatabase source 
chiaradatabase is the official database for storing blob files of chiarafiles.org website

Please configure com/gaspard/you/ChiaraConfiguration  is very important to change pass,username and where the index and files will be store in your filesystem !

after that do : 
```java
mvn clean compile assembly:single

```
And run the jar on TARGET/


