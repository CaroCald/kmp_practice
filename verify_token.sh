#!/bin/bash

# TMDB API Token Verification Script
echo "🔍 Verifying TMDB API Token"
echo "============================"

# Check if .env file exists
if [ ! -f ".env" ]; then
    echo "❌ .env file not found. Run setup_env.sh first."
    exit 1
fi

# Read token from .env file
TOKEN=$(grep "TMDB_ACCESS_TOKEN=" .env | cut -d'=' -f2)

if [ "$TOKEN" = "your_tmdb_api_token_here" ] || [ -z "$TOKEN" ]; then
    echo "❌ API token not configured in .env file"
    echo "   Please edit .env and replace 'your_tmdb_api_token_here' with your actual token"
    exit 1
fi

echo "✅ API token found in .env file"
echo "🔐 Testing API connection..."

# Test the API with a simple request
RESPONSE=$(curl -s -H "Authorization: Bearer $TOKEN" \
    -H "Content-Type: application/json" \
    "https://api.themoviedb.org/3/movie/550" 2>/dev/null)

if echo "$RESPONSE" | grep -q "success.*false"; then
    echo "❌ API test failed"
    echo "   Response: $RESPONSE"
    echo ""
    echo "🔧 Troubleshooting:"
    echo "   1. Check if your API token is correct"
    echo "   2. Verify your TMDB account is active"
    echo "   3. Check if you have API access permissions"
    exit 1
elif echo "$RESPONSE" | grep -q "id.*550"; then
    echo "✅ API token is working correctly!"
    echo "   Successfully retrieved movie data from TMDB API"
    echo ""
    echo "🎉 Your KMP project is ready to run!"
else
    echo "⚠️  Unexpected response from API"
    echo "   Response: $RESPONSE"
    echo ""
    echo "🔧 This might be a temporary issue. Try running your project anyway."
fi 