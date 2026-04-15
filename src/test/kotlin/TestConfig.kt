import dev.staeming.configreader.ConfigValues

data class TestConfig(
    var a:Int = 0,
    var b:Float = 3.14f,
    var c:Boolean = true,
    var d:Double = 1.1,
    var e:String = "apple"
) : ConfigValues
