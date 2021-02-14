# pac-bb

# About

Implementation of Conferencing App which will help us to have a central place where all details about a conference - events, talks, persons a.s.o. are stored. 
Backend and frontend are separated, solution is scalable (because while the events take place the load gets very high).
Monitoring will ensure that bottlenecks can be detected early to prevent low performance.

Solution will use:
 
- docker and kubernetes, backend is headless and covers all CRUD functionalities for all entities (http://pac.backend/api/swagger-ui/index.html)
- keycloak for authentication and secure services

![image_2020_10_23T14_11_37_003Z](https://user-images.githubusercontent.com/19586444/97015797-48528f00-154c-11eb-852c-b403ec4d57e9.png)

# How to

- Start from clean environment, you should probably remove your minikube if there is one

- Install minikube (minimal setup)

```
minikube config view
cpus: 4
disk-size: 100g
memory: 6000
```

- Start minikube

- Enabled addons: dashboard, default-storageclass, freshpod, ingress, metrics-server, storage-provisioner

- Add in hosts file:

```
<minikube ip> minikube keycloak.minikube pac.backend pac.frontend grafana.minikube prometheus.minikube
```

- Install helm and run these commands

```
helm repo add equinor-charts https://equinor.github.io/helm-charts/charts/
helm repo add codecentric https://codecentric.github.io/helm-charts
helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo add stable https://charts.helm.sh/stable
helm repo update
```

- Go to pac-bb/terraform/terraform/ and run these commands

```
terraform init
terraform validate
terraform plan
terraform apply
```

- Check minikube dashboard

```
minikube dashboard
```

- Setup keycloak http://keycloak.minikube/ (admin credentials: keycloak | keycloak)

```
- realm: pac

- client: pac-backend
  - Access Type: Bearer-only

- client: pac-frontend
  - Access Type: public
  - Standard Flow Enabled: On
  - Direct Access Grants Enabled: On
  - Root URL: http://pac.frontend/
  - Valid Redirect URIs: http://pac.frontend/*
  - Base URL: http://pac.frontend/
  - Admin URL http://pac.frontend/
  - Web Origins: *
  
- roles: Admin, Manager

- users: bbelojevic
  - Email: bbelojevic@gmail.com
  - Set password
  - Role mappings: Admin
```

# Access application via 

```
http://pac.frontend
```

# Info about database (neo4j)

```
helm repo - equinor-charts https://equinor.github.io/helm-charts/charts/

username | password 
neo4j | pacneo4j

access via http://minikube:32010/browser/, connect with bolt://minikube:32011 and username and password.

MATCH (n) RETURN n;
MATCH (n) DETACH DELETE n;
```

# Info about backend (pac-backend)

```   
http://pac.backend/api/actuator/metrics
http://pac.backend/api/actuator/health
http://pac.backend/api/v2/api-docs
http://pac.backend/api/swagger-ui/index.htm

docker image - bbelojevic/pac-backend:0.0.11-SNAPSHOT
```

# Info about frontend (pac-frontend)

```
http://pac.frontend

docker image - bbelojevic/pac-frontend:0.26
```

# Info about keycloak

```
helm repo - codecentric https://codecentric.github.io/helm-charts // version 8.3.0
helm repo - bitnami https://charts.bitnami.com/bitnami

http://keycloak.minikube/

```

# Info about prometheus and grafana

```
helm repo - stable https://kubernetes-charts.storage.googleapis.com/

http://prometheus.minikube/

- check if prometheus is working, go to http://prometheus.minikube/targets and check for "kubernetes-pods" and there should be our endpoint similar to http://172.17.0.11:8080api/actuator/prometheus
- graph expression example: pac_locations_getall_seconds_count
```

```
// helm repo - stable https://charts.helm.sh/stable

http://grafana.minikube/

username | password

admin | secret

- add datasource Prometheus with url http://prometheus-server.monitoring.svc.cluster.local
- example: go to Dashboards -> Manage menu item and click Import to add the dashboard with ID: 1621
- add custom panel with for example pac_locations_getall_seconds_count{... copy value from prometheus ...}
```

# Delete all 

```
kubectl delete namespace persistence
kubectl delete namespace pac-backend
kubectl delete namespace pac-frontend
kubectl delete namespace keycloak
kubectl delete namespace monitoring

kubectl delete clusterrolebinding pac-backend
```
