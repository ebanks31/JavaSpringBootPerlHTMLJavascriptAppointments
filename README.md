This is a project for displaying and retrieving appointments using Spring Boot.

**Usage**

This project can be used by clone the git repository.
git clone https://github.com/ebanks31/PerlHTMLJavascriptAppointments.git
Change to this project's main directory.
Run command mvn spring-boot:run


This project uses SQLLite which can be downloaded from here.
https://www.sqlite.org/

**Folder locations**
Java Classes are located here.
https://github.com/ebanks31/PerlHTMLJavascriptAppointments/tree/master/src/main/java/com/example/demo

HTML/CSS/PERL files are located here.
https://github.com/ebanks31/PerlHTMLJavascriptAppointments/tree/master/src/main/resources
Main HTML page is index.html is located in the template folder from the above link.
Static file(css, JavaScript. are located in the static folder.

The classes in the PERL script.

**Replacement values that should be used**
There are some values that must be replaced to use this project successfully. 

The perl command string must be replaced with absolute path for the getAppointment method and addNewAppointment
method in the HomeController.
ex=	String perlScript = "C:\\Perl64\\bin\\perl.exe C:\\\\Users\\\\Eric\\\\eclipse-workspace\\\\SpringBootAppointments\\\\src\\\\main\\\\resources\\\\static\\\\pl\\\\newAppointments.pl "

Home controller files is located here:
https://github.com/ebanks31/PerlHTMLJavascriptAppointments/tree/master/src/main/java/com/example/demo/controller

The testDB must be replaced with absolute path in the appointments.pl and newAppointment.pl
ex: my $database = "C:\\Users\\Eric\\testDB.db";
Should be replaced with your testDB location in both files.

Perl files are located here:
https://github.com/ebanks31/PerlHTMLJavascriptAppointments/tree/master/src/main/resources/static/pl

Sample sql scripts can be located here.
https://github.com/ebanks31/PerlHTMLJavascriptAppointments/tree/master/sql
