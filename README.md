# ISA-team33

Student 1 - Jelena Budiša, RA33/2017

Student 2 - Dragana Čarapić, RA36/2017

Student 3 - Slađana Savković, RA37/2017

Student 4 - Jelena Zeljko, RA55/2017


Za izradu projekta korišteni su: 
<ul>
  <li> Spring Boot </li>
  <li> Postgresql baza </li>
  <li> jQuery </li>
</ul>

Radno okruženje: Eclipse
Verzija jave: jdk15

Pokretanje projekta:
<ul>
  <li>Kreirati Postgresql bazu podataka sa nazivom mydb </li>  
  <li>Importovati projekat i instalirati sve dependency-je iz pom.xml </li>  
  <li>Ukoliko se crvene klase u kojima su pisani testovi, odabrati opciju Add JUnit 4 library to the build path (ponuđeni import je dostupan prilikom prelaska preko anotacije @RunWith)</li>  
  <li>U application.properties treba da budu podešeni:
    <pre>
      spring.datasource.driverClassName=org.postgresql.Driver
      spring.datasource.platform=postgres
      spring.datasource.username = username
      spring.datasource.password = sifra
      spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
      spring.jpa.hibernate.ddl-auto = create-drop
      spring.jpa.show-sql = true
      spring.jpa.properties.hibernate.format_sql=true
      spring.datasource.initialization-mode=always
      spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
      spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQL95Dialect
      spring.mail.host = smtp.gmail.com
      spring.mail.username =student123ftn@gmail.com
      spring.mail.password =student_123
      spring.mail.port=587
      spring.mail.properties.mail.smtp.starttls.enable = true
      spring.mvc.view.prefix:/WEB-INF/jsp/
      spring.mvc.view.suffix:.jsp
      spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true</pre> </li>
  <li> Uraditi maven update projekta </li>
  <li> Projekat se pokreće na portu 8080. </li>
  <li> Za ucitavanje stranice za logovanje, u browseru otkucati http://localhost:8080/html/user/login.html </li>
</ul>
