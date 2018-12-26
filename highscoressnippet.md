Get all:
```kotlin
val highscoreService = HighscoreService()
highscoreService.all(object : AsyncHandler<List<HighscoreEntry>> {
    override fun success(data: List<HighscoreEntry>) {
        val logger = logger<DirkFallsGame>()
        for(entry in data) {
            logger.info(entry.name + " -- " + entry.score)
        }
    }
    override fun error(t: Throwable) {
        logger<DirkFallsGame>().error(t.toString(), t)
    }
})
```

Create one:
```kotlin
val highscoreService = HighscoreService()
highscoreService.create("robin", 10, object : AsyncHandler<HighscoreEntry> {
    override fun success(data: HighscoreEntry) {
        logger<DirkFallsGame>().info(data.toString())
    }

    override fun error(t: Throwable) {
        logger<DirkFallsGame>().error(t.toString(), t)
    }
})
```