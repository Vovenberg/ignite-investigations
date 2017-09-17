import org.apache.ignite.Ignite
import org.apache.ignite.Ignition
import org.apache.ignite.configuration.CacheConfiguration
import java.util.*

/**
 * Created by vladimir on 15.09.17.
 */


fun <T> cacheFilling(ignite: Ignite, name: String, entities: List<T>) {
    val cacheConfiguration: CacheConfiguration<Long, T> = CacheConfiguration()
    cacheConfiguration.name = name
    val igniteCache = ignite.getOrCreateCache(cacheConfiguration)
    entities.forEach({ entity ->
        val id = Random().nextLong()
        igniteCache.put(id, entity)
        igniteCache.getAsync(id).listen { println("Retrieved and put in cache $entity") }
    })
}


fun main(args: Array<String>) {
    Ignition.start("/Users/vladimir/TOOLS/ignite/examples/config/example-ignite.xml").use { ignite ->
        cacheFilling(ignite, "Repository", listOf(
                Repository(1, "ignite", "imdg", "ignite.com"),
                Repository(2, "spring-boot", "spring", "spr.com"),
                Repository(3, "spring-boot2", "spring", "spr.com"),
                Repository(4, "spring-boot3", "spring", "spr.com")))
        cacheFilling(ignite, "Issue", listOf(
                Issue(1, 1, "todo", "imdg", Date()),
                Issue(2, 2, "todo", "spring", Date()),
                Issue(3, 2, "todo2", "spring", Date()),
                Issue(4, 4, "v3", "spring", Date())))

    }
}


data class Repository(val id: Long,
                      val name: String,
                      val description: String,
                      val url: String)

data class Issue(val id: Long,
                 val repository_id: Long,
                 val state: String,
                 val title: String,
                 val createdDate: Date)
