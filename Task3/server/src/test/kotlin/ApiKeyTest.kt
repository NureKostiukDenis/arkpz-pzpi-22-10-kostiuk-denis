import org.anware.core.apikey.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class ApiKeyTest {

    private val secret = "mySuperSecretKey"
    private val signature = ApiKeySignature(
        warehouseId = "12345",
        warehouseName = "MainWarehouse",
        type = Type.READ
    )

    @Test
    fun `generateApiKey creates a valid API key`() {
        val apiKey = generateApiKey(signature, secret)
        assertNotNull(apiKey, "API key should not be null")
    }

    @Test
    fun `validateApiKey returns true for a valid API key`() {
        val apiKey = generateApiKey(signature, secret)
        val isValid = validateApiKey(apiKey, secret)
        assertTrue(isValid, "API key should be valid")
    }

    @Test
    fun `validateApiKey returns false for an invalid API key`() {
        val apiKey = generateApiKey(signature, secret)
        val modifiedApiKey = apiKey + "AAAAAAAAAAAAAAAAAAA"
        val isValid = validateApiKey(modifiedApiKey, secret)
        assertFalse(isValid, "Modified API key should be invalid")
    }

    @Test
    fun `validateApiKey returns false for a malformed API key`() {
        val malformedApiKey = Base64.getEncoder().encodeToString("invalid:data".toByteArray())
        val isValid = validateApiKey(malformedApiKey, secret)
        assertFalse(isValid, "Malformed API key should be invalid")
    }

    @Test
    fun `generateApiKey includes all parts in the key`() {
        val apiKey = generateApiKey(signature, secret)
        val decodedKey = String(Base64.getDecoder().decode(apiKey))
        val parts = decodedKey.split(":")
        assertEquals(4, parts.size, "API key should contain all expected parts")
        assertEquals(signature.warehouseName, parts[0], "Warehouse name should match")
        assertEquals(signature.warehouseId, parts[1], "Warehouse ID should match")
        assertEquals(signature.type.name, parts[2], "Type should match")
        assertNotNull(parts[3], "Signature should not be null")
    }

    @Test
    fun `extractDataFromApiKey correctly extracts the data`() {
        val apiKey = generateApiKey(signature, secret)
        val extractedData = extractDataFromApiKey(apiKey)
        assertNotNull(extractedData, "Extracted data should not be null")
        assertEquals(signature.warehouseName, extractedData?.warehouseName, "Warehouse name should match")
        assertEquals(signature.warehouseId, extractedData?.warehouseId, "Warehouse ID should match")
        assertEquals(signature.type, extractedData?.type, "Type should match")
    }

    @Test
    fun `extractDataFromApiKey returns null for a malformed API key`() {
        val malformedApiKey = Base64.getEncoder().encodeToString("invalid:data".toByteArray())
        val extractedData = extractDataFromApiKey(malformedApiKey)
        assertNull(extractedData, "Extracted data should be null for malformed API key")
    }
}
