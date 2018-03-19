Bayer-HealthCare Web-Service (*bhc-ws*)
=================

1. [Abstract](#abstract)
2. [Collaborators](#collaborators)
3. [Endpoints](#endpoints)
4. [Frontend](#frontend)
5. [Deployment](#deployment)
6. [Todo](#todo)

#  Abstract
BHC-Webservice is a REST microservice for CRUD ops for portfolios managed by Bayer-HealthCare.


# Collaborators

* MySQL is used as persistence layer (see application.yml) where portfolio data are stored
* BHC-Web-UI to upload/download a portfolio by CSV file. See [BHC-WebUI](https://bhc-test.bisnode.de/signin).

# Endpoints
See swagger-ui for BHC-WS running on dock-qa-se-02 (192.168.127.156) in Darmstadt:
 * QA - http://dock-qa-se-02:8084/swagger-ui.html#

# Deployment

* Docker Image is build by Bamboo. See [Bamboo-Job](https://buildtools.bisnode.com/bamboo/chain/admin/config/defaultStages.action?buildKey=DE-PORTAPI) here.

* See on dock-qa-se-02 in folder */opt/bhc*. The shell script *deployit.sh* will stop, deploy and start
all necessary components. See in *docker-compose.yml* which components are required and how they are configured.

* See [Jenkins-Deploy-Job](http://jenkins.bi.bisnode.de/jenkins/job/BHC-Deploy-2-QA/) which just calls *deployit.sh*.

     > ATTENTION: You need to execute manually Jenkins job or deployit.sh to deploy a new version of bhc-ws.

* See [Jenkins-Test-Job](http://jenkins.bi.bisnode.de/jenkins/job/BHC-QA-Test/) which runs BDD tests.

# TODO
* Deployment: Steps from deployit.sh can be done within Jenkins job. So no need to maintain deployit.sh anymore.
Separate then the deployment from BHC and BDD stuff. So making different Jenkins jobs which will be executed in sequence.
* Build: Use *Gradle* instead of *Maven*
* Use bisconf.yaml instead of kubeconf.yml. Automated tagging is then included. See bdd-driver-ws as sample.
* Add *ApiExceptionHandler* as in bdd-driver-ws to separate exception handling from request handling