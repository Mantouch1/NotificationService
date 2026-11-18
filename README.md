# NotificationService

Stores notifications (own PostgreSQL database `notification_db`) and sends them by email.
Port 8084. By default emails are **not sent** (only logged): set `MAIL_ENABLED=true` + SMTP settings to send for real.

## Endpoints (JWT issued by IdentityService)
| Method | URL | Permission |
|---|---|---|
| POST | /api/notifications | NOTIFICATION_SEND |
| GET | /api/notifications?recipient=&page=&size= | NOTIFICATION_READ |
| GET | /api/notifications/{id} | NOTIFICATION_READ |

Body of POST: `{"recipientEmail":"a@b.de","subject":"...","message":"...","type":"DEADLINE_REMINDER"}`

## Run
Eclipse: set env vars `SPRING_DATASOURCE_PASSWORD`, `JWT_SECRET` (same value as IdentityService).
Docker: `docker network create platform-net` (once), `copy .env.example .env`, `docker compose up --build`.
