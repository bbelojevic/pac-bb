resource "kubernetes_namespace" "pac-frontend" {
  metadata {
    name = "pac-frontend"
  }
}

resource "helm_release" "pac-frontend" {
  name = "pac-frontend"
  chart = "../chart/pac-frontend"
  namespace = kubernetes_namespace.pac-frontend.metadata[0].name

  depends_on = [
    helm_release.pac-backend
  ]

  values = [
    file("helm/pac-frontend-config.yaml")
  ]
}
