docker-compose -f docker-compose.yml down
docker stop jenkins-pipeline
sudo cp -r /tmp/jenkins /home/ec2-user/jenkins/backup
