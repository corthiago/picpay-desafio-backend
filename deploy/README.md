# VPS deployment

This directory is copied to the Hostinger VPS by GitHub Actions.

## GitHub environments

Create two GitHub environments: `staging` and `production`.

Each environment needs these secrets:

- `VPS_HOST`
- `VPS_USER`
- `VPS_SSH_KEY`
- `VPS_SSH_PORT`
- `DEPLOY_PATH`

Each environment should also define an `APP_DOMAIN` variable:

- staging: `staging.example.com`
- production: `api.example.com`

## VPS prerequisites

Install Docker and Docker Compose on the VPS. The deploy user must be able to run Docker commands.

Point the staging and production DNS records to the VPS IP address. The included Nginx config serves HTTP on port 80. Add Certbot or another TLS layer after DNS is working.

## Manual check

After the first deployment, the VPS deployment directory should contain:

- `compose.yml`
- `.env`
- `nginx/templates/default.conf.template`

Run this on the VPS to inspect the rendered stack:

```bash
docker compose -f compose.yml config
```
