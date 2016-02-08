REM gui-proxy
cd gui-proxy
start "gui-proxy" lein ring server-headless
cd ..

REM register-user
cd register-user
start "register-user" lein ring server-headless
cd .. 

REM user-info
cd user-info
start "user-info" lein ring server-headless
cd ..  

REM register-tool
cd register-tool
start "register-tool" lein ring server-headless
cd .. 

REM current-user
cd current-user
start "current-user" lein ring server-headless
cd .. 
 
