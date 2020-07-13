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

curl http://localhost:7474/
```

In chrome go to http://minikube:32010/ this will lead to http://minikube:32010/browser/, you should connect with bolt://localhost:7687 and no authentication.

Before that, there was an issue with certification (neo4j helm WebSocket connection to ws:// failed: Error in connection establishment: net::ERR_CONNECTION_REFUSED), 
from chrome go to https://localhost:7687/, click advanced and accept certificate. After that you'll be able to connect to db (You are connected to bolt://localhost:7687)


# pac-backend

- create a docker hub account
- add Dockerfile, go to pac-bb/Dockerfile and add a plugin for com.spotify/dockerfile-maven-plugin into the pom.xml

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
kubectl -n pac-backend create deployment --image=bbelojevic/pac-backend:0.0.2-SNAPSHOT pac-backend

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
