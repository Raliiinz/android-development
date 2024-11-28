package ru.itis.androiddevelopment.repository

import ru.itis.androiddevelopment.model.Answer
import ru.itis.androiddevelopment.model.Question

object QuestionsRepository {
    private val questions = mutableListOf<Question>()

    init {
        val shoppingFrequencyQuestion = Question(
            id = 1,
            text = "Как часто вы посещаете магазины за покупками?",
            answers = mutableListOf(
                Answer("2 раза в день", false),
                Answer("Ежедневно", false),
                Answer("1-3 раза в неделю", false),
                Answer("1 раз в две недели", false),
                Answer("Раз в месяц", false),
                Answer("Реже", false),
                Answer("Нет правильного ответа", false)
            )
        )

        val transportPreferenceQuestion = Question(
            id = 2,
            text = "Какую форму общественного транспорта считаете наиболее удобным?",
            answers = mutableListOf(
                Answer("Метро", false),
                Answer("Трамвай/троллейбус", false),
                Answer("Автобус", false),
                Answer("Железнодорожный транспорт", false),
                Answer("Пешком", false),
                Answer("Такси", false),
                Answer("Нет правильного ответа", false)
            )
        )

        val socialSituationQuestion = Question(
            id = 3,
            text = "Какую социальную ситуацию считаете оптимальной для жизни?",
            answers = mutableListOf(
                Answer("Город", false),
                Answer("Малый город", false),
                Answer("Село", false),
                Answer("Вилла в пригороде", false),
                Answer("Дом на даче", false),
                Answer("Нет правильного ответа", false)
            )
        )

        val cinemaFrequencyQuestion = Question(
            id = 4,
            text = "Как часто вы посещаете кинотеатры?",
            answers = mutableListOf(
                Answer("Каждую неделю", false),
                Answer("Ежемесячно", false),
                Answer("Раз в два месяца", false),
                Answer("Раз в три месяца", false),
                Answer("Реже", false),
                Answer("Никогда", false),
                Answer("Нет правильного ответа", false)
            )
        )


        val vacationPreferenceQuestion = Question(
            id = 5,
            text = "Какую форму отдыха считаете наиболее предпочтительной?",
            answers = mutableListOf(
                Answer("Путешествие в другой город", false),
                Answer("Дома с семьей", false),
                Answer("Спать весь день", false),
                Answer("Мастер-классы", false),
                Answer("Путешествие за границу", false),
                Answer("Отдых на даче", false),
                Answer("Курортный отдых", false),
                Answer("Развлекательный отдых", false),
                Answer("Нет правильного ответа", false)
            )
        )


        val sportPreferenceQuestion = Question(
            id = 6,
            text = "Какую форму спорта считаете наиболее предпочтительной для занятий?",
            answers = mutableListOf(
                Answer("Бег", false),
                Answer("Волейбол", false),
                Answer("Баскетбол", false),
                Answer("Футбол", false),
                Answer("Танцы", false),
                Answer("Растяжка", false),
                Answer("Плавание", false),
                Answer("Гимнастика", false),
                Answer("Нет предпочтений", false),
                Answer("Нет правильного ответа", false)
            )
        )

        val entertainmentPreferenceQuestion = Question(
            id = 7,
            text = "Какую форму развлечения считаете наиболее предпочтительной?",
            answers = mutableListOf(
                Answer("Кино", false),
                Answer("Театр", false),
                Answer("Концерт", false),
                Answer("Игры на компьютере", false),
                Answer("Развлекательные программы на ТВ", false),
                Answer("Нет правильного ответа", false)
            )
        )

        val learningPreferenceQuestion = Question(
            id = 8,
            text = "Какую форму обучения считаете наиболее эффективной?",
            answers = mutableListOf(
                Answer("Очно-заочное образование", false),
                Answer("Заочное образование", false),
                Answer("Онлайн-образование", false),
                Answer("Семинары/тренинги", false),
                Answer("Самостоятельное изучение", false),
                Answer("Нет правильного ответа", false)
            )
        )

        val moviePreferences = Question(
            id = 9,
            text = "Какие жанры фильмов вы предпочитаете?",
            answers = mutableListOf(
                Answer("Боевики", false),
                Answer("Ужастик", false),
                Answer("Комедии", false),
                Answer("Фантастика", false),
                Answer("Романтика", false),
                Answer("Семейный", false),
                Answer("Приключение", false),
                Answer("Драмы", false),
                Answer("Нет правильного ответа", false)
            )
        )

        val eveningEntertainmentQuestion = Question(
            id = 10,
            text = "Какую форму развлечения считаете наиболее предпочтительной для вечера?",
            answers = mutableListOf(
                Answer("Фильмы", false),
                Answer("Концерты", false),
                Answer("Игры", false),
                Answer("Театральные представления", false),
                Answer("Разговоры с друзьями", false),
                Answer("Нет правильного ответа", false)
            )
        )

        val singerPreferences = Question(
            id = 11,
            text = "Какие певцы вы предпочитаете?",
            answers = mutableListOf(
                Answer("Бритни Спирс", false),
                Answer("Мадонна", false),
                Answer("Егор Крид", false),
                Answer("Тимати", false),
                Answer("Джастин Бибер", false),
                Answer("Адель", false),
                Answer("Меладзе", false),
                Answer("Дима Билан", false),
                Answer("Майкл Джордан", false),
                Answer("Эминем", false),
                Answer("Нет правильного ответа", false)
            )
        )

        val musicPreferences = Question(
            id = 12,
            text = "Какие жанры музыки вы предпочитаете?",
            answers = mutableListOf(
                Answer("Рок", false),
                Answer("Поп", false),
                Answer("Хип-хоп", false),
                Answer("Классическая музыка", false),
                Answer("Нет правильного ответа", false)
            )
        )

        questions.add(shoppingFrequencyQuestion)
        questions.add(transportPreferenceQuestion)
        questions.add(socialSituationQuestion)
        questions.add(cinemaFrequencyQuestion)
        questions.add(vacationPreferenceQuestion)
        questions.add(sportPreferenceQuestion)
        questions.add(entertainmentPreferenceQuestion)
        questions.add(learningPreferenceQuestion)
        questions.add(moviePreferences)
        questions.add(eveningEntertainmentQuestion)
        questions.add(singerPreferences)
        questions.add(musicPreferences)
    }
    fun getQuestions(): MutableList<Question> {
        return questions
    }

    fun getQuestionTextById(id: Int): String? {
        return questions.find { it.id == id }?.text
    }


    fun getAnswersByQuestionId(questionId: Int): MutableList<Answer>? {
        return questions.find { it.id == questionId }?.answers
    }
}
