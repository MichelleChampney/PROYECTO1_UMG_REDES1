# PROJECT_UMG_NETWORKS1 Use of an existing protocol

# wsXMPP project functions
1. Create chat one to one.
2. Create new user in the server.
3. Delete current account on the server.
4. Show connected users.
5. Receive file.
6. Create a roster chat group.
7. Send file.
8. Create a multi user chat group.

# wsXMPP project characteristics
1. Use of the existing protocol XMPP.
2. Direct connection with the server.
3. Manage of the account.
4. Basic information of the current user and the users in the server.
5. Creates chats, shows when the other user send a message as typing and then the receive the message.
6. Is able to create to different type of chats.

# USE wsXMPP project
1. Download the Spring Tool Suite from https://spring.io/tools3/sts/all
2. Download the maven binary zip apache-maven-3.6.1-bin.zip from https://maven.apache.org/download.cgi 
3. Decompile the maven binary zip.
4. Once it´s decompile we have to create an environment variable y the local disk C.
    4.1. Right clic on This PC.
    4.2. Clic on properties.
    4.3. Clic on advanced options.
    4.4. When the new window is open then, clic on environment variables.
    4.5. Clic on New in the part o systema variables, it will show a new window there we enter the name of the variable which is M2_HOME and the value of the variable which is the path were the maven zip was decompiled.
    4.6 Clic on OK in the window.
5. Add the new environment variable to the variable PATH.
    5.1. Search the variable with the name PATH.
    5.2. Clic edit in system variables, it wil open a new window were we have to add the new path.
    5.3. Add ;%M2_HOME%\bin at the end of the value of the variable.
    5.4. Clic on OK in all the windows thara ere prompted.
6. Verify maven is install
    6.1. Windows + R, then write cmd and press Enter.
    6.2. Write in the command line mvn -v , this command is te equivalent to mvn -version or mvn --version.
    6.3. The cmd will return the version of maven if it was already installed.
7. Download the project from git.
8. To open the project in STS.
    8.1. Open the STS and clic on the option file.
    8.2. Clic in import, search for the option Maven -> Existing Maven Projects.
    8.3. Browse to the location were the project is located, then clic OK.
    8.4. Clic on Finish.
9. Open the project.
    9.1. Get into the App.java file.
    9.2. Right clic, then stand on Run As.
    9.3. Clic on Java Application.
