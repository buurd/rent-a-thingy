REM gui-proxy
cd gui-proxy
start "gui-proxy" lein ring server-headless
cd ..

REM register-user
cd register-user
start "register-user" lein ring server-headless
cd .. 
 
cd user-info
start "user-info" lein ring server-headless
cd ..  
 
