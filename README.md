# Основы программной инженерии
## Лабораторная работа №3
**Вариант 926**

Написать сценарий для утилиты Gradle, реализующий компиляцию, тестирование и упаковку в jar-архив кода проекта из лабораторной работы №3 по дисциплине "Веб-программирование".

Каждый этап должен быть выделен в отдельный блок сценария; все переменные и константы, используемые в сценарии, должны быть вынесены в отдельный файл параметров; MANIFEST.MF должен содержать информацию о версии и о запускаемом классе.

**Cценарий должен реализовывать следующие цели (targets):**
- compile -- компиляция исходных кодов проекта.
- build -- компиляция исходных кодов проекта и их упаковка в исполняемый jar-архив. Компиляцию исходных кодов реализовать посредством вызова цели compile.
- clean -- удаление скомпилированных классов проекта и всех временных файлов (если они есть).
- test -- запуск junit-тестов проекта. Перед запуском тестов необходимо осуществить сборку проекта (цель build).
- music - воспроизведение музыки по завершению сборки (цель build).
- history - если проект не удаётся скомпилировать (цель compile), загружается предыдущая версия из репозитория svn. Операция повторяется до тех пор, пока проект не удастся собрать, либо не будет получена самая первая ревизия из репозитория. Если такая ревизия найдена, то формируется файл, содержащий результат операции diff для всех файлов, измёненных в ревизии, следующей непосредственно за последней работающей. 

**Вопросы к защите лабораторной работы:**
- Тестирование ПО. Цель тестирования, виды тестирования.
- Модульное тестирование, основные принципы и используемые подходы.
- Пакет JUnit, основные API.
- Системы автоматической сборки. Назначение, принципы работы, примеры систем.
- Утилита make. Make-файлы, цели и правила.
- Утилита Gralde. Сценарии сборки, цели и команды.

