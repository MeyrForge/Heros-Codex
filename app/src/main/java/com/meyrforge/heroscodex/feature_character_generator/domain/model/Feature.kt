package com.meyrforge.heroscodex.feature_character_generator.domain.model

data class Feature(
  val id: String,
  val name: String,
  val description: String,
  val level: Int
) {
  companion object {
    // Level 1 features for each class
    private val BARBARIAN_FEATURES = listOf(
      Feature(
        id = "rage",
        name = "Rage",
        description = "In battle, you fight with primal ferocity. On your turn, you can enter a rage as a bonus action.",
        level = 1
      ),
      Feature(
        id = "unarmored_defense_barbarian",
        name = "Unarmored Defense",
        description = "While you are not wearing any armor, your Armor Class equals 10 + your Dexterity modifier + your Constitution modifier.",
        level = 1
      )
    )

    private val BARD_FEATURES = listOf(
      Feature(
        id = "spellcasting_bard",
        name = "Spellcasting",
        description = "You have learned to untangle and reshape the fabric of reality in harmony with your wishes and music.",
        level = 1
      ),
      Feature(
        id = "bardic_inspiration",
        name = "Bardic Inspiration",
        description = "You can inspire others through stirring words or music. To do so, you use a bonus action on your turn to choose one creature other than yourself within 60 feet of you who can hear you.",
        level = 1
      )
    )

    private val CLERIC_FEATURES = listOf(
      Feature(
        id = "spellcasting_cleric",
        name = "Spellcasting",
        description = "As a conduit for divine power, you can cast cleric spells.",
        level = 1
      ),
      Feature(
        id = "divine_domain",
        name = "Divine Domain",
        description = "Choose one domain related to your deity. Your choice grants you domain spells and other features when you choose it at 1st level.",
        level = 1
      )
    )

    private val DRUID_FEATURES = listOf(
      Feature(
        id = "druidic",
        name = "Druidic",
        description = "You know Druidic, the secret language of druids. You can speak the language and use it to leave hidden messages.",
        level = 1
      ),
      Feature(
        id = "spellcasting_druid",
        name = "Spellcasting",
        description = "Drawing on the divine essence of nature itself, you can cast spells to shape that essence to your will.",
        level = 1
      )
    )

    private val FIGHTER_FEATURES = listOf(
      Feature(
        id = "fighting_style",
        name = "Fighting Style",
        description = "You adopt a particular style of fighting as your specialty. Choose one of the following options: Archery, Defense, Dueling, Great Weapon Fighting, Protection, or Two-Weapon Fighting.",
        level = 1
      ),
      Feature(
        id = "second_wind",
        name = "Second Wind",
        description = "You have a limited well of stamina that you can draw on to protect yourself from harm. On your turn, you can use a bonus action to regain hit points equal to 1d10 + your fighter level.",
        level = 1
      )
    )

    private val MONK_FEATURES = listOf(
      Feature(
        id = "unarmored_defense_monk",
        name = "Unarmored Defense",
        description = "While you are wearing no armor and not wielding a shield, your AC equals 10 + your Dexterity modifier + your Wisdom modifier.",
        level = 1
      ),
      Feature(
        id = "martial_arts",
        name = "Martial Arts",
        description = "Your practice of martial arts gives you mastery of combat styles that use unarmed strikes and monk weapons.",
        level = 1
      )
    )

    private val PALADIN_FEATURES = listOf(
      Feature(
        id = "divine_sense",
        name = "Divine Sense",
        description = "The presence of strong evil registers on your senses like a noxious odor, and powerful good rings like heavenly music in your ears.",
        level = 1
      ),
      Feature(
        id = "lay_on_hands",
        name = "Lay on Hands",
        description = "Your blessed touch can heal wounds. You have a pool of healing power that replenishes when you take a long rest.",
        level = 1
      )
    )

    private val RANGER_FEATURES = listOf(
      Feature(
        id = "favored_enemy",
        name = "Favored Enemy",
        description = "You have significant experience studying, tracking, hunting, and even talking to a certain type of enemy.",
        level = 1
      ),
      Feature(
        id = "natural_explorer",
        name = "Natural Explorer",
        description = "You are particularly familiar with one type of natural environment and are adept at traveling and surviving in such regions.",
        level = 1
      )
    )

    private val ROGUE_FEATURES = listOf(
      Feature(
        id = "expertise",
        name = "Expertise",
        description = "Choose two of your skill proficiencies. Your proficiency bonus is doubled for any ability check you make that uses either of the chosen proficiencies.",
        level = 1
      ),
      Feature(
        id = "sneak_attack",
        name = "Sneak Attack",
        description = "You know how to strike subtly and exploit a foe's distraction. Once per turn, you can deal an extra 1d6 damage to one creature you hit with an attack if you have advantage on the attack roll.",
        level = 1
      ),
      Feature(
        id = "thieves_cant",
        name = "Thieves' Cant",
        description = "During your rogue training you learned thieves' cant, a secret mix of dialect, jargon, and code that allows you to hide messages in seemingly normal conversation.",
        level = 1
      )
    )

    private val SORCERER_FEATURES = listOf(
      Feature(
        id = "spellcasting_sorcerer",
        name = "Spellcasting",
        description = "An event in your past, or in the life of a parent or ancestor, left an indelible mark on you, infusing you with arcane magic.",
        level = 1
      ),
      Feature(
        id = "sorcerous_origin",
        name = "Sorcerous Origin",
        description = "Choose a sorcerous origin, which describes the source of your innate magical power.",
        level = 1
      )
    )

    private val WARLOCK_FEATURES = listOf(
      Feature(
        id = "otherworldly_patron",
        name = "Otherworldly Patron",
        description = "You have struck a bargain with an otherworldly being of your choice.",
        level = 1
      ),
      Feature(
        id = "pact_magic",
        name = "Pact Magic",
        description = "Your arcane research and the magic bestowed on you by your patron have given you facility with spells.",
        level = 1
      )
    )

    private val WIZARD_FEATURES = listOf(
      Feature(
        id = "spellcasting_wizard",
        name = "Spellcasting",
        description = "As a student of arcane magic, you have a spellbook containing spells that show the first glimmerings of your true power.",
        level = 1
      ),
      Feature(
        id = "arcane_recovery",
        name = "Arcane Recovery",
        description = "You have learned to regain some of your magical energy by studying your spellbook. Once per day when you finish a short rest, you can choose expended spell slots to recover.",
        level = 1
      )
    )

    fun getFeaturesForClass(characterClass: CharacterClass, level: Int = 1): List<Feature> {
      if (level != 1) return emptyList() // For now, only level 1 is supported

      return when (characterClass.id) {
        "barbarian" -> BARBARIAN_FEATURES
        "bard" -> BARD_FEATURES
        "cleric" -> CLERIC_FEATURES
        "druid" -> DRUID_FEATURES
        "fighter" -> FIGHTER_FEATURES
        "monk" -> MONK_FEATURES
        "paladin" -> PALADIN_FEATURES
        "ranger" -> RANGER_FEATURES
        "rogue" -> ROGUE_FEATURES
        "sorcerer" -> SORCERER_FEATURES
        "warlock" -> WARLOCK_FEATURES
        "wizard" -> WIZARD_FEATURES
        else -> emptyList()
      }
    }
  }
}

