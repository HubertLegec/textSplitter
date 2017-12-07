# Text Splitter

## Temat projektu - Podział tekstu na paragrafy

Opracowanie metodę podziału na paragrafy tekstu napisanego w języku naturalnym. Na potrzeby metody tekst może być przetworzony metodami NLP. Opracowaną metodę należy zaimplementować w postaci narzędzia. 
Dane wejściowe: dokument tekstowy. Dane wyjściowe tekst dokumentu z podziałem na paragrafy. Paragrafy powinny zostać zaznaczone tagami XML.
Należy przeprowadzić eksperymenty pozwalające na ocenę jakości podziału na paragrafy.
Język programowania java, scala

## Technologie
- Projekt jest napisany w Java 8.
- Interfejs graficzny jest oparty na bibliotece JavaFX.
- Do budowania i zarządzania zależnościami wykorzystany został Gradle.
- Do stworzenia sieci neuronowej wykorzystana została biblioteka Encog.
- Testy jednostkowe zostały napisane we frameworku Spock

## Algorytm podziału tekstu na paragrafy

Aplikacja będzie dokonywała podziału tekstu na paragrafy biorąc pod uwagę szereg warunków. 
Aby możliwe było sprawdzanie tych warunków tekst wejściowy musi zostać wstępnie przetworzony.

### Wstępne przetwarzanie

Za pomocą odpowiednich wyrażeń regularnych tekst zostanie podzielony na zdania i znaki znajdujące się pomiędzy zdaniami. 
Dodatkowo każde zdanie zostanie powiązane z numerem wiersza, w którym się rozpoczyna oraz kończy. 
W kolejnym kroku lista takich zdań i elementów je otaczających będzie dalej przetwarzana.

### Warunki

1. Czy jest to pierwsze zdanie w tekście.
2. Czy zdanie rozpoczyna nową linię.
3. Czy bezpośrednio przed zdaniem znajdował się znak nowej linii.
4. Czy wiersz powyżej zdania był pusty.
5. Czy bezpośrednio przed zdaniem znajdował się znak tabulacji.
6. Czy wiersz poprzedzający ten, w którym rozpoczyna się zdanie był krótszy od poprzedzającego go wiersza.
7. Czy zdanie to element listy (punktowanej lub numerowanej).
8. Czy zdanie rozpoczyna się frazą charakterystyczną dla nowego paragrafu (przygotowana zostanie lista takich fraz dla języka angielskiego)
9. W razie potrzeby dodatkowe warunki...


