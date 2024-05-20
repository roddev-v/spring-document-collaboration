#FROM --platform=linux/amd64 alpine
#EXPOSE 8080
#COPY target/api-gateway /app/api-gateway
#CMD ["./api-gateway"]

FROM container-registry.oracle.com/graalvm/native-image:21-ol8 AS builder

WORKDIR /build

COPY ./api-gateway /build

RUN ./mvnw --no-transfer-progress native:compile -Pnative

FROM container-registry.oracle.com/os/oraclelinux:8-slim

EXPOSE 8080

COPY --from=builder /build/target/api-gateway app
ENTRYPOINT ["/app"]