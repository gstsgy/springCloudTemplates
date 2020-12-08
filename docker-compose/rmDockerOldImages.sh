#!/bin/bash
appnames=(permission basedata gateway)
for appname in ${appnames[*]};do
        dockerid=$(docker ps -aq --filter name=docker-compose_${appname})
        echo dockerid=${dockerid}
        docker stop ${dockerid}
        docker rm ${dockerid}
        dockerid=$(docker images -q --filter reference=docker-compose_${appname})
        docker rmi ${dockerid}
done
docker images