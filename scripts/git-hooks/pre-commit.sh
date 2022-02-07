#!/bin/sh
# Adapted from https://proandroiddev.com/ooga-chaka-git-hooks-to-enforce-code-quality-11ce8d0d23cb

echo "Checking code formatting"

./gradlew ktlintCheck

status=$?

if [ "$status" = 0 ] ; then
    echo "No formatting issues were found"
    exit 0
else
    echo 1>&2 "* There are code formatting issues that must be addressed"
    exit 1
fi
