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


	@Test
	fun testChineseSimplified() {
		val locale = Locale.forLanguageTag("zh-Hans")

		assertEquals(expected = "德国", actual = germany.name(locale))
		assertEquals(expected = "美国", actual = unitedStates.name(locale))
	}


	@Test
	fun testChineseTraditional() {
		val locale = Locale.forLanguageTag("zh-Hant")

		assertEquals(expected = "德國", actual = germany.name(locale))
		assertEquals(expected = "美國", actual = unitedStates.name(locale))
	}


	@Test
	fun testChineseScripts_produceDifferentNames() {
		val simplified = Locale.forLanguageTag("zh-Hans")
		val traditional = Locale.forLanguageTag("zh-Hant")

		assertNotEquals(illegal = germany.name(traditional), actual = germany.name(simplified))
		assertNotEquals(illegal = unitedStates.name(traditional), actual = unitedStates.name(simplified))
	}


	@Test
	fun testJapaneseExamples() {
		val locale = Locale.forLanguageTag("ja")

		assertEquals(expected = "ドイツ", actual = germany.name(locale))
		assertEquals(expected = "アメリカ合衆国", actual = unitedStates.name(locale))
	}


	@Test
	fun testEnUsLocale_fallsBackToEnglish() {
		val locale = Locale.forLanguageTag("en-US")

		assertEquals(expected = "Germany", actual = germany.name(locale))
		assertEquals(expected = "United States", actual = unitedStates.name(locale))
		assertEquals(expected = "US", actual = unitedStates.shortName(locale))
		assertEquals(expected = "Côte d’Ivoire", actual = ivoryCoast.name(locale))
		assertEquals(expected = "Ivory Coast", actual = ivoryCoast.variantName(locale))
	}


	@Test
	fun testShortAndVariantNames_withNonEnglishLocale() {
		val german = Locale.forLanguageTag("de")

		assertEquals(expected = "USA", actual = unitedStates.shortName(german))
		assertEquals(expected = "Elfenbeinküste", actual = ivoryCoast.variantName(german))
	}
}
