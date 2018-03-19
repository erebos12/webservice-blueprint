# Bayer-HealthCare Web-Service (*bhc-ws*)

# Table of contents
1. [Abstract](#abstract)
2. [Endpoints](#endpoints)
3. [BHC Frontend](#bhcfrontent)
4. [Deployment](#deployment)
5. [Todo](#todo)


##  Abstract
* BHC-Webservice is a REST microservice for CRUD ops for portfolios managed by Bayer-HealthCare
* MySQL is used as persistence layer (see application.yml) where portfolio data are stored

## Endpoints
See swagger-ui for BHC-WS running on dock-qa-se-02 (192.168.127.156):
 * QA - http://dock-qa-se-02:8084/swagger-ui.html#

## BHC Frontend
See "BHC Web-UI" to upload/download a portfolio by CSV-File:
 * QA - https://bhc-test.bisnode.de/signin

## Deployment

1. Docker Image is build by Bamboo. See https://buildtools.bisnode.com/bamboo/chain/admin/config/defaultStages.action?buildKey=DE-PORTAPI

2. See on dock-qa-se-02 in folder */opt/bhc*. The shell script *deployit.sh* will stop, deploy and start
all necessary components. See in *docker-compose.yml* which components are required and how they are configured.

3. See Jenkins job http://jenkins.bi.bisnode.de/jenkins/job/BHC-Deploy-2-QA/ which just calls *deployit.sh*.

  > ATTENTION: You need to execute manually Jenkins job or deployit.sh to deploy a new version of BHC.

4. See Jenkins (test) job http://jenkins.bi.bisnode.de/jenkins/job/BHC-QA-Test/ which runs BDD tests.

## TODO List
* TBC
