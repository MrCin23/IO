#!/bin/bash

for file in /initstructure/*.sql; do
  psql -U ias -d skph_test -f "$file"
done

for file in /initdata/*.sql; do
  psql -U ias -d skph_test -f "$file"
done