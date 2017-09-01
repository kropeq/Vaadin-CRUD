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
### Uruchamianie projektu

* Kompilacja projektu: "mvn install"
* Uruchomienie projektu na serwerze jetty: "mvn jetty:run"
* Projekt dostępny pod adresem: "http://localhost:8080/"
