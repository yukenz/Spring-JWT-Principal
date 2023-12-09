# Create LDAP Image Ubuntu 16.04

## Create Docker Container
```shell
docker container create \
--interactive \
--tty \
--publish 8022:22 \
--name ubuntu_ldap \
--hostname ldapserver \
ubuntu:16.04
```
## Install tools
```shell
docker exec -it ubuntu_ldap bash
apt-get update
apt-get install slapd ldap-utils phpldapadmin net-tools ssh vim
```

## Setup LDAP
```shell
dpkg-reconfigure slapd
# Omit OpenLDAP server configuration? [yes/no] no
# DNS domain name: awan.edu
# Organization name: awan
# Administrator password: awan
# Confirm password: awan
# Database backend to use: 3
# Do you want the database to be removed when slapd is purged? [yes/no] yes
#Move old database? [yes/no] no
service slapd start
```

## Setup PHPLDAPAdmin & Apache
```shell
vi /etc/phpldapadmin/config.php
# $servers->setValue('server','base',array('dc=awan,dc=edu'));
# $servers->setValue('login','bind_id','cn=admin,dc=awan,dc=edu');
service apache2 start
# visit http://localhost:8081/phpldapadmin
```

## Setup SSH
```shell
passwd root
# Enter Password
vi /etc/ssh/sshd_config 
# PermitRootLogin yes
service ssh start
```

## Tunneling Host
```shell
ssh root@localhost -p 8022 -N -L 8081:127.0.0.1:80 -L 8389:127.0.0.1:389
```
