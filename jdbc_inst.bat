echo  Регистрирует в локальном репозитарии Мавен JDBC-драйвер БД Oracle ojdbc6-12.1.0.2.jar
echo  Драйвер должен лежать в  src/lib/ojdbc6-12.1.0.2.jar
mvn install:install-file -Dfile=src/lib/ojdbc6-12.1.0.2.jar  -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=12.1.0.2 -Dpackaging=jar

rem mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc6 \
rem     -Dversion=12.1.0.2 -Dpackaging=jar -Dfile=ojdbc.jar -DgeneratePom=true