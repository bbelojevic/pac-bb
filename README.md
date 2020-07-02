# pac-bb



# neo4j

1. Starting point was to work with Neo4j Desktop, no authentication.
2. Next step was to connect ot the neo4j that lives inside the minikube:

// there is an issue with license on https://github.com/neo4j-contrib/neo4j-helm

helm repo add equinor-charts https://equinor.github.io/helm-charts/charts/
helm repo update
helm -n persistence upgrade --install neo4j-community equinor-charts/neo4j-community --set acceptLicenseAgreement=yes --set neo4jPassword=pacneo4j

Change service to be able to access it from outside of cluster, go to pac-bb/minikube/neo4j/service.yaml

kubectl -n persistence delete service neo4j-community-neo4j-community
kubectl -n persistence create -f C:\PAC\pac-source\minikube\neo4j\service.yaml

curl http://localhost:7474/

In chrome go to http://minikube:32010/ this will lead to http://minikube:32010/browser/, you should connect with bolt://localhost:7687 and no authentication.

Before that, there was an issue with certification (neo4j helm WebSocket connection to ws:// failed: Error in connection establishment: net::ERR_CONNECTION_REFUSED), from chrome go to https://localhost:7687/, click advanced and accept certificate. After that you'll be able to connect to db (You are connected to bolt://localhost:7687)


# pac-backend

# pac-frontend

