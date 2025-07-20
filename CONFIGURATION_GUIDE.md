# Configuration Guide

## Environment Setup

### 1. API Token Configuration

To use the TMDB API, you need to set up your API token as an environment variable:

#### For Development:
Create a `.env` file in your project root:
```bash
TMDB_ACCESS_TOKEN=your_tmdb_api_token_here
```

#### For Production:
Set the environment variable in your deployment platform:
```bash
export TMDB_ACCESS_TOKEN=your_tmdb_api_token_here
```

### 2. Getting a TMDB API Token

1. Go to [The Movie Database](https://www.themoviedb.org/)
2. Create an account or sign in
3. Go to Settings > API
4. Request an API key
5. Copy the API key (v3 auth)

### 3. Environment Variables

| Variable | Description | Default | Required |
|----------|-------------|---------|----------|
| `TMDB_ACCESS_TOKEN` | TMDB API authentication token | None | Yes |
| `ENVIRONMENT` | Application environment | development | No |
| `LOG_LEVEL` | Logging level | INFO | No |

### 4. Platform-Specific Configuration

#### Android
Add to `local.properties`:
```properties
TMDB_ACCESS_TOKEN=your_token_here
```

#### iOS
Add to your Xcode project environment variables or use a configuration file.

#### Web
Set in your build environment or use a configuration service.

### 5. Security Best Practices

- Never commit API tokens to version control
- Use environment variables for sensitive data
- Rotate tokens regularly
- Use different tokens for development and production
- Monitor API usage to prevent abuse

### 6. Testing Configuration

For testing, you can use a mock API token or create a separate test environment with limited API access.

## Troubleshooting

### Common Issues:

1. **API Token Not Found**: Ensure the environment variable is properly set
2. **Authentication Errors**: Verify your API token is valid and has the correct permissions
3. **Rate Limiting**: TMDB has rate limits; implement proper caching if needed

### Debug Mode:

Set `LOG_LEVEL=DEBUG` to see detailed API request/response logs.

## Next Steps

1. Set up your API token
2. Test the configuration
3. Deploy with proper environment variables
4. Monitor API usage and performance 