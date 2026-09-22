# ParcelPilot

ParcelPilot er et lite konsollsystem for administrasjon av leveranser. Prosjektet er laget som et pet-prosjekt for en portefølje: domenet er realistisk nok til å vise god objektorientert programmering, samtidig som koden er enkel å forstå og kjøre uten database eller eksterne tjenester.

## Funksjoner

- opprette leveranser med adresser, koordinater, vekt, størrelse og prioritet;
- beregne leveringspris basert på avstand, hastighet og om pakken er skjør;
- automatisk tildele den nærmeste ledige kureren som kan frakte pakken;
- kontrollere leveransens livssyklus: `CREATED → ASSIGNED → PICKED_UP → IN_TRANSIT → DELIVERED`;
- frigjøre kureren etter levering eller kansellering;
- lagre data gjennom in-memory-repositorier som enkelt kan erstattes med en PostgreSQL-adapter.

## Arkitektur

Prosjektet er delt inn i lag uten å koble forretningslogikken direkte til lagringen:

```text
application       starter demonstrasjonsscenarioet
domain/model      domeneobjekter og value objects
domain/service    applikasjonens use cases
domain/strategy   utskiftbare regler for pris og kurertildeling
domain/port       grensesnitt for repositorier
infrastructure    in-memory-implementasjoner av repositorier
```

Prosjektet bruker blant annet:

- innkapsling av forretningsregler i `Delivery`, `Parcel`, `Money` og `Courier`;
- Strategy Pattern for prisberegning og valg av kurér;
- Dependency Inversion gjennom grensesnitt for repositorier og strategier;
- injeksjon av `Clock`, slik at koden blir enkel å teste;
- immutable value objects for adresser, koordinater og penger;
- enhetstester av forretningsscenarier med JUnit 5.

## Kjøre prosjektet

Krav: Java 21+ og Maven 3.9+.

```bash
mvn clean verify
mvn package
java -jar target/parcel-pilot-1.0.0.jar
```

Eksempel på output:

```text
ParcelPilot demo
Delivery: 8f23...
Route: Oslo, Karl Johans gate 1 -> Oslo, Torggata 12, apt. 4
Courier: Иван
Price: 9.09 EUR
Status: DELIVERED
```

## Mulige videreutviklinger

- REST API med Spring Boot;
- PostgreSQL-repositorier og Flyway-migrasjoner;
- autentisering for dispatcher og kunde;
- beregning av forventet leveringstid basert på veinett;
- varsler når leveransestatus endres;
- Docker Compose, Testcontainers og integrasjonstester.

## Lisens

MIT
