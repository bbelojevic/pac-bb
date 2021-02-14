resource "kubernetes_namespace" "monitoring" {
  metadata {
    name = "monitoring"
  }
}

# helm -n monitoring upgrade --install -f C:\PAC\pac-source\minikube\prometheus\prometheus-config.yaml prometheus stable/prometheus
# helm -n monitoring upgrade --install -f C:\PAC\pac-source\minikube\grafana\grafana-config.yaml grafana stable/grafana

resource "helm_release" "prometheus" {
  repository = local.helm_repository_stable
  chart = "prometheus"
  version = "11.12.0"
  namespace = kubernetes_namespace.monitoring.metadata[0].name
  name = "prometheus"

  depends_on = [
    helm_release.pac-backend
  ]

  values = [
    file("helm/prometheus.yaml")
  ]
}

resource "helm_release" "grafana" {
  repository = local.helm_repository_stable
  chart = "grafana"
  version = "5.5.5"
  namespace = kubernetes_namespace.monitoring.metadata[0].name
  name = "grafana"

  depends_on = [
    helm_release.prometheus
  ]

  values = [
    file("helm/grafana.yaml")
  ]
}
