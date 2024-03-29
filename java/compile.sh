#!/bin/sh
# Indicate the path of the java compiler to use
export JAVA_HOME=/usr/csshare/pkgs/jdk1.7.0_17
export PATH=$JAVA_HOME/bin:$PATH

# Export classpath with the postgressql driver
export CLASSPATH=$CLASSPATH:$PWD/pg73jdbc3.jar

#Index tuning (NOTE:Might be wrong) 
psql −h localhost −p $PGPORT $USER”_DB” < ../sql/create_index.sql

# compile the java program
javac DBProject.java

#run the java program
#Use your database name, port number and login
java DBProject $USER"_DB" $PGPORT $USER

