# PROJECT_UMG_NETWORKS1 Use of an existing protocol

## wsXMPP project functions
1. Create chat one to one.
2. Create new user in the server.
3. Delete current account on the server.
4. Show connected users.
5. Receive file.
6. Create a roster chat group.
7. Send file.
8. Create a multi user chat group.

## wsXMPP project characteristics
1. Use of the existing protocol XMPP.
2. Direct connection with the server.
3. Manage of the account.
4. Basic information of the current user and the users in the server.
5. Creates chats, shows when the other user send a message as typing and then the receive the message.
6. Is able to create to different type of chats.

## USE wsXMPP project
* Download the Spring Tool Suite from [https://spring.io/tools3/sts/all
](https://spring.io/tools3/sts/all
)
* Download the maven binary zip apache-maven-3.6.1-bin.zip from 
[https://maven.apache.org/download.cgi 
](https://maven.apache.org/download.cgi 
)
* Decompile the maven binary zip.
* Once it´s decompile we have to create an environment variable y the local disk C.
    * Right clic on This PC.
    * Clic on properties.
    * Clic on advanced options.
    * When the new window is open then, clic on environment variables.
    * Clic on New in the part o systema variables, it will show a new window there we enter the name of the variable which is M2_HOME and the value of the variable which is the path were the maven zip was decompiled.
    * Clic on OK in the window.
* Add the new environment variable to the variable PATH.
    * Search the variable with the name PATH.
    * Clic edit in system variables, it wil open a new window were we have to add the new path.
    * Add ;%M2_HOME%\bin at the end of the value of the variable.
    * Clic on OK in all the windows thara ere prompted.
* Verify maven is install
    * Windows + R, then write cmd and press Enter.
    * Write in the command line mvn -v , this command is te equivalent to mvn -version or mvn --version.

    ```bash
    mvn -v
    ```
    * The cmd will return the version of maven if it was already installed.
    ```bash
    PS C:\Users\aruano> mvn -v
    Apache Maven 3.3.9 (bb52d8502b132ec0a5a3f4c09453c07478323dc5; 2015-11-10T10:41:47-06:00)
    Maven home: C:\apache-maven-3.3.9-bin
    Java version: 1.8.0_101, vendor: Oracle Corporation
    Java home: C:\Java\jdk1.8.0_101\jre
    Default locale: es_GT, platform encoding: Cp1252
    OS name: "windows 10", version: "10.0", arch: "amd64", family: "dos"
    ```
* Download the project from git.
* To open the project in STS.
    * Open the STS and clic on the option file.
    * Clic in import, search for the option Maven -> Existing Maven Projects.
    * Browse to the location were the project is located, then clic OK.
    * Clic on Finish.
* Open the project.
    * Get into the App.java file.
    * Right clic, then stand on Run As.
    * Clic on Java Application.
