##  Projekt z użyciem frameworka Vaadin

----
### Cel projektu

Poznanie podstawowych możliwości frameworka Vaadin. W tym celu stworzyłem rejestrację, logowanie, obsługę pól, komunikaty poprawności, rozpoznawanie sesji oraz CRUD( create, read, update, delete ) do tworzenia listy startowej skoczków narciarskich.

----
### Wykorzystane narzędzia

* apache maven 3.3.9
* eclipse jee mars 1

----
### Uprawnienia aktorów

* _administrator_ - zgłaszanie i usuwanie zawodników z listy startowej
* _użytkownik_ - przeglądanie listy startowej i zakładanie kont

----
### Funkcjonalność

* Rejestracja
* Logowanie
* Sprawdzanie sesji
* Wyświetlanie listy startowej zawodników
* Zarządzanie listą przez administratora( CRUD )

----
### Uruchamianie projektu

* Kompilacja projektu: "mvn install"
* Uruchomienie projektu na serwerze jetty: "mvn jetty:run"
* Projekt dostępny pod adresem: "http://localhost:8080/"

---
### Prezentacja aplikacji

##### Rejestracja
Formularz z możliwościa założenia konta. Po rejestracji zostajemy przekierowani do logowania. Pola formularza są sprawdzane pod względem poprawności, między innymi czy wpisane hasło i powtórzone hasło są takie same.

![Rejestracja](https://github.com/kropeq/Vaadin-CRUD/blob/master/screens/rejestracja.png)

##### Logowanie i sesja
Po rejestracji musimy się zalogować. Na górze jest zakładka, w której możemy sprawdzić czy jesteśmy zapisani w sesji( nazwa użytkownika po zalogowaniu ).

![Logowanie](https://github.com/kropeq/Vaadin-CRUD/blob/master/screens/sesja.png)

##### Widok użytkownika
Użytkownik po zalogowaniu może obejrzeć listę startową. Po zalogowaniu sesja jest już rozpoznawana.

![Widok użytkownika](https://github.com/kropeq/Vaadin-CRUD/blob/master/screens/widok_uzytkownika.png)

##### Widok administratora
Administrator po zalogowaniu może zarządzać listą startową. Dodatkowo, tabela z zawodnikami jest klikalna, więc po wciśnięciu odpowiedniego rzędu dane zostaną przeniesione do formularza, co znacznie usprawnia zarządzanie listą. Dane można również zmienić bezpośrednio w tabeli i wcisnąć _Update_.

![Widok administratora](https://github.com/kropeq/Vaadin-CRUD/blob/master/screens/widok_administratora.png)