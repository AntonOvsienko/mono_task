

Разработать сервис продажи билетов на рейсовый автобусы. Сервис состоит из двух частей Система управления билетами Система оплаты

Система оплаты имеет 2 сервиса

    Сервис оплаты
    Сервис получения статуса платежа

Сервис оплаты на вход принимает фио клиента + сумма, на выходе возвращает уникальный идентификатор платежа. Сервис получения статусов на вход принимает уникальный идентификатор платежа, на выходе случайным образом отдает 1 из статусов NEW/FAILED/DONE

Система управления билетами состоит

    Список рейсов (идентификатор/Откуда/Куда/Время отправления/Стоимость/количество доступных билетов) (способ заполнения не важен, хранение в базе)
    Сервис покупки билета. На вход: ФИО/ идентификатор рейса. На выходе: идентификатор билета.
    Сервис информации по билету. На вход: ид билета, на выходе Информация о рейсе и статус платежа

Процесс покупки билета. При заказе билета необходимо сохранить билет в базу, вычесть билет из количества доступных билетов на рейсе и создать платеж в платежной системе. Дополнительно необходимо реализовать обработчик по расписанию, который будет проверять состояние всех билетов, у которых состояние платежа NEW. Если получаем статус FAIL, возвращаем количество доступных билетов на рейсе и больше не опрашиваем статус. Если получаем DONE, то ничего не делаем и больше не опрашиваем статус.

Общение между системой оплаты и системой управления билетами осуществлять по http протоколу. Реализовывать на спринг бут.

Покрытие тестами приветствуется.
