# pac-bb

# While developing the stack was used

- Windows 10 + HyperV
- minikube v1.11.0 on Microsoft Windows 10 Pro 10.0.18363 Build 18363
- Kubernetes v1.18.3 on Docker 19.03.8
- Enabled addons: dashboard, default-storageclass, freshpod, ingress, metrics-server, storage-provisioner

```
minikube config view
cpus: 4
disk-size: 100g
memory: 6000
```

and more: 

- java 8
- lombok 1.8.12 (C:\Users\bbelojevic\.m2\repository\org\projectlombok\lombok\1.18.12>java -jar lombok-1.18.12.jar)
- node.js v8.10.0
- npm 5.6.0
- maven 3.5.2

# neo4j

1. Starting point was to work with Neo4j Desktop, no authentication.
2. Next step was to connect ot the neo4j that lives inside the minikube:

// there is an issue with license on https://github.com/neo4j-contrib/neo4j-helm

```
helm repo add equinor-charts https://equinor.github.io/helm-charts/charts/
helm repo update
helm -n persistence upgrade --install neo4j-community equinor-charts/neo4j-community --set acceptLicenseAgreement=yes --set neo4jPassword=pacneo4j
```

Change service to be able to access it from outside of cluster, go to pac-bb/minikube/neo4j/service.yaml

```
kubectl -n persistence delete service neo4j-community-neo4j-community
kubectl -n persistence create -f C:\PAC\pac-source\minikube\neo4j\service.yaml
```

In chrome go to http://minikube:32010/ this will lead to http://minikube:32010/browser/, you should connect with bolt://minikube:32011 and username and password.

If there is an issue with certification (neo4j helm WebSocket connection to ws:// failed: Error in connection establishment: net::ERR_CONNECTION_REFUSED), 
from chrome go to https://minikube:32011/, click advanced and accept certificate. After that you'll be able to connect to db.


# pac-backend

- create a docker hub account
- add Dockerfile, go to pac-bb/pac/pac-backend/Dockerfile, also add a plugin for com.spotify/dockerfile-maven-plugin into the pom.xml

```
mvn install 
// check if image is there
docker images
```

- put username and password into settings.xml (docker.io)
```
        <server>
            <id>docker.io</id>
            <username>bbelojevic</username>
            <password>pass</password>
        </server>
```

- push to the docker hub with 
```
mvn dockerfile:push
```
- or do a 
```
docker login
docker push bbelojevic/pac-backend:0.0.1-SNAPSHOT // it was extremely slow
```

```
kubectl create namespace pac-backend
kubectl -n pac-backend create deployment --image=bbelojevic/pac-backend:0.0.1-SNAPSHOT pac-backend
kubectl -n pac-backend expose deployment pac-backend --type="NodePort" --port=8080
kubectl -n pac-backend describe service
minikube ip
go to http://192.168.1.6:30884/locations
```

- now we want to use ingress instead of nodeport

```
kubectl delete namespace pac-backend
kubectl create namespace pac-backend
kubectl -n pac-backend create deployment --image=bbelojevic/pac-backend:0.0.5-SNAPSHOT pac-backend

// we should add containerPort 8080

kubectl -n pac-backend expose deployment pac-backend --port=8080

// it should probably be port 80

kubectl -n pac-backend apply -f C:\PAC\pac-source\minikube\backend\configmap.yaml

// after every change you should delete the pod and check the logs (configmap parameters issues possible)

kubectl -n pac-backend logs pac-backend-5774d57b66-45hn2 -f

// kubectl -n pac-backend create serviceaccount pac-backend
kubectl -n pac-backend apply -f C:\PAC\pac-source\minikube\backend\clusterrolebinding.yaml

kubectl -n pac-backend apply -f C:\PAC\pac-source\minikube\backend\ingress.yaml

// add resources to deployment and create hpa

kubectl -n pac-backend autoscale deployment pac-backend --min=1 --max=3 --cpu-percent=80
```

# pac-frontend

```
npm install -g @vue/cli
vue create pac-frontend
?  Your connection to the default yarn registry seems to be slow.
   Use https://registry.npm.taobao.org for faster installation? Yes


Vue CLI v4.4.6
? Please pick a preset: default (babel, eslint)
? Pick the package manager to use when installing dependencies: NPM
```

```
cd pac-frontend
npm run serve

> pac-frontend@0.1.0 serve C:\PAC\pac-source\pac-frontend
> vue-cli-service serve

 INFO  Starting development server...
98% after emitting CopyPlugin

 DONE  Compiled successfully in 3782ms 15:28:34


  App running at:
  - Local:   http://localhost:8081/
  - Network: http://192.168.1.5:8081/

  Note that the development build is not optimized.
  To create a production build, run npm run build.
```

- access to a docker hub account
- add Dockerfile, go to pac-bb/pac/pac-frontend/Dockerfile (https://vuejs.org/v2/cookbook/dockerize-vuejs-app.html)

```
PS C:\PAC\pac-source\pac-frontend> docker build -t bbelojevic/pac-frontend:0.14 .
// check if image is there
docker images
```

- push to the docker hub with

```
docker login
docker push bbelojevic/pac-frontend:0.14

docker run -it -p 8080:80 --rm --name pac-frontend-1 bbelojevic/pac-frontend:0.14
```

- now we want to use ingress

```
kubectl create namespace pac-frontend
kubectl -n pac-frontend create deployment --image=bbelojevic/pac-frontend:0.15 pac-frontend

// we should add containerPort 80

kubectl -n pac-frontend expose deployment pac-frontend --port=80

// targetPort is 80

kubectl -n pac-frontend apply -f C:\PAC\pac-source\minikube\frontend\ingress.yaml

// add resources to deployment and create hpa

kubectl -n pac-frontend autoscale deployment pac-frontend --min=1 --max=3 --cpu-percent=80
```

# keycloak

- we will install mariadb and connect keycloak to it, after that we must setup pac-frontend and after that pac-backend

```
helm repo add codecentric https://codecentric.github.io/helm-charts
helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo update

kubectl create namespace keycloak
helm -n keycloak upgrade --install -f C:\PAC\pac-source\minikube\keycloak\maraidb-config.yaml keycloak-mariadb bitnami/mariadb
helm -n keycloak upgrade --install -f C:\PAC\pac-source\minikube\keycloak\keycloak-config.yaml keycloak codecentric/keycloak
kubectl -n keycloak get pods

// go to http://keycloak.minikube/

// check if mariadb is working 

kubectl -n keycloak exec -ti keycloak-mariadb-master-0 -- bash

I have no name!@keycloak-mariadb-master-0:/$ mysql -ukeycloak -pkeycloak keycloak
Welcome to the MariaDB monitor.  Commands end with ; or \g.
Your MariaDB connection id is 1212
Server version: 10.3.23-MariaDB-log Source distribution

Copyright (c) 2000, 2018, Oracle, MariaDB Corporation Ab and others.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

MariaDB [keycloak]> show tables;
Empty set (0.000 sec)

MariaDB [keycloak]>

```

- there was an issue with jwt iss (WWW-Authenticate: Bearer realm="pac", error="invalid_token", error_description="Invalid token issuer. Expected 'http://keycloak-http.keycloak/auth/realms/pac', but was 'http://keycloak.minikube/auth/realms/pac'")
- didn't find a way to work in a one node environment suh as minikube
- in the end, frontend was pointing to the ingress exposed url 'http://keycloak.minikube/auth/realms/pac' and backend was pointing directly to service 'http://keycloak-http.keycloak/auth/realms/pac'
- it was neccessary to expose service as NodePort

```
kubectl -n keycloak delete service keycloak-http
kubectl -n keycloak create -f C:\PAC\pac-source\minikube\keycloak\service.yaml

http://minikube:32020 
```

# prometheus and grafana

- we will install prometheus and grafana so we can monitor our API calls

```
helm repo add stable https://kubernetes-charts.storage.googleapis.com/
helm repo update

kubectl create namespace monitoring
helm -n monitoring upgrade --install -f C:\PAC\pac-source\minikube\prometheus\prometheus-config.yaml prometheus stable/prometheus

kubectl -n monitoring get pods

// go to http://prometheus.minikube/

// check if prometheus is working, go to http://prometheus.minikube/targets and check for "kubernetes-pods" and there should be our endpoint http://172.17.0.11:8080api/actuator/prometheus  
// pac_locations_getall_seconds_count{app="pac-backend",exception="None",instance="172.17.0.11:8080",job="kubernetes-pods",kubernetes_namespace="pac-backend",kubernetes_pod_name="pac-backend-847857cf9f-c4g86",method="GET",outcome="SUCCESS",pod_template_hash="847857cf9f",status="200",uri="/locations"}

PS C:\PAC\pac-source> helm -n monitoring upgrade --install -f C:\PAC\pac-source\minikube\prometheus\prometheus-config.yaml prometheus stable/prometheus
Release "prometheus" does not exist. Installing it now.
NAME: prometheus
LAST DEPLOYED: Tue Jul 21 18:11:13 2020
NAMESPACE: monitoring
STATUS: deployed
REVISION: 1
TEST SUITE: None
NOTES:
The Prometheus server can be accessed via port 80 on the following DNS name from within your cluster:
prometheus-server.monitoring.svc.cluster.local

From outside the cluster, the server URL(s) are:
http://prometheus.minikube


The Prometheus alertmanager can be accessed via port 80 on the following DNS name from within your cluster:
prometheus-alertmanager.monitoring.svc.cluster.local


Get the Alertmanager URL by running these commands in the same shell:
  export POD_NAME=$(kubectl get pods --namespace monitoring -l "app=prometheus,component=alertmanager" -o jsonpath="{.items[0].metadata.name}")
  kubectl --namespace monitoring port-forward $POD_NAME 9093
#################################################################################
######   WARNING: Pod Security Policy has been moved to a global property.  #####
######            use .Values.podSecurityPolicy.enabled with pod-based      #####
######            annotations                                               #####
######            (e.g. .Values.nodeExporter.podSecurityPolicy.annotations) #####
#################################################################################


The Prometheus PushGateway can be accessed via port 9091 on the following DNS name from within your cluster:
prometheus-pushgateway.monitoring.svc.cluster.local


Get the PushGateway URL by running these commands in the same shell:
  export POD_NAME=$(kubectl get pods --namespace monitoring -l "app=prometheus,component=pushgateway" -o jsonpath="{.items[0].metadata.name}")
  kubectl --namespace monitoring port-forward $POD_NAME 9091

For more information on running Prometheus, visit:
https://prometheus.io/

```