FROM alpine
WORKDIR /app
EXPOSE 8080
COPY target/api-gateway .
RUN ls -la
CMD ["./api-gateway"]