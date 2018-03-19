sudo docker-compose down
sudo docker pull artifactory.bisnode.net/bisnode/bhc-ws:develop
sudo docker pull artifactory.bisnode.net/bisnode/bhc-frontend:develop
sudo docker pull artifactory.bisnode.net/bisnode/bdd-driver-ws-v1:latest-master
sudo docker pull artifactory.bisnode.net/bisnode/bdd-ui:latest-master
sudo docker-compose up -d  --remove-orphans
