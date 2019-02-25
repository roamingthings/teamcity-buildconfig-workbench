#!/bin/sh

mkdir keys
ssh-keygen -t rsa -f ./keys/teamcity_agent -m PEM -N ''
