echo  ������������ � ��������� ����������� ����� JDBC-������� �� Oracle ojdbc6-12.1.0.2.jar
echo  ������� ������ ������ �  src/lib/ojdbc6-12.1.0.2.jar
mvn install:install-file -Dfile=src/lib/ojdbc6-12.1.0.2.jar  -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=12.1.0.2 -Dpackaging=jar