# PROJECT_UMG_NETWORKS1 Use of an existing protocol

## wsXMPP project functions
1. Start chats one to one.
2. Create new user account in the server.
3. Delete current account on the server.
4. Show connected users.
5. Recieve files.
6. Create a roster chat group.
7. Send files.
8. Create a multi user chat group.

## wsXMPP project characteristics
1. Use of the existing protocol XMPP.
2. Direct connection with the server.
3. Account management.
4. Current user basic account information and rest of users as well.
5. Creates chats, shows when the other user send a message, also when is typing and then the receive the message.
6. The project is able to create different type of chats.

## Use of wsXMPP project
* Download the Spring Tool Suite from [https://spring.io/tools3/sts/all
](https://spring.io/tools3/sts/all
)
* Download the maven binary zip apache-maven-3.6.1-bin.zip from 
[https://maven.apache.org/download.cgi 
](https://maven.apache.org/download.cgi 
)
* Extract maven binaries from zip file.
* Once extraction is completed we have to create a new environment variable.
    * Right clic on This PC.
    * Clic on properties.
    * Clic on advanced options.
    * When the new window is open then, clic on environment variables.
    * Clic on New in the part o system variables, it will show a new window, there we enter the name of the variable which is M2_HOME and the value of the variable which is the path where maven binaries were extracted.
    * Clic the OK button.
* Add the new environment variable to the PATH variable.
    * Search the variable with the name PATH.
    * Clic edit in system variables, it will open a new window where we have to add the new path.
    * Add ;%M2_HOME%\bin at the end of the value of the variable.

    ```bash
    ;%M2_HOME%\bin
    ```
    * Clic on OK in all the windows that where prompted.
* Verify maven is installed
    * Windows + R, then type cmd and press Enter.
    * Type in the command line mvn -v , this command is the equivalent to mvn -version or mvn --version.
    ```bash
    mvn -v
    ```
    * The cmd will return the maven's version if it was already installed.
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
    * Browse to the location where the project is located, then clic OK.
    * Clic on Finish.
* Open the project.
    * Get into the App.java file.
    * Right clic, then stand on Run As.
    * Clic on Java Application.
