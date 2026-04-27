# Coverage Report

## 1. Загальне покриття (Frontend)

- Statements / Instructions: 40.24%
- Branches: 30%
- Functions / Methods: 31.49%
- Lines: 40.69%

---

## 2. Аналіз покриття коду

### ✔ Добре покриті частини:
- `src/helper/getHref.js`
- `src/helper/setLink.js`
- `src/helper/search.js`
- базові утиліти (helper functions)

Ці модулі мають unit-тести з перевіркою:
- нормальних сценаріїв (positive cases)
- edge cases (null, empty, whitespace)

---

###  Слабко покриті частини:
- UI компоненти (форми, сторінки)
- Redux reducers (частково)
- Redux sagas (асинхронна логіка)
- великі компоненти сторінок

---

## 3. Причини неповного покриття

- частина логіки виконується тільки в UI (React lifecycle)
- Redux/Saga залежить від API та side effects
- деякі гілки коду виконуються лише при специфічних сценаріях
- поточний рівень тестів — unit level (без інтеграційних тестів UI)

---

## 4. Mutation Testing

- Загальний mutation score: **97.62%**
- getHref.js: **100%**
- search.js: ~95%+

### Результати:
- ✔ killed mutants: 19
- ✔ survived mutants: 0 (у helper частині)
- ✔ високий рівень якості тестів

---

## 5. Аналіз mutation testing

- початково було багато "живих мутантів"
- після додавання edge-case тестів вони були успішно “вбиті”
- покриті сценарії:
  - null / undefined
  - пусті значення
  - trimming рядків
  - формування HTML

---

## 6. Відомі обмеження

- 1 no coverage у `search.js`
- частина UI не покрита unit тестами
- асинхронна логіка (sagas) потребує інтеграційного тестування

---

## 7. Висновок

Проєкт має:
- стабільні unit тести для helper функцій
- високий mutation score (97%+)
- перевірку edge cases
- базове, але якісне покриття логіки

Тести достатні для перевірки core logic frontend helper модулів.

---

## 8. Скріншоти

###  Mutation testing report


<!-- INSERT SCREENSHOT HERE -->
📸 Screenshot 1:<img width="1906" height="821" alt="image" src="https://github.com/user-attachments/assets/539a7d5d-52ae-4242-b4be-74ba66b46060" />

📸 Screenshot 2: <img width="1908" height="869" alt="image" src="https://github.com/user-attachments/assets/16de98cd-751a-4621-9830-2ee9d6209f2b" />

<!-- INSERT SCREENSHOT HERE -->
📸 Screenshot 3: Overall coverage table
<img width="1887" height="926" alt="image" src="https://github.com/user-attachments/assets/e5cc3d36-5071-4d24-8983-741d24593486" />
<img width="1900" height="905" alt="image" src="https://github.com/user-attachments/assets/1de306e0-39bb-4671-a3d2-dcfe281a1a04" />
<img width="1917" height="935" alt="image" src="https://github.com/user-attachments/assets/79f649f8-df3d-4621-993a-396353762176" />

---

## 9. Додатково

- Тести проходять: 159 / 159 ✔
- Test suites: 34 / 34 ✔
- Mutation testing запущено та проаналізовано ✔
