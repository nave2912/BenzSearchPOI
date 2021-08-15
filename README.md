# BenzSearchPOI
POI based on city

URL without cache
http://localhost:8080/v1/searchPOI?city=berlin&isCacheable=false

URL with cahce 
http://localhost:8080/v1/searchPOI?city=berlin&isCacheable=true

Docker build command,

docker build -t <imageName> <dockerFileLocation>

Run docker image,

docker run -p 8080:8080 <imageName>
