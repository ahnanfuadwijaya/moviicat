@file:Suppress("SpellCheckingInspection")

package id.riverflows.moviicat.util

import id.riverflows.moviicat.data.entity.GenreEntity
import id.riverflows.moviicat.data.entity.MovieDetailEntity
import id.riverflows.moviicat.data.entity.TvDetailEntity

object DataDummy {
    private val movieList = mutableListOf<MovieDetailEntity>()
    private val tvList = mutableListOf<TvDetailEntity>()
    init {
        movieList.addAll(generateMovieData())
        tvList.addAll(generateTvData())
    }

    private fun generateMovieData(): List<MovieDetailEntity>{
        val list = mutableListOf<MovieDetailEntity>()
        list.add(
                MovieDetailEntity(
                        399566,
                        "Godzilla vs. Kong",
                        listOf(GenreEntity( 28, "Action"), GenreEntity(878, "Science Fiction")),
                        "In a time when monsters walk the Earth, humanity’s fight for its future sets Godzilla and Kong on a collision course that will see the two most powerful forces of nature on the planet collide in a spectacular battle for the ages.",
                        5405.33f,
                        "godzilla_vs_kong",
                        "2021-03-24",
                        "Released",
                        8.3f
                )
        )
        list.add(
                MovieDetailEntity(
                        791373,
                        "Zack Snyder's Justice League",
                        listOf(GenreEntity(28, "Action"), GenreEntity(12, "Adventure"), GenreEntity(14,"Fantasy"), GenreEntity(878, "Science Fiction")),
                        "Determined to ensure Superman's ultimate sacrifice was not in vain, Bruce Wayne aligns forces with Diana Prince with plans to recruit a team of metahumans to protect the world from an approaching threat of catastrophic proportions.",
                        2743.895f,
                        "zack_snyders_justice_league",
                        "2021-03-18",
                        "Released",
                        8.5f
                )
        )
        list.add(
                MovieDetailEntity(
                        412656,
                        "Chaos Walking",
                        listOf(GenreEntity(878, "Science Fiction"), GenreEntity(28, "Action"), GenreEntity(12, "Adventure"), GenreEntity(53, "Thriller")),
                        "Two unlikely companions embark on a perilous adventure through the badlands of an unexplored planet as they try to escape a dangerous and disorienting reality, where all inner thoughts are seen and heard by everyone.", 2136.844f, "chaos_walking",
                        "2021-02-24",
                        "Released",
                        7.5f
                )
        )
        list.add(
                MovieDetailEntity(
                        460465,
                        "Mortal Kombat",
                        listOf(GenreEntity(14, "Fantasy"), GenreEntity(28, "Action"), GenreEntity(12, "Adventure")),
                        "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe.",
                        1643.792f,
                        "mortal_kombat",
                        "2021-04-07",
                        "Released",
                        7.5f
                )
        )
        list.add(MovieDetailEntity(
                527774,
                "Raya and the Last Dragon",
                listOf(GenreEntity(16, "Animation"), GenreEntity(12,"Adventure"), GenreEntity(14, "Fantasy"), GenreEntity(10751, "Family"), GenreEntity(28, "Action")),
                "Long ago, in the fantasy world of Kumandra, humans and dragons lived together in harmony. But when an evil force threatened the land, the dragons sacrificed themselves to save humanity. Now, 500 years later, that same evil has returned and it’s up to a lone warrior, Raya, to track down the legendary last dragon to restore the fractured land and its divided people.",
                1563.753f,
                "raya_and_the_last_dragon",
                "2021-03-03",
                "Released",
                8.3f
        ))
        list.add(MovieDetailEntity(
                664767,
                "Mortal Kombat Legends: Scorpion's Revenge",
                listOf(GenreEntity(16, "Animation"), GenreEntity(28, "Action"), GenreEntity(14, "Fantasy")),
                "After the vicious slaughter of his family by stone-cold mercenary Sub-Zero, Hanzo Hasashi is exiled to the torturous Netherrealm. There, in exchange for his servitude to the sinister Quan Chi, he’s given a chance to avenge his family – and is resurrected as Scorpion, a lost soul bent on revenge. Back on Earthrealm, Lord Raiden gathers a team of elite warriors – Shaolin monk Liu Kang, Special Forces officer Sonya Blade and action star Johnny Cage – an unlikely band of heroes with one chance to save humanity. To do this, they must defeat Shang Tsung’s horde of Outworld gladiators and reign over the Mortal Kombat tournament.",
                1258.857f,
                "mortal_kombat_legends_scorpions_revenge",
                "2020-04-12",
                "Released",
                8.4f
        ))
        list.add(MovieDetailEntity(
                644083,
                "Twist",
                listOf(GenreEntity(80, "Crime"), GenreEntity(18, "Drama"), GenreEntity(28, "Action")),
                "A Dicken’s classic brought thrillingly up to date in the teeming heartland of modern London, where a group of street smart young hustlers plan the heist of the century for the ultimate payday.",
                1179.433f,
                "twist",
                "2021-01-22",
                "Released",
                7.1f
        ))
        list.add(MovieDetailEntity(
                544401,
                "Cherry",
                listOf(GenreEntity(80, "Crime"), GenreEntity(18, "Drama")),
                "Cherry drifts from college dropout to army medic in Iraq - anchored only by his true love, Emily. But after returning from the war with PTSD, his life spirals into drugs and crime as he struggles to find his place in the world.",
                1090.901f,
                "cherry",
                "2021-02-26",
                "Released",
                7.6f
        ))
        list.add(MovieDetailEntity(
                587807,
                "Tom & Jerry",
                listOf(GenreEntity(35, "Comedy"), GenreEntity(10751, "Family"), GenreEntity(16, "Animation")),
                "Tom the cat and Jerry the mouse get kicked out of their home and relocate to a fancy New York hotel, where a scrappy employee named Kayla will lose her job if she can’t evict Jerry before a high-class wedding at the hotel. Her solution? Hiring Tom to get rid of the pesky mouse.",
                1085.913f,
                "tom_and_jerry",
                "2021-02-11",
                "Released",
                7.3f
        ))
        list.add(MovieDetailEntity(
                458576,
                "Monster Hunter",
                listOf(GenreEntity(14, "Fantasy"), GenreEntity(28, "Action"), GenreEntity(12, "Adventure")),
                "A portal transports Cpt. Artemis and an elite unit of soldiers to a strange world where powerful monsters rule with deadly ferocity. Faced with relentless danger, the team encounters a mysterious hunter who may be their only hope to find a way home.",
                1012.683f,
                "monster_hunter",
                "2020-12-03",
                "Released",
                7.1f
        ))
        list.add(MovieDetailEntity(
                581389,
                "Space Sweepers",
                listOf(GenreEntity(18, "Drama"), GenreEntity(14, "Fantasy"), GenreEntity(878, "Science Fiction")),
                "When the crew of a space junk collector ship called The Victory discovers a humanoid robot named Dorothy that's known to be a weapon of mass destruction, they get involved in a risky business deal which puts their lives at stake.",
                903.091f,
                "space_sweepers",
                "2021-02-05",
                "Released",
                7.2f
        ))
        return list
    }

    private fun generateTvData(): List<TvDetailEntity>{
        val list = mutableListOf<TvDetailEntity>()
        list.add(TvDetailEntity(
                88396,
                "The Falcon and the Winter Soldier",
                listOf(GenreEntity(10765, "Sci-Fi & Fantasy"), GenreEntity(10759, "Action & Adventure"), GenreEntity(18, "Drama"), GenreEntity(10768, "War & Politics")),
                "2021-03-19",
                "2021-04-16",
                "the_falcon_and_the_winter_soldier",
                "Following the events of “Avengers: Endgame”, the Falcon, Sam Wilson and the Winter Soldier, Bucky Barnes team up in a global adventure that tests their abilities, and their patience.",
                7013.338f,
                "Returning Series",
                7.9f
        ))
        list.add(TvDetailEntity(
                71712,
                "The Good Doctor",
                listOf(GenreEntity(18, "Drama")),
                "2017-09-25",
                "2021-03-29",
                "the_good_doctor",
                "A young surgeon with Savant syndrome is recruited into the surgical unit of a prestigious hospital. The question will arise: can a person who doesn't have the ability to relate to people actually save their lives",
                1881.58f,
                "Returning Series",
                8.6f
        ))
        list.add(TvDetailEntity(
                60735,
                "The Flash",
                listOf(GenreEntity(18, "Drama"), GenreEntity(10765, "Sci-Fi & Fantasy")),
                "2014-10-07",
                "2021-04-13",
                "the_flash",
                "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \"meta-human\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash.",
                1308.643f,
                "Returning Series",
                7.7f
        ))
        list.add(TvDetailEntity(
                95557,
                "Invincible",
                listOf(GenreEntity(16, "Animation"), GenreEntity(10759, "Action & Adventure"), GenreEntity(18, "Drama")),
                "2021-03-26",
                "2021-04-16",
                "invincible",
                "Mark Grayson is a normal teenager except for the fact that his father is the most powerful superhero on the planet. Shortly after his seventeenth birthday, Mark begins to develop powers of his own and enters into his father’s tutelage.",
                1313.434f,
                "Returning Series",
                8.9f
        ))
        list.add(TvDetailEntity(
                69050,
                "Riverdale",
                listOf(GenreEntity(9648, "Mystery"), GenreEntity(18, "Drama"), GenreEntity(80, "Crime")),
                "2017-01-26",
                "2021-03-31",
                "riverdale",
                "Set in the present, the series offers a bold, subversive take on Archie, Betty, Veronica and their friends, exploring the surreality of small-town life, the darkness and weirdness bubbling beneath Riverdale’s wholesome facade.",
                983.102f,
                "Returning Series",
                8.6f
        ))
        list.add(TvDetailEntity(
                0,
                "Grey's Anatomy",
                listOf(GenreEntity(18, "Drama")),
                "2005-03-27",
                "2021-04-15",
                "greys_anatomy",
                "Follows the personal and professional lives of a group of doctors at Seattle’s Grey Sloan Memorial Hospital.",
                994.98f,
                "Returning Series",
                8.2f
        ))
        list.add(TvDetailEntity(
                63174,
                "Lucifer",
                listOf(GenreEntity(80, "Crime"), GenreEntity(10765, "Sci-Fi & Fantasy")),
                "2016-01-25",
                "2020-08-21",
                "lucifer",
                "Bored and unhappy as the Lord of Hell, Lucifer Morningstar abandoned his throne and retired to Los Angeles, where he has teamed up with LAPD detective Chloe Decker to take down criminals. But the longer he's away from the underworld, the greater the threat that the worst of humanity could escape.",
                876.319f,
                "Returning Series",
                8.5f
        ))
        list.add(TvDetailEntity(
                1402,
                "The Walking Dead",
                listOf(GenreEntity(10759, "Action & Adventure"), GenreEntity(18, "Drama"), GenreEntity(10765, "Sci-Fi & Fantasy")),
                "2010-10-31",
                "2021-04-04",
                "the_walking_dead",
                "Sheriff's deputy Rick Grimes awakens from a coma to find a post-apocalyptic world dominated by flesh-eating zombies. He sets out to find his family and encounters many other survivors along the way.",
                755.255f,
                "Returning Series",
                8.1f
        ))
        list.add(TvDetailEntity(
                95057,
                "Superman & Lois",
                listOf(GenreEntity(10759, "Action & Adventure"), GenreEntity(10765, "Sci-Fi & Fantasy")),
                "2021-02-23",
                "2021-03-23",
                "superman_and_lois",
                "After years of facing megalomaniacal supervillains, monsters wreaking havoc on Metropolis, and alien invaders intent on wiping out the human race, The Man of Steel aka Clark Kent and Lois Lane come face to face with one of their greatest challenges ever: dealing with all the stress, pressures and complexities that come with being working parents in today's society.",
                764.008f,
                "Returning Series",
                8.3f
        ))
        list.add(TvDetailEntity(
                120168,
                "Who Killed Sara?",
                listOf(GenreEntity(18, "Drama"), GenreEntity(80, "Crime"), GenreEntity(9648, "Mystery")),
                "2021-03-24",
                "2021-03-24",
                "who_kill_sara",
                "Hell-bent on exacting revenge and proving he was framed for his sister's murder, Álex sets out to unearth much more than the crime's real culprit.",
                767.618f,
                "Returning Series",
                7.9f
        ))
        list.add(TvDetailEntity(
                85271,
                "WandaVision",
                listOf(GenreEntity(10765, "Sci-Fi & Fantasy"), GenreEntity(9648, "mystery"), GenreEntity(18, "Drama")),
                "2021-01-15",
                "2021-03-05",
                "wanda_vision",
                "Wanda Maximoff and Vision—two super-powered beings living idealized suburban lives—begin to suspect that everything is not as it seems.",
                730.166f,
                "Ended",
                8.4f
        ))
        return list
    }

    fun getMovieList(): List<MovieDetailEntity> = movieList
    fun getTvList(): List<TvDetailEntity> = tvList
    fun getMovie(movieId: Int): MovieDetailEntity?{
        for(item in movieList){
            if(item.id == movieId) return item
        }
        return null
    }
    fun getTv(tvId: Int): TvDetailEntity?{
        for(item in tvList){
            if(item.id == tvId) return item
        }
        return null
    }
}