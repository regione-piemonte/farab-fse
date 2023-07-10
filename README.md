# Prodotto
Il prodotto ***Consegna ricette in farmacia*** realizza un servizio che consente al paziente di ritirare i farmaci prescritti su ricette dematerializzate presentando al farmacista solamente la propria tessera sanitaria.

# Versione
1.0.0

# Descrizione del prodotto 
Il prodotto è composto attualmente dalle seguenti componenti 
| Componente |Descrizione  |Versione |
|--|--|--|
| [DMACONTATTI](https://github.com/regione-piemonte/farab-fse/tree/main/DMACONTATTI) | modulo per la gestione dei contatti dei cittadini | 1.0.0 |
| DMAIRIDECACHE | modulo per la gestione di una cache temporanea per l'autenticazione dei farmacisti, mediante credenziali RUPARPIEMONTE| 1.0.1 |
| FARMAB (FARMAB) | API per la gestione della scelta della farmacia abituale, l'autorizzazione della farmacia occasionale, la certificazione del device, l'elenco dei delegati e l'elenco delle ricette| 1.0.0 |


Le interfacce utente (implementate con una PWA -progressive web application) per il cittadino sono disponibili sul prodotto (SANSOL) [https://github.com/regione-piemonte/sansol/tree/main/XXXX]
La pwa richiama servizi JSON/REST disponibili sul prodotto (APISAN) [https://github.com/regione-piemonte/apisan/tree/master/XXXX]
La profilazione delle farmacie/farmacisti avviene mediante il prodotto (CONFIGURATORE OPERATORI) [https://github.com/regione-piemonte/lcce]

# Prerequisiti di sistema 

## Software
- [Apache 2.4](https://www.apache.org/)
- [RedHat JBoss EAP 6.4](https://developers.redhat.com/products/eap/download)
- [JDK Oracle 1.8](https://www.oracle.com/java/technologies/downloads/archive/) 
- [PostgreSQL 12.2](https://www.postgresql.org/download/)
- [CentOS 7.6](https://www.centos.org/)

## Dipendenze da sistemi esterni

### Sistema FSE della Regione Piemonte
Il prodotto dipende da Web Services del FSE regionale piemontese per la verifica del consenso alla consultazione da parte degli operatori sanitari.

### Sistema di autenticazione
Il sistema di autenticazione dei servizi utilizzati dai gestionali delle farmacie è basato sull'invocazione di un servizio esposto come web services SOAP

# Installing
Vedere il file install.pdf nella cartella DOCS 


# Versioning
Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).

# Authors
La lista delle persone che hanno partecipato alla realizzazione del software sono:
- Antonino Lofaro
- Giuliano Iunco
- Giuseppe Vezzetti
- Manuela Bontempi
- Davide Grosso
- Liliana Guerra
- Michele Mastrorilli
- Nicola Gaudenzi
- Yvonne Carpegna


# Copyrights
© Copyright Regione Piemonte – 2023


# License
EUPL-1.2

Vedere il file LICENSE.txt per i dettagli.
