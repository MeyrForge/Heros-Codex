package com.meyrforge.heroscodex.feature_character_generator.domain.usecase

import com.meyrforge.heroscodex.feature_character_generator.domain.model.CharacterClass
import com.meyrforge.heroscodex.feature_character_generator.domain.model.StartingEquipment
import com.meyrforge.heroscodex.feature_character_generator.domain.repository.CharacterRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class GenerateInventoryUseCaseTest {

  private lateinit var characterRepository: CharacterRepository
  private lateinit var useCase: GenerateInventoryUseCase

  @Before
  fun setUp() {
    characterRepository = mockk()
    useCase = GenerateInventoryUseCase(characterRepository)
  }

  @Test
  fun `should generate starting equipment for given character class`() {
    val fighter = CharacterClass.AVAILABLE_CLASSES.first { it.id == "fighter" }
    val expectedEquipment = StartingEquipment.getEquipmentForClass(fighter)

    every { characterRepository.getStartingEquipment(fighter) } returns expectedEquipment

    val equipment = useCase.generate(fighter)

    assertNotNull("Equipment should not be null", equipment)
  }

  @Test
  fun `should call repository when generating equipment`() {
    val fighter = CharacterClass.AVAILABLE_CLASSES.first { it.id == "fighter" }
    val expectedEquipment = StartingEquipment.getEquipmentForClass(fighter)

    every { characterRepository.getStartingEquipment(fighter) } returns expectedEquipment

    useCase.generate(fighter)

    verify { characterRepository.getStartingEquipment(fighter) }
  }

  @Test
  fun `fighter should have weapons`() {
    val fighter = CharacterClass.AVAILABLE_CLASSES.first { it.id == "fighter" }
    val expectedEquipment = StartingEquipment.getEquipmentForClass(fighter)

    every { characterRepository.getStartingEquipment(fighter) } returns expectedEquipment

    val equipment = useCase.generate(fighter)

    assertTrue("Fighter should have weapons", equipment.weapons.isNotEmpty())
  }

  @Test
  fun `starting gold should be non-negative`() {
    val fighter = CharacterClass.AVAILABLE_CLASSES.first { it.id == "fighter" }
    val expectedEquipment = StartingEquipment.getEquipmentForClass(fighter)

    every { characterRepository.getStartingEquipment(fighter) } returns expectedEquipment

    val equipment = useCase.generate(fighter)

    assertTrue("Starting gold should be non-negative", equipment.startingGold >= 0)
  }

  @Test
  fun `generated equipment should have unique item ids`() {
    val ranger = CharacterClass.AVAILABLE_CLASSES.first { it.id == "ranger" }
    val expectedEquipment = StartingEquipment.getEquipmentForClass(ranger)

    every { characterRepository.getStartingEquipment(ranger) } returns expectedEquipment

    val equipment = useCase.generate(ranger)

    val allItems = equipment.weapons + equipment.armor + equipment.gear
    val allIds = allItems.map { it.id }
    val uniqueIds = allIds.toSet()

    assertEquals(
      "All item IDs should be unique (no duplicates, quantities are tracked per item)",
      uniqueIds.size,
      allIds.size
    )
  }

  @Test
  fun `ranger should have arrows`() {
    val ranger = CharacterClass.AVAILABLE_CLASSES.first { it.id == "ranger" }
    val expectedEquipment = StartingEquipment.getEquipmentForClass(ranger)

    every { characterRepository.getStartingEquipment(ranger) } returns expectedEquipment

    val equipment = useCase.generate(ranger)

    val arrows = equipment.weapons.find { it.name.contains("Arrow", ignoreCase = true) }
    assertNotNull("Ranger should have arrows", arrows)
  }

  @Test
  fun `ranger should have 20 arrows`() {
    val ranger = CharacterClass.AVAILABLE_CLASSES.first { it.id == "ranger" }
    val expectedEquipment = StartingEquipment.getEquipmentForClass(ranger)

    every { characterRepository.getStartingEquipment(ranger) } returns expectedEquipment

    val equipment = useCase.generate(ranger)

    val arrows = equipment.weapons.find { it.name.contains("Arrow", ignoreCase = true) }
    assertEquals("Ranger should have 20 arrows", 20, arrows?.quantity)
  }

  @Test
  fun `ranger should have shortswords`() {
    val ranger = CharacterClass.AVAILABLE_CLASSES.first { it.id == "ranger" }
    val expectedEquipment = StartingEquipment.getEquipmentForClass(ranger)

    every { characterRepository.getStartingEquipment(ranger) } returns expectedEquipment

    val equipment = useCase.generate(ranger)

    val shortswords = equipment.weapons.find { it.name.equals("Shortsword", ignoreCase = true) }
    assertNotNull("Ranger should have shortswords", shortswords)
  }

  @Test
  fun `ranger should have 2 shortswords`() {
    val ranger = CharacterClass.AVAILABLE_CLASSES.first { it.id == "ranger" }
    val expectedEquipment = StartingEquipment.getEquipmentForClass(ranger)

    every { characterRepository.getStartingEquipment(ranger) } returns expectedEquipment

    val equipment = useCase.generate(ranger)

    val shortswords = equipment.weapons.find { it.name.equals("Shortsword", ignoreCase = true) }
    assertEquals("Ranger should have 2 shortswords", 2, shortswords?.quantity)
  }

  @Test
  fun `paladin should have single javelin item entry`() {
    val paladin = CharacterClass.AVAILABLE_CLASSES.first { it.id == "paladin" }
    val expectedEquipment = StartingEquipment.getEquipmentForClass(paladin)

    every { characterRepository.getStartingEquipment(paladin) } returns expectedEquipment

    val equipment = useCase.generate(paladin)

    val javelins =
      (equipment.weapons + equipment.gear).filter { it.name.contains("Javelin", ignoreCase = true) }
    assertEquals("Should have only one javelin item entry", 1, javelins.size)
  }

  @Test
  fun `paladin should have 5 javelins`() {
    val paladin = CharacterClass.AVAILABLE_CLASSES.first { it.id == "paladin" }
    val expectedEquipment = StartingEquipment.getEquipmentForClass(paladin)

    every { characterRepository.getStartingEquipment(paladin) } returns expectedEquipment

    val equipment = useCase.generate(paladin)

    val javelins =
      (equipment.weapons + equipment.gear).filter { it.name.contains("Javelin", ignoreCase = true) }
    assertEquals("Paladin should have 5 javelins", 5, javelins.first().quantity)
  }

  @Test
  fun `monk should have single dart item entry`() {
    val monk = CharacterClass.AVAILABLE_CLASSES.first { it.id == "monk" }
    val expectedEquipment = StartingEquipment.getEquipmentForClass(monk)

    every { characterRepository.getStartingEquipment(monk) } returns expectedEquipment

    val equipment = useCase.generate(monk)

    val darts =
      (equipment.weapons + equipment.gear).filter { it.name.equals("Dart", ignoreCase = true) }
    assertEquals("Should have only one dart item entry", 1, darts.size)
  }

  @Test
  fun `monk should have 10 darts`() {
    val monk = CharacterClass.AVAILABLE_CLASSES.first { it.id == "monk" }
    val expectedEquipment = StartingEquipment.getEquipmentForClass(monk)

    every { characterRepository.getStartingEquipment(monk) } returns expectedEquipment

    val equipment = useCase.generate(monk)

    val darts =
      (equipment.weapons + equipment.gear).filter { it.name.equals("Dart", ignoreCase = true) }
    assertEquals("Monk should have 10 darts", 10, darts.first().quantity)
  }

  @Test
  fun `cleric should have holy symbol`() {
    val cleric = CharacterClass.AVAILABLE_CLASSES.first { it.id == "cleric" }
    val expectedEquipment = StartingEquipment.getEquipmentForClass(cleric)

    every { characterRepository.getStartingEquipment(cleric) } returns expectedEquipment

    val equipment = useCase.generate(cleric)

    val hasHolySymbol = (equipment.weapons + equipment.armor + equipment.gear)
      .any { it.name.contains("Holy Symbol", ignoreCase = true) }

    assertTrue("Cleric should have a holy symbol", hasHolySymbol)
  }

  @Test
  fun `cleric should have shield`() {
    val cleric = CharacterClass.AVAILABLE_CLASSES.first { it.id == "cleric" }
    val expectedEquipment = StartingEquipment.getEquipmentForClass(cleric)

    every { characterRepository.getStartingEquipment(cleric) } returns expectedEquipment

    val equipment = useCase.generate(cleric)

    val hasShield = (equipment.armor + equipment.gear + equipment.weapons)
      .any { it.name.contains("Shield", ignoreCase = true) }

    assertTrue("Cleric should have a shield", hasShield)
  }
}
