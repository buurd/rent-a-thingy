# register-user

Delprojekt i rent-a-thingy med scopet att registrera användare.

## Database
Det behöver skapas en databas i .db med namn register-user. Följande tabell ska skapas:
CREATE TABLE "user-registration" (
  id INTEGER PRIMARY KEY,
  "registration-date"
  DATE NOT NULL,
  username VARCHAR (255) NOT NULL,
  content TEXT);
  
