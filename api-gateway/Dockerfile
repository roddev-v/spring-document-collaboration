FROM frolvlad/alpine-glibc

WORKDIR /app

EXPOSE 8080

COPY target/api-gateway .

RUN chmod +x /app/api-gateway

RUN ls -l /app

CMD ["/api-gateway"]
