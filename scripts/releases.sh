#!/bin/bash
# scripts/release.sh

npm run release
git push --follow-tags origin main
