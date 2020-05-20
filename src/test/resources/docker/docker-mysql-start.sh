#!/bin/zsh

name_chartnomy_mysql='chartnomy-mysql'

cnt_chartnomy_mysql=`docker container ls --filter name=chartnomy-mysql | wc -l`
cnt_chartnomy_mysql=$(($cnt_chartnomy_mysql -1))

if [ $cnt_chartnomy_mysql -eq 0 ]
then
    echo "'$name_chartnomy_mysql' 컨테이너를 구동시킵니다."
    docker container run --rm -d -p 23306:3306 --name chartnomy-mysql -v ~/env/docker/chartnomy/volumes/chartnomy-mysql:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=1111 -e MYSQL_DATABASE=ec2_web_stockdata -d mysql:latest

else
    echo "'$name_chartnomy_mysql' 컨테이너가 존재합니다. 기존 컨테이너를 중지하고 삭제합니다."
    docker container stop chartnomy-mysql
    echo "\n'$name_chartnomy_mysql' 컨테이너 삭제를 완료했습니다.\n"

    echo "'$name_chartnomy_mysql' 컨테이너를 구동시킵니다."
    docker container run --rm -d -p 23306:3306 --name chartnomy-mysql -v ~/env/docker/chartnomy/volumes/chartnomy-mysql:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=1111 -e MYSQL_DATABASE=ec2_web_stockdata -d mysql:latest
fi