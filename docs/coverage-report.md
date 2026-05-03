# Coverage Report (Frontend)

## 1. Загальне покриття

- Statements / Instructions: ~40–41%
- Branches: ~30%
- Functions / Methods: ~31%
- Lines: ~40%

---

## 2. Аналіз покриття коду

### ✔ Добре покриті частини

- `src/helper/getHref.js`
- `src/helper/search.js`
- `src/helper/setLink.js`

Ці модулі мають unit-тести, які перевіряють:

- коректну роботу з валідними даними
- edge cases:
  - `null`
  - `undefined`
  - пусті рядки
  - пробіли
  - спеціальні символи
- різні типи даних (string, number)

Особливо добре покрито:
- `getHref.js` → 100% mutation coverage
- `search.js` → основна бізнес-логіка

---

### ⚠ Слабко покриті частини

- React UI компоненти (форми, сторінки)
- Redux reducers (частково)
- Redux sagas (асинхронна логіка)
- великі контейнерні компоненти

---

## 3. Причини неповного покриття

- UI логіка залежить від React lifecycle
- Redux Saga містить side effects та асинхронність
- частина гілок виконується тільки в специфічних сценаріях
- основний фокус — unit tests (без інтеграційних тестів)

---

## 4. Mutation Testing

- Загальний mutation score: **75.56%**
- Кількість мутантів: **45**

### Результати:

-  Killed mutants: 12
-  Survived mutants: 4
-  Timeout: 22
-  No coverage: 7

---

## 5. Аналіз mutation testing

Після додавання edge-case тестів:

- значно покращено покриття
- перевірено критичні сценарії:
  - `null / undefined`
  - пусті значення
  - trimming рядків
  - регістронезалежність
  - числові значення
  - неіснуючі поля
- для `getHref.js` досягнуто 100% mutation coverage

---

## 6. Відомі обмеження

- 4 мутанти залишились alive
- 22 timeout (можливі довгі або складні сценарії)
- 7 no coverage (частини коду не викликаються тестами)
- UI та async логіка не покрита unit тестами

---

## 7. Висновок

Проєкт має:

- стабільні unit тести для helper функцій
- mutation score ~75%, що перевищує мінімальний поріг (50%)
- перевірку основних та edge-case сценаріїв
- достатній рівень якості тестування frontend логіки

---

## 8. Скріншоти

### Mutation testing report
<img width="1911" height="640" alt="image" src="https://github.com/user-attachments/assets/b21ec5e3-ce92-49e7-a0a6-6417bfb457e7" />

### Coverage table
<img width="1913" height="929" alt="image" src="https://github.com/user-attachments/assets/69ecc3e3-da22-4d59-8353-5ce22847fc06" />
<img width="1918" height="910" alt="image" src="https://github.com/user-attachments/assets/7d436492-ed18-4381-bf50-4a2c4e0d500a" />



---

## 9. Результати запуску тестів

### Unit tests результат

- Test Suites: **34 passed, 34 total**
- Tests: **156 passed, 156 total**
- Snapshots: **0 total**
- Time: **13.753 s**

---

## 10. Висновок по тестуванню

Всі unit-тести успішно пройдені:

- ✔ 100% тестових наборів виконано без помилок
- ✔ Всі 156 тестів пройшли успішно
- ✔ Регресій та падінь не виявлено
- ✔ Стабільна робота helper-функцій підтверджена

Це свідчить про те, що основна бізнес-логіка frontend частини працює коректно та стабільно
