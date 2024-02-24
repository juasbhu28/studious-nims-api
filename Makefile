SHELL := /bin/bash
PROJECT_NAME := $(shell basename $(PWD))

# Puedes usar un comando para generar un valor único, como la fecha y hora actual
CACHEBUST := $(shell date +%s)

.PHONY: run_all build_docker run_docker update_api rebuild_api

run_all:
	@echo "Starting project $(PROJECT_NAME)..."
	@echo "Preparing docker..."
	make build_docker
	@echo "Running docker..."
	make run_docker
	make exe_sql

build_docker:
	docker-compose up --build -d

run_docker:
	docker-compose up

rebuild_api:
	docker-compose build --no-cache --build-arg CACHEBUST=$(CACHEBUST) java-api

test_api:
	@echo "Testing API..."
	@echo "Building API..."
	cd api-user && ./gradlew clean build test

