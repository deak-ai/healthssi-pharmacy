# syntax=docker/dockerfile:1
FROM gradle:jdk17 AS buildstage
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

RUN gradle wasmJsBrowserDistribution --no-daemon

FROM nginx:alpine
COPY --from=buildstage /home/gradle/src/composeApp/build/dist/wasmJs/productionExecutable/ /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]


# run in basedir of repo:
# docker buildx build -f Dockerfile -t healthssi-pharmacy:latest .
# docker tag healthssi-pharmacy:latest ctodeakai/healthssi-pharmacy:latest
# docker push ctodeakai/healthssi-pharmacy:latest