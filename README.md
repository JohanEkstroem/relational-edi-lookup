# relational-edi-lookup
Purpose of the API is to let clients look for a company's EDI DocumentTypes and how the company can communicate.
An API that does a HTTP Requests to several EDI sources and returns a collection of results.
One endpoint for a wider search query and another endpoint for a more narrow specific result.
http://localhost:7000/general?[QUERY]
http://localhost:7000/specific?[QUERY])
All external requests is async and handled when ready.
Built with Java 19 and Javalin 5.

Project lacks Maven jar build file.
# To start the API server:
## git clone https://github.com/JohanEkstroem/relational-edi-lookup.git
## navigate to src/main/java/com/johanekstroem
## run Main.java with shift + F10 IntelliJ or F5 VSC
