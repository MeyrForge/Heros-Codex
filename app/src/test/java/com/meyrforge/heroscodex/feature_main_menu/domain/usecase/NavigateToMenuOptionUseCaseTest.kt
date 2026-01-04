package com.meyrforge.heroscodex.feature_main_menu.domain.usecase

import com.meyrforge.heroscodex.navigation.Screen
import com.meyrforge.heroscodex.feature_main_menu.domain.model.MenuOption
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class NavigateToMenuOptionUseCaseTest {

  private lateinit var useCase: NavigateToMenuOptionUseCase

  @Before
  fun setUp() {
    useCase = NavigateToMenuOptionUseCase()
  }

  @Test
  fun `invoke with NAME_GENERATOR returns NameGenerator screen`() {
    val result = useCase(MenuOption.NAME_GENERATOR)
    assertEquals(Screen.NameGenerator, result)
  }

  @Test
  fun `invoke with HERO_GENERATOR returns null`() {
    val result = useCase(MenuOption.HERO_GENERATOR)
    assertNull(result)
  }

  @Test
  fun `invoke with MY_HEROES returns null`() {
    val result = useCase(MenuOption.MY_HEROES)
    assertNull(result)
  }
}
