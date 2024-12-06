import org.anware.data.service.ApiKeyGenerator
import org.anware.data.service.ApiKeyGenerator.ApiKeySignature
import org.anware.data.service.ApiKeyGenerator.Type
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class ApiKeyGeneratorTest {

    private lateinit var apiKeyGenerator: ApiKeyGenerator
    private var standartSecret = "mySuperSecretKey"
    private val signatureWithPassword = ApiKeySignature(
        warehouseId = "12345",
        warehouseName = "MainWarehouse",
        warehousePassword = "warehouseSpecificSecret",
        type = Type.READ
    )
    private val signatureWithoutPassword = ApiKeySignature(
        warehouseId = "12345",
        warehouseName = "MainWarehouse",
        warehousePassword = null,
        type = Type.WRITE
    )

    @BeforeEach
    fun setup() {
        apiKeyGenerator = ApiKeyGenerator()
        apiKeyGenerator.standartSecret = standartSecret
    }

    @Test
    fun `generateApiKey creates a valid API key using specific password`() {
        val apiKey = apiKeyGenerator.generateApiKey(signatureWithPassword)
        assertNotNull(apiKey, "API key should not be null")
    }

    @Test
    fun `generateApiKey creates a valid API key using default password`() {
        val apiKey = apiKeyGenerator.generateApiKey(signatureWithoutPassword)
        assertNotNull(apiKey, "API key should not be null")
    }

    @Test
    fun `validateApiKey returns true for a valid API key with specific password`() {
        val apiKey = apiKeyGenerator.generateApiKey(signatureWithPassword)
        val isValid = apiKeyGenerator.validateApiKey(apiKey!!)
        assertTrue(isValid, "API key should be valid with specific password")
    }

    @Test
    fun `validateApiKey returns true for a valid API key with default password`() {
        val apiKey = apiKeyGenerator.generateApiKey(signatureWithoutPassword)
        val isValid = apiKeyGenerator.validateApiKey(apiKey!!)
        assertTrue(isValid, "API key should be valid with default password")
    }

    @Test
    fun `validateApiKey returns false for an invalid API key`() {
        val apiKey = apiKeyGenerator.generateApiKey(signatureWithPassword)
        val modifiedApiKey = apiKey + "AAAAAAAAAAAAAAAAAAA"
        val isValid = apiKeyGenerator.validateApiKey(modifiedApiKey)
        assertFalse(isValid, "Modified API key should be invalid")
    }

    @Test
    fun `validateApiKey returns false for a malformed API key`() {
        val malformedApiKey = Base64.getEncoder().encodeToString("invalid:data".toByteArray())
        val isValid = apiKeyGenerator.validateApiKey(malformedApiKey)
        assertFalse(isValid, "Malformed API key should be invalid")
    }

    @Test
    fun `generateApiKey includes all parts in the key`() {
        val apiKey = apiKeyGenerator.generateApiKey(signatureWithPassword)
        val decodedKey = String(Base64.getDecoder().decode(apiKey))
        val parts = decodedKey.split(":")
        assertEquals(4, parts.size, "API key should contain all expected parts")
        assertEquals(signatureWithPassword.warehouseName, parts[0], "Warehouse name should match")
        assertEquals(signatureWithPassword.warehouseId, parts[1], "Warehouse ID should match")
        assertEquals(signatureWithPassword.type.name, parts[2], "Type should match")
        assertNotNull(parts[3], "Signature should not be null")
    }

    @Test
    fun `extractDataFromApiKey correctly extracts the data`() {
        val apiKey = apiKeyGenerator.generateApiKey(signatureWithPassword)
        val extractedData = apiKeyGenerator.extractDataFromApiKey(apiKey!!)
        assertNotNull(extractedData, "Extracted data should not be null")
        assertEquals(signatureWithPassword.warehouseName, extractedData?.warehouseName, "Warehouse name should match")
        assertEquals(signatureWithPassword.warehouseId, extractedData?.warehouseId, "Warehouse ID should match")
        assertEquals(signatureWithPassword.type, extractedData?.type, "Type should match")
    }

    @Test
    fun `extractDataFromApiKey returns null for a malformed API key`() {
        val malformedApiKey = Base64.getEncoder().encodeToString("invalid:data".toByteArray())
        val extractedData = apiKeyGenerator.extractDataFromApiKey(malformedApiKey)
        assertNull(extractedData, "Extracted data should be null for malformed API key")
    }
}
