# RandomImageBot
A Discord bot that uses the Discord4J unofficial API to listen for commands (in the form of messages /test, /image, etc.) and sends feedback. This can be in the form of sending a message back, uploading images, or uploading files. This bot will mainly focus on uploading one random image (although it can upload any file) from an earlier specified directory when given the /pic [KEYWORD] command in the channel.

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