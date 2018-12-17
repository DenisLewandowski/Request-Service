# Request Service

## 1. Wykorzystane technologie
- Java 8
- Maven
- SQLite
- DOM Parser (XML)

## 2. Baza danych
Baza zawiera jedną tabelę o nazwie „requests”.
Zawiera ona pola:
- id (INTEGER PRIMARY KEY)
- clientID (CHARACTER(6))
- requestID (INTEGER)
- name (varchar(255))
- quantity (INTEGER)
- price (REAL)
Pole „id” zostało dodane jako primary key, ponieważ przykładowe dane podane w pliku .pdf sugerowały, że pole requestId nie jest unikatowe (numery zamówień się powtarzają).

## 3. Uruchomienie
Program można zbuildować i uruchomić za pomocą Mavena.
Do tego celu został specjalnie napisany plik wsadowy „compile.bat” oraz "run.bat", lub dla użytkowników systemu linux analogicznie "compile.sh" oraz "run.sh"
