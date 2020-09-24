# M223: Punchclock
Dies ist die Abschlussprüfungsapplikation vom Modul 223.

## Loslegen
Folgende Schritte befolgen um loszulegen:
1. Sicherstellen, dass JDK 12 installiert und in der Umgebungsvariable `path` definiert ist.
1. Ins Verzeichnis der Applikation wechseln und über die Kommandozeile mit `./gradlew bootRun` oder `./gradlew.bat bootRun` starten
1. Unittest mit `./gradlew test` oder `./gradlew.bat test` ausführen.
1. Ein ausführbares JAR kann mit `./gradlew bootJar` oder `./gradlew.bat bootJar` erstellt werden.
5. Die Applikation kann auch ausgeführt werden direkt im IntelliJ mit ch.zli.m223.punchclock.PunchclockApplication als Main Class und im punchclock.main module mit der JDK 11.

Folgende Dienste stehen während der Ausführung im Profil `dev` zur Verfügung:
- REST-Schnittstelle der Applikation: http://localhost:8081
- Der Default-User ist admin mit dem Passwort admin.
- Dashboard der H2 Datenbank: http://localhost:8081/h2-console

## Applikation
In dieser Applikation können Zeiten erfasst werden von Mitarbeitern.
Es gibt Benutzer, Einträge, Kategorien und Rollen.
<br>Einträge und Users können im Frontend erstellt und verwaltet werden, Kategorien und Rollen müssen über manuell gefertigte Requests angelegt werden, jedoch sind standardmässig einige Kategorien und Rollen schon integriert.
