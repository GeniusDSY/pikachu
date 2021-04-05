# Pikachu Gateway platform

## Application-name

ad-gateway

## Port

8090

## Hystrix Time-out setting

```yaml
ribbon:
  ReadTimeout: 12000
  ConnectTimeout: 12000
  eureka:
    enabled: true
```

## Route configuration

### ad-sponsor

```text
http://localhost:{gateway-port}/pikachu/ad-sponsor/**
```

### ad-search

```text
http://localhost:{gateway-port}/pikachu/ad-search/**
```

### ad-dashboard

```text
http://localhost:{gateway-port}/pikachu/ad-dashboard/**
```