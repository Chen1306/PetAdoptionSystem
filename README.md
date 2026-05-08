# pet-adopt-backend

## Run

1. Import and execute [sql/pet_adopt_init.sql](./sql/pet_adopt_init.sql)
2. Update `src/main/resources/application.yml`
3. Run `PetAdoptApplication`

## Default accounts

- admin / admin123
- user01 / 123456

## Current scope

- Spring Boot + MyBatis backend scaffold
- Basic auth/login/register flow
- Pet list and admin pet management
- Adoption apply/cancel/audit
- Statistics overview

## Token usage

Login returns a simple demo token.

Use request header:

```text
Authorization: Bearer {token}
```
