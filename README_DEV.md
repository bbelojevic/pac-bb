# pac-bb

# While developing the stack was used

- Windows 10 + HyperV
- minikube v1.11.0 on Microsoft Windows 10 Pro 10.0.18363 Build 18363
- Kubernetes v1.18.3 on Docker 19.03.8
- Enabled addons: dashboard, default-storageclass, freshpod, ingress, metrics-server, storage-provisioner

```
PS C:\PAC\pac-source> minikube start --vm-driver hyperv --hyperv-virtual-switch "Primary Virtual Switch"
* minikube v1.11.0 on Microsoft Windows 10 Pro 10.0.18363 Build 18363
* Using the hyperv driver based on existing profile
* Starting control plane node minikube in cluster minikube
* Restarting existing hyperv VM for "minikube" ...
* Preparing Kubernetes v1.18.3 on Docker 19.03.8 ...
* Verifying Kubernetes components...
* Enabled addons: dashboard, default-storageclass, freshpod, ingress, metrics-server, storage-provisioner
* Done! kubectl is now configured to use "minikube"
PS C:\PAC\pac-source>
```

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
helm -n persistence upgrade --install neo4j-community equinor-charts/neo4j-community --set acceptLicenseAgreement=yes --set neo4jPassword=pacneo4j //--version 1.1.1
```

Change service to be able to access it from outside of cluster, go to pac-bb/minikube/neo4j/service.yaml

```
kubectl -n persistence delete service neo4j-community-neo4j-community
kubectl -n persistence create -f C:\PAC\pac-source\minikube\neo4j\service.yaml
```

In chrome go to http://minikube:32010/ this will lead to http://minikube:32010/browser/, you should connect with bolt://minikube:32011 and username and password.

If there is an issue with certification (neo4j helm WebSocket connection to ws:// failed: Error in connection establishment: net::ERR_CONNECTION_REFUSED), 
from chrome go to https://minikube:32011/, click advanced and accept certificate. After that you'll be able to connect to db.

```
MATCH (n) RETURN n;
MATCH (n) DETACH DELETE n;
```

# pac-backend

```
http://localhost:8080/api/actuator/metrics
http://localhost:8080/api/actuator/health
http://localhost:8080/api/v2/api-docs
http://localhost:8080/api/swagger-ui/index.htm

http://pac.backend/api/actuator/metrics
http://pac.backend/api/actuator/health
http://pac.backend/api/v2/api-docs
http://pac.backend/api/swagger-ui/index.htm
```

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
docker push bbelojevic/pac-backend:0.0.11-SNAPSHOT
```

```
kubectl create namespace pac-backend
kubectl -n pac-backend create deployment --image=bbelojevic/pac-backend:0.0.11-SNAPSHOT pac-backend
kubectl -n pac-backend expose deployment pac-backend --type="NodePort" --port=8080
kubectl -n pac-backend describe service
minikube ip
go to http://192.168.1.6:30884/locations
```

- now we want to use ingress instead of nodeport

```
kubectl delete namespace pac-backend
kubectl create namespace pac-backend
kubectl -n pac-backend create deployment --image=bbelojevic/pac-backend:0.0.11-SNAPSHOT pac-backend

// we should add containerPort 8080

kubectl -n pac-backend expose deployment pac-backend --port=8080

kubectl -n pac-backend apply -f C:\PAC\pac-source\minikube\backend\configmap.yaml

// after every change you should delete the pod and check the logs (configmap parameters issues possible)

kubectl -n pac-backend logs pac-backend-5774d57b66-45hn2 -f

// kubectl -n pac-backend create serviceaccount pac-backend
kubectl -n pac-backend apply -f C:\PAC\pac-source\minikube\backend\clusterrolebinding.yaml

kubectl -n pac-backend apply -f C:\PAC\pac-source\minikube\backend\ingress.yaml

// add resources to deployment and create hpa

kubectl -n pac-backend autoscale deployment pac-backend --min=1 --max=3 --cpu-percent=80

// test hpa 
kubectl -n pac-backend get hpa 

C:\PAC\httpd-2.4.43-win64-VS16\Apache24\bin>ab -n 100000 -c 1000 http://pac.backend/api/locations
This is ApacheBench, Version 2.3 <$Revision: 1874286 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking pac.backend (be patient)
Completed 10000 requests
Completed 20000 requests
Completed 30000 requests
Completed 40000 requests
Completed 50000 requests
Completed 60000 requests
Completed 70000 requests
Completed 80000 requests
Completed 90000 requests
Completed 100000 requests
Finished 100000 requests


Server Software:        nginx/1.17.10
Server Hostname:        pac.backend
Server Port:            80

Document Path:          /api/locations
Document Length:        56 bytes

Concurrency Level:      1000
Time taken for tests:   391.756 seconds
Complete requests:      100000
Failed requests:        93942
   (Connect: 0, Receive: 0, Length: 93942, Exceptions: 0)
Total transferred:      58035359 bytes
HTML transferred:       15835359 bytes
Requests per second:    255.26 [#/sec] (mean)
Time per request:       3917.559 [ms] (mean)
Time per request:       3.918 [ms] (mean, across all concurrent requests)
Transfer rate:          144.67 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    1   1.1      1      28
Processing:     6 3875 4490.2    393   14576
Waiting:        5 3869 4490.6    386   14572
Total:          7 3877 4490.1    395   14577

Percentage of the requests served within a certain time (ms)
  50%    395
  66%   7188
  75%   8699
  80%   9136
  90%  10093
  95%  10938
  98%  12692
  99%  13277
 100%  14577 (longest request)
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
PS C:\PAC\pac-source\pac-frontend> docker build -t bbelojevic/pac-frontend:0.26 .
PS C:\PAC\pac-source\pac-frontend> docker build -t bbelojevic/pac-frontend:0.26 -t bbelojevic/pac-frontend:latest .
// check if image is there
docker images
```

- push to the docker hub with

```
docker login
docker push bbelojevic/pac-frontend:0.26
docker push bbelojevic/pac-frontend:latest

docker run -it -p 8080:80 --rm --name pac-frontend-1 bbelojevic/pac-frontend:0.26
```

- now we want to use ingress

```
kubectl create namespace pac-frontend
kubectl -n pac-frontend create deployment --image=bbelojevic/pac-frontend:0.26 pac-frontend

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
helm -n keycloak upgrade --install -f C:\PAC\pac-source\minikube\keycloak\maraidb-config.yaml keycloak-mariadb bitnami/mariadb // --version 7.9.2 (28.8.2020.)
helm -n keycloak upgrade --install -f C:\PAC\pac-source\minikube\keycloak\keycloak-config.yaml keycloak codecentric/keycloak --version 8.3.0
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

- general setup

```

- realm: pac
- client: pac-backend
  - Access Type: Bearer-only


- client: pac-frontend
  - Access Type: Public
  - Standard Flow Enabled: On
  - Direct Access Grants Enabled: On
  - Root URL: http://pac.frontend/
  - * Valid Redirect URIs: http://pac.frontend/*
  - Base URL: http://pac.frontend/
  - Admin URL http://pac.frontend/
  - Web Origins: *
  
- client: pac-frontend-dev
  - Access Type: Public
  - Standard Flow Enabled: On
  - Direct Access Grants Enabled: On
  - Root URL: http://localhost:8081/
  - * Valid Redirect URIs: http://localhost:8081/*
  - Base URL: http://localhost:8081/
  - Admin URL http://localhost:8081/
  - Web Origins: *
  
- roles: Admin, Manager
- groups: Admin, Manager

- users: bbelojevic
  - Email: bbelojevic@gmail.com
  - Role mappings: Admin

``` 

# prometheus and grafana

- we will install prometheus and grafana so we can monitor our API calls

```
// not valid anymore helm repo add stable https://kubernetes-charts.storage.googleapis.com/ 
// https://helm.sh/blog/new-location-stable-incubator-charts/
helm repo add stable https://charts.helm.sh/stable
helm repo update

kubectl create namespace monitoring
helm -n monitoring upgrade --install -f C:\PAC\pac-source\minikube\prometheus\prometheus-config.yaml prometheus stable/prometheus // --version 11.12.0

kubectl -n monitoring get pods

// go to http://prometheus.minikube/

// check if prometheus is working, go to http://prometheus.minikube/targets and check for "kubernetes-pods" and there should be our endpoint http://172.17.0.11:8080api/actuator/prometheus  
// pac_locations_getall_seconds_count{app_kubernetes_io_instance="pac-backend",app_kubernetes_io_name="pac-backend",exception="None",instance="172.17.0.19:8080",job="kubernetes-pods",kubernetes_namespace="pac-backend",kubernetes_pod_name="pac-backend-6bcb59687f-mr2dr",method="GET",outcome="SUCCESS",pod_template_hash="6bcb59687f",status="200",uri="/locations"}

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

```
// helm repo add stable https://charts.helm.sh/stable
// helm repo update

// kubectl create namespace monitoring
helm -n monitoring upgrade --install -f C:\PAC\pac-source\minikube\grafana\grafana-config.yaml grafana stable/grafana // --version 5.5.5

kubectl -n monitoring get pods

// go to http://grafana.minikube/

fana stable/grafana
Release "grafana" does not exist. Installing it now.
NAME: grafana
LAST DEPLOYED: Fri Jul 24 10:31:24 2020
NAMESPACE: monitoring
STATUS: deployed
REVISION: 1
NOTES:
1. Get your 'admin' user password by running:

   kubectl get secret --namespace monitoring grafana -o jsonpath="{.data.admin-password}" | base64 --decode ; echo

2. The Grafana server can be accessed via port 80 on the following DNS name from within your cluster:

   grafana.monitoring.svc.cluster.local

   If you bind grafana to 80, please update values in values.yaml and reinstall:
   ```
   securityContext:
     runAsUser: 0
     runAsGroup: 0
     fsGroup: 0

   command:
   - "setcap"
   - "'cap_net_bind_service=+ep'"
   - "/usr/sbin/grafana-server &&"
   - "sh"
   - "/run.sh"
   ```
   Details refer to https://grafana.com/docs/installation/configuration/#http-port.
   Or grafana would always crash.

   From outside the cluster, the server URL(s) are:
     http://grafana.minikube


3. Login with the password from step 1 and the username: admin
#################################################################################
######   WARNING: Persistence is disabled!!! You will lose your data when   #####
######            the Grafana pod is terminated.                            #####
#################################################################################
PS C:\PAC\pac-source>

```

- add datasource Prometheus with url http://prometheus-server.monitoring.svc.cluster.local
- example: go to Dashboards -> Manage menu item and click Import to add the dashboard with ID: 1621
- add panel with 
```
pac_locations_getall_seconds_count{app_kubernetes_io_instance="pac-backend",app_kubernetes_io_name="pac-backend",exception="None",instance="172.17.0.19:8080",job="kubernetes-pods",kubernetes_namespace="pac-backend",kubernetes_pod_name="pac-backend-6bcb59687f-mr2dr",method="GET",outcome="SUCCESS",pod_template_hash="6bcb59687f",status="200",uri="/locations"}
```

- helm charts for pac-backend and pac-frontend

```

helm lint // to check if helm chart is well formatted

kubectl delete namespace pac-backend
kubectl delete clusterrolebinding pac-backend

kubectl create namespace pac-backend
helm -n pac-backend upgrade --install -f C:\PAC\pac-source\helm\pac-backend\pac-backend-config.yaml pac-backend C:\PAC\pac-source\helm\pac-backend

kubectl delete namespace pac-frontend

kubectl create namespace pac-frontend
helm -n pac-frontend upgrade --install -f C:\PAC\pac-source\helm\pac-frontend\pac-frontend-config.yaml pac-frontend C:\PAC\pac-source\helm\pac-frontend

```

# terraform

```
 // locate yourself in  C:\PAC\pac-source\terraform\terraform>
 
terraform init
terraform validate
terraform plan
terraform apply

```

# delete all 

```
kubectl delete namespace persistence
kubectl delete namespace pac-backend
kubectl delete namespace pac-frontend
kubectl delete namespace keycloak
kubectl delete namespace monitoring

kubectl delete clusterrolebinding pac-backend
```
