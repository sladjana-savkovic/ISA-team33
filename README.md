# ISA-team33

Student 1 - Jelena Budiša, RA33/2017

Student 2 - Dragana Čarapić, RA36/2017

Student 3 - Slađana Savković, RA37/2017

Student 4 - Jelena Zeljko, RA55/2017

--------------------------------------------------------------------------------------

Za izradu projekta koristili smo: 
<ul>
  <li> Spring Boot </li>
  <li> Postgresql baza </li>
  <li> jQuery </li>
</ul>

Radno okruženje: Eclipse IDE for Enterprise Java Developers (2020-03)

Verzija jave: jdk15

Uputstvo za pokretanje projekta:
<ul>
  <li>Kreirati Postgresql bazu podataka sa nazivom mydb </li>  
  <li>Importovati projekat i instalirati sve dependency-je iz pom.xml </li>  
  <li>Ukoliko se crvene klase u kojima su pisani testovi, odabrati opciju Add JUnit 4 library to the build path (ponuđeni import je dostupan prilikom prelaska preko anotacije @RunWith)</li>  
  <li>U application.properties potrebno je podesiti:
    <pre>
      spring.datasource.username = username
      spring.datasource.password = sifra </pre> </li>
  <li> Uraditi maven update projekta </li>
  <li> Prilikom razvoja, projekat je pokretan na portu 8080. </li>
  <li> Za ucitavanje stranice za logovanje, u browseru otkucati http://localhost:8080/html/user/login.html </li>
  <li> Automatsko pokretanje testova je iskljuceno, a za pokretanje svakog testa pojedinacno, neophodno je ići na Run As -> JUnit test </li>
  <li> U slučaju da je slanje mejla onemogućeno, uraditi disable antivirusnog softvera na računaru. </li>
</ul>
