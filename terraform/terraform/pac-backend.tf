resource "kubernetes_namespace" "pac-backend" {
  metadata {
    name = "pac-backend"
  }
}

resource "helm_release" "pac-backend" {
  name = "pac-backend"
  chart = "../chart/pac-backend"
  namespace = kubernetes_namespace.pac-backend.metadata[0].name

  depends_on = [
    helm_release.neo4j-community,
    kubernetes_service.neo4j-community-neo4j-community-nodeport,
    helm_release.keycloak
  ]

  values = [
    file("helm/pac-backend-config.yaml")
  ]
}
