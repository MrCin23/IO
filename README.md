# IO
## Projekt z przedmiotu Inżynieria Oprogramowania semestr zimowy 2024/25

Każdy moduł ma własną gałąź odpowiadającą numerowi przydzielonego modułu.

1.	Zarządzanie zgłoszeniami potrzeb przez poszkodowanych – Kamil Dzierżawski, Mikołaj Kubik
2.	Śledzenie i zarządzanie zasobami – Wojciech Florczak, Piotr Lewkowicz
3.	Komunikacja pomiędzy aktorami – Wojciech Michalak, Bartosz Podemski
4.	Zarządzanie darczyńcami i darowiznami – Wiktoria Bilecka, Grzegorz Janasek
5.	Zarządzanie wolontariuszami – Franciszek Pietrzycki, Krzysztof Dusza
6.	Raportowanie i monitorowanie działań – Wiktor Idziński, Bartosz Mazurkiewicz
7.	Integracja z mapami – Piotr Sokoliński, Marcin Targoński
8.	Bezpieczeństwo i uwierzytelnianie – Aleksandra Bryja, Kevin Makarewicz
9.	Powiadomienia o niebezpieczeństwie w oparciu o lokalizację – Mateusz Giełczyński, Jakub Kubiś

# Instrukcja dla debila

## Praca z repozytorium

- Pobierz najnowszą wersję głównego branchu (main):  
> git pull origin main

- Utwórz nowy branch dla swojej zmiany i przełącz się na niego:  
> git checkout -b nazwa brancha

- Zanim zrobisz commita oczywiście sprawdź czy się projekt buduje.

- Dodaj zmiany.
> git add .  
> git commit -m "opis"

- Wypchnij brancha.
> git push origin nazwa brancha

- Jakiekolwiek zmiany wprowadzamy z użyciem gałęzi.

- Kiedy chcecie scalić swojego brancha z main:
  - Przełącz się na main:
  > git checkout main

  - Pobierz najnowsze zmiany z main:
  > git pull origin main

  - Sprawdź czy projekt się buduje.

  - Scal swojego brancha z main:
  > git merge nazwa brancha

  - Wypchnij zaktualizowanego main'a:
  > git push origin main

## Baza danych

- Aby dodać lub zmienić własne tabele w bazie danych, modyfikujemy odpowiednie pliki w katalogach initdata bądź initstructure.

- *docker-compose.yaml* jest skonfigurowany w sposób umożliwiający dowolne testowanie na bazie danych skph_test (tworzone są dwa kontenery). Jest to baza BEZSTANOWA, czyli usunięcie jej spowoduje usunięcie danych (w przeciwieństwie do głównej bazy danych).

- Plik db_password.txt należy umieścić w katalogu /sql

## Testy

- Testy nie są wymagane.

- Jeżeli któraś grupa chce je robić, należy stworzyć plik z nazwą modułu lub katalog z nazwą modułu w której zawarte będą testy (wtedy nazewnictwo jest dowolne)

- Przed mergem z mainem należy się upewnić że testy przechodzą pozytywnie