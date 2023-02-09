# GBAndroidPro
Профессиональная разработка Android-приложений (DICTIONARY)

1. Встроен Room в качестве БД и создан отдельный экран для истории.
![image](https://user-images.githubusercontent.com/95467816/217824534-919c778c-0456-443f-bea5-9a2424b92829.png)

2. Создан экран с описанием слова. использован Coil для загрузки изображения.
3. Доработан экран с описанием слова: фото + озвучка + транскрипция.
![image](https://user-images.githubusercontent.com/95467816/217825135-439038c7-335d-4704-bd22-38906608aaff.png)

4. Использована передача функции как параметра:
val onSearchDialogClose: (String?) -> Unit = { word: String? ->
                if (word != null) binding.searchEditText.setText(word)
                setBlur(binding.root, false)
            }

val searchDialogFragment = SearchDialogFragment.newInstance(onSearchDialogClose)
