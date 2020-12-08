#!/usr/bin/env bash

set -eo pipefail

modules=( permission gateway basedata )

for module in "${modules[@]}"; do
    docker build -t "wms-cli/${module}:latest" ${module}
done