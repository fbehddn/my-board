CREATE USER 'exporter'@'%' IDENTIFIED BY 'export_pwd';
GRANT PROCESS, REPLICATION CLIENT, SELECT ON *.* TO 'exporter'@'%';