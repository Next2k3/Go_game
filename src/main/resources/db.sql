-- Tworzenie bazy danych
CREATE DATABASE IF NOT EXISTS test;
USE test;

-- Tworzenie tabeli ruchów
CREATE TABLE IF NOT EXISTS ruchy (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    move VARCHAR(100) NOT NULL,
    kolor VARCHAR(10) NOT NULL,
    czas_ruchu DATETIME NOT NULL
);
