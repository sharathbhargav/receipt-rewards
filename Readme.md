Instructions to run the application:
1. Clone the repo and run the command `docker build -t rewards:latest .`
2. Once the image is built, run the command `docker run --rm -it -p 8080:8080/tcp -p 9002:9002/tcp rewards:latest`
3. The above command will the create and run the container with port 8080 exposed. Assuming no other application is running on 8080.


## To create a receipt the following command can be run, while the docker container is running
```
curl --location 'http://localhost:8080/receipts/process' \
--header 'Content-Type: application/json' \
--data '{
    "items":[
        {
            "shortDescription":"short description",
            "price":22.32
        }
    ],
    "retailer":"retailor",
    "total":22,
    "purchaseDate":"2023-06-02",
    "purchaseTime":"22:05"

}'
```

## To get the points for a receipt a command similar to below can be run
`curl --location 'http://localhost:8080/receipts/1/points'`