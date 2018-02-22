#!/bin/bash

if [[ -z $1 ]]; then
	echo "Invalid argument. Available options:"
	echo "    $0 create: creates a container with dummy database"
	echo "    $0 backup: creates a .sql file to backup data"
	echo "    $0 restore [filename]: restores a .sql into mysql database running into a container"
	exit
fi

OPTION=$1

# Check if docker is installed
PACKAGETOLOOKFOR=docker
CONTAINER_NAME=kowalski-mysql
EXTERNAL_PORT=2020

INSTALLED=$(dpkg -l | grep ${PACKAGETOLOOKFOR} >/dev/null && echo "yes" || echo "no")

if [[ $INSTALLED == "no" ]]; then
	echo "* ${PACKAGETOLOOKFOR} is installed... failed"
	echo ""
	echo "Please install docker in order to continue the data installation for Kowalski."
	exit
else
	echo "* ${PACKAGETOLOOKFOR} is installed ... ${INSTALLED}"
fi


# Check docker permissions
RESULT=$( groups | grep docker )
if [[ -z "$RESULT" ]]; then
	echo "* Checking user persmissions to run docker without sudo... failed"
	echo ""
	echo "User $(whoami) does not belong to group 'docker'. Please add it to docker group with the following command:"
	echo ""
	echo "$ sudo usermod -aG docker $(whoami)"
	echo ""
	echo "You have to restart the user session to apply the changes."
	exit
else
	echo "* Checking user persmissions to run docker without sudo... ok"
fi

#-----------------------------------
# Create
#-----------------------------------
if [[ $OPTION == "create" ]]; then
	RESULT=$( docker images -q $CONTAINER_NAME )
	if [[ -n "$RESULT" ]]; then
	  echo "Container $CONTAINER_NAME already exists"
	  exit
	fi

	echo "* Pull mysql container..."
	docker pull mysql:latest

	echo "* Create docker container to run database..."
	docker run -d -p $EXTERNAL_PORT:3306 --name=$CONTAINER_NAME --env="MYSQL_ROOT_PASSWORD=root" --env="MYSQL_PASSWORD=root" --env="MYSQL_DATABASE=kowalskidb" mysql:latest
	echo "* Database container created successfully"

	echo "* Waiting for mysql to be up and running..."
	sleep 15

	# create database with dummy data
	cat kowalskidb_backup.sql | docker exec -i kowalski-mysql /usr/bin/mysql -u root --password=root kowalskidb
	echo "* Dummy database restored successfully"

#-----------------------------------
# Backup
#-----------------------------------
elif [[ $OPTION == "backup" ]]; then
	echo "* Creating backup..."
	TIMESTAMP=$(date +%Y%m%d_%H%M%S)
	docker exec $CONTAINER_NAME /usr/bin/mysqldump -u root --password=root kowalskidb > kowalskidb_backup_$TIMESTAMP.sql
	echo "Backup created successfully"

#-----------------------------------
# Restore
#-----------------------------------
elif [[ $OPTION == "restore" ]]; then
	RESULT=$( docker images -q $CONTAINER_NAME )
	if [[ -z "$RESULT" ]]; then
	  echo "Container $CONTAINER_NAME does not exist"
	  exit
	fi

	echo "* Restoring database $2..."
	cat $2 | docker exec -i $CONTAINER_NAME /usr/bin/mysql -u root --password=root kowalskidb
	echo "* Database $2 restored successfully"
fi

# Restore database with dummy data
#cat kowalskidb_backup.sql | docker exec -i kowalski-mysql /usr/bin/mysql -u root --password=root kowalskidb