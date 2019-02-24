package de.roamingthings.tc

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class HelloMessageProducerTest {

    @Test
    fun `Say Hello to Bob`() {
        val helloMessageProducer = HelloMessageProducer()

        val message = helloMessageProducer.sayHelloTo()

        assertThat(message).isEqualTo("Hello, Bob!")
    }
}