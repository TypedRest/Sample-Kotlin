plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    api(project(":dto"))
    api("net.typedrest:typedrest:0.33.2")
}
