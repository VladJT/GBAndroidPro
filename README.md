В проект добавлены ViewModel, LiveData и Dagger.

2. * Создана проверка на наличие интернета (добавлен NetworkModule в DI + подписка на изменение статуса сети в MainViewModel). 
При остутствии связи запрос идет к локальному репозиторию.

3. * Реализовано сохранение состояние во ViewModel:через lastCustomNonConfigurationInstance и onRetainCustomNonConfigurationInstance
![image](https://user-images.githubusercontent.com/95467816/215256369-c5df2cd9-b0c5-4791-8a51-1131b6e1b827.png)
