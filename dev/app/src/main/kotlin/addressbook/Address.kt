package addressbook

import org.springframework.data.annotation.Id

data class Address(
    @Id val id: String?,
    val userID: String,
    val name: String,
    val postalCode: String,
    val houseNumber: String,
    val addition: String?,
)
