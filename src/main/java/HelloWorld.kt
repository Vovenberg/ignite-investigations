import org.apache.ignite.Ignition

/**
 * Created by vladimir on 04.09.17.
 */
fun main(args: Array<String>) {
    Ignition.start("/Users/vladimir/TOOLS/ignite/examples/config/example-ignite.xml").use { ignite ->
        val forRemotes = ignite.cluster().forRemotes()
        val collection: Collection<Int> = ignite.compute(forRemotes).apply({ word: String ->
            println("Вычисляем длину слова $word")
            return@apply word.length
        }, "easy easy real talk think about it man".split(" "))

        print("Всего букв: ${collection.sum()}")

//        ignite.compute(ignite.cluster().forRemotes()).broadcast({ println("HELLO WORLD ") })
    }
}