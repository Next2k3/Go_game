# Go Game Client-Server Application

Aplikacja umożliwia grę w grę Go w trybie klient-serwer. Klient komunikuje się z serwerem, który zarządza stanem gry i logiką. Gra opiera się na klasycznych zasadach gry Go, gdzie celem jest zdobycie większej powierzchni planszy poprzez otoczenie kamieni przeciwnika.

## Technologie

- **Java** - Język programowania użyty do stworzenia aplikacji.
- **JavaFX** - Użyte do stworzenia interfejsu użytkownika (jeśli dotyczy).
- **Socket Programming** - Protokół komunikacyjny klient-serwer.
- **Maven** (opcjonalnie) - Użyty do zarządzania zależnościami.

## Użycie

- Po uruchomieniu aplikacji klient łączy się z serwerem.
- Gracze mogą ustawić wielkość planszy i rozpocząć grę.
- Gra odbywa się na planszy o standardowym rozmiarze 19x19, z możliwością zmiany w przyszłości.
- Po zakończeniu tury, serwer informuje klientów o aktualnym stanie gry.

## Funkcjonalności

- **Serwer:**
- Obsługuje wielu graczy.
- Przechowuje stan gry (plansza, tura).
- Przeprowadza logikę gry (sprawdzanie ruchów, zdobywanie terenów).

- **Klient:**
- Wyświetla planszę i umożliwia wybór miejsca na ruch.
- Wysyła ruchy do serwera.
- Odbiera informacje o stanie gry od serwera.

## Przyszłe funkcje

- Dodanie obsługi różnych rozmiarów planszy (np. 13x13, 9x9).
- Wprowadzenie logiki przyznawania punktów i kończenia gry.
- Możliwość gry z AI.

