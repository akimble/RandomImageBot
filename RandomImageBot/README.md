# RandomImageBot
A Discord bot coded in Java that uses the Discord4J unofficial API to listen for commands (in the form of messages /test, /random, etc.) and sends feedback. The feedback ranges from sending a message back, uploading images, or uploading files. Bot admins can be added using the bot's token with /admin [TOKEN] to have access to various commands (/sleep and /addFolder). Using /addFolder [KEYWORD] [PATH] a bot admin can add a keyword and folder that the bot will randomly upload images from when given the /pic [KEYWORD] command in the channel.

### Commands:

  - /test
    - Return a test message
    
  - /random
    - Return a random file from a hardcoded folder
    
  - /pic [KEYWORD]
    - Return a random file from a folder that a Bot Admin has added using the /addFolder command

  - /admin [TOKEN]
    - Make user an "admin" (can use /addFolder and /sleep)
    
  - #### Admins Only:
    
  - /addFolder [FOLDER NAME] "C:/.../..."
    - Add folder path to /pic [FOLDER NAME]
    
  - /sleep
    - Make bot go offline

### Additional Information:

  - There are two text files (admins.txt and keywordToPath.txt) in /RandomImageBot/src/main/resources/text files/ that hold Bot Admin userIDs and /pic KEYWORD and PATH entries, respectively.
    Delete these or change them manually if desired to remove/change Bot Admins or /pic KEYWORDS and PATHS. For keywordToPath, if the KEYWORD is the same in more than one line, the PATH in
    the line closet to the bottom is read in for that KEYWORD.
    
  - Bot Admins can peruse and upload unprotected files from the hosts PC. Be wary of who you add to this role (ideally only the host should be a Bot Admin).
  
  - Before creating a JAR file of the project, change the path names in readFile, readHashMapFile, and writeToFile in BotUtils to `".\\"` (the current path is just for easier testing in Eclipse).
    Also create text files for admins.txt and keywordToPath.txt and place them IN the same folder as the JAR file (wherever you put it). Also make sure the folder they are in allows
    you to write into it because the JAR file needs to create a modules folder due to Maven (it's not used but will throw an error if it can't create it). After that, you can just "cd" into
    the folder in CMD and type "java -jar yourJar.jar yourBotToken" to run the RandomImageBot program.