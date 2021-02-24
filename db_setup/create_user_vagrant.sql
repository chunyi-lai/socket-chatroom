-- Add another user called vagrant and grant all user abilities
CREATE USER 'vagrant'@'localhost' IDENTIFIED BY 'Vagrantadmin123!';
GRANT ALL PRIVILEGES ON * . * TO 'vagrant'@'localhost';