locals {
  neo4j_password = "pacneo4j"
}

resource "kubernetes_namespace" "persistence" {
  metadata {
    name = "persistence"
  }
}

# helm -n persistence upgrade --install neo4j-community equinor-charts/neo4j-community --set acceptLicenseAgreement=yes --set neo4jPassword=pacneo4j

resource "helm_release" "neo4j-community" {
  name = "neo4j-community"
  chart = "neo4j-community"
  version = "1.1.1"
  repository = local.helm_repository_equinor
  namespace = kubernetes_namespace.persistence.metadata[0].name

  values = [
    file("helm/neo4j.yaml")
  ]

  set {
    name = "neo4jPassword"
    value = local.neo4j_password
  }
}

resource "kubernetes_service" "neo4j-community-neo4j-community-nodeport" {
  metadata {
    name = "neo4j-community-neo4j-community-nodeport"
    namespace = kubernetes_namespace.persistence.metadata[0].name

  }

  depends_on = [
    helm_release.neo4j-community
  ]

  spec {
    selector = {
      app = "neo4j-community"
    }

    port {
      name = "http"
      node_port   = 32010
      port        = 7474
      target_port = 7474
      protocol = "TCP"
    }

    port {
      name = "bolt"
      node_port   = 32011
      port        = 7687
      target_port = 7687
      protocol = "TCP"
    }

    type = "NodePort"
  }
}
