package com.example.minecraftpotionapp


enum class Ingredient(val resourceID:Int) {
    sugar(R.drawable.sugar),
    fermented_spider_eye(R.drawable.fermented_spider_eye),
    rabbit_foot(R.drawable.rabbits_foot),
    spider_eye(R.drawable.spider_eye),
    blaze_powder(R.drawable.blaze_powder),
    glistering_melon(R.drawable.glistering_melon),
    ghast_tear(R.drawable.ghast_tear),
    magma_cream(R.drawable.magma_cream),
    pufferfish(R.drawable.pufferfish),
    golden_carrot(R.drawable.golden_carrot),
    turtle_shell(R.drawable.turtle_shell),
    phantom_membrane(R.drawable.phantom_membrane),
    netherwart (R.drawable.nether_wart),
    glowstone(R.drawable.glowstone),
    redstone(R.drawable.redstone),
    gunpowder(R.drawable.gunpowder),
    dragons_breath(R.drawable.dragons_breath)
}

//enum class Ingredient(val resourceID:Int,val image_button_id:Int) {
//    netherwart (R.drawable.nether_wart,R.id.Ingredient_Redstone),
//    sugar(R.drawable.sugar,R.id.Ingredient_Sugar),
//    glowstone(R.drawable.glowstone, R.id.Ingredient_Glowstone),
//    redstone(R.drawable.redstone, R.id.Ingredient_Redstone),
//    gunpowder(R.drawable.gunpowder, R.id.Ingredient_Gunpowder),
//    dragons_breath(R.drawable.dragons_breath, R.id.Ingredient_DragonsBreath),
//    fermented_spider_eye(R.drawable.fermented_spider_eye, R.id.Ingredient_FermentedSpiderEye),
//    rabbit_foot(R.drawable.rabbits_foot, R.id.Ingredient_RabbitsFoot),
//    spider_eye(R.drawable.spider_eye, R.id.Ingredient_SpiderEye),
//    blaze_powder(R.drawable.blaze_powder, R.id.Ingredient_BlazePowder),
//    glistering_melon(R.drawable.glistering_melon, R.id.Ingredient_GlisteringMelon),
//    ghast_tear(R.drawable.ghast_tear, R.id.Ingredient_GhastTear),
//    magma_cream(R.drawable.magma_cream, R.id.Ingredient_MagmaCream),
//    pufferfish(R.drawable.pufferfish, R.id.Ingredient_Pufferfish),
//    golden_carrot(R.drawable.golden_carrot, R.id.Ingredient_GoldenCarrot),
//    turtle_shell(R.drawable.turtle_shell, R.id.Ingredient_TurtleShell),
//    phantom_membrane(R.drawable.phantom_membrane, R.id.Ingredient_PhantomMembrane)
//}


abstract class Potion(
    var isGlowstone: Boolean = false,
    var isRedstone: Boolean = false,
    var isSplashPotion: Boolean = false,
    var isLingeringPotion: Boolean = false
) {
    abstract val name: String
    abstract val normal_potion_icon:Int
    abstract val splash_potion_icon:Int
    abstract val lingering_potion_icon:Int
    abstract val effect_icon: Int
    abstract val duration: Int

    abstract fun brew(ingredient: Ingredient): Potion?

    fun print(ingredient: Ingredient) {
        println("The ${getPotionName()} is brewed with ${ingredient.name}.")
    }

    fun getDrawable():Int{
        if (isLingeringPotion)
            return lingering_potion_icon
        else if (isSplashPotion)
            return splash_potion_icon
        else
            return normal_potion_icon
    }

    fun getPotionName(): String {
        if (isSplashPotion && isLingeringPotion && isGlowstone)
            return "Lingering potion of $name 2"
        else if (isSplashPotion && isLingeringPotion && isRedstone)
            return "Lingering potion of $name +"
        else if (isSplashPotion && isGlowstone)
            return "Splash potion of $name 2"
        else if (isSplashPotion && isRedstone)
            return "Splash potion of $name +"
        else if (isSplashPotion && isLingeringPotion)
            return "Lingering potion of $name"
        else if (isSplashPotion)
            return "Splash potion of $name"
        else if (isGlowstone)
            return "Potion of $name 2"
        else if (isRedstone)
            return "Potion of $name +"
        else
            return "Potion of $name"
    }

    fun getDuration(): String {
        if (duration == 0){
            return ""
        }
        val minutes = duration / 60
        val seconds = duration % 60
        if (minutes == 0){
            return "$seconds seconds"
        }
        if (seconds == 0){
            return "$minutes minutes"
        }
        return "$minutes minutes $seconds seconds"
    }

    open fun validIngredients(): List<Ingredient> {
        val validIngredients = mutableListOf<Ingredient>()
        if (!isSplashPotion) {
            validIngredients.add(Ingredient.gunpowder)
        }
        if (isSplashPotion && !isLingeringPotion) {
            validIngredients.add(Ingredient.dragons_breath)
        }
        return validIngredients
    }

}
class SwiftnessPotion(
    isRedstone: Boolean = false,
    isGlowstone: Boolean = false,
    isSplashPotion: Boolean = false,
    isLingeringPotion: Boolean = false,
    override val normal_potion_icon: Int = R.drawable.potion_of_swiftness,
    override val splash_potion_icon: Int = R.drawable.splash_potion_of_swiftness,
    override val lingering_potion_icon: Int = R.drawable.lingering_potion_of_swiftness,
    override val effect_icon: Int = R.drawable.speed ,
    override var duration: Int=180
) : Potion(
    isRedstone = isRedstone,
    isGlowstone = isGlowstone,
    isSplashPotion = isSplashPotion,
    isLingeringPotion = isLingeringPotion
) {
    override val name = "Swiftness"
    override fun brew(ingredient: Ingredient): Potion? {
        if (!isSplashPotion && ingredient == Ingredient.gunpowder) {
            return SwiftnessPotion(
                duration = duration,
                isSplashPotion=true,
                isLingeringPotion = isLingeringPotion,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone)
        }
        else if (!isLingeringPotion && isSplashPotion && ingredient == Ingredient.dragons_breath) {
            return SwiftnessPotion(
                duration = duration,
                isSplashPotion=true,
                isLingeringPotion = true,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone)
        } else if (!isGlowstone && ingredient == Ingredient.glowstone) {
            return SwiftnessPotion(
                duration = 90,
                isSplashPotion=isSplashPotion,
                isLingeringPotion = isLingeringPotion,
                isGlowstone = true,
                isRedstone = isRedstone
            )
        } else if (!isRedstone && ingredient == Ingredient.redstone) {
            return SwiftnessPotion(
                duration = 480,
                isSplashPotion=isSplashPotion,
                isLingeringPotion = isLingeringPotion,
                isGlowstone = isGlowstone,
                isRedstone = true
            )
       }else
           if (ingredient == Ingredient.fermented_spider_eye && !isGlowstone) {
            return SlownessPotion(
                isRedstone = isRedstone,
                isGlowstone = isGlowstone,
                isSplashPotion = isSplashPotion,
                isLingeringPotion = isLingeringPotion
            )
        }
        return null
    }


    override fun validIngredients(): List<Ingredient> {
        val validIngredients = super.validIngredients().toMutableList()
        if (!isGlowstone && !isRedstone) {
            validIngredients.add(Ingredient.glowstone)
            validIngredients.add(Ingredient.redstone)
        }
        if (!isGlowstone)
            validIngredients.add(Ingredient.fermented_spider_eye)

        return validIngredients
    }
}
class SlownessPotion(
    isRedstone: Boolean = false,
    isGlowstone: Boolean = false,
    isSplashPotion: Boolean = false,
    isLingeringPotion: Boolean = false,
    override val normal_potion_icon: Int = R.drawable.potion_of_slowness,
    override val splash_potion_icon: Int = R.drawable.splash_potion_of_slowness,
    override val lingering_potion_icon: Int = R.drawable.lingering_potion_of_slowness,
    override val effect_icon: Int = R.drawable.slowness ,
    override var duration: Int=90
) : Potion(
    isRedstone = isRedstone,
    isGlowstone = isGlowstone,
    isSplashPotion = isSplashPotion,
    isLingeringPotion = isLingeringPotion
) {
    override val name = "Slowness"
    override fun brew(ingredient: Ingredient): Potion? {
        if (!isSplashPotion && ingredient == Ingredient.gunpowder) {
            return SlownessPotion(
                duration = duration,
                isSplashPotion=true,
                isLingeringPotion = isLingeringPotion,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone
            )
        }
        else if (!isLingeringPotion && isSplashPotion && ingredient == Ingredient.dragons_breath) {
            return SlownessPotion(
                duration = duration,
                isSplashPotion=true,
                isLingeringPotion = true,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone)
        } else if (!isGlowstone && ingredient == Ingredient.glowstone) {
            return SlownessPotion(
                duration = 20,
                isSplashPotion=isSplashPotion,
                isLingeringPotion = isLingeringPotion,
                isGlowstone = true,
                isRedstone = isRedstone
            )
        } else if (!isRedstone && ingredient == Ingredient.redstone) {
            return SlownessPotion(
                duration = 240,
                isSplashPotion=isSplashPotion,
                isLingeringPotion = isLingeringPotion,
                isGlowstone = isGlowstone,
                isRedstone = true
            )
        }
        return null
    }


    override fun validIngredients(): List<Ingredient> {
        val validIngredients = super.validIngredients().toMutableList()
        if (!isGlowstone && !isRedstone) {
            validIngredients.add(Ingredient.glowstone)
            validIngredients.add(Ingredient.redstone)
        }
        return validIngredients
    }
}
class LeapingPotion(
    isRedstone: Boolean = false,
    isGlowstone: Boolean = false,
    isSplashPotion: Boolean = false,
    isLingeringPotion: Boolean = false,
    override val normal_potion_icon: Int = R.drawable.potion_of_leaping,
    override val splash_potion_icon: Int = R.drawable.splash_potion_of_leaping,
    override val lingering_potion_icon: Int = R.drawable.lingering_potion_of_leaping,
    override val effect_icon: Int = R.drawable.jump_boost ,
    override var duration: Int=180
) : Potion(
    isRedstone = isRedstone,
    isGlowstone = isGlowstone,
    isSplashPotion = isSplashPotion,
    isLingeringPotion = isLingeringPotion
) {
    override val name = "Leaping"
    override fun brew(ingredient: Ingredient): Potion? {
        if (!isSplashPotion && ingredient == Ingredient.gunpowder) {
            return LeapingPotion(
                duration = duration,
                isSplashPotion=true,
                isLingeringPotion = isLingeringPotion,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone
            )
        }
        else if (!isLingeringPotion && isSplashPotion && ingredient == Ingredient.dragons_breath) {
            return LeapingPotion(
                duration = duration,
                isSplashPotion=true,
                isLingeringPotion = true,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone)
        } else if (!isGlowstone && ingredient == Ingredient.glowstone) {
            return LeapingPotion(
                duration = 90,
                isSplashPotion=isSplashPotion,
                isLingeringPotion = isLingeringPotion,
                isGlowstone = true,
                isRedstone = isRedstone
            )
        } else if (!isRedstone && ingredient == Ingredient.redstone) {
            return LeapingPotion(
                duration = 480,
                isSplashPotion=isSplashPotion,
                isLingeringPotion = isLingeringPotion,
                isGlowstone = isGlowstone,
                isRedstone = true
            )
        }else
            if (ingredient == Ingredient.fermented_spider_eye && !isGlowstone) {
                return SlownessPotion(
                    isRedstone = isRedstone,
                    isGlowstone = isGlowstone,
                    isSplashPotion = isSplashPotion,
                    isLingeringPotion = isLingeringPotion
                )
            }
        return null
    }


    override fun validIngredients(): List<Ingredient> {
        val validIngredients = super.validIngredients().toMutableList()
        if (!isGlowstone && !isRedstone) {
            validIngredients.add(Ingredient.glowstone)
            validIngredients.add(Ingredient.redstone)
        }
        if (!isGlowstone)
            validIngredients.add(Ingredient.fermented_spider_eye)

        return validIngredients
    }
}

class StrengthPotion(
    isRedstone: Boolean = false,
    isGlowstone: Boolean = false,
    isSplashPotion: Boolean = false,
    isLingeringPotion: Boolean = false,
    override val normal_potion_icon: Int = R.drawable.potion_of_swiftness,
    override val splash_potion_icon: Int = R.drawable.splash_potion_of_swiftness,
    override val lingering_potion_icon: Int = R.drawable.lingering_potion_of_swiftness,
    override val effect_icon: Int = R.drawable.strength ,
    override var duration: Int=180
) : Potion(
    isRedstone = isRedstone,
    isGlowstone = isGlowstone,
    isSplashPotion = isSplashPotion,
    isLingeringPotion = isLingeringPotion
) {
    override val name = "Strength"
    override fun brew(ingredient: Ingredient): Potion? {
        if (!isSplashPotion && ingredient == Ingredient.gunpowder) {
            return StrengthPotion(
                duration = duration,
                isSplashPotion=true,
                isLingeringPotion = isLingeringPotion,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone
            )
        }
        else if (!isLingeringPotion && isSplashPotion && ingredient == Ingredient.dragons_breath) {
            return StrengthPotion(
                duration = duration,
                isSplashPotion=true,
                isLingeringPotion = true,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone)
        } else if (!isGlowstone && ingredient == Ingredient.glowstone) {
           return StrengthPotion(
               duration = 90,
               isSplashPotion=isSplashPotion,
               isLingeringPotion = isLingeringPotion,
               isGlowstone = true,
               isRedstone = isRedstone
           )
        } else if (!isRedstone && ingredient == Ingredient.redstone) {
            return StrengthPotion(
                duration = 480,
                isSplashPotion=isSplashPotion,
                isLingeringPotion = isLingeringPotion,
                isGlowstone = isGlowstone,
                isRedstone = true
            )
        }
        return null
    }

    override fun validIngredients(): List<Ingredient> {
        val validIngredients = super.validIngredients().toMutableList()
        if (!isGlowstone && !isRedstone) {
            validIngredients.add(Ingredient.glowstone)
            validIngredients.add(Ingredient.redstone)
        }

        return validIngredients
    }
}

class HealingPotion(
    isRedstone: Boolean = false,
    isGlowstone: Boolean = false,
    isSplashPotion: Boolean = false,
    isLingeringPotion: Boolean = false,
    override val normal_potion_icon: Int = R.drawable.potion_of_healing,
    override val splash_potion_icon: Int = R.drawable.splash_potion_of_healing,
    override val lingering_potion_icon: Int = R.drawable.lingering_potion_of_healing,
    override val effect_icon: Int = R.drawable.instant_health ,
    override var duration: Int=0
) : Potion(
    isRedstone = isRedstone,
    isGlowstone = isGlowstone,
    isSplashPotion = isSplashPotion,
    isLingeringPotion = isLingeringPotion
) {
    override val name = "Healing"
    override fun brew(ingredient: Ingredient): Potion? {
        if (!isSplashPotion && ingredient == Ingredient.gunpowder) {
            return HealingPotion(
isSplashPotion=true,
isLingeringPotion = isLingeringPotion,
isRedstone = isRedstone,
isGlowstone = isGlowstone
)
        }
        else if (!isLingeringPotion && isSplashPotion && ingredient == Ingredient.dragons_breath) {
            return HealingPotion(
                isSplashPotion=true,
                isLingeringPotion = true,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone)

        } else if (!isGlowstone && ingredient == Ingredient.glowstone) {
            return HealingPotion(
                isSplashPotion=isSplashPotion,
                isLingeringPotion = isLingeringPotion,
                isGlowstone = true,
                isRedstone = isRedstone
            )
        }else
            if (ingredient == Ingredient.fermented_spider_eye) {
                return HarmingPotion(
                    isRedstone = isRedstone,
                    isGlowstone = isGlowstone,
                    isSplashPotion = isSplashPotion,
                    isLingeringPotion = isLingeringPotion
                )
            }
        return null
    }

    override fun validIngredients(): List<Ingredient> {
        val validIngredients = super.validIngredients().toMutableList()
        if (!isGlowstone) {
            validIngredients.add(Ingredient.glowstone)
        }
        if (!isRedstone)
            validIngredients.add(Ingredient.fermented_spider_eye)

        return validIngredients
    }
}

class HarmingPotion(
    isRedstone: Boolean = false,
    isGlowstone: Boolean = false,
    isSplashPotion: Boolean = false,
    isLingeringPotion: Boolean = false,
    override val normal_potion_icon: Int = R.drawable.potion_of_harming,
    override val splash_potion_icon: Int = R.drawable.splash_potion_of_harming,
    override val lingering_potion_icon: Int = R.drawable.lingering_potion_of_harming,
    override val effect_icon: Int = R.drawable.instant_damage ,
    override var duration: Int=0
) : Potion(
    isRedstone = isRedstone,
    isGlowstone = isGlowstone,
    isSplashPotion = isSplashPotion,
    isLingeringPotion = isLingeringPotion
) {
    override val name = "Harming"
    override fun brew(ingredient: Ingredient): Potion? {
        if (!isSplashPotion && ingredient == Ingredient.gunpowder) {
            return HarmingPotion(
isSplashPotion=true,
isLingeringPotion = isLingeringPotion,
isRedstone = isRedstone,
isGlowstone = isGlowstone
)
        }
        else if (!isLingeringPotion && isSplashPotion && ingredient == Ingredient.dragons_breath) {
            return HarmingPotion(
                isSplashPotion=true,
                isLingeringPotion = true,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone)
        } else if (!isGlowstone && ingredient == Ingredient.glowstone) {
            return HarmingPotion(
                isSplashPotion=isSplashPotion,
                isLingeringPotion = isLingeringPotion,
                isGlowstone = true,
                isRedstone = isRedstone
            )
        }
        return null
    }

    override fun validIngredients(): List<Ingredient> {
        val validIngredients = super.validIngredients().toMutableList()
        if (!isGlowstone) {
            validIngredients.add(Ingredient.glowstone)
        }

        return validIngredients
    }
}

class PoisonPotion(
    isRedstone: Boolean = false,
    isGlowstone: Boolean = false,
    isSplashPotion: Boolean = false,
    isLingeringPotion: Boolean = false,
    override val normal_potion_icon: Int = R.drawable.potion_of_poison,
    override val splash_potion_icon: Int = R.drawable.splash_potion_of_poison,
    override val lingering_potion_icon: Int = R.drawable.lingering_potion_of_poison,
    override val effect_icon: Int = R.drawable.poison ,
    override var duration: Int=45
) : Potion(
    isRedstone = isRedstone,
    isGlowstone = isGlowstone,
    isSplashPotion = isSplashPotion,
    isLingeringPotion = isLingeringPotion
) {
    override val name = "Poison"
    override fun brew(ingredient: Ingredient): Potion? {
        if (!isSplashPotion && ingredient == Ingredient.gunpowder) {
            return PoisonPotion(
                duration = duration,
                isSplashPotion=true,
                isLingeringPotion = isLingeringPotion,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone
                )
        }
        else if (!isLingeringPotion && isSplashPotion && ingredient == Ingredient.dragons_breath) {
            return PoisonPotion(
                duration = duration,
                isSplashPotion=true,
                isLingeringPotion = true,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone)

        } else if (!isGlowstone && ingredient == Ingredient.glowstone) {
            return PoisonPotion(
                duration = 21,
                isSplashPotion=isSplashPotion,
                isLingeringPotion = isLingeringPotion,
                isGlowstone = true,
                isRedstone = isRedstone)
        } else if (!isRedstone && ingredient == Ingredient.redstone) {
            return PoisonPotion(
                duration = 90,
                isSplashPotion=isSplashPotion,
                isLingeringPotion = isLingeringPotion,
                isGlowstone = isGlowstone,
                isRedstone = true
            )
        }else
            if (ingredient == Ingredient.fermented_spider_eye && !isGlowstone) {
                return HarmingPotion(
                    isRedstone = isRedstone,
                    isGlowstone = isGlowstone,
                    isSplashPotion = isSplashPotion,
                    isLingeringPotion = isLingeringPotion
                )
            }
        return null
    }


    override fun validIngredients(): List<Ingredient> {
        val validIngredients = super.validIngredients().toMutableList()
        if (!isGlowstone && !isRedstone) {
            validIngredients.add(Ingredient.glowstone)
            validIngredients.add(Ingredient.redstone)
        }
        if (!isRedstone)
            validIngredients.add(Ingredient.fermented_spider_eye)

        return validIngredients
    }
}

class RegenerationPotion(
    isRedstone: Boolean = false,
    isGlowstone: Boolean = false,
    isSplashPotion: Boolean = false,
    isLingeringPotion: Boolean = false,
    override val normal_potion_icon: Int = R.drawable.potion_of_regeneration,
    override val splash_potion_icon: Int = R.drawable.splash_potion_of_regeneration,
    override val lingering_potion_icon: Int = R.drawable.lingering_potion_of_regeneration,
    override val effect_icon: Int = R.drawable.regeneration ,
    override var duration: Int=45
) : Potion(
    isRedstone = isRedstone,
    isGlowstone = isGlowstone,
    isSplashPotion = isSplashPotion,
    isLingeringPotion = isLingeringPotion
) {
    override val name = "Regeneration"
    override fun brew(ingredient: Ingredient): Potion? {
        if (!isSplashPotion && ingredient == Ingredient.gunpowder) {
            return RegenerationPotion(
                duration = duration,
                isSplashPotion=true,
                isLingeringPotion = isLingeringPotion,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone
                )
        }
        else if (!isLingeringPotion && isSplashPotion && ingredient == Ingredient.dragons_breath) {
            return RegenerationPotion(
                duration = duration,
                isSplashPotion=true,
                isLingeringPotion = true,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone)
        } else if (!isGlowstone && ingredient == Ingredient.glowstone) {
            return RegenerationPotion(
                duration = 22,
                isSplashPotion=isSplashPotion,
                isLingeringPotion = isLingeringPotion,
                isGlowstone = true,
                isRedstone = isRedstone)
        } else if (!isRedstone && ingredient == Ingredient.redstone) {
            return RegenerationPotion(
                duration = 90,
                isSplashPotion=isSplashPotion,
                isLingeringPotion = isLingeringPotion,
                isGlowstone = isGlowstone,
                isRedstone = true
            )
        }
        return null
    }


    override fun validIngredients(): List<Ingredient> {
        val validIngredients = super.validIngredients().toMutableList()
        if (!isGlowstone && !isRedstone) {
            validIngredients.add(Ingredient.glowstone)
            validIngredients.add(Ingredient.redstone)
        }

        return validIngredients
    }
}

class FireResistancePotion(
    isRedstone: Boolean = false,
    isGlowstone: Boolean = false,
    isSplashPotion: Boolean = false,
    isLingeringPotion: Boolean = false,
    override val normal_potion_icon: Int = R.drawable.potion_of_swiftness,
    override val splash_potion_icon: Int = R.drawable.splash_potion_of_swiftness,
    override val lingering_potion_icon: Int = R.drawable.lingering_potion_of_swiftness,
    override val effect_icon: Int = R.drawable.fire_resistance ,
    override var duration: Int=180
) : Potion(
    isRedstone = isRedstone,
    isGlowstone = isGlowstone,
    isSplashPotion = isSplashPotion,
    isLingeringPotion = isLingeringPotion
) {
    override val name = "Fire Resistance"
    override fun brew(ingredient: Ingredient): Potion? {
        if (!isSplashPotion && ingredient == Ingredient.gunpowder) {
            return FireResistancePotion(
                duration = duration,
                isSplashPotion=true,
                isLingeringPotion = isLingeringPotion,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone
                )
        }
        else if (!isLingeringPotion && isSplashPotion && ingredient == Ingredient.dragons_breath) {
            return FireResistancePotion(
                duration = duration,
                isSplashPotion=true,
                isLingeringPotion = true,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone)
        } else if (!isRedstone && ingredient == Ingredient.redstone) {
            return FireResistancePotion(
                duration = 480,
                isSplashPotion=isSplashPotion,
                isLingeringPotion = isLingeringPotion,
                isGlowstone = isGlowstone,
                isRedstone = true
            )
        }
        return null
    }


    override fun validIngredients(): List<Ingredient> {
        val validIngredients = super.validIngredients().toMutableList()
        if (!isRedstone) {
            validIngredients.add(Ingredient.redstone)
        }

        return validIngredients
    }
}

class WaterBreathingPotion(
    isRedstone: Boolean = false,
    isGlowstone: Boolean = false,
    isSplashPotion: Boolean = false,
    isLingeringPotion: Boolean = false,
    override val normal_potion_icon: Int = R.drawable.potion_of_water_breathing,
    override val splash_potion_icon: Int = R.drawable.splash_potion_of_water_breathing,
    override val lingering_potion_icon: Int = R.drawable.lingering_potion_of_water_breathing,
    override val effect_icon: Int = R.drawable.water_breathing ,
    override var duration: Int=180
) : Potion(
    isRedstone = isRedstone,
    isGlowstone = isGlowstone,
    isSplashPotion = isSplashPotion,
    isLingeringPotion = isLingeringPotion
) {
    override val name = "Water Breathing"
    override fun brew(ingredient: Ingredient): Potion? {
        if (!isSplashPotion && ingredient == Ingredient.gunpowder) {
            return WaterBreathingPotion(
                duration = duration,
                isSplashPotion=true,
                isLingeringPotion = isLingeringPotion,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone
                )
        }
        else if (!isLingeringPotion && isSplashPotion && ingredient == Ingredient.dragons_breath) {
            return WaterBreathingPotion(
                duration = duration,
                isSplashPotion=true,
                isLingeringPotion = true,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone)
        } else if (!isRedstone && ingredient == Ingredient.redstone) {
            return WaterBreathingPotion(
                duration = 480,
                isSplashPotion=isSplashPotion,
                isLingeringPotion = isLingeringPotion,
                isGlowstone = isGlowstone,
                isRedstone = true
            )
        }
        return null
    }

    override fun validIngredients(): List<Ingredient> {
        val validIngredients = super.validIngredients().toMutableList()
        if (!isRedstone) {
            validIngredients.add(Ingredient.redstone)
        }

        return validIngredients
    }
}

class NightVisionPotion(
    isRedstone: Boolean = false,
    isGlowstone: Boolean = false,
    isSplashPotion: Boolean = false,
    isLingeringPotion: Boolean = false,
    override val normal_potion_icon: Int = R.drawable.potion_of_night_vision,
    override val splash_potion_icon: Int = R.drawable.splash_potion_of_night_vision,
    override val lingering_potion_icon: Int = R.drawable.lingering_potion_of_night_vision,
    override val effect_icon: Int = R.drawable.night_vision ,
    override var duration: Int=180
) : Potion(
    isRedstone = isRedstone,
    isGlowstone = isGlowstone,
    isSplashPotion = isSplashPotion,
    isLingeringPotion = isLingeringPotion
) {
    override val name = "Night Vision"
    override fun brew(ingredient: Ingredient): Potion? {
        if (!isSplashPotion && ingredient == Ingredient.gunpowder) {
            return NightVisionPotion(
                duration = duration,
                isSplashPotion=true,
                isLingeringPotion = isLingeringPotion,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone
                )
        }
        else if (!isLingeringPotion && isSplashPotion && ingredient == Ingredient.dragons_breath) {
            return NightVisionPotion(
                duration = duration,
                isSplashPotion=true,
                isLingeringPotion = true,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone)
        } else if (!isRedstone && ingredient == Ingredient.redstone) {
            return NightVisionPotion(
                duration = 480,
                isSplashPotion=isSplashPotion,
                isLingeringPotion = isLingeringPotion,
                isGlowstone = isGlowstone,
                isRedstone = true)
        }else
            if (ingredient == Ingredient.fermented_spider_eye && !isGlowstone) {
                return InvisibilityPotion(
                    isRedstone = isRedstone,
                    isGlowstone = isGlowstone,
                    isSplashPotion = isSplashPotion,
                    isLingeringPotion = isLingeringPotion
                )
            }
        return null
    }


    override fun validIngredients(): List<Ingredient> {
        val validIngredients = super.validIngredients().toMutableList()
        if (!isRedstone) {
            validIngredients.add(Ingredient.redstone)
        }
        if (!isGlowstone)
            validIngredients.add(Ingredient.fermented_spider_eye)

        return validIngredients
    }
}

class InvisibilityPotion(
    isRedstone: Boolean = false,
    isGlowstone: Boolean = false,
    isSplashPotion: Boolean = false,
    isLingeringPotion: Boolean = false,
    override val normal_potion_icon: Int = R.drawable.potion_of_invisibility,
    override val splash_potion_icon: Int = R.drawable.splash_potion_of_invisibility,
    override val lingering_potion_icon: Int = R.drawable.lingering_potion_of_invisibility,
    override val effect_icon: Int = R.drawable.invisibility ,
    override var duration: Int=180
) : Potion(
    isRedstone = isRedstone,
    isGlowstone = isGlowstone,
    isSplashPotion = isSplashPotion,
    isLingeringPotion = isLingeringPotion
) {
    override val name = "Invisibility"
    override fun brew(ingredient: Ingredient): Potion? {
        if (!isSplashPotion && ingredient == Ingredient.gunpowder) {
            return InvisibilityPotion(
                duration = duration,
                isSplashPotion=true,
                isLingeringPotion = isLingeringPotion,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone
                )
        }
        else if (!isLingeringPotion && isSplashPotion && ingredient == Ingredient.dragons_breath) {
            return InvisibilityPotion(
                duration = duration,
                isSplashPotion=true,
                isLingeringPotion = true,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone)
        } else if (!isRedstone && ingredient == Ingredient.redstone) {
            return InvisibilityPotion(
                duration = 480,
                isSplashPotion=isSplashPotion,
                isLingeringPotion = isLingeringPotion,
                isGlowstone = isGlowstone,
                isRedstone = true
            )
        }
        return null
    }


    override fun validIngredients(): List<Ingredient> {
        val validIngredients = super.validIngredients().toMutableList()
        if (!isRedstone) {
            validIngredients.add(Ingredient.redstone)
        }

        return validIngredients
    }
}

class TurtleMasterPotion(
    isRedstone: Boolean = false,
    isGlowstone: Boolean = false,
    isSplashPotion: Boolean = false,
    isLingeringPotion: Boolean = false,
    override val normal_potion_icon: Int = R.drawable.potion_of_the_turtle_master,
    override val splash_potion_icon: Int = R.drawable.splash_potion_of_the_turtle_master,
    override val lingering_potion_icon: Int = R.drawable.lingering_potion_of_the_turtle_master,
    override val effect_icon: Int = R.drawable.resistance ,
    override var duration: Int=20
) : Potion(
    isRedstone = isRedstone,
    isGlowstone = isGlowstone,
    isSplashPotion = isSplashPotion,
    isLingeringPotion = isLingeringPotion
) {
    override val name = "Turtle Master"
    override fun brew(ingredient: Ingredient): Potion? {
        if (!isSplashPotion && ingredient == Ingredient.gunpowder) {
            return TurtleMasterPotion(
                duration = duration,
                isSplashPotion=true,
                isLingeringPotion = isLingeringPotion,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone
                )
        }
        else if (!isLingeringPotion && isSplashPotion && ingredient == Ingredient.dragons_breath) {
            return TurtleMasterPotion(
                duration = duration,
                isSplashPotion=true,
                isLingeringPotion = true,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone)
        } else if (!isGlowstone && ingredient == Ingredient.glowstone) {
            return TurtleMasterPotion(
                duration = 20,
                isSplashPotion=isSplashPotion,
                isLingeringPotion = isLingeringPotion,
                isGlowstone = true,
                isRedstone = isRedstone
            )
        } else if (!isRedstone && ingredient == Ingredient.redstone) {
            return TurtleMasterPotion(
                duration = 40,
                isSplashPotion=isSplashPotion,
                isLingeringPotion = isLingeringPotion,
                isGlowstone = isGlowstone,
                isRedstone = true
            )
        }
        return null
    }


    override fun validIngredients(): List<Ingredient> {
        val validIngredients = super.validIngredients().toMutableList()
        if (!isGlowstone && !isRedstone) {
            validIngredients.add(Ingredient.glowstone)
            validIngredients.add(Ingredient.redstone)
        }

        return validIngredients
    }
}

class SlowFallingPotion(
    isRedstone: Boolean = false,
    isGlowstone: Boolean = false,
    isSplashPotion: Boolean = false,
    isLingeringPotion: Boolean = false,
    override val normal_potion_icon: Int = R.drawable.potion_of_slow_falling,
    override val splash_potion_icon: Int = R.drawable.splash_potion_of_slow_falling,
    override val lingering_potion_icon: Int = R.drawable.lingering_potion_of_slow_falling,
    override val effect_icon: Int = R.drawable.slow_falling ,
    override var duration: Int=90
) : Potion(
    isRedstone = isRedstone,
    isGlowstone = isGlowstone,
    isSplashPotion = isSplashPotion,
    isLingeringPotion = isLingeringPotion
) {
    override val name = "Slow Falling"
    override fun brew(ingredient: Ingredient): Potion? {
        if (!isSplashPotion && ingredient == Ingredient.gunpowder) {
            return SlowFallingPotion(
                duration = duration,
                isSplashPotion=true,
                isLingeringPotion = isLingeringPotion,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone
                )
        }
        else if (!isLingeringPotion && isSplashPotion && ingredient == Ingredient.dragons_breath) {
            return SlowFallingPotion(
                duration = duration,
                isSplashPotion=true,
                isLingeringPotion = true,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone)
        } else if (!isRedstone && ingredient == Ingredient.redstone) {
           return SlowFallingPotion(
               duration = 240,
               isSplashPotion=isSplashPotion,
               isLingeringPotion = isLingeringPotion,
               isGlowstone = isGlowstone,
               isRedstone = true
           )
        }
        return null
    }

    override fun validIngredients(): List<Ingredient> {
        val validIngredients = super.validIngredients().toMutableList()
        if (!isRedstone) {
            validIngredients.add(Ingredient.redstone)
        }

        return validIngredients
    }
}

class WeaknessPotion(
    isRedstone: Boolean = false,
    isGlowstone: Boolean = false,
    isSplashPotion: Boolean = false,
    isLingeringPotion: Boolean = false,
    override val normal_potion_icon: Int = R.drawable.potion_of_weakness,
    override val splash_potion_icon: Int = R.drawable.splash_potion_of_weakness,
    override val lingering_potion_icon: Int = R.drawable.lingering_potion_of_weakness,
    override val effect_icon: Int = R.drawable.weakness ,
    override var duration: Int=90
) : Potion(
    isRedstone = isRedstone,
    isGlowstone = isGlowstone,
    isSplashPotion = isSplashPotion,
    isLingeringPotion = isLingeringPotion
) {
    override val name = "Weakness"
    override fun brew(ingredient: Ingredient): Potion? {
        if (!isSplashPotion && ingredient == Ingredient.gunpowder) {
            return WeaknessPotion(
                duration = duration,
                isSplashPotion=true,
                isLingeringPotion = isLingeringPotion,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone)
        }
        else if (!isLingeringPotion && isSplashPotion && ingredient == Ingredient.dragons_breath) {
            return WeaknessPotion(
                duration = duration,
                isSplashPotion=true,
                isLingeringPotion = true,
                isRedstone = isRedstone,
                isGlowstone = isGlowstone)
        } else if (!isRedstone && ingredient == Ingredient.redstone) {
            return WeaknessPotion(
                duration = 240,
                isSplashPotion=isSplashPotion,
                isLingeringPotion = isLingeringPotion,
                isGlowstone = isGlowstone,
                isRedstone = true
            )
        }
        return null
    }
    override fun validIngredients(): List<Ingredient> {
        val validIngredients = super.validIngredients().toMutableList()
        if (!isRedstone) {
            validIngredients.add(Ingredient.redstone)
        }

        return validIngredients
    }
}

class AwkwardPotion(
    isSplashPotion: Boolean = false,
    isLingeringPotion: Boolean = false,
    override val normal_potion_icon: Int = R.drawable.water_bottle,
    override val splash_potion_icon: Int = R.drawable.splash_potion,
    override val lingering_potion_icon: Int = R.drawable.lingering_potion,
    override val effect_icon: Int = 0,
    override var duration: Int=0
) : Potion(
    isSplashPotion = isSplashPotion,
    isLingeringPotion = isLingeringPotion
) {
    override val name = "Awkward"
    override fun brew(ingredient: Ingredient): Potion? {
        if (!isSplashPotion && ingredient == Ingredient.gunpowder) {
            return AwkwardPotion(
                        isSplashPotion=true,
                        isLingeringPotion = isLingeringPotion,
                        )
        }
        else if (!isLingeringPotion && isSplashPotion && ingredient == Ingredient.dragons_breath) {
            return AwkwardPotion(
                isSplashPotion=true,
                isLingeringPotion = true)

        } else if (ingredient == Ingredient.sugar){
            return SwiftnessPotion(isLingeringPotion = isLingeringPotion, isSplashPotion = isSplashPotion)
        } else if (ingredient == Ingredient.rabbit_foot){
            return LeapingPotion(isLingeringPotion = isLingeringPotion, isSplashPotion = isSplashPotion)
        } else if (ingredient == Ingredient.blaze_powder){
            return StrengthPotion(isLingeringPotion = isLingeringPotion, isSplashPotion = isSplashPotion)
        } else if (ingredient == Ingredient.glistering_melon){
            return HealingPotion(isLingeringPotion = isLingeringPotion, isSplashPotion = isSplashPotion)
        } else if (ingredient == Ingredient.spider_eye){
            return PoisonPotion(isLingeringPotion = isLingeringPotion, isSplashPotion = isSplashPotion)
        } else if (ingredient == Ingredient.ghast_tear){
            return RegenerationPotion(isLingeringPotion = isLingeringPotion, isSplashPotion = isSplashPotion)
        } else if (ingredient == Ingredient.magma_cream){
            return FireResistancePotion(isLingeringPotion = isLingeringPotion, isSplashPotion = isSplashPotion)
        } else if (ingredient == Ingredient.pufferfish){
            return WaterBreathingPotion(isLingeringPotion = isLingeringPotion, isSplashPotion = isSplashPotion)
        } else if (ingredient == Ingredient.golden_carrot){
            return NightVisionPotion(isLingeringPotion = isLingeringPotion, isSplashPotion = isSplashPotion)
        } else if (ingredient == Ingredient.turtle_shell){
            return TurtleMasterPotion(isLingeringPotion = isLingeringPotion, isSplashPotion = isSplashPotion)
        } else if (ingredient == Ingredient.phantom_membrane){
            return SlowFallingPotion(isLingeringPotion = isLingeringPotion, isSplashPotion = isSplashPotion)
        } else if (ingredient == Ingredient.fermented_spider_eye){
            return WeaknessPotion(isLingeringPotion = isLingeringPotion, isSplashPotion = isSplashPotion)
        }
        return null
    }


    override fun validIngredients(): List<Ingredient> {
        val validIngredients = super.validIngredients().toMutableList()
        validIngredients.add(Ingredient.sugar)
        validIngredients.add(Ingredient.rabbit_foot)
        validIngredients.add(Ingredient.blaze_powder)
        validIngredients.add(Ingredient.glistering_melon)
        validIngredients.add(Ingredient.spider_eye)
        validIngredients.add(Ingredient.ghast_tear)
        validIngredients.add(Ingredient.magma_cream)
        validIngredients.add(Ingredient.pufferfish)
        validIngredients.add(Ingredient.golden_carrot)
        validIngredients.add(Ingredient.turtle_shell)
        validIngredients.add(Ingredient.phantom_membrane)
        validIngredients.add(Ingredient.fermented_spider_eye)

        return validIngredients
    }
}

class PotionsManager {
    val potion_history = mutableListOf<Potion>(AwkwardPotion())

    fun brew(ingredient: Ingredient) : Potion{
        val current_potion = getCurrentPotion()
        val new_potion = current_potion.brew(ingredient)
        if (new_potion !== null){
            potion_history.add(new_potion)
            println("Added to history: ${new_potion.getPotionName()}")
            printHistory()
            return getCurrentPotion()
        } else {
            return current_potion
        }
    }

    fun getCurrentPotion () : Potion {
        return potion_history.last()
    }

    fun clearPotion() : Potion{
        potion_history.clear()
        potion_history.add(AwkwardPotion())
        return getCurrentPotion()
    }

    fun undo() : Potion{
        if (potion_history.size > 1){
            potion_history.removeAt(potion_history.lastIndex)
        }
        return getCurrentPotion()
    }
    fun printHistory() {
        potion_history.forEach { potion ->
            println("-----${potion.getPotionName()}-----\n" +
                    "Duration : ${potion.duration}\n" +
                    "isSplashPotion: ${potion.isSplashPotion}\n" +
                    "isLingeringPotion: ${potion.isLingeringPotion}\n" +
                    "isGlowstone: ${potion.isGlowstone}\n" +
                    "isRedstone: ${potion.isRedstone}")
        }
    }
}

