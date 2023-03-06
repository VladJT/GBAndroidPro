package jt.projects.gbandroidpro.others

interface ICooker {
    fun cook()
    fun addCoffee()
    fun boilWater()
}

class Cooker() : ICooker {
    override fun cook() {
        println("cook")
    }

    override fun addCoffee() {
        println("add")
    }

    override fun boilWater() {
        println("boil water")
    }
}

interface ICoffee {
    fun getCoffeeName(): String
    fun getCode(): Int
    fun setCode(code: Int)
    fun makeCoffee()
}

class Coffee(val cooker: ICooker) : ICoffee {
    private var name: String = ""
    var cc: Int = 0


    override fun getCoffeeName(): String {
        return name
    }

    override fun getCode(): Int {
        return cc
    }

    override fun setCode(code: Int) {
        this.cc = code
    }

    override fun makeCoffee() {
        cooker.boilWater()
        cooker.addCoffee()
        cooker.cook()
    }

}