#!/bin/bash

echo "======================================"
echo " Running Neural Network Case Study"
echo " Banknote Authentication Demo"
echo "======================================"

set -e

echo "Building project..."
mvn clean compile

echo "Running BanknoteCaseStudy..."
mvn exec:java -Dexec.mainClass="neural_network.case_study.BanknoteCaseStudy"

echo "======================================"
echo " Demo finished successfully"
echo "======================================"
