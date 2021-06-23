package tests

import io.fluidsonic.country.*
import io.fluidsonic.i18n.*
import io.fluidsonic.locale.*
import kotlin.test.*


class CountryNameTests {

	private val germany = Country.forCode("DE")
	private val ivoryCoast = Country.forCode("CI")
	private val unitedStates = Country.forCode("US")


	@Test
	fun testEnglishExamples() {
		assertEquals(expected = "Germany", actual = germany.name)
		assertEquals(expected = null, actual = germany.shortName)
		assertEquals(expected = null, actual = germany.variantName)

		assertEquals(expected = "Côte d’Ivoire", actual = ivoryCoast.name)
		assertEquals(expected = null, actual = ivoryCoast.shortName)
		assertEquals(expected = "Ivory Coast", actual = ivoryCoast.variantName)

		assertEquals(expected = "United States", actual = unitedStates.name)
		assertEquals(expected = "US", actual = unitedStates.shortName)
		assertEquals(expected = null, actual = unitedStates.variantName)
	}


	@Test
	fun testGermanExamples() {
		val locale = Locale.forLanguageTag("de-DE")

		assertEquals(expected = "Deutschland", actual = germany.name(locale))
		assertEquals(expected = null, actual = germany.shortName(locale))
		assertEquals(expected = null, actual = germany.variantName(locale))

		assertEquals(expected = "Côte d’Ivoire", actual = ivoryCoast.name(locale))
		assertEquals(expected = null, actual = ivoryCoast.shortName(locale))
		assertEquals(expected = "Elfenbeinküste", actual = ivoryCoast.variantName(locale))

		assertEquals(expected = "Vereinigte Staaten", actual = unitedStates.name(locale))
		assertEquals(expected = "USA", actual = unitedStates.shortName(locale))
		assertEquals(expected = null, actual = unitedStates.variantName(locale))
	}
}
