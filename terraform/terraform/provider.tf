terraform {
  required_version = "= 0.12.28"
}

provider "kubernetes" {
  version = "1.12.0"

  config_context = "minikube"
}

provider "helm" {
  version = "1.2.4"

  kubernetes {
    config_context = "minikube"
  }
}

provider "random" {
  version = "2.3.0"
  // nothing to configure here
}
