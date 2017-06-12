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
