locals {
  keycloak_username = "keycloak"
  keycloak_database = "keycloak"
  keycloak_password = "keycloak"
}

resource "kubernetes_namespace" "keycloak" {
  metadata {
    name = "keycloak"
    labels = {
      "app" = "keycloak"
    }
  }
}

# helm -n keycloak upgrade --install -f C:\PAC\pac-source\minikube\keycloak\maraidb-config.yaml keycloak-mariadb bitnami/mariadb

resource "helm_release" "keycloak-mariadb" {
  name = "keycloak-mariadb"
  chart = "mariadb"
  version = "7.9.2"
  repository = local.helm_repository_bitnami
  namespace = kubernetes_namespace.keycloak.metadata[0].name

  values = [
    file("helm/keycloak-mariadb.yaml")
  ]

  set {
    name = "db.user"
    value = local.keycloak_username
  }

  set {
    name = "db.name"
    value = local.keycloak_database
  }

  set {
    name = "db.password"
    value = local.keycloak_password
  }
}

# helm -n keycloak upgrade --install -f C:\PAC\pac-source\minikube\keycloak\keycloak-config.yaml keycloak codecentric/keycloak --version 8.3.0
resource "helm_release" "keycloak" {
  depends_on = [
    helm_release.keycloak-mariadb
  ]

  name = "keycloak"
  namespace = kubernetes_namespace.keycloak.metadata[0].name
  chart = "keycloak"
  version = "8.3.0"
  repository = local.helm_repository_codecentric

  values = [
    file("helm/keycloak.yaml")
  ]

  set {
    name = "keycloak.username"
    value = local.keycloak_username
  }

  set {
    name = "keycloak.password"
    value = local.keycloak_password
  }

  set {
    name = "keycloak.persistence.dbName"
    value = local.keycloak_database
  }

  set {
    name = "keycloak.persistence.dbHost"
    value = helm_release.keycloak-mariadb.name
  }

  set {
    name = "keycloak.persistence.dbUser"
    value = local.keycloak_username
  }

  set {
    name = "keycloak.persistence.dbPassword"
    value = local.keycloak_password
  }
}
