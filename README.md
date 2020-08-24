# pac-bb

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

- Setup keycloak

```
- realm: pac
- client: pac-backend
  - Access Type: Bearer-only


- client: pac-frontend
  - Access Type: Bearer-only
  - Standard Flow Enabled: On
  - Direct Access Grants Enabled: On
  - Root URL: http://pac.frontend/
  - Valid Redirect URIs: http://pac.frontend/*
  - Base URL: http://pac.frontend/
  - Admin URL http://pac.frontend/
  - Web Origins: *
  
- roles: Admin, Manager
- groups: Admin, Manager

- users: bbelojevic
  - Email: bbelojevic@gmail.com
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
```

# Info about backend (pac-backend)

```   
http://pac.backend/api/actuator/metrics
http://pac.backend/api/actuator/health
http://pac.backend/api/v2/api-docs
http://pac.backend/api/swagger-ui/index.htm

docker image - bbelojevic/pac-backend:0.0.10-SNAPSHOT
```

# Info about frontend (pac-frontend)

```
http://pac.frontend

docker image - bbelojevic/pac-frontend:0.24
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

check if prometheus is working, go to http://prometheus.minikube/targets and check for "kubernetes-pods" and there should be our endpoint similar to http://172.17.0.11:8080api/actuator/prometheus  
```

```
// helm repo - stable https://kubernetes-charts.storage.googleapis.com/

http://grafana.minikube/

username | password

admin | secret

- add datasource Prometheus with url http://prometheus-server.monitoring.svc.cluster.local
- example: go to Dashboards -> Manage menu item and click Import to add the dashboard with ID: 1621
- add custom panel with for example pac_locations_getall_seconds_count{app="pac-backend",exception="None",instance="172.17.0.10:8080",job="kubernetes-pods",kubernetes_namespace="pac-backend",kubernetes_pod_name="pac-backend-847857cf9f-c4g86",method="GET",outcome="SUCCESS",pod_template_hash="847857cf9f",status="200",uri="/locations"}
```