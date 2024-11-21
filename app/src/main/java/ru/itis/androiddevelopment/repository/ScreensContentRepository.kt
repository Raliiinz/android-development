package ru.itis.androiddevelopment.repository

import ru.itis.androiddevelopment.model.HoldersData
import kotlin.random.Random
import kotlin.random.nextInt

object ScreensContentRepository {
    private val list = mutableListOf<HoldersData>()
    private var films = listOf<HoldersData>()

    init {
        films = arrayListOf(
            HoldersData(
                id = 2,
                headerText = "Король Лев",
                descText ="Этот классический мультфильм Disney рассказывает историю львенка Симбы, который после трагической смерти своего отца, Муфасы, уходит в изгнание. Встречая новых друзей, таких как Тимон и Пумба, Симба учится наслаждаться жизнью. Однако, когда его королевство оказывается под угрозой из-за злого дяди Скара, Симба должен вернуться, чтобы восстановить справедливость и занять свое место на троне.",
                imageUrl = "https://img.goodfon.ru/original/2560x1600/9/ae/disney-the-lion-king-korol.jpg"
            ),
            HoldersData(
                id = 3,
                headerText = "Русалочка",
                descText = "Ариэль — молодая русалка, которая мечтает стать человеком после встречи с принцем Эриком. Она заключает сделку с морской ведьмой Урсулой, чтобы получить человеческие ноги в обмен на свой голос. Мультфильм исследует темы жертвенности, любви и поиска своего места в мире. Песни, такие как \"Under the Sea\" и \"Part of Your World\", стали классикой.",
                imageUrl = "https://cdn.culture.ru/images/56b04aa7-88ac-50d0-8d25-91696922225e"
            ),

            HoldersData(
                id = 4,
                headerText = "История игрушек",
                descText = "Этот groundbreaking мультфильм от Pixar стал первым полностью компьютерным анимационным фильмом. Он рассказывает о том, как игрушки оживают, когда их хозяин, Энди, не смотрит. В центре сюжета — Джесси, Базз Лайтер и Вуди, которые сталкиваются с вопросами дружбы и преданности, когда Энди готовится отправиться в колледж.",
                imageUrl = "https://m.media-amazon.com/images/M/MV5BMjA2MzQzOTUyN15BMl5BanBnXkFtZTcwMDIzMzc4Mg@@._V1_.jpg"
            ),

            HoldersData(
                id = 5,
                headerText = "Гадкий я",
                descText = "Гру — злодей, который планирует украсть луну с помощью своей армии миньонов. Однако его жизнь меняется, когда он принимает на себя опеку трех сироток. Мультфильм исследует темы семьи и искупления через комедийные приключения Гру и его новых дочерей.",
                imageUrl = "https://puzzleit.ru/files/puzzles/16/16226/_original.jpg"
            ),
            HoldersData(
                id = 6,
                headerText = "Коко",
                descText = "Мигель — молодой музыкант из семьи, которая запрещает музыку. Он решает отправиться в мир мертвых во время Дня мертвых, чтобы узнать правду о своей семье и следовать своей мечте стать музыкантом. Мультфильм полон красочных визуалов и трогательных моментов о важности семьи и памяти.",
                imageUrl = "https://cdn.culture.ru/images/3fefd824-a6e7-5a98-8ea1-ffd5406d8abd"),

            HoldersData(
                id = 7,
                headerText = "Суперсемейка",
                descText = "Семья супергероев сражается с повседневной жизнью и злодеем Syndrome, который хочет уничтожить всех супергероев. Мультфильм наполнен экшеном и юмором, исследует динамику семьи и важность принятия своих уникальных способностей.",
                imageUrl = "https://cdn.culture.ru/images/a57ca373-e916-54c9-8089-2c4cd459e832"
            ),

            HoldersData(
                id = 8,
                headerText = "Тайная жизнь домашних животных",
                descText = "Мультфильм показывает, что происходит с домашними животными, когда их владельцы покидают дом. Главный герой Макс — собака, которая пытается спасти своего друга от злого кота и его команды диких животных в большом городе.",
                imageUrl = "https://cdn.culture.ru/images/39311e9a-4d84-54f3-b8f9-9ac93135b9b0"
            ),
            HoldersData(
                id = 9,
                headerText = "Как приручить дракона",
                descText = "Иккинг — молодой викинг, который мечтает стать воином, но вместо этого находит дружбу с драконом по имени Беззубик. Вместе они меняют взгляды своего народа на драконов и учатся работать вместе ради общего блага.",
                imageUrl = "https://cdn1.ozone.ru/s3/multimedia-3/6213214575.jpg"
            ),

            HoldersData(
                id = 10,
                headerText = "Моана",
                descText = "Молодая полинезийская девушка Моана отправляется в опасное морское путешествие, чтобы спасти свой народ от исчезновения ресурсов. Она находит поддержку у полубога Мауи и открывает в себе силу следовать своему сердцу.",
                imageUrl = "https://irecommend.ru/sites/default/files/imagecache/copyright1/user-images/390438/kXMbXzZwerSNpMULqCOHCw.jpg"
            ),

            HoldersData(
                id = 11,
                headerText = "Зверополис",
                descText = "В мире, где животные живут в гармонии, кролик-полицейский Джуди Хопс и лис-обманщик Ник Уайлд объединяются для раскрытия заговоров в Зверополисе. Мультфильм затрагивает темы предвзятости и дружбы через захватывающий детективный сюжет.",
                imageUrl = "https://img.championat.com/s/1350x900/news/big/n/b/prodolzhenie-zveropolisa-obeschayut-sdelat-kruche-pervoj-chasti_1697888763207109113.jpg"
            ),

            HoldersData(
                id = 12,
                headerText = "Большой герой 6",
                descText = "После трагической потери своего брата, гениальный подросток Хиро Хамада находит утешение в своем роботе Бэймаксе. Вместе они собирают команду друзей-супергероев для борьбы с злодеем, угрожающим их городу.",
                imageUrl = "https://images.bauerhosting.com/legacy/empire-tmdb/films/177572/images/2BXd0t9JdVqCp9sKf6kzMkr7QjB.jpg?ar=16%3A9&amp;fit=crop&amp;crop=top&amp;auto=format&amp;w=undefined&amp;q=80"
            ),

            HoldersData(
                id = 13,
                headerText = "Пингвины Мадагаскара",
                descText = "Пингвины из \"Мадагаскара\" получают собственное приключение, когда они сталкиваются с зловещим планировщиком по имени Доктор Октавиус Брайн. Смешные и умные пингвины используют свои навыки шпионов для спасения мира.",
                imageUrl = "https://i.pinimg.com/originals/78/e2/0e/78e20ed2cd794c516c090bcd20e38e6d.jpg"
            ),

            HoldersData(
                id = 14,
                headerText = "Соник в кино",
                descText = "Соник — супербыстрый ежик из другого мира, который попадает на Землю и должен остановить злого доктора Роботника от использования его силы для мирового господства. В фильме много экшена и юмора, а также важные уроки о дружбе и принятии себя.",
                imageUrl = "https://avatars.mds.yandex.net/get-kinopoisk-post-img/2423210/3e7899130ad2129904726a012f875a94/1920x1080"
            ),

            HoldersData(
                id = 15,
                headerText = "Щенячий патруль",
                descText = "Щенята-спасатели отправляются в большое приключение за пределы своего города, чтобы спасти его от угрозы злого бизнесмена. Каждый щенок использует свои уникальные навыки для решения задач и защиты своих друзей.",
                imageUrl = "https://avatars.mds.yandex.net/i?id=a6aaccc41899c71871e594253bec82d5_l-13013568-images-thumbs&n=13"
            ),

            HoldersData(
                id = 16,
                headerText = "Форрест Гамп",
                descText = "Эта трогательная история о простом человеке с низким IQ, который становится свидетелем и участником ключевых событий американской истории. Форрест, сыгранный Томом Хэнксом, вдохновляет всех вокруг своей добротой и невинностью, несмотря на все трудности.",
                imageUrl = "https://upload.wikimedia.org/wikipedia/en/6/67/Forrest_Gump_poster.jpg"
            ),

            HoldersData(
                id = 17,
                headerText = "Зеленая миля",
                descText = "Драма о тюремной жизни в 1930-х годах, рассказывающая историю охранника Джона Кофи, чернокожего мужчины с необычными способностями. Фильм исследует темы справедливости, сострадания и человеческой природы.",
                imageUrl = "https://avatars.mds.yandex.net/i?id=ec134ff73f5619eb31c0f0384f329b1f_l-12935885-images-thumbs&n=13"
            ),

            HoldersData(
                id = 18,
                headerText = "Сияние",
                descText = "Психологический триллер Стэнли Кубрика по роману Стивена Кинга о писателе Джеке Торренсе, который становится смотрителем заброшенного отеля. По мере того как он теряет рассудок, его семья оказывается в опасности. Фильм стал классикой жанра ужасов.",
                imageUrl = "https://avatars.mds.yandex.net/i?id=af44e73929f9fc53bf17c259c762d3fb_l-3921781-images-thumbs&n=13"
            ),

            HoldersData(
                id = 19,
                headerText = "Властелин колец",
                descText = "Эпическая фэнтези-сага о путешествии хоббита Фродо и его друзей, которые стремятся уничтожить могущественное кольцо. Фильм исследует темы дружбы, жертвенности и борьбы со злом.",
                imageUrl = "https://static.okko.tv/images/v4/dc354caa-2bda-4fad-a1b6-862bb9d25665"
            ),

            HoldersData(
                id = 20,
                headerText = "Парк Юрского периода",
                descText = "Научно-фантастический фильм о парке аттракционов, где динозавры были воссозданы с помощью генной инженерии. Когда система безопасности выходит из строя, посетители оказываются в смертельной опасности. Фильм стал классикой жанра и открыл новую эру визуальных эффектов.",
                imageUrl = "https://upload.wikimedia.org/wikipedia/en/e/e7/Jurassic_Park_poster.jpg"
            ),

            HoldersData(
                id = 21,
                headerText = "Матрица",
                descText = "Научно-фантастический фильм о Нео, хакере, который открывает правду о реальности и его роли в борьбе против машин, контролирующих человечество. Фильм задает философские вопросы о свободе выбора и реальности.",
                imageUrl = "https://upload.wikimedia.org/wikipedia/en/c/c1/The_Matrix_Poster.jpg"
            ),

            HoldersData(
                id = 22,
                headerText = "Интерстеллар",
                descText = "Научно-фантастический фильм Кристофера Нолана о команде астронавтов, которые отправляются через червоточину в поиске нового дома для человечества. Фильм исследует темы любви, времени и выживания.",
                imageUrl = "https://upload.wikimedia.org/wikipedia/en/b/bc/Interstellar_film_poster.jpg"
            ),

            HoldersData(
                id = 23,
                headerText = "Гладиатор",
                descText = "История генерала Максимуса, преданного императора, который становится гладиатором после предательства. Фильм сочетает элементы драмы и экшена, исследуя темы мести и искупления в древнем Риме.",
                imageUrl = "https://avatars.mds.yandex.net/i?id=e5ef754143cb526bf3dca9f5c3ed6ba9_l-4809805-images-thumbs&n=13"
            ),

            HoldersData(
                id = 24,
                headerText = "Список Шиндлера",
                descText = "Основанный на реальных событиях фильм о немецком бизнесмене Оскаре Шиндлере, который спас более тысячи евреев во время Холокоста. Фильм затрагивает темы человеческой доброты и морали в условиях ужаса войны.",
                imageUrl = "https://images.kinorium.com/movie/poster/98467/w1500_44942974.jpg"
            ),
            HoldersData(
                id = 25,
                headerText = "Мементо",
                descText = "Психологический триллер о человеке по имени Леонарда, который страдает от кратковременной потери памяти и пытается раскрыть убийство своей жены. Фильм рассказывается в нестандартной структуре, что делает его уникальным и запоминающимся.",
                imageUrl = "https://avatars.mds.yandex.net/i?id=a4d4e1c14de7f5afc96cab6a1d7f2d8e_l-5258229-images-thumbs&n=13"
            ),

            HoldersData(
                id = 26,
                headerText = "В поисках Немо",
                descText = "Анимационный фильм Pixar о рыбке-клоуне Марлине, которая отправляется в опасное путешествие по океану, чтобы спасти своего сына Немо. Фильм полон приключений и затрагивает темы семьи и дружбы.",
                imageUrl = "https://thumbs.dfs.ivi.ru/storage3/contents/3/3/8525720fcb579c5fb9abd689c03dfd.jpg"
            ),
            HoldersData(
                id = 27,
                headerText = "Крестный отец",
                descText = "Эпическая драма о семье Корлеоне, итало-американском криминальном клане. Во главе с Вито Корлеоне, фильм исследует темы власти, преданности и семейных уз, когда его сын Майкл пытается дистанцироваться от преступного мира, но в конечном итоге оказывается втянутым в дела семьи.",
                imageUrl = "https://images.kinorium.com/movie/poster/63924/w1500_50809418.jpg"
            ),
            HoldersData(
                id = 28,
                headerText = "Пробуждение жизни",
                descText = "Анимационный фильм Ричарда Линклейтера о молодом человеке, который исследует философские идеи через свои сны. Фильм использует уникальную технику ротоскопии и затрагивает темы сознания и реальности.",
                imageUrl = "https://i.ytimg.com/vi/tqGhM_MFlbc/maxresdefault.jpg"
            ),
            HoldersData(
                id = 29,
                headerText = "Легенда о пианисте",
                descText = "Драма о мальчике по имени 1900, который родился на океанском лайнере и стал гениальным пианистом. Фильм исследует его жизнь и выбор между свободой и безопасностью.",
                imageUrl = "https://static1-repo.aif.ru/1/3e/1758870/c/5e5a97f40448bd8d35de6ebf51429dc0.jpg"
            ),
            HoldersData(
                id = 30,
                headerText = "Достучаться до небес",
                descText = "Немецкая комедия-драма о двух смертельно больных мужчинах, которые решают осуществить свои мечты перед смертью. Фильм полон юмора и трогательных моментов о жизни и дружбе.",
                imageUrl = "https://avatars.mds.yandex.net/i?id=e12a02b1a59ac0f9a3bcdc9790f60acb_l-5151246-images-thumbs&n=13"
            ),
            HoldersData(
                id = 31,
                headerText = "Джуманджи: Зов джунглей",
                descText = "Современная интерпретация классической истории о настольной игре, которая переносит игроков в джунгли. Четыре подростка становятся персонажами игры и должны работать вместе, чтобы выжить.",
                imageUrl = "https://i.ytimg.com/vi/BDmAh9AGnVE/maxresdefault.jpg"
            ),
            HoldersData(
                id = 32,
                headerText = "Звездные войны",
                descText = "Первый фильм оригинальной трилогии 'Звездные войны', рассказывающий о борьбе повстанцев против Галактической Империи. Фильм стал культовым явлением и основой для целой франшизы.",
                imageUrl = "https://avatars.mds.yandex.net/i?id=4e234d5dd895d299171a72d9f66307cf_l-9181668-images-thumbs&n=13"
            ),
            HoldersData(
                id = 33,
                headerText = "В поисках счастья",
                descText = "Основанный на реальных событиях фильм о Крисе Гарднере, который борется с бездомностью и трудностями ради лучшей жизни для себя и своего сына. Фильм вдохновляет своей историей упорства и надежды.",
                imageUrl = "https://avatars.mds.yandex.net/i?id=d54766657a19b68c45314c73458b36bc_l-9694985-images-thumbs&n=13"
            ),
            HoldersData(
                id = 34,
                headerText = "Человек-паук",
                descText = "Анимационный фильм о молодом человеке по имени Майлз Моралес, который становится новым Человеком-пауком и встречает других Человека-пауков из различных вселенных. Фильм отмечен уникальным стилем анимации и темами самопринятия и heroism.",
                imageUrl = "https://static.okko.tv/images/v4/d80d0763-cd45-4c1a-b00b-ab2bba3575e8"
            ),
            HoldersData(
                id = 35,
                headerText = "Титаник",
                descText = "Романтическая драма о любви между Джеком и Розой, пассажирами на борту легендарного лайнера Титаник. Фильм сочетает реальные исторические события с вымышленной историей любви, показывая как классовые различия и трагедия могут переплетаться.",
                imageUrl = "https://avatars.mds.yandex.net/i?id=f4124905b0590606693868f491d1869e_l-10415038-images-thumbs&n=13"
            ),
            HoldersData(
                id = 36,
                headerText = "Во все тяжкие",
                descText = "Драма о школьном учителе химии Уолтере Уайте, который, узнав о своей смертельной болезни, решает заняться производством метамфетамина, чтобы обеспечить финансовое будущее своей семьи. Сериал показывает его трансформацию из обычного человека в опасного наркобарона и исследует моральные дилеммы и последствия его выбора.",
                imageUrl = "https://cdn.ananasposter.ru/image/cache/catalog/poster/film/81/15821-1000x830.jpg"
            ),
            HoldersData(
                id = 37,
                headerText = "Черное зеркало",
                descText = "Антологический сериал, каждая серия которого представляет собой отдельную историю, исследующую темные стороны технологий и их влияние на общество. Сюжеты варьируются от dystopian до сатирических, поднимая важные вопросы о будущем человечества и моральных аспектах технологического прогресса.",
                imageUrl = "https://avatars.dzeninfra.ru/get-zen_doc/9709627/pub_649c1ae02c361c1c047f435e_649c1af2405ed85aae0f1669/scale_1200"
            ),
            HoldersData(
                id = 38,
                headerText = "Друзья",
                descText = "Комедийный сериал о шести друзьях, живущих в Нью-Йорке. Сюжет фокусируется на их личных и профессиональных отношениях, любовных перипетиях и повседневной жизни. Сериал стал культовым благодаря своему юмору, запоминающимся персонажам и популярным фразам.",
                imageUrl = "https://irecommend.ru/sites/default/files/imagecache/copyright1/user-images/270702/XvajKOwPKfWJK4oU2EpzBQ.jpg"
            ),
            HoldersData(
                id = 39,
                headerText = "Странные дела",
                descText = "Научно-фантастический хоррор-сериал, действие которого происходит в 1980-х годах в вымышленном городе Хокинс, штат Индиана. Сюжет начинается с исчезновения мальчика, что приводит к раскрытию секретов правительства и появлению сверхъестественных существ. Сериал сочетает элементы ностальгии, дружбы и борьбы со злом.",
                imageUrl = "https://i.ytimg.com/vi/_vKRi7nSB5M/maxresdefault.jpg"
            ),
            HoldersData(
                id = 40,
                headerText = "Игра престолов",
                descText = "Эпический фэнтези-сериал, основанный на серии романов Джорджа Р. Р. Мартина 'Песнь льда и огня'. Сюжет разворачивается в вымышленном мире Вестероса, где несколько благородных домов сражаются за Железный трон. Сериал исследует темы власти, предательства, чести и борьбы за выживание, а также включает в себя элементы магии и мифологии.",
                imageUrl = "https://i.ytimg.com/vi/hdxHU-SJ_K0/maxresdefault.jpg"
            )

            )
    }

    fun getAllFilms(): MutableList<HoldersData> = list

    fun getFilms(filmsCount: Int): MutableList<HoldersData> {
        if (list.isEmpty()) fillList(filmsCount)
        return list
    }

    fun getFilmById(id: Int): HoldersData? {
        return list.find { it.id == id }
    }

    private fun fillList(filmsCount: Int) {
        list.clear()
        list.add(HoldersData(
            id = 1,
            headerText = "Some text",
            descText = "",
            imageUrl = ""
        ))
        for (i in 0..filmsCount) {
            val book = films.find { it.id == i }
            if (book != null) {
                list.add(book)
            }
        }
    }

    fun addFilms(count: Int) {
        repeat(count) {
            val randomFilmIndex = Random.nextInt(films.size)
            val randomFilm = films[randomFilmIndex]
            val randomInsertIndex = Random.nextInt(1 until list.size + 1)
            list.add(randomInsertIndex, randomFilm)
        }
    }


    fun removeFilms(count: Int) {
        val currentCount = list.size
        val toRemoveCount = minOf(count, currentCount)
        if (count > currentCount) {
            repeat(toRemoveCount - 1) {
                removeRandomFilm()
            }
        } else {
            repeat(toRemoveCount) {
                removeRandomFilm()
            }
        }
    }

    private fun removeRandomFilm() {
        if (list.isNotEmpty()) {
            val randomIndex = Random.nextInt(list.size)
            list.removeAt(randomIndex)
        }
    }

    fun removeFilmById(id: Int) {
        list.removeAt(id)
    }
}
