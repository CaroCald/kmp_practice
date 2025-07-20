#!/bin/bash

# KMP Project Environment Setup Script
echo "🎬 KMP Project Environment Setup"
echo "=================================="

# Check if .env file exists
if [ -f ".env" ]; then
    echo "✅ .env file already exists"
else
    echo "📝 Creating .env file..."
    cat > .env << EOF
# TMDB API Configuration
TMDB_ACCESS_TOKEN=your_tmdb_api_token_here

# Environment
ENVIRONMENT=development

# Logging
LOG_LEVEL=INFO

# Network Configuration
REQUEST_TIMEOUT=30000
CONNECT_TIMEOUT=10000

# Feature Flags
ENABLE_CACHING=true
ENABLE_ANALYTICS=false
ENABLE_CRASH_REPORTING=false
EOF
    echo "✅ .env file created"
fi

# Check if API token is set
if grep -q "your_tmdb_api_token_here" .env; then
    echo ""
    echo "⚠️  IMPORTANT: You need to set your TMDB API token!"
    echo ""
    echo "📋 Steps to get your API token:"
    echo "   1. Go to https://www.themoviedb.org/"
    echo "   2. Create an account or sign in"
    echo "   3. Go to Settings → API"
    echo "   4. Request an API key"
    echo "   5. Copy the API key (v3 auth)"
    echo ""
    echo "🔧 Then edit the .env file and replace 'your_tmdb_api_token_here' with your actual token"
    echo ""
    echo "💡 Example:"
    echo "   TMDB_ACCESS_TOKEN=eyJhbGciOiJIUzI1NiJ9..."
    echo ""
else
    echo "✅ API token appears to be configured"
fi

# Check if .gitignore includes .env
if grep -q "\.env" .gitignore; then
    echo "✅ .env is properly ignored in .gitignore"
else
    echo "⚠️  .env is not in .gitignore - adding it now"
    echo "" >> .gitignore
    echo "# Environment variables and sensitive data" >> .gitignore
    echo ".env" >> .gitignore
    echo ".env.local" >> .gitignore
    echo ".env.development" >> .gitignore
    echo ".env.production" >> .gitignore
    echo "*.env" >> .gitignore
    echo "✅ .env added to .gitignore"
fi

echo ""
echo "🎉 Setup complete!"
echo ""
echo "📚 Next steps:"
echo "   1. Get your TMDB API token from https://www.themoviedb.org/settings/api"
echo "   2. Edit .env file and replace 'your_tmdb_api_token_here' with your token"
echo "   3. Run your KMP project"
echo ""
echo "🔍 For more information, see CONFIGURATION_GUIDE.md" 