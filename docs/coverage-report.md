# Coverage Report

## Загальне покриття
- Statements: 40.24%
- Branches: 30%
- Functions: 31.49%
- Lines: 40.69%

## Аналіз

### Добре покрито:
- helper функції (setLink, getHref, search)
- прості утиліти (sortArray, urlUtils)

### Погано покрито:
- UI компоненти (форми, сторінки)
- sagas (асинхронна логіка)
- reducers (частково)

## Чому є пропуски:
- багато логіки залежить від Redux і API
- частина коду виконується тільки при конкретних умовах
- асинхронні функції важко тестувати unit тестами

## Висновок:
Тести покривають базову логіку, але UI і бізнес-логіка потребують розширення.

## Скріншот
<img width="1912" height="926" alt="image" src="https://github.com/user-attachments/assets/8c712656-1b5a-47a6-b0aa-e6993df371e1" />
<img width="1913" height="921" alt="image" src="https://github.com/user-attachments/assets/7e5a5d34-65ad-452f-b064-1ac73eaa0a01" />


