package addressbook

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "addresses", path = "addresses")
interface AddressRepository : MongoRepository<Address, String> {

    fun findByName(@Param("name") name: String): List<Address>
}
