DROP DATABASE IF EXISTS GuessNumberDB;
CREATE DATABASE GuessNumberDB;

USE GuessNumberDB;

CREATE TABLE Game(
gameId INT PRIMARY KEY AUTO_INCREMENT,
daNumber CHAR(4),
inProgress bool
);

CREATE TABLE Round(
roundID INT PRIMARY KEY AUTO_INCREMENT,
guessNum CHAR(4),
timeOfRound timeStamp,
gameId int,
result varchar(15),
Foreign Key (gameId) references Game(gameId)
);